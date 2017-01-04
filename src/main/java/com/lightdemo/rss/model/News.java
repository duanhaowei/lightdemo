package com.lightdemo.rss.model;

import java.util.Date;

public class News {
	private String id;
	private String title;
	private String link;
	private Date pubDate;
	private String pubDateStr;
	private String description;
	public News() {
		super();
	}
	
	public News(String id, String title, String link, 
			Date pubDate, String description, String pubDateStr) {
		super();
		this.id = id;
		this.title = title;
		this.link = link;
		this.pubDate = pubDate;
		this.description = description;
		this.pubDateStr = pubDateStr;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubDateStr() {
		return pubDateStr;
	}

	public void setPubDateStr(String pubDateStr) {
		this.pubDateStr = pubDateStr;
	}
	
}
