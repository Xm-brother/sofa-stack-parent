package com.xiaoma.sofa.boot.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存工具类。
 * 
 * @author Administrator
 * 
 */
public class CacheUtils {
	private static Logger log = LoggerFactory.getLogger(CacheUtils.class);

	/** 响应正常 */
	public static final String OK = "0000";
	/** 参数错误 */
	public static final String PARAMERROR = "1001";

	/** 使用的库 */
	private static final Integer db_index = EmallPropUtils.getInteger("redis.db.index", 1);;
	/** 缓存默认有效期 */
	private static final Integer expireTime = 1800;// 30*60半个小时。

	/** 记录会员的一些扩展供特殊情况下使用。 */
	private static final String cacheList = "cachelist:";
	/** 暂时存放会员登录后的信息用。 */
	private static final String cacheEntity = "cacheentity:";
	
	/**是否支持切换数据库，如果为TRUE，则可以使用自定义的数据库，如果为FALSE,只能使用配置文件中指定的数据库*/
	private static Boolean SWITCH_DATABASE = EmallPropUtils.getBoolean("redis.switch.database", true);

	/**
	 * 保存一个list
	 * 
	 * @param key
	 *            信息存放的key，必须全局唯一！
	 * @param value
	 *            要存放的值，参数为范型。
	 * @param exprice
	 *            信息有效期，单位秒。如果传入负数代表永不失效，为空时使用默认值。
	 * @return
	 */
	public static <E> String updateList(String key, List<E> value, Integer exprice) {
		if (StringUtils.isBlank(key) || value == null) {
			log.error("传入的信息有误，保存的键【{}】和值【{}】都不能为空！", key, value);
			return PARAMERROR;
		}

		Jedis jedis = null;
		try {
			jedis = JedisFactory.getJedis();
			//允许使用自定义的数据库
			if(SWITCH_DATABASE.booleanValue())
				jedis.select(db_index);

			// 保存数据
			jedis.set(cacheList + key, JSON.toJSONString(value));

			// 设置有效期
			if (exprice == null) {
				jedis.expire(cacheList + key, expireTime);
			}else{
				if (exprice > 0) {
					jedis.expire(cacheList + key, exprice);
				}
			}
			return OK;
		} catch (Exception e) {
			log.error("updateList[key,value,exprice]" + e.getMessage(), e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 获取指定key对应的list。
	 * 
	 * @param key
	 * @param objClass
	 *            要转换的类
	 * @return
	 */
	public static <E> List<E> getList(String key, Class<E> objClass) {
		if (StringUtils.isBlank(key)) {
			log.error("传入的key[{}]有误", key);
			return null;
		}

		Jedis jedis = null;
		try {
			jedis = JedisFactory.getJedis();
			//允许使用自定义的数据库
			if(SWITCH_DATABASE.booleanValue())
				jedis.select(db_index);

			// 保存数据
			String jsonstr = jedis.get(cacheList + key);
			if (StringUtils.isBlank(jsonstr)) {
				return null;
			}

			// 转换结果并返回。
			List<E> list = JSON.parseArray(jsonstr, objClass);
			if (list == null) {
				list = new ArrayList<E>();
			}

			return list;
		} catch (Exception e) {
			log.error("getList[key,objClass]" + e.getMessage(), e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 存放缓存信息，注意有效期默认30分钟！
	 * 
	 * @param key
	 * @param value
	 * @param exprice
	 *            信息有效期，单位秒。如果传入负数代表永不失效，为空时使用默认值。
	 * @return
	 */
	public static <E> String updateEntity(String key, E value, Integer exprice) {
		if (StringUtils.isBlank(key) || value == null) {
			log.error("传入的信息有误，保存的键【{}】和值【{}】都不能为空！", key, value);
			return PARAMERROR;
		}

		Jedis jedis = null;
		try {
			jedis = JedisFactory.getJedis();
			//允许使用自定义的数据库
			if(SWITCH_DATABASE.booleanValue())
				jedis.select(db_index);

			// 保存数据
			jedis.set(cacheEntity + key, JSON.toJSONString(value));

			// 设置有效期
			if (exprice == null) {
				jedis.expire(cacheEntity + key, expireTime);
			}
			if (exprice > 0) {
				jedis.expire(cacheEntity + key, exprice);
			}

			return OK;
		} catch (Exception e) {
			log.error("updateEntity[key,value,exprice]" + e.getMessage(), e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 从redis中获取一个值，每次获取都会刷新有效时间。
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <E> E getEntity(String key, Class<E> clazz) {
		if (StringUtils.isBlank(key) || clazz == null) {
			log.error("传入的信息有误，键[{}]不能为空且类也不能为空！", key);
			return null;
		}

		Jedis jedis = null;
		try {
			jedis = JedisFactory.getJedis();
			jedis.select(db_index);

			// 保存数据
			String jsonstr = jedis.get(cacheEntity + key);
			if (StringUtils.isBlank(jsonstr)) {
				return null;
			}

			// 转换结果并返回。
			return JSON.parseObject(jsonstr, clazz);
		} catch (Exception e) {
			log.error("getEntity[key,clazz]" + e.getMessage(), e);
			return null;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}

	/**
	 * 删除单个key。
	 * 
	 * @param type
	 *            要删除的key的类型：list对应list，entity对应entity。
	 * @param key
	 *            要删除的key。
	 */
	public static boolean delKey(String type, String key) {
		if (StringUtils.isBlank(key)) {
			log.error("传入的信息有误，键【{}】不能为空！", key);
			return false;
		}

		Jedis jedis = null;
		try {
			jedis = JedisFactory.getJedis();
			//允许使用自定义的数据库
			if(SWITCH_DATABASE.booleanValue())
				jedis.select(db_index);

			if ("list".equals(type)) {
				jedis.del(cacheList + key);
			} else if ("entity".equals(type)) {
				jedis.del(cacheEntity + key);
			} else {
				log.error("未传入要删除的key的类型！");
				return false;
			}

			return true;
		} catch (Exception e) {
			log.error("delKey[key]" + e.getMessage(), e);
			return false;
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
