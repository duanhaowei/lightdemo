package com.lightdemo.sync;
/**
 * 修改组织时需要封装的dto
 * @author cjqbrave@163.com
 *
 */
public class DeptDTO {
	private String eid;

	private String[] departments;

	public String[] getDepartments() {
		return departments;
	}

	public void setDepartments(String[] departments) {
		this.departments = departments;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}
}
