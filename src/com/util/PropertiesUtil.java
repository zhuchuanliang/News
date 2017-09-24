package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static String getValue(String key) throws Exception{
		Properties prop=new Properties();
		InputStream in=new PropertiesUtil().getClass().getResourceAsStream("/news.properties");
		prop.load(in);
		return prop.getProperty(key);
	}

}
