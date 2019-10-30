package com.xiaoma.sofa.boot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmallPropUtils {
	private static Logger log = LoggerFactory.getLogger(EmallPropUtils.class);

	private static class SingletonHolder {
		private static final Properties prop = getProp();
	}

	private EmallPropUtils() {
	}

	/**
	 * 获取配置中string值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(String key, String defaultValue) {
		if (SingletonHolder.prop == null) {
			return defaultValue;
		}
		return SingletonHolder.prop.getProperty(key, defaultValue);
	}

	/**
	 * 获取Integer配置值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Integer getInteger(String key, Integer defaultValue) {
		if (SingletonHolder.prop == null) {
			return defaultValue;
		}
		String value = SingletonHolder.prop.getProperty(key, defaultValue + "");
		if (value == null) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 获取Boolean配置值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Boolean getBoolean(String key, Boolean defaultValue) {
		if (SingletonHolder.prop == null) {
			return defaultValue;
		}
		String value = SingletonHolder.prop.getProperty(key, defaultValue + "");
		if (value == null) {
			return defaultValue;
		}
		return Boolean.parseBoolean(value);
	}

	/**
	 * 获取配置文件。
	 * 
	 * @return
	 */
	private static Properties getProp() {
		try {
			Properties prop = new Properties();
			InputStream stream = JedisFactory.class.getResourceAsStream("/emall.properties");
			prop.load(stream);
			return prop;
		} catch (IOException e) {
			log.error("===================================================================================");
			log.error("===================================================================================");
			log.error("！！！！！！！！！未找到配置文件“emall.properties”，使用默认值！！！！！！！！！！");
			log.error("===================================================================================");
			log.error("===================================================================================");
			return null;
		}
	}

}
