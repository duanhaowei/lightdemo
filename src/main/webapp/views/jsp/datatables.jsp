<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>Datatables表格</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">

<link href="bootstrap-3.3.0-dist/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap-3.3.0-dist/dist/css/bootstrap-responsive.min.css" rel="stylesheet">

<!--标准mui.css-->
<link rel="stylesheet" href="/lightdemo/views/css/mui.min.css">
<!--App自定义的css-->
<link rel="stylesheet" type="text/css"
	href="/lightdemo/views/css/app.css" />

<link rel="stylesheet" type="text/css"
	href="/lightdemo/views/css/jquery.dataTables.min.css">



<style>
.chart {
	height: 200px;
	margin: 0px;
	padding: 0px;
}

h5 {
	margin-top: 30px;
	font-weight: bold;
}

h5:first-child {
	margin-top: 15px;
}
</style>
</head>

<body>
	<header class="mui-bar mui-bar-nav">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">Datatables表格</h1>
	</header>
	<div>
		<table id="example" class="table table-striped table-bordered table-hover table-full-width" cellspacing="0" width="100%"></table>
	</div>
	<script src="/lightdemo/views/js/mui.min.js"></script>
	<script src="/lightdemo/views/js/jquery-2.1.4.min.js"></script>
	<script src="/lightdemo/views/js/echarts-all.js"></script>
	<script type="text/javascript" charset="utf8"
		src="/lightdemo/views/js/jquery.dataTables.min.js"></script>
	<script>
			var model = [{
				"mDataProp":"title",
				"sTitle":"标题",
				"sDefaultContent":"",
				"sClass":"center"
			}, {
				"mDataProp":"link",
				"sTitle":"连接",
				"sDefaultContent":"",
				"sClass":"center"
			}, {
				"mDataProp":"pubDate",
				"sTitle":"发送时间",
				"sDefaultContent":"",
				"sClass":"center"
			}, {
				"mDataProp":"description",
				"sTitle":"描述",
				"sDefaultContent":"",
				"sClass":"center"
			}];
			var url= "/lightdemo/news/getdatatablesnews";
			var tableConfig = {
					"bProcessing":true,
					"bServerSide":true,
					"bAutoWidth":true,
					"bPaginate":true,
					"aoColumns":model,
					"isDisplayLength":10,
					"ajax": url
			};
			 $('#example').dataTable(tableConfig);

		       $('#example').on('click', ' tbody td .row-details',
		               function() {
		                   var nTr = $(this).parents('tr')[0];
		                   if (oTable.fnIsOpen(nTr)) //判断是否已打开
		                   {
		                       /* This row is already open - close it */
		                       $(this).addClass("row-details-close").removeClass("row-details-open");
		                       oTable.fnClose(nTr);
		                   } else {
		                       /* Open this row */
		                       $(this).addClass("row-details-open").removeClass("row-details-close");
		                       //  alert($(this).attr("data_id"));
		                       //oTable.fnOpen( nTr,
		                       // 调用方法显示详细信息 data_id为自定义属性 存放配置ID
		                       fnFormatDetails(nTr, $(this).attr("data_id"));
		                   }
		               });
		</script>
</body>

</html>