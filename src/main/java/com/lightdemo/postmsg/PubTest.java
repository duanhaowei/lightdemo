package com.lightdemo.postmsg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
/**
 * 临时测试类 
 * @author cjqbrave@163.com
 **/
public class PubTest {
	private final static String COOKIE = "name=13560753221; token=3c45c394-973d-4aa2-8f14-9ea56d1eb083";
	private static String post(String url,List <NameValuePair> params){
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String responseBody = null;
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		try {
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));  
			responseBody = httpClient.execute(post,responseHandler);
			System.out.println(responseBody);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();  
		}
		return responseBody;
	}
	
	 public static String sendHttpRequestStr(String url, String requestMethod, String param) {
	    	//BenxLog.info(HttpUtil.class, "urlString:" + url + param);
	    	String result = "";
	    	BufferedReader in = null;
	    	try {
	    		
	    		
	    		String urlNameString = (url).replace("\"", "%22").replace("{", "%7b").replace("}", "%7d");
	    		URL realUrl = new URL(urlNameString);
	    		
	    		//<editor-fold desc="打开和URL之间的连接">
	    		HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
	    		connection.setConnectTimeout(3000);
	    		connection.setReadTimeout(3000);
	    		//</editor-fold>
	    		//<editor-fold desc="设置通用的请求属性">
	    		connection.setRequestProperty("accept", "*/*");
	    		connection.setRequestProperty("connection", "Keep-Alive");
	    		connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//	    		connection.setRequestProperty("Cookie", COOKIE);
	    		//</editor-fold>
	    		//<editor-fold desc="发送POST请求必须设置如下两行">
	    		connection.setDoOutput(true);
	    		connection.setDoInput(true);
	    		//</editor-fold>
	    		
	    		connection.setRequestMethod(requestMethod.toUpperCase());
	    		if (null != param) {
	    			OutputStream outputStream = connection.getOutputStream();
	    			outputStream
	    			.write(param.getBytes("UTF-8"));//replace("\"", "%22").replace("{", "%7b").replace("}", "%7d")
	    			outputStream.close();
	    		}
	    		// 建立实际的连接
	    		connection.connect();
	    		// 获取所有响应头字段
	    		Map<String, List<String>> map = connection.getHeaderFields();
	    		// 遍历所有的响应头字段
	    		for (String key : map.keySet()) {
	    			System.out.println(key + "--->" + map.get(key));
	    		}
	    		// 定义 BufferedReader输入流来读取URL的响应
	    		in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
	    		String line;
	    		while ((line = in.readLine()) != null) {
	    			result += line;
	    		}
	    		if (result.startsWith("["))
	    			result = "{data:" + result + "}";
	    		System.out.println(result);
	    	} catch (Exception e) {
	    		System.out.println("发送" + requestMethod + "请求出现异常！" + e);
	    		//BenxLog.info(HttpUtil.class,"请求超时");
	    		e.printStackTrace();
	    	}
	    	// 使用finally块来关闭输入流
	    	finally {
	    		try {
	    			if (in != null) {
	    				in.close();
	    			}
	    		} catch (Exception e2) {
	    			e2.printStackTrace();
	    		}
	    	}
	    	//BenxLog.info(HttpUtil.class, "result:" + result);
	    	return result;
	    }
	
}
