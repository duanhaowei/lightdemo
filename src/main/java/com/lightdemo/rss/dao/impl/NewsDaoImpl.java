package com.lightdemo.rss.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.lightdemo.rss.dao.NewsDao;
import com.lightdemo.rss.model.News;
@Repository
public class NewsDaoImpl implements NewsDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public News findNews(String id) {
		String sqlStr = "select * from news where id=?";
		final News n = new News();
		jdbcTemplate.query(sqlStr, new Object[]{id}, new RowCallbackHandler() {
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				n.setId(rs.getString("id"));
				n.setDescription(rs.getString("desction"));
				n.setLink(rs.getString("ling"));
				n.setTitle(rs.getString("title"));
			}
		});
		return n;
	}

	@Override
	public void save(News news) {
		String sqlStr = "insert into news values(?,?,?,?,?)";
		Object[] params = new Object[]{news.getId(), news.getTitle(),news.getLink(), news.getPubDate(), news.getDescription()};
		jdbcTemplate.update(sqlStr, params);
	}

}
