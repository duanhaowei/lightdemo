package com.lightdemo.rss.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lightdemo.rss.model.News;
import com.lightdemo.rss.service.NewsService;
import com.lightdemo.util.FileUtil;
import com.lightdemo.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "news")
public class NewsController {
	Logger logger = LoggerFactory.getLogger(NewsController.class);
	@Value("${NEWSQQURL}")
	private String NEWSQQURL;
	@Value("${APPID}")
	private String APPID;
	@Value("${APPNAME}")
	private String appName;
	
	@Value(value="${SECRET}")
	private String SECRET = null;
	@Value(value="${XT_SERVERNAME}")
	private String XT_SERVERNAME = null;
	
	
	@Value("${PUBACC}")
	private String PUBACC;
	@Value("${EID}")
	private String EID;
	@Value("${PUBACC_KEY}")
	private String PUBACC_KEY;
	
	String imgName = "小兔子.png";
	String imgFilePath= "C:/Users/chen/Pictures/小兔子.png";

	private NewsService newsService;

	@Autowired
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	@RequestMapping(value = "/shownew", method = RequestMethod.GET)
	public ModelAndView shownew(HttpServletRequest req, HttpServletResponse rep) {
		String ticket = req.getParameter("ticket");
		
		JSONObject perJson = getPersonName(ticket);
		String openId = (String) perJson.get("openid");
		boolean adm = newsService.isDeptAdmin(openId);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		
		List<News> list = newsService.findNewsGtDate(cal.getTime());
		
		ModelAndView mav = new ModelAndView("news");
		if(adm) {
			mav.addObject("admin", true);
		} else  {
			mav.addObject("admin", false);
		}
		mav.addObject("list", list);
		mav.addObject("appId", APPID);
		mav.addObject("appName", appName);
		return mav;
	}
	
	@RequestMapping(value = "/echart", method = RequestMethod.GET)
	public ModelAndView shownewsechart(HttpServletResponse rep) {
		ModelAndView mav = new ModelAndView("echarts");
		return mav;
	}
	
	@RequestMapping(value = "/datatables", method = RequestMethod.GET)
	public ModelAndView shownewsdatatable(HttpServletResponse rep) {
		ModelAndView mav = new ModelAndView("datatables");
		return mav;
	}
	
	@RequestMapping(value = "/getdatatablesnews", method = RequestMethod.GET)
	public @ResponseBody String getdatatablesnews(HttpServletResponse rep,
			@RequestParam(defaultValue="0") String start,
			@RequestParam(defaultValue="10") String length
			) {
		JSONObject reJson = new JSONObject();
		List<News> list = newsService.getNewsQq(NEWSQQURL);
		
		reJson.put("recordsFiltered", list.size());
		reJson.put("recordsFiltered", list.size());
		reJson.put("data", list);
		return reJson.toString();
	}
	
	
	@RequestMapping(value = "/shareall", method = RequestMethod.POST)
	public @ResponseBody String shareAll(HttpServletResponse rep,
			String desc,
			String title,
			String link
			) {
		JSONObject reJson = new JSONObject();
		
		postmsg(title, desc, link,null, 6);
		
		reJson.put("success", true);
		return reJson.toString();
	}
	
	
	public JSONObject getPersonName(String ticket){
		String url = XT_SERVERNAME + "/openauth2/api/token?grant_type=client_credential&appid="+APPID+"&secret="+SECRET;
		try {
			String rs = Utils.sendPost(url, null);
			if(null != rs && rs.trim().length() > 0){
				JSONObject tokenJson = JSONObject.fromObject(rs);

					if(tokenJson.containsKey("access_token")) {
						String token = tokenJson.getString("access_token");
						String personUrl = XT_SERVERNAME+"/openauth2/api/getcontext?ticket="+ticket+"&access_token="+token;
						String perStr = Utils.sendPost(personUrl,null);
						if(null != perStr && perStr.trim().length() > 0) {
							JSONObject perJson = JSONObject.fromObject(perStr);	
							return perJson;
						}
						 
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 发送消息
	 * @param text 文本
	 * @param users 用户openId
	 * @param type 类型
	 * @return
	 */
	public String postmsg(String title, String text,String link, String users, int type) {

		JSONObject reJson = new JSONObject();
		List<String> toUserList = null;
		if(null != users) {
//			type = 2;
			if(users.contains(",")) {
				String[] arr = users.split(",");
				toUserList = new ArrayList<String>(arr.length);
				for(String obj : arr){
					toUserList.add(obj);
				}
			} else{
				toUserList = new ArrayList<String>(1);
				toUserList.add(users);
			}
		} 
		try {
			String random = String.valueOf(Math.random() * 100);
			String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date());
			long time_1 = System.currentTimeMillis();
			// form
			JSONObject from = new JSONObject();
			from.put("no", EID); // 
			from.put("pub", PUBACC); // 
			from.put("time", time_1);
			from.put("nonce", random);
			String[] data = { EID, PUBACC, PUBACC_KEY, random,
					String.valueOf(time_1) };
			from.put("pubtoken", Utils.sha(data));
			// to
			JSONArray tos = new JSONArray();
			JSONObject to = new JSONObject();
			to.put("no", EID);
			if((type == 5 || type == 6) && null == users) {
				to.put("code", "all");
			} else {
				to.put("user", toUserList);
			}
			
			
			tos.add(to);

			JSONObject msg = new JSONObject();
			if(type == 5) {
				msg.put("url", "http://www.baidu.com");
				msg.put("appid", APPID);
				msg.put("todo", 0);
				msg.put("text", text);
			} else if(type == 6) {
				JSONArray list = new JSONArray();
				JSONObject msgJson = new JSONObject();
				msgJson.put("date", nowDate);
				msgJson.put("title", title);
				msgJson.put("text", text);
//				msgJson.put("name", imgName);
//				msgJson.put("pic", FileUtil.encodeBase64File(imgFilePath));
				msgJson.put("url", link);
				msgJson.put("appid", APPID);
				
				list.add(msgJson);
				msg.put("model", 2);
				msg.put("list", list);
			} else {
				msg.put("text", text);
			}
			JSONObject content = new JSONObject();
			content.put("from", from);
			content.put("to", tos);
			content.put("type", type);
			content.put("msg", msg);
			String contentStr = content.toString();
			System.out.println(contentStr);
			String rs = Utils.sendPostO(XT_SERVERNAME+"/pubacc/pubsend", contentStr);
			System.out.println("结果是---"+rs);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reJson.toString();
	
	}

}
