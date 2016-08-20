package com.lightdemo.rss.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lightdemo.rss.model.News;

/**
 * @author chen
 */
@SuppressWarnings("rawtypes")
public class NewsMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		if(null != rs) {
			News ns = new News();
			ns.setDescription(rs.getString("description"));
			ns.setId(rs.getString("id"));
			ns.setLink(rs.getString("link"));
			ns.setPubDate(rs.getDate("pubDate"));
			ns.setTitle(rs.getString("title"));
			return ns;
		}
		return null;
	}

}
