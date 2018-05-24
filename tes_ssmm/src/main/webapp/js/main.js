$(function(){
	// 注册面板关闭事件
	$(".panel .close").click(function(){
		close_panel(this);
	});
	// 注册面板重置事件
	$(".glyphicon-refresh").click(function(){
		reset_panel();
	});
	
	$("#edit_pwd_button").click(function(){
		alert();
		edit_pwd();
	});
	
	/*今日动态*/
	//在线用户
	online_user();
	//注册用户
	register_user();
	//上传视频
	uploadVideo();
	//收藏视频
	collectVideo();
	//购买视频
	buyVideo();
	//最新活动
	newActivity();
    //最新评论
	newComment();
    
	
	
	/*用户统计图*/
	// 画用户统计饼状图
	draw_user();
	
	/*视频统计图*/
	// 画视频统计环行图
	draw_video();
	
	
	/*用户增长率 */
	UserGrowthRate();
	/*视频增长率 */
	VideoGrowthRate();
	
	
    /*视频收藏排行*/
	collectionVideoOrder();
	
	/*视频购买排行*/
	buyVideoOrder();
});


/*今日动态*/

/**
 * 在线用户
 */
function online_user(){
	$.ajax({
		url:basePath+"main/online",
		type:"get",
		dataType:"json",
		success:function(online_num){	
			$("#onlineuser").html("");	
			$("#onlineuser").append(online_num);		
		},
		error:function(){
			alert("请求失败");
		}
	});

}
//修改密码：
function edit_pwd(){
	//获取修改密码中的旧密码，新密码和确认密码
	var oldPassword=$("#oldPassword").val();
	var newPassword=$("#newPassword").val();
	var inputPassword=$("#inputPassword").val();
	//alert(oldPassword+newPassword+inputPassword);
	if (newPassword==inputPassword){
		$.ajax({
			url:basePath+"user/edit_pwd/newPassword/"+newPassword+"/oldPassword/"+oldPassword,
			type:"post",
			dataType:"json",
			success:function(result){
				//获取Sevlet返回的json数据
				if(result.status==0){
					//alert(result.message);
					window.location.href="index.html";

				}else if(result.status==1){
					alert(result.message);
				}else if(result.status==2){
					alert(result.message);
				}
				//关掉当前的model框
				$("#editPasswd").modal("toggle");
			},
			error:function(){
				alert("请求失败");
			}		
		});
	}else{
		alert("新密码和确认密码不一致，请重新输入。");
	}
}
/**
 * 注册用户
 */
function register_user(){
	$.ajax({
		url:basePath+"main/findAllUserCount",
		type:"get",
		dataType:"json",
		success:function(register_num){	
			$("#registeruser").html("");	
			$("#registeruser").append(register_num);		
		},
		error:function(){
			alert("请求失败");
		}
	});

}

/**
 * 上传视频
 */
function uploadVideo(){
	$.ajax({
		url:basePath+"main/findAllUploadedVideoCount",
		type:"get",
		dataType:"json",
		success:function(uploadvideo_num){	
			$("#uploadvideo").html("");	
			$("#uploadvideo").append(uploadvideo_num);		
		},
		error:function(){
			alert("请求失败");
		}
	});
	
}
 
/**
 * 收藏视频
 */
function collectVideo(){
	$.ajax({
		url:basePath+"main/findAllCollectionVideoCount",
		type:"get",
		dataType:"json",
		success:function(collectvideo_num){	
			$("#collectvideo").html("");	
			$("#collectvideo").append(collectvideo_num);		
		},
		error:function(){
			alert("请求失败");
		}
	});

}

/**
 * 购买视频
 */
function buyVideo(){
	$.ajax({
		url:basePath+"main/findAllPurchasedVideoCount",
		type:"get",
		dataType:"json",
		success:function(buyvideo_num){	
			$("#buyvideo").html("");	
			$("#buyvideo").append(buyvideo_num);		
		},
		error:function(){
			alert("请求失败");
		}
	});

}

/**
 * 最新活动
 */
function newActivity(){
	$.ajax({
		url:basePath+"main/findAllNewActivityCount",
		type:"get",
		dataType:"json",
		success:function(newactivity_num){	
			$("#newactivity").html("");	
			$("#newactivity").append(newactivity_num);		
		},
		error:function(){
			alert("请求失败");
		}
	});

}

/**
 * 最新评论
 */
function newComment(){
	$.ajax({
		url:basePath+"main/findAllNewCommentCount",
		type:"get",
		dataType:"json",
		success:function(newcomment_num){	
			$("#newcomment").html("");	
			$("#newcomment").append(newcomment_num);		
		},
		error:function(){
			alert("请求失败");
		}
	});

}





// 画用户统计饼状图
function draw_user() {
	$.ajax({
		url:basePath+"main/findUserCount",
		type:"get",
		dataType:"json",
		success:function(result){
			var chart=result.data;
				
			var ctx = document.getElementById("chart-user").getContext("2d");
			
			window.myDoughnut = new Chart(ctx).Pie(chart, {responsive : true});
		},
		error:function(){
			alert("请求失败");
		}
	});
}

// 画视频统计环行图
function draw_video() {
	$.ajax({
		url:basePath+"main/findVideoCount",
		type:"get",
		dataType:"json",
		success:function(result){
			var chart=result.data;

			var ctx = document.getElementById("chart-video").getContext("2d");
			
			window.myDoughnut = new Chart(ctx).Doughnut(chart, {responsive : true});
		},
		error:function(){
			alert("请求失败");
		}
	});
}





/*用户增长率 */
function UserGrowthRate(){
	$.ajax({
		url:basePath+"main/userGrowthRate",
		type:"get",
		dataType:"json",
		success:function(mains){	
			$("#userMonthBasis").html("");	
			$("#userSameMonth").html("");	
			$("#userQuarterBasis").html("");	
			$("#userSameQuarter").html("");	
			
			$(mains).each(function(n,value){
				//n:循环的第几个元素，从0开始
				//value：循环的当前元素，实际上就是mains的对象

				var userMonthBasis = value.userMonthBasis;
				var userSameMonth = value.userSameMonth;
				var userQuarterBasis = value.userQuarterBasis;
				var userSameQuarter = value.userSameQuarter;

				$("#userMonthBasis").append(userMonthBasis);	
				$("#userSameMonth").append(userSameMonth);	
				$("#userQuarterBasis").append(userQuarterBasis);	
				$("#userSameQuarter").append(userSameQuarter);	

			});
		},
		error:function(){
			alert("请求失败");
		}
	});
}

/*视频增长率 */
function VideoGrowthRate(){
	$.ajax({
		url:basePath+"main/videoGrowthRate",
		type:"get",
		dataType:"json",
		success:function(mains){	
			$("#videoMonthBasis").html("");	
			$("#videoSameMonth").html("");	
			$("#videoQuarterBasis").html("");	
			$("#videoSameQuarter").html("");	
			
			$(mains).each(function(n,value){
				//n:循环的第几个元素，从0开始
				//value：循环的当前元素，实际上就是mains的对象

				var videoMonthBasis = value.videoMonthBasis;
				var videoSameMonth = value.videoSameMonth;
				var videoQuarterBasis = value.videoQuarterBasis;
				var videoSameQuarter = value.videoSameQuarter;
				
				$("#videoMonthBasis").append(videoMonthBasis);	
				$("#videoSameMonth").append(videoSameMonth);	
				$("#videoQuarterBasis").append(videoQuarterBasis);	
				$("#videoSameQuarter").append(videoSameQuarter);	

			});
		},
		error:function(){
			alert("请求失败");
		}
	});
}





/*视频收藏排行*/
function collectionVideoOrder(){
	$.ajax({
		url:basePath+"main/collectionVideoOrder",
		type:"get",
		dataType:"json",
		success:function(mains){	
			$("#collectionVideoOrder ul").html("");	
				
			$(mains).each(function(n,value){
				//n:循环的第几个元素，从0开始
				//value：循环的当前元素，实际上就是mains的对象

				var li = '<li class="list-group-item">'+
							 '<span class="badge">'+value.number+'</span>'+value.name+
						 '</li>';
				
				$("#collectionVideoOrder ul").append(li);
			});
		},
		error:function(){
			alert("请求失败");
		}
	});
}

/*视频购买排行*/
function buyVideoOrder(){
	$.ajax({
		url:basePath+"main/purchasedVideoOrder",
		type:"get",
		dataType:"json",
		success:function(mains){	
			$("#buyVideoOrder ul").html("");	
				
			$(mains).each(function(n,value){
				//n:循环的第几个元素，从0开始
				//value：循环的当前元素，实际上就是mains的对象

				var li = '<li class="list-group-item">'+
							 '<span class="badge">'+value.number+'</span>'+value.name+
						 '</li>';
				
				$("#buyVideoOrder ul").append(li);
			});
		},
		error:function(){
			alert("请求失败");
		}
	});
}





//关闭面板
function close_panel(btn) {
	$(btn).parent().parent().parent().fadeOut(200);
}
// 重置面板
function reset_panel() {
	$(".panel").parent().fadeIn(200);
}
