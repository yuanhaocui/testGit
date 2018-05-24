$(function(){
	$("#message_dialog button:contains('视频')").click(function(){
		to_audit_video(this);
	});
	$("#message_dialog button:contains('活动')").click(function(){
		to_audit_activity(this);
	});
	$("#showmessage tr").click(function(){
		alert(11);
	});
	findMessage();
	showMessage();
});
function showMessage(){
	$.ajax({
		url : basePath + "message/countNumber",
		type : "get",
		dataType : "json",
		success : function(result) {
			$("#message_number").html(result.message);
		},
		error:function(){
			alert("请求失败");
		}
	});
}
function to_audit_video(btn) {
	$(btn).parent().parent().remove();
	$("#message_dialog .close").trigger("click");
	var count = parseInt($("#header .badge").text());
	if(count>0) {
		$("#header .badge").text(--count)
	}
	$("#siderbar li").removeClass("active");
	$("#siderbar li:contains('视频')").addClass("active");
	$('#main').load('page/video.html', function(){
		$.getScript("js/video.js");
		$("#main .nav-tabs li:contains('详情') a").trigger("click");
	});
}

function to_audit_activity(btn) {
	$(btn).parent().parent().remove();
	$("#message_dialog .close").trigger("click");
	var count = parseInt($("#header .badge").text());
	if(count>0) {
		$("#header .badge").text(--count)
	}
	$("#siderbar li").removeClass("active");
	$("#siderbar li:contains('活动')").addClass("active");
	$('#main').load('page/activity.html', function(){
		$.getScript("js/activity.js");
		$("#main .nav-tabs li:contains('详情') a").trigger("click");
	});
}

function classchange(id,titles){
	//alert("进入change方法");
	alert(titles);
	if(titles=="新视频"){
		alert("进入视频");
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
			          				'<video  class="embed-responsive-item" src="?????" controls>没有找到上传的视频</video >'+
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
				alert("情求失败!");
			}
		});
	}
	
}

function findMessage() {
	
	$.ajax({
		url : basePath + "message/findMessage",
		type : "get",
		dataType : "json",
		success : function(result) {
			$("#showmessage").empty();
			if (result.status == 0) {
				var messages = result.data;
				var messageNum = messages.length;
				
				$("#messageNum").append(messageNum);
				
				$(messages).each(function(n,value) {
					
					var tr ='<tr id='+value.id+'>'+
								'<td>'+
									'<input type="checkbox" name="chk_list" />'+
								'</td>'+
								'<td>'+ value.titles+ '</td>'+
								'<td>'+ value.content+'</td>'+
								'<td>'+ value.nickName+'</td>'+ 
								'<td>'+ new Date(value.time).toLocaleDateString().replace("/", "-").replace("/", "-")+'</td>'+
								'<td id="td'+value.id+'">'+ 
								'<button onclick=\"classchange(\''+value.id+'\',\''+value.titles+'\')\" type="button" class="btn btn-primary btn-sm">'+ value.checkedName+'</button>'+
								'</td>'+
							'</tr>';
//					if("新视频"==value.titles){
//						var td_1='<button onclick=\"classchange(\''+value.id+'\')\" type="button" class="btn btn-primary btn-sm">'+ value.checkedName+'</button>';
//						$("#td"+value.id).append(td_1);
//					}
//					if("新活动"==value.titles){
//						var td_2='<button type="button" class="btn btn-primary btn-sm">'+ value.checkedName+'</button>';
//						$("#td"+value.id).append(td_2);
//					}
					$("#showmessage").append(tr);
				});
				$("#message_dialog button:contains('视频')").click(
						function() {
							to_audit_video(this);
						});
				$("#message_dialog button:contains('活动')").click(
						function() {
							to_audit_activity(this);
						});

			} else if (result.status == 1) {
				alert(result.message);
			}
		},
		error : function() {
			alert("消息请求失败");
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