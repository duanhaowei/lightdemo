<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" href="/lightdemo/views/css/mui.min.css">
</head>

<body>
	<!--下拉刷新容器-->
	<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
		<div class="mui-scroll">
			<!--数据列表-->
			<ul class="mui-table-view mui-table-view-chevron"></ul>
		</div>
	</div>
	<script src="/lightdemo/views/js/jquery-2.1.4.min.js"></script>
	<script src="http://yun.kingdee.com/res/js/qingjs.js"></script>
	<script src="/lightdemo/views/js/mui.min.js"></script>
	<script type="text/javascript" src="/lightdemo/views/js/newlist.js"></script>
</body>
</html>