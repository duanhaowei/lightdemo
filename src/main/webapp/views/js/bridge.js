$(document).ready(function(){
	var url = "http://yunxt.lightdemo.com";
	
	$("#translate").click(function(){
		var txt = $("#srctext").val();
		if(txt.trim().length > 0) {
			$.post("dict/translate",
				  {
				    text:txt
				  },
				  function(result){
					  var d = result.data;
					  if(d && d.trim().length > 0) {
						  d = d.replace(/"/g, '');
						  $("#show").text(d);
					  } else{
						  $("#show").text('');
					  }
				  }
			 );
		
			
		} else {
			$("#show").text('请输入值');
		}
	});
	
	$("#hideRightBtn").click(function(){
		XuntongJSBridge.call('hideOptionMenu');//隐藏右上角按钮
	});
	
	$("#showRightBtn").click(function(){
		XuntongJSBridge.call('showOptionMenu');//显示右上角按钮
	});
	
	$("#hideWebViewTitle").click(function(){
		XuntongJSBridge.call('hideWebViewTitle');//隐藏页面标题
	});
	$("#setWebViewTitle").click(function(){
		XuntongJSBridge.call('setWebViewTitle',{'title':'申请加入群聊'});//设置页面标题并显示
	});
	$("#getPersonInfo").click(function(){
		XuntongJSBridge.call('getPersonInfo',{}, function(result){
			
			alert("用户数据："+JSON.stringify(result));
	    });
	});
	$("#getNetworkType").click(function(){
		XuntongJSBridge.call('getNetworkType',
                {},
                function(result){
	       alert("用户网络状态："+JSON.stringify(result));
            }); 

	});
	$("#gotoApp").click(function(){
		XuntongJSBridge.call('gotoApp',
	            {"data":'kdweibo://p?url=https://itunes.apple.com/cn/app/id595672427'},
	        function(result){
	              alert("结果："+JSON.stringify(result));
	    }); 

	});
	
	$("#sinin").click(function(){
		XuntongJSBridge.call('localFunction',{'name':'signin'},function(result){
			alert(JSON.stringify(result));
		});

	});
	$("#createChat").click(function(){
		XuntongJSBridge.call('localFunction',{'name':'createChat'},function(result){
			alert(JSON.stringify(result));
		});
	});
	
	$("#invite").click(function(){
		XuntongJSBridge.call('localFunction',{'name':'invite'},function(result){
			alert(JSON.stringify(result));
		});
	});
	
	$("#selectFile").click(function(){
		XuntongJSBridge.call('selectFile',{},function(result){
			alert(JSON.stringify(result));
		});
	});
	$("#showFile").click(function(){
		
		XuntongJSBridge.call('showFile',{'fileId':'556d5ddc84ae9a5d5689b159',
			'fileName':'perfect.plist','fileExt':'plist',
			'fileTime':'2015-06-02 15:40','fileSize':'1196'},function(result){
			alert(JSON.stringify(result));
		});
		
	});
	$("#showOFile").click(function(){
		
		XuntongJSBridge.call('showFile',{'fileId':'',
			'fileName':'showme.txt','fileExt':'txt',
			'fileTime':'2015-06-02 15:40','fileSize':'1200', 
			'fileDownloadUrl':'http://172.20.200.66:8888/lightdemo/data/showme.txt'},function(result){
		});
		
		
	});
	
	$("#selectPic").click(function(){
		XuntongJSBridge.call('selectPic',{},function(result){});
	});
	
	$("#scanQRCode").click(function(){
		XuntongJSBridge.call('scanQRCode',
	            {'needResult':0,'scanType':['qrCode']},
	            function(result){}
	            );
	});
	
	$("#selectOrg").click(function(){
		XuntongJSBridge.call('selectOrg',{},function(result){
			alert(JSON.stringify(result));
		});
	});
	
	$("#selectDepts").click(function(){
		XuntongJSBridge.call('selectDepts',{},function(result){
			alert(JSON.stringify(result));
		});
	});
	$("#selectPersons").click(function(){
		XuntongJSBridge.call('selectPersons',{ 'isMulti':'true'},function(result){
			alert(JSON.stringify(result));
		});

	});
	$("#selectPerson").click(function(){
		XuntongJSBridge.call('selectPerson',{},function(result){
			alert(JSON.stringify(result));
		});
	});
	
	//获取url中的参数
	function getUrlParam(name) {
	  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	  var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	  if (r != null) return unescape(r[2]); return null; //返回参数值
	}
	//tiketVerification.jsp 
	$("#ticketByJs").click(function(){
		var ticket=getUrlParam('ticket');
//        var reg = new RegExp("(^|&)" + 'ticket' + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
//        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
//        if (r != null) 
//        	ticket = unescape(r[2]); 
        
        alert('ticket:  ' + ticket);
	});
   
	
	$("#accessToken").click(function(){
		var appid= '10121';
		var appSecret= '201508102005';
		var xturl = url;
		var url =xturl + "/openauth2/api/token?grant_type=client_credential&" +
				"appid="+appid+"&secret="+appSecret;
		 $.get(url,function(data,status){
			    alert(JSON.stringify(data));
			    $("#accesstoken").val(data.access_token);
		 });
	});
	
	$("#validAccessToken").click(function(){
		var accesstoken= $("#accesstoken").val();
		var xturl = url;
		var url =xturl + "/openauth2/api/token?grant_type=valid&" +
				"token="+accesstoken;
		 $.get(url, function(data,status){
			    alert("验证成功，APPId是: " + data.appid);
		 });
	});

	$("#getcontext").click(function(){
		var ticket= $("#ticket").val();
//		var ticket = 'fc7b27c2bc5cf6d8847f895d3e5f0eae';
		var accesstoken= $("#accesstoken").val();
		var xturl = url;
		
		//如果没access_token,只传入了ticket,返回对应用户的企业:
		//如果没ticket,只传入了access_token,返回对应的应用:
		
		var url = null;
		if(ticket){
			xturl = xturl + "/openauth2/api/getcontext?ticket="+ticket;
		}
		if(accesstoken){
			xturl = xturl + "&accessToken="+accesstoken;
		}
				
		 $.get(xturl, function(data,status){
			 if(data){
				 alert("当前登录用户数据是 :" + JSON.stringify(data));
			 }else {
				 alert("数据有误");
			 }
			    
		 });
	});
	
	$("#shareImage").click(function(){
		XuntongJSBridge.call("share", {"shareType":"2", "imageData":"iVBORw0KGgoAAAANSUhEUgAAAFIAAABSCAYAAADHLIObAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA2hpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDE0IDc5LjE1MTQ4MSwgMjAxMy8wMy8xMy0xMjowOToxNSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpFNjAxNDZEQjNBMjA2ODExODIyQUMwODY2NjMxNjcxOCIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo5MjlDQTk2QTQxODcxMUU1QjQxOUE0QTNGMzAzMEQ3MyIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo5MjlDQTk2OTQxODcxMUU1QjQxOUE0QTNGMzAzMEQ3MyIgeG1wOkNyZWF0b3JUb29sPSJBZG9iZSBQaG90b3Nob3AgQ1M2IChNYWNpbnRvc2gpIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6RERDNDdCN0Q0MTZGMTFFNUIwRUJDQTUwNTg2QjEwODgiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6RERDNDdCN0U0MTZGMTFFNUIwRUJDQTUwNTg2QjEwODgiLz4gPC9yZGY6RGVzY3Jp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","appId":"103456","appName":"测试消息转发"}, 
				//XuntongJSBridge.call("share", {"shareType":"2", "imageData":"base64数据","appId":"103456","appName":"测试消息转发"}, 
		function(result) {
		
		});
	});
	$("#useragent").click(function(){
		var flg = checkUserAgent();
	});
	
	function checkUserAgent(){
		var userAgent = window.navigator.userAgent;
		$("#userAgent").val(userAgent);
		if(userAgent.indexOf("clientId:10201")>-1) {
			return false;
		} else {
			return true;
		}
	}
	
	
})