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
		psa.getPerson();
	}
	/**
	 * 添加部门信息
	 */
	public void addDept(){
		//讯通，改成了需要先同步部门
		String  url = host  + "/openaccess/input/dept/add";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        nvps.add(new BasicNameValuePair("data", enyte(setDepartment())));  
        String reponse = Utils.sendPost(url,nvps);
     	System.out.println(reponse);
	}
	
	/**
	 * 添加人员信息
	 */
	public void addPerson(){
		String  url = host  + "/openaccess/input/person/update";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        nvps.add(new BasicNameValuePair("data", enyte(setPerson())));  
        String reponse = Utils.sendPost(url,nvps);
		System.out.println(reponse);
	}
	/**
	 * 获取最近更新的人员信息
	 */
	public void getPersonAtTime(){
		String  url = host  + "/openaccess/input/person/getAtTime";
		List <NameValuePair> data = new ArrayList <NameValuePair>(); 
		data.add(new BasicNameValuePair("eid",EID));
		data.add(new BasicNameValuePair("time","2015-05-26 01:40:38"));
		data.add(new BasicNameValuePair("begin","0"));
		data.add(new BasicNameValuePair("count","10"));
		
		
		List <NameValuePair> nvps = new ArrayList <NameValuePair>(); 
		nvps.add(new BasicNameValuePair("eid",EID));
		//nvps.add(new BasicNameValuePair("data", enyte(setDepartment())));
		nvps.add(new BasicNameValuePair("data", enyte(data.toString())));
		String reponse = Utils.sendPost(url,nvps);
		System.out.println(reponse);
	}
	
	
	/**
	 * 通过openId或者手机号获取人员信息
	 */
	public void getPerson(){
		String  url = host  + "/openaccess/input/person/get";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        nvps.add(new BasicNameValuePair("data", enyte(getPersonGetParam())));  
        String reponse = Utils.sendPost(url,nvps);
        System.out.println(reponse);
	}
	/**
	 * 更新组织信息
	 */
	public void updateDept(){
		String  url = host  + "/openaccess/input/person/updateDept";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        JSONObject obj = new JSONObject();
        obj.put("eid", EID);
        JSONArray ar = new JSONArray();
        JSONObject oj = new JSONObject();
        oj.put("openId", "");
        oj.put("department", "金蝶集团3");
        ar.add(oj);
        obj.put("persons", ar.toString());//修改3041， 从采销部变成金蝶集团
        nvps.add(new BasicNameValuePair("data", enyte(obj.toString())));  
        String reponse = Utils.sendPost(url,nvps);
        System.out.println(reponse);
	}
	/**
	 * 获取所有用户信息
	 */
	public void getAllPersons(){
		String  url = host  + "/openaccess/input/person/getall";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        nvps.add(new BasicNameValuePair("data", enyte(personGetAllParam())));  
        String reponse = Utils.sendPost(url,nvps);
        System.out.println(reponse);
	}
	/**
	 * 更新用户信息
	 */
	public void updatePersonInfo(){
		
		String  url = host  + "/openaccess/input/person/updateInfo";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        JSONObject json = new JSONObject();
        json.put("eid", EID);
        JSONArray ar = new JSONArray();
        JSONObject per = new JSONObject();
        per.put("openId", this.openId);
        per.put("jobTitle", "实施交付经理人");
        ar.add(per);
        json.put("persons", ar);
        nvps.add(new BasicNameValuePair("data", enyte(json.toString())));  
        String reponse = Utils.sendPost(url,nvps);
		System.out.println(reponse);
	}
	
	
	private String personGetAllParam(){
		JSONObject jo = new JSONObject();
		jo.put("eid", this.EID);
		jo.put("begin", 0);
		jo.put("count", 10);
		return jo.toString();
	}
	
	private String getPersonGetParam(){
		JSONObject jo = new JSONObject();
		jo.put("eid", this.EID);
		jo.put("type", 1);
		String[] s = new String[1];
		s[0] = this.openId;
		jo.put("array", s);
		return jo.toString();
	}
	
	private String setPersonInfo(){
		PersonData2DTO p = new PersonData2DTO();
		p.setEid(this.EID);
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		person.setName("ddddd");
		person.setEmail("456789@oamail.htsc.com.cn");
		//person.photoUrl="";
		person.setPhone("456789");
		//person.phones="";
		person.setIsHidePhone("0");
		person.setPassword("123456");
		//person.status = "1";
		//person.gender ="0";
		person.setDepartment("金蝶中国\\决策部2\\ceo");
		person.setLongName("金蝶中国\\决策部2\\ceo");
		person.setJobTitle("总经理");
		person.setPhones("18905158701");
//		person.setOpenId("595cd2ae-6b0e-11e4-9ba3-000c29e6569e");
		//person.jobTitle="CEO";
		persons.add(person);
		p.setPersons(persons);
		JSONObject jo = JSONObject.fromObject(p);
		
		return jo.toString();
	} 
	
	
	
	private String enyte(String data){
		try {
			String path = PersonSyncApiTest.class.getResource("/").getPath();
			byte[] b = FileUtils.readFileToByteArray(new File(path + EID+".key"));
			PrivateKey restorePublicKey = RSAUtils.restorePrivateKey(b);
			byte[] bytes =  Base64.encodeBase64(RSAUtils.encryptLarger(data.getBytes(), restorePublicKey));
			return new String(bytes,"UTF-8");
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
	
	private String setDepartment(){
		DeptDTO depts = new DeptDTO();
		depts.setEid(EID);
		depts.setDepartments(new String[]{"合作伙伴\\福州思博睿管理咨询有限公司"});
		JSONObject jo = JSONObject.fromObject(depts);
		return jo.toString();
	}
	
	private String setPerson(){
		PersonData2DTO p = new PersonData2DTO();
		p.setEid(this.EID);
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
//		person.setName("ddddd");
//		person.setEmail("hello@hotmail.com");
		//person.photoUrl="";
//		person.setPhone("000000003041");
		//person.phones="";
//		person.setIsHidePhone("0");
//		person.setPassword("iworld2013");
		//person.status = "1";
		person.gender ="0";
//		person.setDepartment("金蝶中国\\决策部2\\ceo");
//		person.setLongName("金蝶中国\\决策部2\\ceo");
		person.setJobTitle("实施经理");
//		person.setPhones("18905158701");
		person.setOpenId(this.openId);
		//person.jobTitle="CEO";
		persons.add(person);
		p.setPersons(persons);
		JSONObject jo = JSONObject.fromObject(p);
		
		return jo.toString();
	}
	
}

