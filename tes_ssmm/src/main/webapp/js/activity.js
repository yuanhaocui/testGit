//@ sourceURL=activity.js
var course;
var course1;
var activityId;
var picture_upd;
$(function(){
	//alert("what fuck");
	findActivity(1);
	$("#keyword_button").click(function(){
		findActivity(1);
	});
	//添加活动
	$("#addActPanel form").submit(function(){
		//alert(11);
		return addActivity();
	});
	//添加下拉菜单内容
	$("#add_a").click(function(){
		//alert("添加");
		showCourse();
	});
	$("#add_b").click(function(){
		findActivity(1);
	});
	//获取下拉菜单内容
	$("#addActPanel #courseNO").change(function(){
		course=$(this).val();
	});
	$("#editUser #courseNO").change(function(){
		course1=$(this).val();
	});
	//删除活动
	$("#delete_button").click(function(){
		var dom=getActivityTr(activityId,"delete");
		deleteActivity(activityId,dom);
	});
	//通过按钮点击事件
	/*$("#xiangqing_button").click(function(){
		
	});*/
	//修改活动
	$("#editUser form").submit(function(){
		var dom=getActivityTr(activityId,"update");
		return updateActivity_servlet(activityId,dom);
	});
})
//修改活动的方法
function updateActivity_servlet(id,dom){
	var title=$("#editUser #activityName").val();
	var date=$("#editUser #activityDate").val();
	var persons=$("#editUser #activityPeoNum").val();
	var location=$("#editUser #activityPlace").val();
	var longitude=$("#editUser #longitude").val();
	var latitude=$("#editUser #latitude").val();
	var cost=$("#editUser #activityCost").val();
	var user=$("#editUser #initiator").val();
	var details=$("#editUser #activityDesc").val();
	var course=course1;
	//alert(course);
	$.ajaxFileUpload({
		url:basePath+"activity/update",
		secureuri:false,
		fileElementId:"picture_update",
		type:"post",
		dataType:"text",
		data:{"id":id,"title":title,"time":date,"persons":persons,"location":location,"longitude":longitude,
			"latitude":latitude,"cost":cost,"userName":user,"details":details,"courseName":course,"picture":picture_upd},
		success:function(data,status){
			data=data.replace(/<PRE.*?>/g,'');
			data=data.replace("<PRE>",'');
			data=data.replace("</PRE",'');
			data=data.replace(/<pre.*?>/g,'');
			data=data.replace("<pre>",'');
			data=data.replace("</pre>",'');
			alert(data);
			dom.find("td:eq(1)").html(title);
			dom.find("td:eq(2)").html(date);
			dom.find("td:eq(3)").html(location);
			dom.find("td:eq(4)").html(persons);
			dom.find("td:eq(5)").html(cost);
			//dom.find("td:eq(6)").html(ispass);
		},
		error:function(){
			alert("求情失败!");
		}
	});
	$("#editUser").modal("toggle");
	return false;
}
//显示要修改的信息
function updateActivity(id,title,date,location,persons,cost,latitude,longitude,user,course,details,picture){
	//alert(activityId);
	activityId=id;
	//alert(activityId);
	picture_upd=picture;
	$("#editUser #activityName").val(title);
	$("#editUser #activityDate").val(date);
	$("#editUser #activityPeoNum").val(persons);
	$("#editUser #activityPlace").val(location);
	$("#editUser #longitude").val(longitude);
	$("#editUser #latitude").val(latitude);
	$("#editUser #activityCost").val(cost);
	$("#editUser #initiator").val(user);
	$("#editUser #activityDesc").val(details);
	showCourse_update();
	//alert(1)
	$("#editUser #courseNO").val(course);
}
//显示所有信息
function showActivity(id,title,date,location,persons,cost,latitude,longitude,user,course,details,picture,ispass){
	$("#xiangqing_id").empty();
	//alert(date);
	//var date_now=getMyDate(date);
	//alert(date_now);
	var div_ele='<div class="media-left">'+
        '<a href="#">'+
          '<img class="media-object img-circle" src="images/'+picture+'" alt="头像">'+
        '</a>'+
      '</div>'+
      '<div class="media-body">'+
        '<h1 class="media-heading">'+title+'</h1>'+
        '<br/>'+'<div style="display:none">'+id+'</div>'+
        '<p>标题：<span>'+title+'</span></p>'+
        '<p>日期：<span>'+date+'</span></p>'+
        '<p>地点：<span>'+location+'</span></p>'+
        '<p>人数：<span>'+persons+'</span></p>'+
        '<p>花费：<span>'+cost+'</span></p>'+
        '<p>经度：<span>'+longitude+'</span></p>'+
        '<p>纬度：<span>'+latitude+'</span></p>'+
        '<p>发起人：<span>'+user+'</span></p>'+
        '<p>课程：<span>'+course+'</span></p>'+
        '<p>细节：<span>'+details+'</span></p>'+
        '<div id="rem'+id+'" class="rows">'+
         '</div>'+
      '</div>';
	$("#xiangqing_id").append(div_ele);
	if(ispass=="否"){
		var div_ispass='<button id="pass'+id+'" onclick=\"isPass(\''+id+'\')\" type="button" class="btn btn-success">通过</button>'+
	    '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
	    '<button id="del'+id+'" onclick=\"rempass(\''+id+'\')\" type="button" class="btn btn-danger">拒绝</button>';
		$("#rem"+id).append(div_ispass);
	}
}
//通过活动
function isPass(id){
	//alert(id);
	$.ajax({
		url:basePath+"activity/ispass",
		type:"post",
		data:{"activity_id":id},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.message);
				$("#rem"+id).remove();
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("求情失败");
		}
	});
}
//拒绝活动
function rempass(id){
	//alert(id);
	$.ajax({
		url:basePath+"activity/delete_act",
		type:"delete",
		data:{"activity_id":id},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.message);
				$("#rem"+id).remove();
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("求情失败");
		}
	});
}

//删除活动的方法
function deleteActivity(id,dom){
	$.ajax({
		url:basePath+"activity/del/"+id,
		type:"delete",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.message);
				dom.remove();
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
	$("#deleteActivityModal").modal("toggle");
}
//获取行信息
function getActivityTr(id,type){
	return $("#"+type+id).parent().parent();
}
//获取删除的id
function delActivity(id){
	activityId=id;
}
//添加下拉菜单内容ShowCourse
function showCourse(){
	$("#addActPanel #courseNO").empty();
	$.ajax({
		url:basePath+"activity/show_courseName",
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var p='<option>==请选择==</option>';
				$("#addActPanel #courseNO").append(p);
				$(result.data).each(function(n,value){
					var o='<option>'+value.name+'</option>';
					$("#addActPanel #courseNO").append(o);
				});
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
}
function showCourse_update(){
	$("#editUser #courseNO").empty();
	$.ajax({
		url:basePath+"activity/show_courseName",
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var p='<option>==请选择==</option>';
				$("#editUser #courseNO").append(p);
				$(result.data).each(function(n,value){
					var o='<option>'+value.name+'</option>';
					$("#editUser #courseNO").append(o);
				});
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
}
//添加活动方法
function addActivity(){
	var title=$("#activityName").val();
	var date=$("#activityDate").val();
	//alert(date);
	var person=$("#activityPeoNum").val();
	var location=$("#activityPlace").val();
	//alert(location);
	var longitude=$("#longitude").val();
	var latitude=$("#latitude").val();
	var cost=$("#activityCost").val();
	var user=$("#initiator").val();
	var details=$("#activityDesc").val();
	//alert(longitude);
	$.ajaxFileUpload({
		url:basePath+"activity/add",
		secureuri:false,
		fileElementId:'picture_add',
		type:"post",
		dataType:"text",
		data:{"title":title,"persons":person,"location":location,"longitude":longitude,"latitude":latitude,"cost":cost,
			"userName":user,"courseName":course,"details":details,"time":date},
		success:function(data,status){
			data=data.replace(/<PRE.*?>/g,'');
			data=data.replace("<PRE>",'');
			data=data.replace("</PRE",'');
			data=data.replace(/<pre.*?>/g,'');
			data=data.replace("<pre>",'');
			data=data.replace("</pre>",'');
			alert(data);
		},
		error:function(){
			alert("请求失败!");
		}
	});
	return false;
}
function findActivity(currentPage){
	//alert("what fuck fuck");
	var activityKeyWord=$("#keyword_text").val();
	if(activityKeyWord==""){
		activityKeyWord="undefined";
	}
	$.ajax({
		url:basePath+"activity/page",
		type:"get",
		data:{"currentPage":currentPage,"activityKeyword":activityKeyWord},
		dataType:"json",
		success:function(result){
			//alert("成功");
			if(result.status==0){
				$("#activity_table tbody").html("");
				$("#activity_page").html("");
				page=result.data;
				activity=page.data;
				$(activity).each(function(n,value){
					var activity_date=getMyDate(value.date);
					var tr='<tr>'+
				          '<td>'+(n+1)+'</td>'+
				          '<td><a href="" onclick=\"showActivity(\''+value.id+'\',\''+value.title+'\',\''+activity_date+'\',\''+value.location+'\',\''+value.persons+'\',\''+value.cost+'\',\''+value.latitude+'\','+
				          	'\''+value.longitude+'\',\''+value.user.nickName+'\',\''+value.course.name+'\',\''+value.details+'\',\''+value.picture+'\')\" data-toggle="modal" data-target="#xiangqing">'+value.title+'</a></td>'+
				          '<td>'+activity_date+'</td>'+
				          '<td>'+value.location+'</td>'+
				          '<td>'+value.persons+'</td>'+
				          '<td>'+value.cost+'</td>'+
				          '<td>'+value.ispass+'</td>'+
				          '<td>'+
				          	'<a href="" onclick=\"showActivity(\''+value.id+'\',\''+value.title+'\',\''+activity_date+'\',\''+value.location+'\',\''+value.persons+'\',\''+value.cost+'\',\''+value.latitude+'\','+
				          	'\''+value.longitude+'\',\''+value.user.nickName+'\',\''+value.course.name+'\',\''+value.details+'\',\''+value.picture+'\',\''+value.ispass+'\')\" data-toggle="modal" data-target="#xiangqing"><span class="glyphicon glyphicon-list" aria-hidden="true"></span>详情</a>'+
				            '<a href="" id="update'+value.id+'" onclick=\"updateActivity(\''+value.id+'\',\''+value.title+'\',\''+activity_date+'\',\''+value.location+'\',\''+value.persons+'\',\''+value.cost+'\',\''+value.latitude+'\','+
				          	'\''+value.longitude+'\',\''+value.user.nickName+'\',\''+value.course.name+'\',\''+value.details+'\',\''+value.picture+'\')\" data-toggle="modal" data-target="#editUser"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>'+
				            '<a href="" id="delete'+value.id+'" onclick=\"delActivity(\''+value.id+'\')\" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'+
				          '</td>'+
				        '</tr>';
					$("#activity_table tbody").append(tr);
				});
				//alert(page.aNum.length);
				if(page.aNum.length>0){
					var previousPage='<li>'+
		            '<a href="javascript:findActivity('+page.previousPage+')" aria-label="Previous">'+
	              '<span aria-hidden="true">&laquo;</span>'+
	            '</a>'+
	          '</li>';
				$("#activity_page").append(previousPage);
				$(page.aNum).each(function(n,value){
					var middlePage='<li><a href="javascript:findActivity('+value+')">'+value+'</a></li>';
					$("#activity_page").append(middlePage);
				});
				var nextPage='<li>'+
		            '<a href="javascript:findActivity('+page.nextPage+')" aria-label="Next">'+
	              '<span aria-hidden="true">&raquo;</span>'+
	            '</a>'+
	          '</li>';
				$("#activity_page").append(nextPage);
				}
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败!");
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