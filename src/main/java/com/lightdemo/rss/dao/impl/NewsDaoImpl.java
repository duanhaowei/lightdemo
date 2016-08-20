package com.lightdemo.rss.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.lightdemo.rss.dao.NewsDao;
import com.lightdemo.rss.model.News;
import com.lightdemo.sync.AdminDto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class NewsDaoImpl extends JdbcDaoSupport implements NewsDao {

	@Override
	public News findNews(String id) {
		String sqlStr = "select * from news where id=?";
		final News n = new News();
		this.getJdbcTemplate().queryForObject(sqlStr, new Object[]{id}, News.class);
		return n;
	}

	@Override
	public void save(News news) {
		String sqlStr = "insert into news values(?,?,?,?,?)";
		Object[] params = new Object[]{news.getId(), news.getTitle(),news.getLink(), news.getPubDate(), news.getDescription()};
		this.getJdbcTemplate().update(sqlStr, params);
	}

	@Override
	public List<News> findByTitle(String title) {
		String sql = "select * from news where title=?";
		List<Map<String, Object>> listMap = this.getJdbcTemplate().queryForList(sql,new Object[]{title});
		List<News> list = new ArrayList<News>(listMap.size());
		for(Map<String, Object> map : listMap) {
			News n = new News();
			try {
				BeanUtils.populate(n, map);
				System.out.println(map+"\n");
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			} 
			list.add(n);
		}
		return list;
	}


	@Override
	public AdminDto getOrgAdminByOpenId(String openId) {
		String sql = "select * from orgadmin where openId=?";
		Map<String, Object> map = this.getJdbcTemplate().queryForMap(sql, new Object[]{openId});
		AdminDto ad = null;
		try {
			ad = new AdminDto();
			BeanUtils.populate(ad, map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ad;
	}

	@Override
	public void saveOrgAdmin(String openId, String dept) {
		String sqlStr = "insert into news values(?,?,?)";
		Object[] params = new Object[]{UUID.randomUUID().toString(),openId, dept};
		this.getJdbcTemplate().update(sqlStr, params);
	}

}
