package com.lightdemo.sync;

import java.util.ArrayList;
import java.util.List;
/**
 * 组织人员同步修改人员信息时设置的参数的DTO，便于直接json转换
 * @author cjqbrave@163.com
 *
 */
public class PersonData2DTO {
	private String eid;
	
	private List<Person> persons = new ArrayList<Person>();
	
	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

}
