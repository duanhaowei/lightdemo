<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title></title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link rel="stylesheet" href="/lightdemo/views/css/mui.min.css">
<link rel="stylesheet" href="://yunzhijia.com/openplatform/yunui/dest/yunui.css">
</head>
<body>
	<div class="yzjui-loading-wrap yzjui-wrap">
		<div class="yzjui-loading-container">
			<div class="yzjui-loading">
				<div class="yzjui-loading-inner"></div>
			</div>
			<p class="loading-word">加载中</p>
		</div>
	</div>
</body>
<script src="/lightdemo/views/js/mui.min.js"></script>
<script src="/lightdemo/views/js/new.js"></script>
<script type="text/javascript">
//启用双击监听
mui.init({
	gestureConfig:{
		doubletap:true
	},
	subpages:[{
		url:'/lightdemo/views/jsp/newslist.jsp',
		id:'newslist.jsp',
		styles:{
			top: '0px',
			bottom: '0px',
		}
	}]
});
var contentWebview = null;
document.querySelector('header').addEventListener('doubletap',function () {
	if(contentWebview==null){
		contentWebview = plus.webview.currentWebview().children()[0];
	}
	contentWebview.evalJS("mui('#pullrefresh').pullRefresh().scrollTo(0,0,100)");
});
</script>
</html>