package com.lightdemo.rss.service;

import java.util.List;

import com.lightdemo.rss.model.News;

public interface NewsService {
	/**
	 * 获取凤凰网的rss的新闻
	 * @param ifengNewsUrl
	 * @return
	 */
	List<News> getIFendNews(String ifengNewsUrl);
	
	void save(News news);
	
	News findById(String id);

}
