package com.lightdemo.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lightdemo.rss.model.News;

public class Test {
	public static void main(String[] args) {
		/*int partnerType = 1;
		String orgLongName = null;
		String companyName = "测试工作圈";
		String PARTNERNAME = "商务伙伴";
		String department = "";
		if(partnerType == 1) {
			orgLongName = companyName+ "!" + PARTNERNAME + "!" + department;
		} else {
			orgLongName = companyName + "!" + department;
		}
		if(StringUtils.isEmpty(department)) {
			orgLongName = orgLongName.substring(0, orgLongName.length()-1);
		}
		System.out.println(orgLongName);*/
		Map<String, String> map = new HashMap<String, String>();
		map.put("hello", "java");
		map.put("hello1", "C");
		map.put("hello2", "php");
		System.out.println(map);
	}
	
	
	static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	static DocumentBuilder builder = null;

	public List<News> getIFendNews(String ifengNewsUrl) {
		if (StringUtils.isEmpty(ifengNewsUrl)) {
			return null;
		}
		File file = new File(ifengNewsUrl);
		if (!file.exists()) {
			return null;
		}
		readXML(file);
		return null;
	}

	public void readXML(File file) {
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			Element rootElement = document.getDocumentElement();

			NodeList list = rootElement.getElementsByTagName("Header");
			Element element = (Element) list.item(0);
			System.out.println(element.getChildNodes().item(0).getNodeValue());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
