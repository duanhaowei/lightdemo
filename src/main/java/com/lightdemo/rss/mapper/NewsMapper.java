package com.lightdemo.rss.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

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
			if(null != rs.getDate("pubDate"))
				ns.setPubDate(new Date(rs.getDate("pubDate").getTime()));
			ns.setTitle(rs.getString("title"));
			ns.setPubDateStr(rs.getString("pubDateStr"));
			return ns;
		}
		return null;
	}

}
