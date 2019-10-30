package com.xiaoma.sofa.boot.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class JedisFactory {
	private static Logger log = LoggerFactory.getLogger(JedisFactory.class);

	//查看这篇文章可以知道为嘛会把单例模式弄的这么复杂：
	//http://coolshell.cn/articles/265.html
	
	private static class SingletonHolder{
		//高可用方式的redis
		private static final JedisSentinelPool sentinelPool = getSentinelInstance();
		//单节点方式的jedis连接池
		private static final JedisPool pool = getInstance();
	}
	
	private JedisFactory() {}
	
	/**
	 * 获取redis连接，如果配置文件不存在，则会取默认值。
	 * @return
	 */
	public static Jedis getJedis(){
		if(SingletonHolder.sentinelPool != null){
			return SingletonHolder.sentinelPool.getResource();
		}else{
			return SingletonHolder.pool.getResource();
		}
	}

	/**
	 * 高可用模式初始化
	 */
	private static JedisSentinelPool getSentinelInstance() {
		String model = EmallPropUtils.getString("redis.model", "sentinel");
		if(!"sentinel".equals(model)){
			return null;
		}
		
		String masterName = "redis_6379";
		String sentinels = "192.168.1.205:26379,192.168.1.205:26380,192.168.1.205:26381";
		
		Integer maxTotal = 250;
		Integer maxIdle = 25;
		Integer minIdle = 0;
		Integer maxWaitMillis = 2000;
		Boolean testOnBorrow = true;
		Boolean testOnReturn = true;
		Integer database = 10;

		masterName = EmallPropUtils.getString("spring.redis.sentinel.master", masterName);
		sentinels = EmallPropUtils.getString("spring.redis.sentinel.nodes", sentinels);
		database = Integer.valueOf(EmallPropUtils.getString("spring.redis.sentinel.database", "10")); //默认使用11号数据库
		
		maxTotal = EmallPropUtils.getInteger("redis.maxTotal", maxTotal);
		maxIdle = EmallPropUtils.getInteger("redis.maxIdle", maxIdle);
		minIdle = EmallPropUtils.getInteger("redis.minIdle", minIdle);
		maxWaitMillis = EmallPropUtils.getInteger("redis.maxWaitMillis", maxWaitMillis);
		testOnBorrow = EmallPropUtils.getBoolean("redis.testOnBorrow", testOnBorrow);
		testOnReturn = EmallPropUtils.getBoolean("redis.testOnReturn", testOnReturn);
		
		//设置哨兵的位置
		Set<String> sentinelsSet = new HashSet<String>();
		String[] sentinelsArray = sentinels.split(",");
		for (String str : sentinelsArray) {
			if(StringUtils.isNotBlank(str)){
				sentinelsSet.add(str.trim());
			}
		}
		
		//设置连接池参数
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		poolConfig.setTestOnBorrow(testOnBorrow);
		poolConfig.setTestOnReturn(testOnReturn);
		
		log.info("使用高可用方式初始化redis连接成功！master=["+masterName+"],sentinel=["+sentinels+"],database=["+database+"]");
		
		//建立连接池
		return new JedisSentinelPool(masterName, sentinelsSet, poolConfig,2000,null,database);
	}
	
	/**
	 * 单机模式初始化
	 */
	private static JedisPool getInstance() {
		String model = EmallPropUtils.getString("redis.model", "sentinel");
		if("sentinel".equals(model)){
			return null;
		}
		
		String redisIp = "172.32.5.110";
		String redisPort = "6379";
		String password = "redis";
		
		Integer maxTotal = 250;
		Integer maxIdle = 25;
		Integer minIdle = 0;
		Integer maxWaitMillis = 2000;
		Boolean testOnBorrow = true;
		Boolean testOnReturn = true;

		redisIp = EmallPropUtils.getString("redis.hostName", redisIp);
		redisPort = EmallPropUtils.getString("redis.port", redisPort);
		password = EmallPropUtils.getString("redis.password", password);
		
		maxTotal = EmallPropUtils.getInteger("redis.maxTotal", maxTotal);
		maxIdle = EmallPropUtils.getInteger("redis.maxIdle", maxIdle);
		minIdle = EmallPropUtils.getInteger("redis.minIdle", minIdle);
		maxWaitMillis = EmallPropUtils.getInteger("redis.maxWaitMillis", maxWaitMillis);
		testOnBorrow = EmallPropUtils.getBoolean("redis.testOnBorrow", testOnBorrow);
		testOnReturn = EmallPropUtils.getBoolean("redis.testOnReturn", testOnReturn);
		
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);
		
		log.info("使用单节点方式初始化redis连接成功！");

		return new JedisPool(config, redisIp, Integer.parseInt(redisPort),Protocol.DEFAULT_TIMEOUT,password);
	}	
}
