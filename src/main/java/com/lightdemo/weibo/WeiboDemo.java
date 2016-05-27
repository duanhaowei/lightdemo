package com.lightdemo.weibo;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.lightdemo.model.Common;
import com.lightdemo.util.FileUtil;
import com.lightdemo.util.PropertiesUtil;

import weibo4j.WeiboException;
import weibo4j.httpclient.HttpClient;
import weibo4j.httpclient.ImageItem;
import weibo4j.httpclient.PostParameter;
import weibo4j.httpclient.Response;


public class WeiboDemo {
	private String weiboUrl;
	private static WeiboHttp instance;
	private String consumerKey;
	private String consumerSecret;
	private String email;
	private String password;
	
	
	public WeiboDemo(){
		Common cm = (Common) PropertiesUtil.loadCommonProperties("common.properties", Common.class);
		this.weiboUrl = cm.getWEIBOSERVER();
		this.consumerKey = cm.getCONSUMERKEY();
		this.consumerSecret = cm.getCONSUMERSECRET();
		this.email = cm.getEMAIL();
		this.password = cm.getPASSWORD();
	}
	
	public static void main(String[] args) {
		WeiboDemo wb = new WeiboDemo();
		try {
//			wb.uploadProfileImage("13417396677","04302","C:/Users/kingdee/Pictures/2.png");
//			wb.uploadProfileImage("18028752937","04302","C:/Users/kingdee/Pictures/小兔子.png");
			String img1 = "http://images.missyuan.com/attachments/day_081031/20081031_273ca1dfc0a97651ef26GQvSlLOvnTPN.png";
			wb.uploadProfileImageByHUrl("18028752937","04302",img1);
		} catch (Exception e) {
			e.printStackTrace();
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
				new PostParameter("status","男人哭吧哭吧不是罪")}, true);
		String result = res.getResponseAsString();
		System.out.println("返回结果是：" + result);
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
