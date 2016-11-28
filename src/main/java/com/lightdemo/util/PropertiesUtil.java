package com.lightdemo.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightdemo.model.Common;

import net.sf.json.JSONObject;
/**
 * 读取resources下的资源文件的配置类
 * @author cjqbrave@163.com
 *
 */
public class PropertiesUtil {
    private PropertiesUtil() {
    }
    public static Properties loadProperties(String file) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
        	String url = PropertiesUtil.class.getResource("/").getPath();
        	is = new FileInputStream(url + "/" + file);
            properties.load(is);
        } catch (Exception ignore) {
            if (ignore instanceof FileNotFoundException) {
                System.out.println(ignore.getMessage());
                properties = null;
            } else {
            	System.out.println(ignore.getMessage()+ignore);
            }
            ignore.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        return properties;
    }
    
    public static Object loadCommonProperties(String file, Class cla){
    	Properties properties = loadProperties(file);
    	Set<Entry<Object, Object>> set = properties.entrySet();
    	JSONObject json = new JSONObject();
    	for(Entry<Object, Object> temp : set) {
    		String key = (String) temp.getKey();
    		String value = (String) temp.getValue();
    		json.put(key, value.trim());
    	}
    	return JSONObject.toBean(json, cla);
    }
    public static void main(String[] args) {
    	Common cmtemp = (Common) loadCommonProperties("common.properties", Common.class);
    	System.out.println(cmtemp.getEID());
	}
}
