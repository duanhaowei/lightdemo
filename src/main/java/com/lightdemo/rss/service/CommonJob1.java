package com.lightdemo.rss.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.lightdemo.rss.model.News;
@Component("deptAdminJob")
public class CommonJob1 {
	private int timeout;
	private static int i = 0;

	//调度工厂实例化后，经过timeout时间开始执行调度
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

//	private NewsService newsService;
//	public void setNewsService(NewsService newsService) {
//		this.newsService = newsService;
//	}


	public String doIt() {
		int start = 0;
		int limit = 10;
		// List<AdminDto> list = newsService.getOrgAllAdmin(start, limit);
		// while(list.size() > 0) {
		// for(AdminDto tem : list) {
		// String openId = tem.getOpenId();
		// AdminDto temp = newsService.getOrgAdminByOpenId(openId);
		// if(null == temp) {
		// String dept = tem.getDepartment();
		// newsService.saveOrgAdmin(openId, dept);
		// }
		// }
		// list = newsService.getOrgAllAdmin(start, limit);
		// start = start + 10;
		// }
		System.out.println(timeout++);
		try {
//			News news = newsService.findById("056c484b-5758-4447-8275-552b3881a25b");
//			if(null != news) {
//				System.out.println(news.getDescription());
//			}
			
			System.out.println("Hello....");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
