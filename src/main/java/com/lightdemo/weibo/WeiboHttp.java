package com.lightdemo.weibo;

import com.lightdemo.model.Common;
import com.lightdemo.util.PropertiesUtil;

import weibo4j.WeiboException;
import weibo4j.httpclient.HttpClient;


public class WeiboHttp {
	private static WeiboHttp instance;
	private String weiboUrl;
	private String consumerKey;
	private String consumerSecret;
	private String email;
	private String password;
	
	
	private WeiboHttp(){
		Common cm = (Common) PropertiesUtil.loadCommonProperties("common.properties", Common.class);
		this.weiboUrl = cm.getWEIBOSERVER();
		this.consumerKey = cm.getCONSUMERKEY();
		this.consumerSecret = cm.getCONSUMERSECRET();
		this.email = cm.getEMAIL();
		this.password = cm.getPASSWORD();
	}
	public static WeiboHttp getInstance(){
		if(instance == null) {
			instance = new WeiboHttp();
		} 
		return instance;
	}
	
	public HttpClient getBaseHttpClient() throws WeiboException{
		
		String apiURL = weiboUrl +"/snsapi";
	    HttpClient http = new HttpClient();
	    http.setRequestTokenURL(apiURL+"/oauth/request_token");
	    http.setAccessTokenURL(apiURL+"/oauth/access_token");
	    http.setAuthorizationURL(apiURL+"/oauth/authorize");
	    http.setOAuthConsumer(consumerKey, consumerSecret);//替换成自己申请的应用专属key和secret的
	    http.getXAuthAccessToken(email, password, "client_auth");
	    System.out.println("email: " + email +"  password: " + password);
	    System.out.println("consumerKey: " + consumerKey +"  consumerSecret: " + consumerSecret);
	    return http;
	    //第一次用用户名和密码交换到accessToken后，不需要再用用户名密码，直接用accessToken即可，
	    //上面步骤即可直接省略，按照下面步骤设置accessToken即可
	    //http.setToken("aefaaedc99ef5930a1934b36ae1503a", "9b4ac7d2571ba6fd6d5c31dad3696");

	   /* Response res = null;
	    {//GET请求
	       res = http.httpRequest(apiURL + "/statuses/public_timeline.json?count=20",null,true,"GET");
	       System.out.println(res.asJSONArray());
	   }

	   {//POST请求
	       Set<PostParameter> parameters = new HashSet<PostParameter>();
	       parameters.add(new PostParameter("email", "xxx@kingdee.com"));
	       parameters.add(new PostParameter("name", "张三"));
	       res = http.post(apiURL + "/101/task/removeTask/5551ad2f84ae57efb68172eb.json",      (PostParameter[])parameters.toArray(new PostParameter[0]), true);
	       System.out.println(res.toString());
	   }

	    {//图片
	        File file = new File("D:\\Documents\\Pictures\\1.jpg");
	        byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
	        ImageItem pic = new ImageItem("pic", "filename", bytes);
	        Set params = new HashSet();
	        params.add(new PostParameter("phone", "13723442329"));
	        http.multPartURL(apiURL + "/account/update_profile_image.json", (PostParameter[])params.toArray(new PostParameter[0]), pic, true);
	    }*/

	}
}
