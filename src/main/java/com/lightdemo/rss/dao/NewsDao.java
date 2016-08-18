package com.lightdemo.rss.dao;

import com.lightdemo.rss.model.News;

public interface NewsDao {
	public News findNews(String id);

	public void save(News news);
}
