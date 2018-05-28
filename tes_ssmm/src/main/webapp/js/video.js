//@ sourceURL=video.js
var course_type_update;
var user_type_update;
var isBuy;
var isPass;
$(function(){
	findVideo(1);
	$("#keyword_button").click(function(){
		findVideo(1);
	});
	$("#videoPanel_button button").click(function(){
		//alert($(this).html());
		findVideo_button(1,$(this).html());
	});
	//给删除的modal框里的确定按钮添加事件
	$("#deletevideo_button").click(function(){
		//alert("deleteVideo");
	    deleteVideo();
	});
	
	$("#update_sumbit").submit(function(){
		//alert("updateVideo");
		return updateVideo_new();
	});
	
	//获取修改视频的下拉菜单值课程
	$("#courseNo").change(function(){
		course_type_update=$(this).val();
	});
	//获取修改视频的下拉菜单值用户
	$("#teacherNo").change(function(){
		user_type_update=$(this).val();
	});
	//获取修改视频的下拉菜单值购买状态
	$("#isBuy").change(function(){
		isBuy=$(this).val();
	});
	//获取修改视频的下拉菜单值购买状态
	$("#isPass").change(function(){
		isPass=$(this).val();
	});
	
});
var video_id;
function delete_getId(id){
	alert(id);
	video_id=id;
}

//删除视频
//删除视频信息
function deleteVideo(){
	alert(video_id);
		//异步提交删除视频
		$.ajax({
			url:basePath+"video/deleteVideo/"+video_id,
			type:"delete",
			dataType:"json",
			success:function(result){
				alert("成功");
				if(result.status==0){
					alert(result.message);
				}else if(result.status==1){
					alert(result.message);
				}
			},
			error:function(){
				alert("请求失败！");
			}
		});
	$("#delete_modal").modal('toggle');
	$("#del_div").empty();
}
//修改视频
function updateVideo_new(){
	//alert(video_id);
	var videoName=$("#editVideo #videoName").val();
	var videoPath=$("#editVideo #videoPath").val();
	var courseName=course_type_update;
	var userName=user_type_update;
	var level=$("#editVideo #level").val();
	var diffLevel=$("#editVideo #diffLevel").val();
	var isbuy=isBuy
	var ispass=isPass;
	var time=$("#editVideo #time").val();
	var videoDesc=$("#editVideo #videoDesc").val();
	$.ajax({
		url:basePath+"video/updateVideo/"+time,
		type:"post",
		data:{"id":video_id,"title":videoName,"picture":videoPath,"courseName":courseName,
			  "userNickname":userName,"special":level,"difficulty":diffLevel,
			  "forsale":isbuy,"introduction":videoDesc},
		dataType:"json",
		success:function(result){
			//alert("成功");
			if(result.status==0){
				alert(result.message);
				classchange(video_id);
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败！");
		}
	});
	
	$("#editVideo").modal('toggle');
	return false;
}

//获取修改信息
function updateVideo(id,title,filename,courseId,userId,special,difficulty,forsale,ontime,introduction){
	video_id=id;
	$("#editVideo #videoName").val(title);
	$("#editVideo #videoPath").val(filename);
	$("#editVideo #videoPath").val(filename);
	$("#editVideo #level").val(special);
	$("#editVideo #diffLevel").val(difficulty);
	$("#editVideo #time").val(ontime);
	$("#editVideo #videoDesc").val(introduction);
	
	showCourse();
	showUser();
}
//下拉菜单讲师
function showUser(){
	$("#teacherNo").empty();
	$.ajax({
		url:basePath+"video/username",
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var p='<option>==请选择==</option>';
				$("#teacherNo").append(p);
				$(result.data).each(function(n,value){
					var o='<option>'+value.nickName+'</option>';
					$("#teacherNo").append(o);
				});
			}else if(result.status==1){
				var o='<option>'+result.message+'</option>';
				$("#teacherNo").append(o);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
}
//下拉菜单课程
function showCourse(){
	$("#courseNo").empty();
	$.ajax({
		url:basePath+"video/coursename",
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var p='<option>==请选择==</option>';
				$("#courseNo").append(p);
				$(result.data).each(function(n,value){
					var o='<option>'+value.name+'</option>';
					$("#courseNo").append(o);
				});
			}else if(result.status==1){
				var o='<option>'+result.message+'</option>';
				$("#courseNo").append(o);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
}

//改变图标事件并且寻找当前单击的对象
function classchange(id){
	//alert("进入change方法");
	$("#xiangqing_head").attr("aria-expanded","true");
	$("#xiangqing_head").parent().attr("class","active");
	$("#video_head").attr("aria-expanded","false");
	$("#video_head").parent().attr("class",null);
	$.ajax({
		url:basePath+"video/findById",
		type:"get",
		dataType:"json",
		data:{"id":id},
		success:function(result){
			//alert("成功");
			if(result.status==0){
				//alert(result.message);
				var video=result.data;
				var time_video=getMyDate(video.ontime);
				if(video.url_cc=="undefined" || video.url_cc==null){
					video.url_cc="未找到正确路径";
				}
				if(video.state==2){
					video.state="否";
				}else if(video.state==6){
					video.state="是";
				}
				$("#row_show").empty();
				var div_ele='<div class="embed-responsive embed-responsive-16by9 col-md-9">'+
		          				'<video  class="embed-responsive-item" src="video/'+video.filename+'" controls>没有找到上传的视频</video >'+
		          			'</div>'+
		          			
			          '<div id="del_div" class="caption col-md-3">'+
			             '<h3>'+video.title+'</h3>'+
			             '<p><span>视频路径 : </span>'+video.filename+'</p>'+
			             '<p><span>课程名称 : </span>'+video.course.name+'</p>'+
			             '<p><span>讲师名称 : </span>'+video.user.nickName+'</p>'+
			             '<p><span>推荐等级 : </span>'+video.special+'</p>'+
			             '<p><span>难度等级 : </span>'+video.difficulty+'</p>'+
			             '<p><span>点击次数 : </span>18</p>'+
			             '<p><span>上架日期 : </span>'+time_video+'</p>'+
			             '<p><span>是否购买 : </span>'+video.forsale+'</p>'+
			             '<p><span>审核状态 : </span>'+video.state+'</p>'+
			             '<p><span>视频介绍 : </span>'+video.introduction+'</p>'+
			             '<div id="show_ispass" class="rows">'+
			             '<button type="button" id="update_video'+id+'" onclick=\"updateVideo(\''+id+'\',\''+video.title+'\',\''+video.filename+'\',\''+video.courseId+'\',\''+video.userId+'\',\''+video.special+'\',\''+video.difficulty+'\',\''+video.forsale+'\',\''+video.ontime+'\',\''+video.introduction+'\',)\" data-toggle="modal" data-target="#editVideo" class="btn btn-primary">编辑</button>'+
			              '<button onclick=\"delete_getId(\''+id+'\')\" type="button" data-toggle="modal" data-target=".bs-example-modal-sm" class="btn btn-danger">删除</button>'+
			             '</div>'+
			         ' </div>';
				$("#row_show").append(div_ele);
				var show_ispass='<button onclick=\"changestate(\''+id+'\')\" type="button" class="btn btn-success">通过</button>';
				if(video.state=="否"){
					$("#show_ispass").append(show_ispass);
				}
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("求情失败!");
		}
	});
}
function changestate(id){
	$.ajax({
		url:basePath+"video/changestate/"+id,
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.message);
				classchange(id);
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
}

function findVideo_button(currentPage,keyWord){
	$.ajax({
		url:basePath+"video/page",
		type:"get",
		data:{"currentPage":currentPage,"videoKeyword":keyWord},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				$("#div_row").html("");
				$("#video_page").html("");
				var page=result.data;
				var videos=page.data;
				$(videos).each(function(n,value){
				var time_video=getMyDate(value.ontime);
				var div_ele='<div class="col-md-3">'+
		            '<div class="thumbnail">'+
	              '<a onclick=\"classchange(\''+value.id+'\')\" href="#videoDesc" data-toggle="tab">'+
	              '<img src="images/Java-evn.png" alt="...">'+//'+value.picture+'
	              '</a>'+
	              '<div class="caption">'+
	                '<a onclick=\"classchange(\''+value.id+'\')\" href="#videoDesc" data-toggle="tab"><h3>'+value.title+'</h3></a>'+
	                '<p><span>上传日期 : </span>'+time_video+'</p>'+
	                '<p><span>视频介绍 : </span>'+value.introduction+'</p>'+
	              '</div>'+
	            '</div>'+
	         '</div>';
				$("#div_row").append(div_ele);
				});
				if(page.aNum.length>0){
					var previousPage='<li>'+
			            '<a href="javascript:findVideo('+page.previousPage+')" aria-label="Previous">'+
		              '<span aria-hidden="true">&laquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#video_page").append(previousPage);
					$(page.aNum).each(function(n,value){
						var middlePage='<li><a href="javascript:findVideo('+value+')">'+value+'</a></li>';
						$("#video_page").append(middlePage);
					});
					var nextPage='<li>'+
			            '<a href="javascript:findVideo('+page.nextPage+')" aria-label="Next">'+
		              '<span aria-hidden="true">&raquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#video_page").append(nextPage);
				}
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
}

function findVideo(currentPage){
	var videoKeyWord=$("#video_keyword").val();
	if(videoKeyWord==""){
		videoKeyWord="undefined";
	}
	//alert(videorKeyWord);
	$.ajax({
		url:basePath+"video/page",
		type:"get",
		data:{"currentPage":currentPage,"videoKeyword":videoKeyWord},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				$("#div_row").html("");
				$("#video_page").html("");
				var page=result.data;
				var videos=page.data;
				$(videos).each(function(n,value){
				var time_video=getMyDate(value.ontime);
				var div_ele='<div class="col-md-3">'+
		            '<div class="thumbnail">'+
	              '<a onclick=\"classchange(\''+value.id+'\')\" href="#videoDesc" data-toggle="tab">'+
	              '<img src="images/Java-evn.png" alt="...">'+//'+value.picture+'
	              '</a>'+
	              '<div class="caption">'+
	                '<a onclick=\"classchange(\''+value.id+'\')\" href="#videoDesc" data-toggle="tab"><h3>'+value.title+'</h3></a>'+
	                '<p><span>上传日期 : </span>'+time_video+'</p>'+
	                '<p><span>视频介绍 : </span>'+value.introduction+'</p>'+
	              '</div>'+
	            '</div>'+
	         '</div>';
				$("#div_row").append(div_ele);
				});
				if(page.aNum.length>0){
					var previousPage='<li>'+
			            '<a href="javascript:findVideo('+page.previousPage+')" aria-label="Previous">'+
		              '<span aria-hidden="true">&laquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#video_page").append(previousPage);
					$(page.aNum).each(function(n,value){
						var middlePage='<li><a href="javascript:findVideo('+value+')">'+value+'</a></li>';
						$("#video_page").append(middlePage);
					});
					var nextPage='<li>'+
			            '<a href="javascript:findVideo('+page.nextPage+')" aria-label="Next">'+
		              '<span aria-hidden="true">&raquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#video_page").append(nextPage);
				}
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
}
//修改时间方法
function getMyDate(str){  
    var oDate = new Date(str),  
    oYear = oDate.getFullYear(),  
    oMonth = oDate.getMonth()+1,  
    oDay = oDate.getDate(),  
    oTime = oYear +'-'+ getzf(oMonth) +'-'+ getzf(oDay);//最后拼接时间  
    return oTime;  
};  
//补0操作  
function getzf(num){  
    if(parseInt(num) < 10){  
        num = '0'+num;  
    }  
    return num;  
}