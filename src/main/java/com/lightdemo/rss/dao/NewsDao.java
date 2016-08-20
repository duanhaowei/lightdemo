package com.lightdemo.rss.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lightdemo.rss.model.News;
import com.lightdemo.sync.AdminDto;

public interface NewsDao {
	/**
	 * 获取News
	 * @param id
	 * @return
	 */
	public News findNews(String id);

	public void save(News news);

	public List<News> findByLink(String link);
	/**
	 * 获取大于指定日期的新闻
	 * @param date
	 * @return
	 */
	public List<News> findNewsGtDate(Date date);
	
	/**
	 * 通过openId获取管理员
	 * @param openId
	 * @return
	 */
	public AdminDto getOrgAdminByOpenId(String openId);

	public void saveOrgAdmin(String openId, String dept);
}
