package com.lightdemo.sync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Test extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appid = "10802";
	private String appSecret = "20160813";
	
	@Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
		ServletContext serv = getServletContext(); 
		System.out.println("获取ticket:"+req.getQueryString());
		String url = "http://msg.xmqinggong.cn/openauth2/api/token?grant_type=client_credential&appid="
		+appid+"&secret="+appSecret;
		String result = sendGet(url);
		resp.getWriter().println(result);
	}
	
	public static String sendGet(String url){
		String result = null;
		BufferedReader in = null;
		URLConnection connection = null;
		try {			
			URL realUrl = new URL(url);
			connection = realUrl.openConnection();
			connection.setRequestProperty("Access-Control-Allow-Origin", "*/*");
			connection.setRequestProperty("Cache-Control", "no-cache");
	        connection.setRequestProperty("Connection", "keep-alive");
	        connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");

	        connection.connect();
	        in = new BufferedReader(new InputStreamReader(
	                connection.getInputStream()));            
	        String line;
	        while ((line = in.readLine()) != null) {
	            result += line;
	        }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		/*	try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		return result;
	}
	
}
