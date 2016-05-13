<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
	<script type="text/javascript" src="./views/js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="./views/js/index.js" > </script>
</head>

<body>
	<div style="text-align:center; margin-top:100px">
	<form>
		<p>
			原文：<input id="srctext" type="text"/>
		</p>
		<input id="translate" type="button" value="确认"/>
	</form>
	译文：
	<div style="text-align:center; margin-top:20px"></div>
	<div id="show"></div>
	</div>
</body>

</html>
