package com.lightdemo.rss.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lightdemo.rss.dao.NewsDao;
import com.lightdemo.rss.model.News;
import com.lightdemo.rss.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
	Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);
	private NewsDao newsDao;
	
	@Autowired
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	static DocumentBuilder builder = null;

	@Override
	public List<News> getIFendNews(String ifengNewsUrl) {
		if (StringUtils.isEmpty(ifengNewsUrl)) {
			logger.error("ifengNewsUrl is null");
			return null;
		}
		List<News> list = readXML(ifengNewsUrl);
		return list;
	}

	public List<News> readXML(String ifengNewsUrl) {
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(ifengNewsUrl);
			Element rootElement = document.getDocumentElement();
			NodeList itemllist = rootElement.getElementsByTagName("item");
			List<News> list = new ArrayList<News>();
			for (int i = 0; i < itemllist.getLength(); i++) {
				Node node = itemllist.item(i);
				if (node.hasChildNodes()) {
					NodeList clist = node.getChildNodes();
					News nw = new News();
					nw.setId(UUID.randomUUID().toString());
					for (int j = 0; j < clist.getLength(); j++) {
						Node nc = clist.item(j);
						if (null != nc) {
							String name = nc.getNodeName();
							if (null != name && !name.equals("#text") && nc.getFirstChild() != null) {
								String value = nc.getFirstChild().getNodeValue().trim();
								if (name.equals("title")) {
									nw.setTitle(value);
								} else if (name.equals("link")) {
									nw.setLink(value);
								} else if (name.equals("author")) {
								} else if (name.equals("guid")) {

								} else if (name.equals("pubDate")) {
									nw.setPubDate(value);
								} else if (name.equals("description")) {
									if (nc.hasChildNodes()) {
										NodeList decList = nc.getChildNodes();
										Node dec = decList.item(1);
										if (dec != null) {
											String decVal = dec.getNodeValue().trim();
											nw.setDescription(decVal);
											value = decVal;
										}
									}
								}
								System.out.print(name + ":" + value + "\t");

							}

						}
					}
					list.add(nw);
				}
			}
			return list;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error("file is not exit {}", e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("read error", e);
		} catch (Exception e) {
			logger.error("read xml found error .. {}", e);
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String ifengNewsUrl = "http://news.ifeng.com/rss/world.xml";
		System.out.println("执行开始");
		NewsServiceImpl im = new NewsServiceImpl();
		im.getIFendNews(ifengNewsUrl);
		System.out.println("执行结束");
	}

	@Override
	public void save(News news) {
		newsDao.save(news);
	}

	@Override
	public News findById(String id) {
		// TODO Auto-generated method stub
		return newsDao.findNews(id);
	}
}
