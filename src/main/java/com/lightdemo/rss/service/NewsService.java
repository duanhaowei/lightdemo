package com.lightdemo.rss.service;

import java.util.List;
import java.util.Map;

import com.lightdemo.rss.model.News;
import com.lightdemo.sync.AdminDto;

public interface NewsService {
	/**
	 * 获取凤凰网的rss的新闻
	 * @param ifengNewsUrl
	 * @return
	 */
	List<News> getIFendNews(String ifengNewsUrl);
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
	 * 通过title查询News
	 * @param title
	 * @return
	 */
	List<News> findByTitle(String title);
	boolean isDeptAdmin(String openId);
	AdminDto getOrgAdminByOpenId(String openId);
	void saveOrgAdmin(String openId, String dept);
	List<AdminDto> getOrgAllAdmin(int start, int limit);

}
