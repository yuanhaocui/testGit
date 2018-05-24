//@ sourceURL=user.js
var user_type;
var user_type_update;
var userId;
var user_loginName;
var user_nickName;
var user_age;
var roleType="all";
findUser(1);
$(function(){
	if($("#user_manager").hasClass("active")){
		//alert(1);
		findUser(1);
	}
	$("#user_button").click(function(){
		//alert("模糊查询");
		findUser(1);
	});
	//添加用户信息
	$("#addUserPanel form").submit(function(){
		return addUser();
	});
	//获取添加用户的下拉菜单值
	$("#add_select").change(function(){
		user_type=$(this).val();
	});
	//获取修改用户的下拉菜单值
	$("#update_select").change(function(){
		user_type_update=$(this).val();
	});
	//删除用户信息
	$("#delete_button").click(function(){
		var dom=getUserTr(userId,"delUser");
		//alert(userId);
		return deleteUser(userId,dom);
	});
	//修改用户信息
	$("#update_button").click(function(){
		var dom=getUserTr(userId,"updUser");
		return updateUser(dom);
	});
	$("#add").click(function(){
		var type1="addUserPanel";
		var type2="add_select";
		showRoleName(type1,type2);
	});
	//给全部变量roleType赋值
	$("#role_type button").click(function(){
		roleType=$(this).html();
		if("全部"==roleType){
			roleType="all";
		}
		$(this).siblings("button").removeClass("active");
		$(this).addClass("active");
		findUser(1);
	});
	//给导出按钮添加click事件
	$("#exprot_btn").click(function(){
		window.location.href=basePath+"user/exprotuser";
	});
});
//添加下拉菜单内容ShowCourse
function showRoleName(type1,type2){
	//alert(type1+"   "+type2);
	$("#"+type1+" #"+type2).empty();
	$.ajax({
		url:basePath+"user/rolename",
		type:"get",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var p='<option>==请选择==</option>';
				$("#"+type1+" #"+type2).append(p);
				$(result.data).each(function(n,value){
					var o='<option>'+value.name+'</option>';
					$("#"+type1+" #"+type2).append(o);
				});
			}else if(result.status==1){
				var o='<option>'+result.message+'</option>';
				$("#"+type1+" #"+type2).append(o);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
}
//显示详细信息
function getShowUser(id,loginName,loginType,nickName,sex,age,score,regdate,islock,type,head){
	//alert(head);
	$("#div_xiangxi").empty();
	var div_ele='<div class="media-left">'+
        '<a href="#">'+
    	'<img class="media-object img-circle" src="head/'+head+'" alt="头像">'+
    	'</a>'+
    	'</div>'+
    	'<div class="media-body">'+
		'<h1 class="media-heading">'+loginName+'</h1>'+
       '<br/>'+
    '<p>账号类型：<span>'+loginType+'</span></p>'+
    '<p>昵称：<span>'+nickName+'</span></p>'+
    '<p>性别：<span>'+sex+'</span></p>'+
    '<p>年龄：<span>'+age+'</span></p>'+
    '<p>积分：<span>'+score+'</span></p>'+
    '<p>注册日期：<span>'+regdate+'</span></p>'+
    '<p>锁定：<span>'+islock+'</span></p>'+
    '<p>角色：<span>'+type+'</span></p>'+
    '</div>';
	$("#div_xiangxi").append(div_ele);
}
//修改用户信息的方法
function updateUser(dom){
	var id=userId;
	var loginName=$("#editUser #inputEmail").val();
	var password1=$("#editUser #inputPassword").val();
	var password2=$("#editUser #inputPassword2").val();
	var nickName=$("#editUser #nickName").val();
	var age=$("#editUser #age").val();
	var type=user_type_update;
	var sex=$("#editUser form input:radio:checked").val();
	var head_original=$("#editUser #picture_update").attr("value");
	if(password1!=password2){
		alert("两次用户名和密码输入不一致");
		return false;
	}
	//alert(id);
	$.ajaxFileUpload({
		url:basePath+"user/update/"+type,
		secureuri:false,
		fileElementId:"picture_update",
		type:"post",
		dataType:"text",
		data:{"id":id,"loginName":loginName,"password":password1,"nickName":nickName,"age":age,"sex":sex,"head":head_original},
		success:function(data,status){
			data=data.replace(/<PRE.*?>/g,'');
			data=data.replace("<PRE>",'');
			data=data.replace("</PRE",'');
			data=data.replace(/<pre.*?>/g,'');
			data=data.replace("<pre>",'');
			data=data.replace("</pre>",'');
			dom.find("td:eq(1)").html(loginName);
			dom.find("td:eq(2)").html(nickName);
			dom.find("td:eq(7)").html(type);
			alert(data);
		},
		error:function(){
			alert("求情失败!");
		}
	});
	$("#editUser").modal("toggle");
	return false;
}
//删除用户信息的方法
function deleteUser(id,dom){
	//alert(id);
	$.ajax({
		url:basePath+"user/del/"+id,
		type:"delete",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.message);
				//alert(dom);
				dom.remove();
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
	$("#deleteUserModal").modal("toggle");
	return false;
}
//获取当前用户id的方法
function getUserId(id){
	userId=id;
}
//获取当前tr
function getUserTr(userId,type){
	//alert(userId+"  "+type);
	return $("#"+type+userId).parent().parent();
}
//获取修改行的信息
function getUpdateUser(id,name,password,nickName,age,type,sex,head){
	userId=id;
	user_loginName=name;
	var user_password=password;
	user_nickName=nickName;
	user_age=age;
	var user_type=type;
	var user_sex=sex;
	//alert(user_type+"    "+user_sex);
	$("#editUser #inputEmail").val(user_loginName);
	$("#editUser #inputPassword").val(user_password);
	$("#editUser #inputPassword2").val(user_password);
	$("#editUser #nickName").val(user_nickName);
	$("#editUser #age").val(user_age);
	$("#editUser #update_select").attr("value",user_type);
//	$("#editUser input[type=radio]").removeAttr("checked");
	if("男"==sex){
		$("#editUser input[type=radio]:eq(0)").attr("checked","checked");
	}else{
		$("#editUser input[type=radio]:eq(1)").attr("checked","checked");
	}
	//$("#editUser input[type=radio]").attr("checked",sex);
	$("#editUser #picture_update").attr("value",head);
	var type1="editUser";
	var type2="update_select";
	showRoleName(type1,type2);
}
//添加用户信息方法
function addUser(){
	var loginName=$("#addUserPanel #inputEmail").val();
	var password1=$("#addUserPanel #inputPassword").val();
	var password2=$("#addUserPanel #inputPassword2").val();
	var nickName=$("#addUserPanel #nickName").val();
	var age=$("#addUserPanel #age").val();
	var type=user_type;
	var sex=$("#addUserPanel form input:radio:checked").val();
	//alert(type);
	if(password1!=password2){
		alert("密码不一致,");
		return false;
	}
	if(age<=0){
		$("#addUserPanel #age").focus();
		return false;
	}
	//alert(loginName+"  "+password1+"  "+nickName+"  "+age+"  "+type+"  "+sex);
	$.ajaxFileUpload({
		url:basePath+"user/new/"+type,
		secureuri:false,
		fileElementId:'picture_add',
		type:"post",
		dataType:"text",
		data:{"loginName":loginName,"password":password1,"nickName":nickName,"age":age,"sex":sex},
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
function findUser(currentPage){
	var userKeyWord=$("#user_keyword").val();
	if(userKeyWord==""){
		userKeyWord="undefined";
	}
	//alert(userKeyWord);
	$.ajax({
		url:basePath+"user/page/"+roleType,
		type:"get",
		data:{"currentPage":currentPage,"userKeyword":userKeyWord},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//alert("成功");
				$("#user_table tbody").html("");
				$("#user_page").html("");
				var page=result.data;
				var users=page.data;
				$(users).each(function(n,value){
					value.regDate=getMyDate(value.regDate);
					var roles=value.roles;
					if(roles!=null && roles!=""){
						var role_name="";
						$(roles).each(function(n1,value1){
							role_name=role_name+value1.name+",";
							//alert(role_name);
						});
						role_name=role_name.substr(0,role_name.length-1);
						//alert(role_name);
						var tr='<tr>'+
		                '<td>'+(n+1)+'</td>'+
		                '<td>'+value.loginName+'</td>'+
		                '<td>'+value.nickName+'</td>'+
		                '<td>'+value.loginType+'</td>'+
		                '<td>'+value.score+'</td>'+
		                '<td>'+value.regDate+'</td>'+
		                '<td>'+value.isLock+'</td>'+
		                '<td>'+role_name+'</td>'+
		                '<td>'+
		                '<a href="" id="showUser'+value.id+'" onclick=\"getShowUser(\''+value.id+'\',\''+value.loginName+'\',\''+value.loginType+'\',\''+value.nickName+'\',\''+value.sex+'\',\''+value.age+'\',\''+value.score+'\',\''+value.regDate+'\',\''+value.isLock+'\',\''+role_name+'\',\''+value.head+'\')\" data-toggle="modal" data-target="#xiangqingUser"><span class="glyphicon glyphicon-list" aria-hidden="true"></span>详情</a>'+
		                  '<a href="" id="updUser'+value.id+'" onclick=\"getUpdateUser(\''+value.id+'\',\''+value.loginName+'\',\''+value.password+'\',\''+value.nickName+'\',\''+value.age+'\',\''+value.type+'\',\''+value.sex+'\',\''+value.head+'\')\" data-toggle="modal" data-target="#editUser"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>'+
		                  '<a href="" id="delUser'+value.id+'" onclick=\"getUserId(\''+value.id+'\')\" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'+
		                '</td>'+
		              '</tr>';
					$("#user_table tbody").append(tr);
					}else{
						var role_name="无";
						var tr='<tr>'+
		                '<td>'+(n+1)+'</td>'+
		                '<td>'+value.loginName+'</td>'+
		                '<td>'+value.nickName+'</td>'+
		                '<td>'+value.loginType+'</td>'+
		                '<td>'+value.score+'</td>'+
		                '<td>'+value.regDate+'</td>'+
		                '<td>'+value.isLock+'</td>'+
		                '<td>无</td>'+
		                '<td>'+
		                '<a href="" id="showUser'+value.id+'" onclick=\"getShowUser(\''+value.id+'\',\''+value.loginName+'\',\''+value.loginType+'\',\''+value.nickName+'\',\''+value.sex+'\',\''+value.age+'\',\''+value.score+'\',\''+value.regDate+'\',\''+value.isLock+'\',\''+role_name+'\',\''+value.head+'\')\" data-toggle="modal" data-target="#xiangqingUser"><span class="glyphicon glyphicon-list" aria-hidden="true"></span>详情</a>'+
		                  '<a href="" id="updUser'+value.id+'" onclick=\"getUpdateUser(\''+value.id+'\',\''+value.loginName+'\',\''+value.password+'\',\''+value.nickName+'\',\''+value.age+'\',\''+value.type+'\',\''+value.sex+'\')\" data-toggle="modal" data-target="#editUser"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>'+
		                  '<a href="" id="delUser'+value.id+'" onclick=\"getUserId(\''+value.id+'\')\" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'+
		                '</td>'+
		              '</tr>';
					$("#user_table tbody").append(tr);
					}
				});
				if(page.aNum.length>0){
					var previousPage='<li>'+
			            '<a href="javascript:findUser('+page.previousPage+')" aria-label="Previous">'+
		              '<span aria-hidden="true">&laquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#user_page").append(previousPage);
					$(page.aNum).each(function(n,value){
						var middlePage='<li><a href="javascript:findUser('+value+')">'+value+'</a></li>';
						$("#user_page").append(middlePage);
					});
					var nextPage='<li>'+
			            '<a href="javascript:findUser('+page.nextPage+')" aria-label="Next">'+
		              '<span aria-hidden="true">&raquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#user_page").append(nextPage);
				}
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("求情失败");
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