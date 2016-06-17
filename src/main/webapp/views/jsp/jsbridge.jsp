<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<%
 		String path = request.getContextPath();
 		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	%>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<script type="text/javascript" src="<%=basePath%>views/js/jquery-2.1.4.min.js"></script>
	
	<script src=”http://yun.kingdee.com/res/js/qingjs.js”></script>
	
		<!-- 新 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="<%=basePath%>views/bootstrap-3.3.5-dist/css/bootstrap.min.css">
	
	<!-- 可选的Bootstrap主题文件（一般不用引入） 
	<link rel="stylesheet" href="./views/bootstrap-3.3.5-dist/css/bootstrap-theme.min.css">
	-->
	<link href="<%=basePath%>views/css/starter-template.css" rel="stylesheet">
	
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="<%=basePath%>views/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
	
	<script type="text/javascript" src="<%=basePath%>views/js/bridge.js?201508131439" > </script>
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          	<a class="navbar-brand" href="#">JSBradge测试</a>
        </div>
      </div>
    </nav>
    <div class="container">

      <div class="starter-template">
        <div class="btn-group-vertical" role="group" aria-label="...">
        	<button type="button" class="btn btn-default" id="hideRightBtn">隐藏右上角按钮</button>
  			<button type="button" class="btn btn-default" id="showRightBtn">显示右上角按钮</button>
  			
  			<button type="button" class="btn btn-default" id="hideWebViewTitle">隐藏页面标题</button>
  			<button type="button" class="btn btn-default" id="setWebViewTitle">设置页面标题并显示</button>
 			<button type="button" class="btn btn-default" id="getPersonInfo">获取当期用户信息</button>
  			<button type="button" class="btn btn-default" id="getNetworkType">获取用户网络状态</button>
  			<button type="button" class="btn btn-default" id="gotoApp">打开第三方应用</button>
  			<button type="button" class="btn btn-default" id="sinin">调用云之家签到</button>
  			<button type="button" class="btn btn-default" id="createChat">创建会话</button>
  			<button type="button" class="btn btn-default" id="invite">邀请</button>
  			<button type="button" class="btn btn-default" id="selectFile">选择文件</button>
  			<button type="button" class="btn btn-default" id="showFile">显示文件</button>
  			<button type="button" class="btn btn-default" id="selectPic">选择图片</button>
  			<button type="button" class="btn btn-default" id="shareImage">分享图片</button>
  			
  			
  			<button type="button" class="btn btn-default" id="scanQRCode">扫一扫</button>
  			<button type="button" class="btn btn-default" id="selectOrg">选择组织</button>
  			
  			<button type="button" class="btn btn-default" id="selectDepts">选择部门</button>
  			<button type="button" class="btn btn-default" id="selectPersons">选择多人</button>
  			<button type="button" class="btn btn-default" id="selectPerson">选择单人</button>
		</div>
      </div>

    </div>
</body>

</html>