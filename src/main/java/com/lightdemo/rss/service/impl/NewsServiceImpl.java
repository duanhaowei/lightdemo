package com.lightdemo.rss.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lightdemo.rss.dao.NewsDao;
import com.lightdemo.rss.model.News;
import com.lightdemo.rss.service.NewsService;
import com.lightdemo.sync.AdminDto;
import com.lightdemo.sync.PersonSyncApiTest;
import com.lightdemo.util.RSAUtils;
import com.lightdemo.util.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service(value="newsService")
public class NewsServiceImpl implements NewsService {
	Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);
	private NewsDao newsDao;
	
	@Value(value="${APPID}")
	private String APPID = null;
	@Value(value="${XT_SERVERNAME}")
	private String host = null;
	@Value(value="${EID}")
	private String EID = null;
	
	@Autowired
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}

	static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	static DocumentBuilder builder = null;

	@Override
	public List<News> getNewsQq(String newsqq) {
		if (StringUtils.isEmpty(newsqq)) {
			logger.error("ifengNewsUrl is null");
			return null;
		}
		List<News> list = readXML(newsqq);
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
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									nw.setPubDate(sdf.parse(value));
								} else if (name.equals("description")) {
									if (nc.hasChildNodes()) {
										NodeList decList = nc.getChildNodes();
										Node dec = decList.item(0);
										if (dec != null) {
											String decVal = dec.getNodeValue().trim();
											nw.setDescription(decVal);
											value = decVal;
										}
									}
								}
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

	@Override
	public List<News> findByLink(String link) {
		return newsDao.findByLink(link);
	}

	@Override
	public boolean isDeptAdmin(String openId) {
		AdminDto temp = newsDao.getOrgAdminByOpenId(openId);
		if(null != temp) {
			return true;
		}
		return false;
	}

	@Override
	public AdminDto getOrgAdminByOpenId(String openId) {
		return newsDao.getOrgAdminByOpenId(openId);
	}

	@Override
	public void saveOrgAdmin(String openId, String dept) {
		newsDao.saveOrgAdmin(openId, dept);
	}

	@Override
	public List<AdminDto> getOrgAllAdmin(int start, int limit) {
		String str = null;
		try {
			str = getOrgAdmin(start, limit);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getOrgAllAdmin found error ... {}", e);
		}
		logger.info(str);
		if(null != str) {
			JSONArray jsonar =  null;
			JSONObject rejson = JSONObject.fromObject(str);
			if(null == rejson || !rejson.containsKey("success")) {
				return null;
			} else {
				jsonar = JSONArray.fromObject(rejson.get("data"));
				List<AdminDto> list = new ArrayList<AdminDto>(jsonar.size());
				for(int i = 0; i < jsonar.size(); i ++) {
					jsonar.get(i);
					JSONObject obj = jsonar.getJSONObject(i);
					AdminDto dto = new AdminDto();
					dto.setDepartment(obj.getString("department"));
					dto.setOpenId(obj.getString("openId"));
					list.add(dto);
				}
				return list;
			}
		}
		return null;
	}
	
	
	/**
	 * 添加部门信息
	 * @throws Exception 
	 */
	public String getOrgAdmin(int start, int limit) throws Exception{
		//讯通，改成了需要先同步部门
		String  url = host  + "/openaccess/input/company/queryOrgAdmins";
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
        nvps.add(new BasicNameValuePair("nonce", String.valueOf(new Date().getTime())));  
        nvps.add(new BasicNameValuePair("eid", EID));  
        JSONObject json = new JSONObject();
        json.put("begin", start);
        json.put("count", limit);
        nvps.add(new BasicNameValuePair("data", enyte(json.toString())));  
        String reponse = Utils.sendPost(url,nvps);
        return reponse;
	}
	
	
	private String enyte(String data){
		try {
			String path = PersonSyncApiTest.class.getResource("/").getPath();
			byte[] b = FileUtils.readFileToByteArray(new File(path + EID+".key"));
			PrivateKey restorePublicKey = RSAUtils.restorePrivateKey(b);
			byte[] bytes =  Base64.encodeBase64(RSAUtils.encryptLarger(data.getBytes(), restorePublicKey));
			return new String(bytes,"UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<News> findNewsGtDate(Date date) {
		return newsDao.findNewsGtDate(date);
	}
}
