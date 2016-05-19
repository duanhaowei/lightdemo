package com.lightdemo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 公共类封装有http请求等。
 * @author cjqbrave@163.com
 *
 */
public class Utils {
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	static int HTTP_OK = 200;
	/**
	 * post方法
	 * @param url 地址
	 * @param params 参数
	 * @return
	 */
	public static String sendPost(String url, List<NameValuePair> params){
		HttpUriRequest httpPost = null;
		try {
			if(null != params) {
				httpPost = RequestBuilder
						.post()
						.addHeader("Content-Type",
								ContentType.APPLICATION_FORM_URLENCODED.toString())
						.setEntity(new UrlEncodedFormEntity(params,"UTF-8"))
						.setUri(url).build();
			} else {
				httpPost = RequestBuilder
						.post()
						.addHeader("Content-Type",
								ContentType.APPLICATION_FORM_URLENCODED.toString())
						.setUri(url).build();
			}
			
		} catch (UnsupportedEncodingException e) {
			logger.error("send http requset faile",e.getMessage());
		}
		return sendHttpRequest(httpPost);
	}
	
	
	public static String sendHttpRequest(HttpUriRequest httpRequest) {
		HttpClient httpClient = HttpClients.createDefault();
		try {
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			if (isRequestSuccessful(httpResponse)) {
				HttpEntity httpEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(httpEntity, "utf-8");
				logger.info("http response", response); 
				return response;
			}
		} catch (ClientProtocolException e) {
			logger.error("send http requset faile,uri={}, exception={}",
					httpRequest.getURI(), e);
			return e.getMessage();
		} catch (IOException e) {
			logger.error("send http requset faile,uri={}, exception={}",
					httpRequest.getURI(), e);
			return e.getMessage();
		}
		return null;
	}

	private static boolean isRequestSuccessful(HttpResponse httpResponse) {
		logger.info("response code：{}", httpResponse.getStatusLine().getStatusCode());
		return httpResponse.getStatusLine().getStatusCode() == HTTP_OK;
	}
	
	public static String sendPostO(String urlstr, String params) {
		StringBuffer result = new StringBuffer();
		try {
			HttpURLConnection connection = null;
			URL url = new URL(urlstr);
			connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(6000);
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(
			connection.getOutputStream(), "UTF-8");
			out.append(params);
			out.flush();
			out.close();
			int HttpResult = connection.getResponseCode();
			if(HttpResult == 200) {
				BufferedReader br = new BufferedReader(
				new InputStreamReader(connection.getInputStream(),"utf-8"));
				String line = null;
				while((line = br.readLine()) != null) {
					result.append(line + "\n");
				}
				br.close();
			} else {
				System.out.println("found error and error code is  " +  HttpResult);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	/**
	 * sha 加密
	 * @param data 加密数据字符串数组
	 * @return
	 */
	public static String sha(String[] data) {
		Arrays.sort(data);// 
		return DigestUtils.shaHex(StringUtils.join(data));// 
	}
}
