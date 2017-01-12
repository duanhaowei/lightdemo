<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<link href="/lightdemo/views/css/mui.min.css" rel="stylesheet" />
	</head>
	<body>
		<script src="/lightdemo/views/js/mui.min.js"></script>
		<script src="/lightdemo/views/js/jquery-2.1.4.min.js"></script>
		<script type="text/javascript" src="/lightdemo/views/js/new.js" ></script>
		<script src="http://yun.kingdee.com/res/js/qingjs.js"></script>
		<input id="appId" type="hidden" value="${appId}">
		<input id="appName" type="hidden" value="${appName}">
		<div class="mui-content">
			<ul class="mui-table-view mui-table-view-chevron">
				<li class="mui-table-view-cell">
					国内最新资讯
				</li>
			</ul>
		</div>
		
		<!-- <div class="mui-slider">
			<div id="slider" class="mui-slider-group">
				<div class="mui-slider-item"><a href="#"><img src="/lightdemo/views/img/shuijiao.jpg" /></a></div>
				<div class="mui-slider-item"><a href="#"><img src="/lightdemo/views/img/yuantiao.jpg" /></a></div>
			</div>
			<div id="slider-ind" class="mui-slider-indicator mui-text-right">
			</div>
		</div> -->
			<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
				<div class="mui-scroll">
					<!--数据列表-->
					<ul class="mui-table-view mui-table-view-chevron">
					</ul>
				</div>
			</div>
		<div>
			
		<%-- 	<c:forEach items="${list}" var="item">
				<div class="mui-card">
					<!--页眉，放置标题-->
					<div id="title_${item.id}" class="mui-card-header">${item.title}</div>
					<!--内容区-->
					<div id="desc_${item.id}" class="mui-card-content-inner">${item.description}<a href="${item.link}">【点击查看全文】</a></div>
					<!--页脚，放置补充信息或支持的操作-->
					<div class="mui-card-footer">
						<div>${item.pubDateStr}</div>
						<div><button class="share" value="${item.id}">分享</button></div>
						<c:if test="${admin == true}">
						   <div><button class="shareall" value="${item.id}">广播</button></div>
						</c:if>
						
					</div>
					<input type="hidden" id="link_${item.id}" value="${item.link}">
				</div>
			</c:forEach> --%>
		</div>
	</body>

</html>