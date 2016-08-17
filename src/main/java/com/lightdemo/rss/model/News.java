package com.lightdemo.rss.model;

public class News {
	private String id;
	private String title;
	private String link;
	private String pubDate;
	private String description;
	public News() {
		super();
	}
	
	public News(String id, String title, String link, String pubDate, String description) {
		super();
		this.id = id;
		this.title = title;
		this.link = link;
		this.pubDate = pubDate;
		this.description = description;
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
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
