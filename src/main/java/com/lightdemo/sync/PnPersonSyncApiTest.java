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
 * 商务伙伴组织人员同步测试类
 * @author cjqbrave@163.com
 *
 */
public class PnPersonSyncApiTest {
	private String EID;
	private String host;
	private String openId;

	public PnPersonSyncApiTest() {
		Common cm = (Common) PropertiesUtil.loadCommonProperties("common.properties", Common.class);
		this.EID = cm.getEID();
		this.host = cm.getXT_SERVERNAME();
		this.openId = cm.getOPENIDS();
	}
	
	public static void main(String[] args) {
		PnPersonSyncApiTest psa = new PnPersonSyncApiTest();
		try {
//			psa.addDept();
//			psa.deleteDept();
//			psa.updateDept();
//			psa.addPerson();
//			psa.updatePersonInfo();
			psa.personUpdateDept();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteDept() throws Exception {

		String url = host + "/openaccess/input/pndept/delete";
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));
		nvps.add(new BasicNameValuePair("eid", EID));
		JSONObject json = new JSONObject();
		json.put("eid", EID);
		json.put("departments", new String[]{"山东\\烟台烟台"});
		nvps.add(new BasicNameValuePair("data", enyte(json.toString())));
		String reponse = Utils.sendPost(url, nvps);
		System.out.println(reponse);
	}

	/**
	 * 添加部门信息
	 * @throws Exception 
	 */
	public void addDept() throws Exception{
		//讯通，改成了需要先同步部门
		String  url = host  + "/openaccess/input/pndept/add";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        JSONObject json = new JSONObject();
		json.put("eid", EID);
		json.put("departments", new String[]{"山东\\烟台"});
        nvps.add(new BasicNameValuePair("data", enyte(json.toString())));  
        String reponse = Utils.sendPost(url,nvps);
     	System.out.println(reponse);
	}
	
	private String setPerson(){
		PersonData2DTO p = new PersonData2DTO();
		p.setEid(this.EID);
		List<Person> persons = new ArrayList<Person>();
		Person person = new Person();
		person.setName("阳光灿烂");
//		person.setEmail("hello@hotmail.com");
		//person.photoUrl="";
		person.setPhone("18666930548");
		//person.phones="";
//		person.setIsHidePhone("0");
//		person.setPassword("iworld2013");
		//person.status = "1";
		person.gender ="0";
		person.setDepartment("山东");
//		person.setLongName("金蝶中国\\决策部2\\ceo");
		person.setJobTitle("实施经理");
//		person.setOpenId(this.openId);
		//person.jobTitle="CEO";
		persons.add(person);
		p.setPersons(persons);
		JSONObject jo = JSONObject.fromObject(p);
		
		return jo.toString();
	}
	/**
	 * 添加人员信息
	 * @throws Exception 
	 */
	public void addPerson() throws Exception{
		String  url = host  + "/openaccess/input/pnperson/add";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        nvps.add(new BasicNameValuePair("data", enyte(setPerson())));  
        String reponse = Utils.sendPost(url,nvps);
		System.out.println(reponse);
	}
	/**
	 * 获取最近更新的人员信息
	 * @throws Exception 
	 */
	public void getPersonAtTime() throws Exception{
		String  url = host  + "/openaccess/input/pnperson/getAtTime";
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
	 * @throws Exception 
	 */
	public void getPerson() throws Exception{
		String  url = host  + "/openaccess/input/pnperson/get";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        nvps.add(new BasicNameValuePair("data", enyte(getPersonGetParam())));  
        String reponse = Utils.sendPost(url,nvps);
        System.out.println(reponse);
	}
	/**
	 * 更新组织信息
	 * @throws Exception 
	 */
	public void updateDept() throws Exception{
		String  url = host  + "/openaccess/input/pndept/update";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        JSONObject obj = new JSONObject();
        JSONArray ar = new JSONArray();
        
        JSONObject oj = new JSONObject();
//        oj.put("department", "山东\\威海");
        oj.put("department", "山东\\威海");
        oj.put("todepartment", "山东\\青岛");
        ar.add(oj);
        
        obj.put("eid", EID);
        obj.put("departments", ar.toString());
        nvps.add(new BasicNameValuePair("data", enyte(obj.toString())));  
        String reponse = Utils.sendPost(url,nvps);
        System.out.println(reponse);
	}
	/**
	 * 更新人员的组织
	 * @throws Exception
	 */
	public void personUpdateDept() throws Exception{
		String  url = host  + "/openaccess/input/pnperson/updateDept";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        JSONObject obj = new JSONObject();
        obj.put("eid", EID);
        JSONArray ar = new JSONArray();
        JSONObject oj = new JSONObject();
        oj.put("openId", this.openId);
        oj.put("department", "山东");
        ar.add(oj);
        obj.put("persons", ar.toString());//修改3041， 从采销部变成金蝶集团
        nvps.add(new BasicNameValuePair("data", enyte(obj.toString())));  
        String reponse = Utils.sendPost(url,nvps);
        System.out.println(reponse);
	}
	/**
	 * 获取所有用户信息
	 * @throws Exception 
	 */
	public void getAllPersons() throws Exception{
		String  url = host  + "/openaccess/input/pnperson/getall";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        nvps.add(new BasicNameValuePair("data", enyte(personGetAllParam())));  
        String reponse = Utils.sendPost(url,nvps);
        System.out.println(reponse);
	}
	/**
	 * 更新用户信息
	 * @throws Exception 
	 */
	public void updatePersonInfo() throws Exception{
		
		String  url = host  + "/openaccess/input/pnperson/updateInfo";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        JSONObject json = new JSONObject();
        json.put("eid", EID);
        JSONArray ar = new JSONArray();
        JSONObject per = new JSONObject();
        per.put("openId", this.openId);
        per.put("jobTitle", "实施交付经理人");
        per.put("name", "猪八戒");
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
			String path = PnPersonSyncApiTest.class.getResource("/").getPath();
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
		depts.setDepartments(new String[]{});
		JSONObject jo = JSONObject.fromObject(depts);
		return jo.toString();
	}
	
}

