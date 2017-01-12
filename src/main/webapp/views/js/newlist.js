/*!
 *
 * Date: 2016-08-17
 */

$(document).ready(function() {
	mui.init({
		pullRefresh: {
			container: '#pullrefresh',
			down: {
				callback: pulldownRefresh
			},
			up: {
				contentrefresh: '正在加载...',
				callback: pullupRefresh
			}
		}
	});
	function pulldownRefresh() {
		setTimeout(function() {
			var table = document.body.querySelector('.mui-table-view');
			var cells = document.body.querySelectorAll('.mui-table-view-cell');
			/* for (var i = cells.length, len = i + 3; i < len; i++) {
				var li = document.createElement('li');
				li.className = 'mui-table-view-cell';
				li.innerHTML = '<a class="mui-navigate-right">Item ' + (i + 1) + '</a>';
				//下拉刷新，新纪录插到最前面；
				table.insertBefore(li, table.firstChild);
			} */
			mui('#pullrefresh').pullRefresh().endPulldownToRefresh(); //refresh completed
		}, 1500);
	}
	var count = 0;
	function pullupRefresh() {
		setTimeout(function() {
			mui('#pullrefresh').pullRefresh().endPullupToRefresh((++count > 2)); //参数为true代表没有更多数据了。
			var table = document.body.querySelector('.mui-table-view');
			var cells = document.body.querySelectorAll('.mui-table-view-cell');
			var start = 0;
			if(table.children) {
				start = table.children.length;
			}
			$.get("/lightdemo/news/nextshownew", {
				start : start,
				limit: 20
			}, function(result) {
				var result = JSON.parse(result);
				if(result.list) {
					var list = result.list;				

					for (var i = 0, len = i + 20; i < len; i++) {
						var item = list[i];
						var mcard = document.createElement('div');
						mcard.className = 'mui-card';
						var child = '<div id="title_${item.id}" class="mui-card-header">'+item.title+'</div>'
							+'<div id="desc_'+item.id+'" class="mui-card-content-inner">'
							+item.description+'<a href="'+item.link+'">【点击查看全文】</a></div>'
							+'<div class="mui-card-footer">'
							+'<div>'+item.pubDateStr+'</div>'
							+'<div><button class="share" value="'+item.id+'">分享</button></div>';
							if(result.admin == true) {
								child = child+'<div><button class="shareall" value="'+item.id+'">广播</button></div>';
							}
							child = child +'</div>'
							+'<input type="hidden" id="link_'+item.id+'" value="'+item.link+'">';
						mcard.innerHTML=child;
						table.appendChild(mcard);
					}
				}
			});
		}, 1500);
	}
	if (mui.os.plus) {
		mui.plusReady(function() {
			setTimeout(function() {
				mui('#pullrefresh').pullRefresh().pullupLoading();
			}, 1000);
		});
	} else {
		mui.ready(function() {
			mui('#pullrefresh').pullRefresh().pullupLoading();
		});
	}
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
