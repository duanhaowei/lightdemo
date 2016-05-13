<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<script type="text/javascript" src="../js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="../js/index.js" > </script>
</head>

<body>
	<div style="text-align:center; margin-top:100px">
	<button id="showTicket">显示ticket</button>
	<div id="showTic"></div>
	
	<div style="text-align:center; margin-top:20px"></div>
	
	<button id="getPer">通过后台获取用户信息</button>
	<div id="showPer"></div>
	</div>
</body>

</html>