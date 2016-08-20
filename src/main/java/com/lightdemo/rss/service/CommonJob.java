package com.lightdemo.rss.service;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lightdemo.rss.model.News;
import com.lightdemo.sync.AdminDto;
/**
 * 方式一的实现方式
 * @author Administrator
 *
 */
public class CommonJob extends QuartzJobBean {
	
	private NewsService newsService;
	private int timeout;

	/**
	 * Setter called after the ExampleJob is instantiated with the value from
	 * the JobDetailFactoryBean (5)
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}


	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
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
			News news = newsService.findById("056c484b-5758-4447-8275-552b3881a25b");
			if(null != news) {
				System.out.println(news.getDescription());
			}
			
			System.out.println("Hello....");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
