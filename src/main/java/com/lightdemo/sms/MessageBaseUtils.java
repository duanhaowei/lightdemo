package com.lightdemo.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageBaseUtils {	
	
	private static Logger logger = LoggerFactory.getLogger(MessageBaseUtils.class);
	
	private static ExecutorService service = Executors.newFixedThreadPool(2);
	
	private String url = "http://lightdemo.lightdemo.net";
	private String name = "";
	private String password = "";
	private String gsend = "/send/g70send.aspx";
	
	

	/**维纳多短信平台
	 * 一条消息发送给多个电话号码
	 * param phones:多个手机号码，支持移动联通电信，手机号码之间用“,”分割
	 */
	public  String sendSMS(String phones,String msg) throws Exception{
		
		String URL = url + gsend;
		
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("name",name);
		params.put("pwd",password);
		params.put("dst",phones);
		params.put("msg",msg);	
		logger.info("sendSMS:"+"name:"+name+" phone:"+phones+" url:"+URL);
		String rtn = readContentFromGet(params,URL,"GBK");
		//String rtn = HttpHelper.get(params, URL, "GBK");
		logger.info(rtn);
		return rtn;
	}
	
	/**维纳多短信平台
	 * 批量发送多条消息给多个电话号码
	 * key值定义：
	 * phone:单个手机号码
	 * msg:具体消息
	 * @throws Exception 
	 */
	public  void sendSMS(List<Map<String,String>> list){
		
		for(final Map<String,String> map : list){
			
			try{
				Callable<Object> call = new Callable<Object>(){
					
					public Object call() throws Exception {
						
						String phone = map.get("phone");
						String msg = map.get("msg");
						sendSMS(phone, msg);
						return null;
					}
				};
				service.submit(call);
			}catch(Exception e){
				logger.error(e.getMessage()+"/{}/{}",map.get("phone"),map.get("msg"));
			}
		}
	}
	
	 public  String readContentFromGet(Map<String,String> params,String url,String charset){
	        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
	        String getURL = getURL(params,url,charset);
	        
	        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
	        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
	        HttpURLConnection connection = null;
			try {
				URL getUrl = new URL(getURL);
				connection = (HttpURLConnection) getUrl
				        .openConnection();
				connection.connect();
			} catch (IOException e1) {
				logger.error("connect fail!");
			}
	        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
	        // 服务器
	        
	        BufferedReader reader = null;
	        try{
		        // 取得输入流，并使用Reader读取
	        	reader = new BufferedReader(new InputStreamReader(
		                connection.getInputStream(),Charset.forName(charset)));
		        String lines;
		        while ((lines = reader.readLine()) != null) {
		           logger.info(lines);
		           return lines;
		        }
	        }catch(Exception e){
	        	logger.error("", e);
	        }finally{
				try {
					reader.close();
					connection.disconnect();
				} catch (Exception e) {
				}
	        }
		    return null; 
	    }
	 
	 public  String getURL(Map<String,String> params,String url,String charset){
		if(params != null){
			StringBuffer uri = new StringBuffer(url);
			uri.append("?");
			
			Set<String> set = params.keySet();
			Iterator<String> it = set.iterator();		
			while(it.hasNext()){
				
				String key = it.next();
				String value = params.get(key);
				try {
					uri.append(URLEncoder.encode(key,charset)).append("=")
					.append(URLEncoder.encode(value,charset)).append("&");
				} catch (UnsupportedEncodingException e) {
					logger.error("URLEncoder error!");
					return null;
				}
			}
			uri.deleteCharAt(uri.length()-1);
			return uri.toString();
		}
		return null;	
	}
	 public static void main(String[] args) {
		MessageBaseUtils mess = new MessageBaseUtils();
		try {
			mess.sendSMS("18028752937", "测试");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
