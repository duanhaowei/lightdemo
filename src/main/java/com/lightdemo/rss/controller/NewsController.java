package com.lightdemo.rss.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.lightdemo.rss.model.News;
import com.lightdemo.rss.service.NewsService;

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
		ModelAndView mav = new ModelAndView("news");
		mav.addObject("list", list);
		mav.addObject("appId", appId);
		mav.addObject("appName", appName);
		return mav;
	}

}
