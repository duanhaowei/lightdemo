package com.lightdemo.weibo;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.IOUtils;

import com.lightdemo.model.Common;
import com.lightdemo.util.FileUtil;
import com.lightdemo.util.PropertiesUtil;

import weibo4j.WeiboException;
import weibo4j.httpclient.HttpClient;
import weibo4j.httpclient.ImageItem;
import weibo4j.httpclient.PostParameter;
import weibo4j.httpclient.Response;

/**
 * 注意， GET请求请参考fetchWeibo 这个方法
 *     POST请求请参考sendWeibo 这个方法
 * @author junquan_chen
 *
 */
public class WeiboDemo {
	private String weiboUrl;
	private static WeiboHttp instance;
	private String consumerKey;
	private String consumerSecret;
	private String account;
	private String password;
	private String networkId;
	
	public WeiboDemo(){
		Common cm = (Common) PropertiesUtil.loadCommonProperties("common.properties", Common.class);
		this.weiboUrl = cm.getWEIBOSERVER();
		this.consumerKey = cm.getCONSUMERKEY();
		this.consumerSecret = cm.getCONSUMERSECRET();
		this.account = cm.getACCOUNT();
		this.password = cm.getPASSWORD();
		this.networkId = "101";
	}
	
	public static void main(String[] args) {
		WeiboDemo wb = new WeiboDemo();
		try {
//			wb.uploadProfileImage("13417396677",wb.networkId,"C:/Users/chen/Pictures/111.png");
//			wb.uploadProfileImage("18028752937",wb.networkId,"C:/Users/chen/Pictures/u.png");
//			String img1 = "http://images.missyuan.com/attachments/day_081031/20081031_273ca1dfc0a97651ef26GQvSlLOvnTPN.png";
//			wb.uploadProfileImageByHUrl("18028752937",wb.networkId,img1);
//			wb.fetchWeibo();
//			wb.sendWeibo();
//			wb.getTOken();
//			wb.getUserInfo();
//			wb.addGroupMember();
//			wb.removeToken();
//			wb.createGroup();
			wb.jsonGroup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void removeToken()  throws Exception {
		String url = "/snsapi/client/remove_token.json ";
		HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
		Response res = http.post(weiboUrl + url, new PostParameter[]{
				new PostParameter("mobile", "18028752939"),
				}, true);
		String result = res.getResponseAsString();
		System.out.println("返回结果是：" + result);
	}

	private void addGroupMember() throws Exception{
		/*String url = "/snsapi/group/add_member.json";
		HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
		Response res = http.post(weiboUrl + url, new PostParameter[]{
				new PostParameter("group_id", "57ee2e8284ae4611184bdb2b"),
				new PostParameter("user_ids", "9cd96d10-7e46-11e6-8825-005056ac6b20"),
				}, true);
		String result = res.getResponseAsString();
		System.out.println("返回结果是：" + result);*/
		
		String url = "/snsapi/group/add_member.json";
		HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
		Response res = http.post(weiboUrl + url, new PostParameter[]{
				new PostParameter("group_id", "57ee2e8284ae4611184bdb2b"),
				new PostParameter("user_ids", "0b3c97d8-73ce-11e6-8825-005056ac6b20"),
				}, true);
		String result = res.getResponseAsString();
		System.out.println("返回结果是：" + result);
	}

	private void getUserInfo() throws Exception{
		String url = "/snsapi/users/show.json";
		HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
		Response res = http.post(weiboUrl + url, new PostParameter[]{new PostParameter("screen_name", "刘成俊 ")}, true);
		String result = res.getResponseAsString();
		System.out.println("返回结果是：" + result);
	}

	private void getTOken()  throws WeiboException{
		try {
			HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
			Response res = http.httpRequest(weiboUrl + "/snsapi/oauth/weblogin_token?operator="+"15773176023",null,true,"GET");
			weibo4j.org.json.JSONObject json = res.asJSONObject();
			if(json.getString("success").equals("true")){
				String url= weiboUrl+"/snsapi/oauth/login?lgtk="+json.getString("lgtk")+"&to=%2fmicroblog";
				try{
					java.net.URI uri = java.net.URI.create(url); 
				    // 获取当前系统桌面扩展 
				    java.awt.Desktop dp = java.awt.Desktop.getDesktop(); 
				    // 判断系统桌面是否支持要执行的功能 
				    if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) { 
				    	//File file = new File("D:\\aa.txt"); 
				    	//dp.edit(file);// 　编辑文件 
				    	dp.browse(uri);// 获取系统默认浏览器打开链接 
				    	// dp.open(file);// 用默认方式打开文件 
				    	// dp.print(file);// 用打印机打印文件 
				     } 
				   } catch (java.lang.NullPointerException e) { 
					   // 此为uri为空时抛出异常 
					   e.printStackTrace(); 
				   } catch (java.io.IOException e) { 
					   // 此为无法获取系统默认浏览器 
					   e.printStackTrace(); 
				   } 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送微博的接口调用样例
	 * @throws WeiboException
	 */
	private void fetchWeibo() throws WeiboException{
		String url = "/snsapi/statuses/public_timeline.json";
		HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
		Response res = null;
	    {//GET请求
	       res = http.httpRequest(weiboUrl + url,null,true,"GET");
	       System.out.println(res.asJSONArray());
	    }
	}
	
	/**
	 * 发送微博的接口调用样例
	 * @throws WeiboException
	 */
	private void sendWeibo() throws WeiboException{
		String url = "/snsapi/statuses/update.json";
		HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
		Response res = http.post(weiboUrl + url, new PostParameter[]{
				new PostParameter("status","今天下了一场很大很大的雨，捉到一条很大很大的雨")}, true);
		String result = res.getResponseAsString();
		System.out.println("返回结果是：" + result);
	}
	/**
	 * 创建微博小组
	 * @throws WeiboException
	 */
	private void createGroup() throws WeiboException {
		String url = "/snsapi/104/group/create.json";
		HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
		Response res = http.post(weiboUrl + url, new PostParameter[]{
				new PostParameter("name", "普洱")
				}, true);
		System.out.println("返回结果是：" + res.asString());
	}
	/**
	 * 创建小组
	 * @throws WeiboException
	 */
	private void jsonGroup() throws WeiboException{
		String url = "/snsapi/104/group/add_member.json";
		HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
		Response res = http.post(weiboUrl + url, new PostParameter[]{
				new PostParameter("group_id", "589a66d784aea980f0acad6b"),
				new PostParameter("user_ids", "5f7bfd99-39a1-11e6-8825-005056ac6b20")
				}, true);
		System.out.println("返回结果是：" + res.asJSONArray());
	}
	
	/**
	 * 通过固定账号一次性更新人员头像
	 * @param account 账号
	 * @param networkId 工作圈ID
	 * @param path 文件路径
	 * @throws Exception
	 */
	private void uploadProfileImage(String account, String networkId, String path) throws Exception{
		File file = new File(path);
		byte[] bytes = IOUtils.toByteArray(new FileInputStream(file));
		ImageItem pic = new ImageItem("image", "filename", bytes);//参数固定
		HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
	    String urlImg = "/snsapi/account/update_account_image.json?account="+account+"&networkId="+networkId;
		Response res = http.multPartURL(weiboUrl + urlImg,
				new PostParameter[]{} , pic, true);
		String result = res.getResponseAsString();
		System.out.println("返回结果是：" + result);
	}
	
	/**
	 * 通过固定账号一次性更新人员头像,这个是提供一个图片的url地址
	 * 注意这里要确保图片的地址可以通过普通的Get请求获取。
	 * 有些网站的图片是不可以Get下来的，可能因为版权保护等问题
	 * @param account 账号
	 * @param networkId 工作圈ID
	 * @param path 文件路径
	 * @throws Exception
	 */
	private void uploadProfileImageByHUrl(String account, String networkId, String imgurl) throws Exception{
		byte[] bytes = FileUtil.getImageFromNetByUrl(imgurl);
		if(null == bytes) {
			System.out.println("出现错误：未能下载图片");
			return;
		}
		ImageItem pic = new ImageItem("image", "filename", bytes);//参数固定
		HttpClient http = WeiboHttp.getInstance().getBaseHttpClient();
	    String urlImg = "/snsapi/account/update_account_image.json?account="+account+"&networkId="+networkId;
		Response res = http.multPartURL(weiboUrl + urlImg,
				new PostParameter[]{} , pic, true);
		String result = res.getResponseAsString();
		System.out.println("返回结果是：" + result);
	}
                                                                                                                                                                                                                                                                                                                                                                                                                       
}
