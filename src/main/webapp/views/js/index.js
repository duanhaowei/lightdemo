$(document).ready(function(){
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
	var getUrlParam = function(name) {
	  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	  var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	  if (r != null) return unescape(r[2]); return null; //返回参数值
	};
	
	$("#showTicket").click(function(){
		 var ticket = getUrlParam('ticket');
		 $("#showTic").text(ticket);
	});
	$("#getPer").click(function(){
		 var ticket = getUrlParam('ticket');
		 $.get("/lightdemo/sync/getper",
			  {
			    ticket:ticket
			  },
			  function(result){
				  $("#showPer").text(result);
			  }
		 );
	});
	$("#sendmsg").click(function(){
		var txt = $("#content").val();
		XuntongJSBridge.call('selectPersons',{ 'isMulti':'true'},function(result){
			if(result.success == true){
				var perArr='';
				var persons = result.data.persons;
				for(var i = 0; i < persons.length; i ++){
					var temp = persons[i];
					perArr = perArr + temp.personId+",";
					
				}
				perArr =perArr.substring(0, perArr.length-1);
				alert(JSON.stringify(persons));
				$.post("/lightdemo/msg/postmsg",
				  {
					text:txt,
				    users:perArr
				  },
				  function(result){
					  alert('发送成功');
				  }
				);

			} else {
				alert("false");
			}
			
			
		});
		
	});
	
})