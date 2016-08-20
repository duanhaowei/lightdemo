package com.lightdemo.rss.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lightdemo.rss.model.News;
import com.lightdemo.sync.AdminDto;

public interface NewsService {
	/**
	 * 获取腾讯的rss的新闻
	 * @param ifengNewsUrl
	 * @return
	 */
	List<News> getNewsQq(String newsqq);
	/**
	 * 保存消息
	 * @param news
	 */
	void save(News news);
	/**
	 * 通过id查询消息
	 * @param id
	 * @return
	 */
	News findById(String id);
	/**
	 * 通过link查询News
	 * @param title
	 * @return
	 */
	List<News> findByLink(String link);
	/**
	 * 判断是不是管理员
	 * @param openId
	 * @return
	 */
	boolean isDeptAdmin(String openId);
	/**
	 * 通过openId获取管理员
	 * @param openId
	 * @return
	 */
	AdminDto getOrgAdminByOpenId(String openId);
	/**
	 * 保存管理员
	 * @param openId
	 * @param dept
	 */
	void saveOrgAdmin(String openId, String dept);
	/**
	 * 获取管理员列表
	 * @param start
	 * @param limit
	 * @return
	 */
	List<AdminDto> getOrgAllAdmin(int start, int limit);
	
	/**
	 * 获取大于指定日期的新闻
	 * @param date
	 * @return
	 */
	public List<News> findNewsGtDate(Date date);

}
