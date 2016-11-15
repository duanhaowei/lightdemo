package com.lightdemo.sync;
/**
 * 修改人员基本封装的DTO
 * @author cjqbrave@163.com
 *
 */
public class Person {
	
	private  String name;
	
	private  String phone;
	
	private  String email;
	
	private  String isHidePhone;
	
	private  String status;
	
	private  String gender;
	
	private  String department;
	
	private  String jobTitle;
	
	private String password;
	
	private String longName;
	
	private String openId;
	
	private int weights = 0;
	
	private int orgUserType = 0;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsHidePhone() {
		return isHidePhone;
	}

	public void setIsHidePhone(String isHidePhone) {
		this.isHidePhone = isHidePhone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getWeights() {
		return weights;
	}

	public void setWeights(int weights) {
		this.weights = weights;
	}

	public int getOrgUserType() {
		return orgUserType;
	}

	public void setOrgUserType(int orgUserType) {
		this.orgUserType = orgUserType;
	}
	
}
