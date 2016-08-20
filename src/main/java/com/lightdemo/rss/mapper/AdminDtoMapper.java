package com.lightdemo.rss.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.lightdemo.sync.AdminDto;

/**
 * @author chen
 */
public class AdminDtoMapper implements RowMapper {  
    public AdminDto mapRow(ResultSet rs, int rowNum) throws SQLException {  
    	if(null == rs) {
    		return null;
    	}
    	AdminDto adt = new AdminDto();  
    	adt.setDepartment(rs.getString("department"));
    	adt.setOpenId(rs.getString("openId"));
        return adt;  
    }  
}  
