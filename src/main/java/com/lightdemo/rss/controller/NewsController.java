package com.lightdemo.rss.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "news")
public class NewsController {
	@Value("${IFENGNEWSURL}")
	private String ifengNewsUrl;
	@Value("${APPID}")
	private String appId;
	@Value("${APPNAME}")
	private String appName;

	private NewsService newsService;

	@Autowired
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	@RequestMapping(value = "/shownew", method = RequestMethod.GET)
	public ModelAndView shownew(HttpServletResponse rep) {
		List<News> list = newsService.getIFendNews(ifengNewsUrl);
		for(News temp : list) {
			String id = temp.getId();
			News ex = newsService.findById(id);
			if(null != ex) {
				newsService.save(temp);
			}
		}
		
		
		ModelAndView mav = new ModelAndView("news");
		mav.addObject("list", list);
		mav.addObject("appId", appId);
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
		List<News> list = newsService.getIFendNews(ifengNewsUrl);
		
		reJson.put("recordsFiltered", list.size());
		reJson.put("recordsFiltered", list.size());
		reJson.put("data", list);
		return reJson.toString();
	}

}
