package com.lightdemo.test;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lightdemo.rss.model.News;

public class Test {
	public static void main(String[] args) {
		String ifengNewsUrl = "http://news.ifeng.com/rss/world.xml";
		Test im = new Test();
		im.getIFendNews(ifengNewsUrl);

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
