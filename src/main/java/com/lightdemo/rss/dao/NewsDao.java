package com.lightdemo.rss.dao;

import java.util.List;
import java.util.Map;

import com.lightdemo.rss.model.News;
import com.lightdemo.sync.AdminDto;

public interface NewsDao {
	public News findNews(String id);

	public void save(News news);

	public List<News> findByTitle(String title);

	public AdminDto getOrgAdminByOpenId(String openId);

	public void saveOrgAdmin(String openId, String dept);
}
