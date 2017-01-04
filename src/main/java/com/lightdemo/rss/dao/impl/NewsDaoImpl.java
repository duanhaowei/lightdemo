package com.lightdemo.rss.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.lightdemo.rss.dao.NewsDao;
import com.lightdemo.rss.mapper.AdminDtoMapper;
import com.lightdemo.rss.mapper.NewsMapper;
import com.lightdemo.rss.model.News;
import com.lightdemo.sync.AdminDto;

public class NewsDaoImpl extends JdbcDaoSupport implements NewsDao {
	Logger logger = LoggerFactory.getLogger(NewsDaoImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public News findNews(String id) {
		String sqlStr = "select *,date_format(pubDate,'%Y-%m-%d %H:%I:%S') as pubDateStr from news where id=?";
		List<News> list = this.getJdbcTemplate().query(sqlStr,	new Object[]{id}, new NewsMapper());
		if(null != list) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void save(News news) {
		String sqlStr = "insert into news values(?,?,?,?,?)";
		Object[] params = new Object[]{news.getId(), news.getTitle(),news.getLink(), news.getPubDate(), news.getDescription()};
		this.getJdbcTemplate().update(sqlStr, params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<News> findByLink(String link) {
		String sql = "select *,date_format(pubDate,'%Y-%m-%d %H:%I:%S') as pubDateStr from news where link=?";
		List<News> list = this.getJdbcTemplate().query(sql,
				new Object[]{link}, new NewsMapper());
		return list;
	}


	@Override
	public AdminDto getOrgAdminByOpenId(String openId) {
		String sql = "select * from orgadmin where openId=?";
		
		@SuppressWarnings("unchecked")
		List<AdminDto> list = this.getJdbcTemplate().query(sql,
				new Object[]{openId}, new AdminDtoMapper());
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveOrgAdmin(String openId, String dept) {
		String sqlStr = "insert into orgadmin values(?,?,?)";
		Object[] params = new Object[]{UUID.randomUUID().toString(),openId, dept};
		this.getJdbcTemplate().update(sqlStr, params);
	}

	@Override
	public List<News> findNewsGtDate(Date date) {
		String sqlStr = "select *,date_format(pubDate,'%Y-%m-%d %H:%I:%S') as pubDateStr from news where pubDate > ?";
		List<News> list = this.getJdbcTemplate().query(sqlStr,	new Object[]{date}, new NewsMapper());
		if(null != list) {
			return list;
		}
		return null;
	}

}
