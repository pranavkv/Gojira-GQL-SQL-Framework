package com.gojira.gql.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.gojira.gql.exception.AppConfigNoKeyFoundException;

public class AppConfig {


	static PropertiesConfiguration config = null;

	public static void loadFile(String fileName) throws ConfigurationException {

		config = new PropertiesConfiguration(fileName);

	}

	public static String getValue(String key) throws AppConfigNoKeyFoundException {

		String s = config.getString(key);
		if (null == s)
			throw new AppConfigNoKeyFoundException(String.format("'%s' key not found in AppConfig", key));

		return s;
	}

	public static String optValue(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}

	public static String getTicker(String symbol) throws AppConfigNoKeyFoundException
	{
		String s="";
		return s;
	}
	
	public static PropertiesConfiguration getProperties() {
		return config;
	}

	public static boolean hasKey(String key) {

		return config.containsKey(key);
	}
}
