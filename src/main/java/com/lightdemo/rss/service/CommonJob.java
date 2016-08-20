package com.lightdemo.rss.service;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	Logger logger = LoggerFactory.getLogger(CommonJob.class);
	private NewsService newsService;
	private String newsqqurl;
	
	public void setNewsqqurl(String newsqqurl) {
		this.newsqqurl = newsqqurl;
	}


	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}


	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("commonJob start ....");
		logger.debug("commonJob start ....");
		try {
			/**调度获取管理员**/
			int start = 0;
			int limit = 10;
			List<AdminDto> list = newsService.getOrgAllAdmin(start, limit);
			logger.info("admin size ....{}", list.size());
			while (null != list && list.size() > 0) {
				for (AdminDto tem : list) {
					String openId = tem.getOpenId();
					AdminDto temp = newsService.getOrgAdminByOpenId(openId);
					if (null == temp) {
						String dept = tem.getDepartment();
						newsService.saveOrgAdmin(openId, dept);
					}
				}
				start = start + 10;
				list = newsService.getOrgAllAdmin(start, limit);
			}
			/**调度获取新闻保存到表中**/
			List<News> newslist = newsService.getNewsQq(newsqqurl);
			for(News temp : newslist) {
				String link = temp.getLink();
				List<News> ex = newsService.findByLink(link);
				if(ex.size() == 0) {
					newsService.save(temp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("commonJob error ....{}",e);
		}
		logger.info("commonJob end ....");
		logger.debug("commonJob end ....");
	}

}
