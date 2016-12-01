package com.lightdemo.sync;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.lightdemo.model.Common;
import com.lightdemo.util.PropertiesUtil;
import com.lightdemo.util.RSAUtils;
import com.lightdemo.util.Utils;

/**
 * 组织人员同步测试类
 * 
 * @author cjqbrave@163.com
 *
 */
public class PersonSyncApiTest {
	private String EID;
	private String host;
	private String openId;

	public PersonSyncApiTest() {
		Common cm = (Common) PropertiesUtil.loadCommonProperties("common.properties", Common.class);
		this.EID = cm.getEID();
		this.host = cm.getXT_SERVERNAME();
		this.openId = cm.getOPENIDS();
	}

	public static void main(String[] args) {
		PersonSyncApiTest psa = new PersonSyncApiTest();
		try {
			 psa.getAllPersons();
//			 psa.addPerson();
//			 psa.updatePersonInfo();
//			 psa.addDept();
//			 psa.getallcasvir();
//			 psa.getcasvir();
//			 psa.addcasvir();
//			 psa.updatecasvir();
//			 psa.deletecasvir();
//			 psa.updatePersonStatus();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updatePersonStatus() throws Exception{
		String url = host + "/openaccess/input/person/updateStatus";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		
		JSONObject obj = new JSONObject();
		obj.put("openId", this.openId);
		obj.put("type", "1");
		
		JSONArray jsonay = new JSONArray();
		jsonay.add(obj);
		
		JSONObject param = new JSONObject();
		param.put("eid", EID);
		param.put("persons", jsonay.toString());
		nvps.add(new BasicNameValuePair("data", enyte(param.toString())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	/**
	 * 添加部门信息
	 * 
	 * @throws Exception
	 */
	public void addDept() throws Exception {
		// 讯通，改成了需要先同步部门
		String url = host + "/openaccess/input/dept/add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		nvps.add(new BasicNameValuePair("data", enyte(setDepartment())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	/**
	 * 添加人员信息
	 * 
	 * @throws Exception
	 */
	public void addPerson() throws Exception {
		String url = host + "/openaccess/input/person/add";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		nvps.add(new BasicNameValuePair("data", enyte(addPersonJson())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	private String addPersonJson() {
		PersonData2DTO p = new PersonData2DTO();
		p.setEid(this.EID);
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		person.setName("陈俊全——测试");
		person.setPhone("18028751818");
		person.setEmail("hello@hotmail.com");
		person.setIsHidePhone("0");
//		person.setStatus("1");
		person.setGender("0");
		person.setDepartment("分公司");
		person.setWeights(33);
		person.setJobTitle("CEO");
		person.setOrgUserType(1);
		persons.add(person);
		p.setPersons(persons);
		JSONObject jo = JSONObject.fromObject(p);
		return jo.toString();
	}

	/**
	 * 获取最近更新的人员信息
	 * 
	 * @throws Exception
	 */
	public void getPersonAtTime() throws Exception {
		String url = host + "/openaccess/input/person/getAtTime";
		List<NameValuePair> data = new ArrayList<NameValuePair>();
		data.add(new BasicNameValuePair("eid", EID));
		data.add(new BasicNameValuePair("time", "2015-05-26 01:40:38"));
		data.add(new BasicNameValuePair("begin", "0"));
		data.add(new BasicNameValuePair("count", "10"));

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("eid", EID));
		// nvps.add(new BasicNameValuePair("data", enyte(setDepartment())));
		nvps.add(new BasicNameValuePair("data", enyte(data.toString())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	/**
	 * 通过openId或者手机号获取人员信息
	 * 
	 * @throws Exception
	 */
	public void getPerson() throws Exception {
		String url = host + "/openaccess/input/person/get";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		nvps.add(new BasicNameValuePair("data", enyte(getPersonGetParam())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	/**
	 * 更新组织信息
	 * 
	 * @throws Exception
	 */
	public void updateDept() throws Exception {
		String url = host + "/openaccess/input/person/updateDept";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		JSONObject obj = new JSONObject();
		obj.put("eid", EID);
		JSONArray ar = new JSONArray();
		JSONObject oj = new JSONObject();
		oj.put("openId", "");
		oj.put("department", "金蝶集团3");
		ar.add(oj);
		obj.put("persons", ar.toString());// 修改3041， 从采销部变成金蝶集团
		nvps.add(new BasicNameValuePair("data", enyte(obj.toString())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	/**
	 * 获取所有用户信息
	 * 
	 * @throws Exception
	 */
	public void getAllPersons() throws Exception {
		String url = host + "/openaccess/input/person/getall";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		String temp = personGetAllParam();
		System.out.println(temp);
		nvps.add(new BasicNameValuePair("data", enyte(temp)));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	/**
	 * 更新用户信息
	 * 
	 * @throws Exception
	 */
	public void updatePersonInfo() throws Exception {

		String url = host + "/openaccess/input/person/updateInfo";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		JSONObject json = new JSONObject();
		json.put("eid", EID);
		
		JSONArray ar = new JSONArray();
		JSONObject per = new JSONObject();
		per.put("openId", this.openId);
		per.put("name", "陈俊全--管理");
		per.put("jobTitle", "CTO");
		per.put("weights", 15);
		per.put("orgUserType", 0);
		ar.add(per);
		json.put("persons", ar);
		
		nvps.add(new BasicNameValuePair("data", enyte(json.toString())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	private String personGetAllParam() {
		JSONObject jo = new JSONObject();
		jo.put("eid", this.EID);
		jo.put("begin", 0);
		jo.put("count", 500);
		return jo.toString();
	}

	private String getPersonGetParam() {
		JSONObject jo = new JSONObject();
		jo.put("eid", this.EID);
		jo.put("type", 1);
		String[] s = new String[1];
		s[0] = this.openId;
		jo.put("array", s);
		return jo.toString();
	}

	private String setPersonInfo() {
		PersonData2DTO p = new PersonData2DTO();
		p.setEid(this.EID);
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		person.setName("ddddd");
		person.setEmail("456789@oamail.htsc.com.cn");
		// person.photoUrl="";
		person.setPhone("456789");
		// person.phones="";
		person.setIsHidePhone("0");
		person.setPassword("123456");
		// person.status = "1";
		// person.gender ="0";
		person.setDepartment("中国\\决策部2\\ceo");
		person.setLongName("中国\\决策部2\\ceo");
		person.setJobTitle("总经理");
		// person.setOpenId("595cd2ae-6b0e-11e4-9ba3-000c29e6569e");
		// person.jobTitle="CEO";
		persons.add(person);
		p.setPersons(persons);
		JSONObject jo = JSONObject.fromObject(p);

		return jo.toString();
	}

	private String enyte(String data) {
		try {
			String path = PersonSyncApiTest.class.getResource("/").getPath();
			byte[] b = FileUtils.readFileToByteArray(new File(path + EID + ".key"));
			PrivateKey restorePublicKey = RSAUtils.restorePrivateKey(b);
			byte[] bytes = Base64.encodeBase64(RSAUtils.encryptLarger(data.getBytes("utf-8"), restorePublicKey));
			String temp =new String(bytes, "UTF-8");
			return temp;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String setDepartment() {
		DeptDTO depts = new DeptDTO();
		depts.setEid(EID);
		depts.setDepartments(new String[] { "分公司\\北京分公司" });
		JSONObject jo = JSONObject.fromObject(depts);
		System.out.println(jo.toString());
		return jo.toString();
	}


	/**
	 * 通过openId或者手机号获取人员信息
	 * 
	 * @throws Exception
	 */
	public void getallcasvir() throws Exception {
		String url = host + "/openaccess/input/person/getallcasvir";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		nvps.add(new BasicNameValuePair("data", enyte(getPersonGetParam())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	private String getcasvirparam() {
		JSONObject jo = new JSONObject();
		jo.put("eid", this.EID);
		// jo.put("type", 0); //openid形式
		jo.put("type", 1);

		JSONArray ar = new JSONArray();
		// JSONObject t1 = new JSONObject();
		// t1.put("department", "测试账号");
		// t1.put("user", "81b9f322-39a0-11e6-8825-005056ac6b20"); //openId
		//// t1.put("user", "18566684664"); //phone
		// t1.put("jobTitle", "测试");
		// ar.add("81b7dccd-39a0-11e6-8825-005056ac6b20");
		ar.add("18236582562");
		ar.add("15975643210");
		jo.put("list", ar);
		return jo.toString();

	}

	/**
	 * 通过openId或者手机号获取人员信息
	 * 
	 * @throws Exception
	 */
	public void getcasvir() throws Exception {
		String url = host + "/openaccess/input/person/getcasvir";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		nvps.add(new BasicNameValuePair("data", enyte(getcasvirparam())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	// 王攀 18566684664 81b9f322-39a0-11e6-8825-005056ac6b20 测试
	private String addcasvirparam() {
		JSONObject jo = new JSONObject();
		jo.put("eid", this.EID);
		 jo.put("type", 1);
//		jo.put("type", 0);
		JSONArray ar = new JSONArray();
		JSONObject t1 = new JSONObject();
		t1.put("department", "10000");
//		t1.put("user", "81b9f322-39a0-11e6-8825-005056ac6b20"); // openId
		 t1.put("user", "18566684664"); // phone
		t1.put("jobTitle", "测试");
		t1.put("weights", 2147483647);
		ar.add(t1);
		
//		JSONObject t2 = new JSONObject();
//		t2.put("department", "10000");
//		// t2.put("user", "18028752937"); // phone
//		t2.put("user", "81b88ebc-39a0-11e6-8825-005056ac6b20"); // openId
//		t2.put("jobTitle", "测试");
//		t2.put("weights", -1);
//		ar.add(t2);
		jo.put("list", ar);
		return jo.toString();
	}

	public void addcasvir() throws Exception {
		String url = host + "/openaccess/input/person/addcasvir";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		nvps.add(new BasicNameValuePair("data", enyte(addcasvirparam())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	// 王攀 18566684664 81b9f322-39a0-11e6-8825-005056ac6b20 测试
	private String updatecasvirparam() {
		JSONObject jo = new JSONObject();
		jo.put("eid", this.EID);
		 jo.put("type", 1);
//		jo.put("type", 0);
		JSONArray ar = new JSONArray();
		JSONObject t1 = new JSONObject();
		t1.put("department", "10000");
		t1.put("todepartment", "");
//		t1.put("user", "81b9f322-39a0-11e6-8825-005056ac6b20"); // openId
		 t1.put("user", "18566684664"); // phone18566684664
		t1.put("jobTitle", "测试");
		t1.put("tojobTitle", "测试");
		t1.put("toweights", -10);
		ar.add(t1);
		
//		JSONObject t2 = new JSONObject();
//		t2.put("department", "10000");
//		t2.put("todepartment", "10000");
//		// t2.put("user", "18028752937"); // phone
//		t2.put("user", "81b88ebc-39a0-11e6-8825-005056ac6b20"); // openId
//		t2.put("jobTitle", "开发");
//		t2.put("tojobTitle", "开发");
//		t2.put("toweights", -55);
//		ar.add(t2);
		
		jo.put("list", ar);
		return jo.toString();
	}

	public void updatecasvir() throws Exception {
		String url = host + "/openaccess/input/person/updatecasvir";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		nvps.add(new BasicNameValuePair("data", enyte(updatecasvirparam())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	// 王攀 18566684664 81b9f322-39a0-11e6-8825-005056ac6b20 测试
	private String deletecasvirparam() {
		JSONObject jo = new JSONObject();
		jo.put("eid", this.EID);
		// jo.put("type", 1);
		jo.put("type", 0);
		JSONArray ar = new JSONArray();
		JSONObject t1 = new JSONObject();
		t1.put("department", "10000");
		t1.put("user", "81b9f322-39a0-11e6-8825-005056ac6b20"); // openId
		t1.put("jobTitle", "开发");
		// t1.put("user", "18566684664"); // phone
		JSONObject t2 = new JSONObject();
		t2.put("department", "10000");
		// t2.put("user", "18028752937"); // phone
		t2.put("user", "81b88ebc-39a0-11e6-8825-005056ac6b20"); // openId
		t2.put("jobTitle", "开发");
		ar.add(t1);
		ar.add(t2);
		jo.put("list", ar);
		return jo.toString();
	}

	public void deletecasvir() throws Exception {
		String url = host + "/openaccess/input/person/deletecasvir";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		nvps.add(new BasicNameValuePair("data", enyte(deletecasvirparam())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}
}
