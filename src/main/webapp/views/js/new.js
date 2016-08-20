/*!
 *
 * Date: 2016-08-17
 */

$(document).ready(function() {

	$(".share").click(function() {
		var id = this.value;
		var appId = $('#appId').val();
		var appName = $('#appName').val();
		var title = $('#title_' + id).text();
		var content = $('#desc_' + id).text();
		var webpageUrl = $('#link_' + id).val();
		XuntongJSBridge.call("share", {
			"shareType" : "4",
			"appId" : appId,
			"appName" : appName,
			"lightAppId" : appId,
			"title" : title,
			"content" : content,
			"webpageUrl" : webpageUrl,
		}, function(result) {
			alert("结果：" + JSON.stringify(result));
		});
	});

	$(".shareall").click(function() {
		var id = this.value;
		var appId = $('#appId').val();
		var appName = $('#appName').val();
		var title = $('#title_' + id).text();
		var desc = $('#desc_' + id).text();
		var link = $('#link_' + id).val();
		$.post("/lightdemo/news/shareall", {
			title : title,
			desc : desc,
			link: link
		}, function(result) {
			alert('发送成功');
		});
	});

});
