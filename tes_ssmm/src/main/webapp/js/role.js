//@ sourceURL=role.js
var roleId;
var roleName;
$(function(){
	//alert("role.js");
	findRole(1);
	$("#role_search_button").click(function(){
		//alert("模糊查询");
		findRole(1);
	});
	//添加角色
	$("#addPanel form").submit(function(){
		return addRole();
	});
	//删除角色
	$("#delbutton").click(function(){
		//alert(roleId);
		var dom=getRoleTr(roleId,"deleteRole");
		deleteRole(roleId,dom);
	});
	//修改角色
	$("#updbutton").click(function(){
		var dom=getRoleTr(roleId,"updateRole");
		return updateRole(roleId,dom);
	});
});
//修改角色的方法
function updateRole(roleId,dom){
	var newRoleName=$("#editRole form input[type=text]").val();
	$.ajax({
		url:basePath+"role/update/"+roleId+"/"+newRoleName,
		type:"put",
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.message);
				dom.find("td:eq(2)").html(newRoleName);
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
	$("#editRole").modal('toggle');
	return false;
};
//删除角色的方法
function deleteRole(roleId,dom){
	//alert(roleId);
	//alert(roleId+"  11111 "+dom);
	$.ajax({
		url:basePath+"role/del/"+roleId,
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
	$("#deleteRoleModal").modal("toggle");
}
//获取删除或修改角色的tr行
function getRoleTr(roleId,type){
	//alert(roleId+"    "+type);
	return $("#"+type+roleId).parent().parent();
}
//获取删除或修改角色的id
function getRoleId(id){
	roleId=id
	//alert(id);
}
//获取修改角色的名字
function getUpdateRoleId(id,name){
	//alert(id+"    "+name);
	roleId=id;
	roleName=name;
	$("#editRole form input[type=text]").val(roleName);
}
//添加角色的方法
function addRole(){
	var roleName=$("#roleName").val();
	//alert(roleName);
	$.ajax({
		url:basePath+"role/new",
		type:"post",
		data:{"name":roleName},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.message);
			}else if(result==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败!");
		}
	});
	return false;
}
function findRole(currentPage){
	//alert(currentPage);
	var roleKeyWord=$("#role_search").val();
	//alert(roleKeyWord);
	if(roleKeyWord==""){
		roleKeyWord="undefined";
	}
	//alert(roleKeyWord);
	$.ajax({
		url:basePath+"role/page",
		tyep:"get",
		data:{"currentPage":currentPage,"roleKeyword":roleKeyWord},
		dataType:"json",
		success:function(result){
			//alert(result.status);
			if(result.status==0){
				$("#role_table tbody").html("");
				$("#role_page").html("");
				//alert("先成功");
				var page=result.data;
				var roles=page.data;
				
				$(roles).each(function(n,value){
					//alert("成功");
					var name=value.name;
					if(name.indexOf("管理员")>=0){
						var tr='<tr>'+
				             ' <td>'+(n+1)+'</td>'+
				              '<td>'+value.id+'</td>'+
				              '<td>'+value.name+'</td>'+
				              '<td>'+
				                '<a href="" id="updateRole'+value.id+'" onclick=\"getUpdateRoleId(\''+value.id+'\',\''+value.name+'\')\" data-toggle="modal" data-target="#editRole" ><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>'+
				                '<a href="" id="deleteRole'+value.id+'" onclick=\"getRoleId(\''+value.id+'\');\" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'+
				              '</td>';
						$("#role_table tbody").append(tr);
					}else{
						//alert(value.name);
						var tr='<tr>'+
				               '<td>'+(n+1)+'</td>'+
				               '<td>'+value.id+'</td>'+
				               '<td>'+value.name+'</td>'+
				               '<td>'+
				               '</td>'+
				               '</tr>';
						$("#role_table tbody").append(tr);
					}
				});
				//alert(page.aNum.length);
				if(page.aNum.length>0){
					var previousPage=' <li>'+
				         	' <a href="javascript:findRole('+page.previousPage+')" aria-label="Previous">'+
				         	' <span aria-hidden="true">&laquo;</span>'+
				         	' </a>'+
				         	'</li>';
					$("#role_page").append(previousPage);
					$(page.aNum).each(function(n,value){
						var middlePage='<li><a href="javascript:findRole('+value+')">'+value+'</a></li>';
						$("#role_page").append(middlePage);
					});
					var nextPage='<li>'+
				          		 '<a href="javascript:findRole('+page.nextPage+')" aria-label="Next">'+
				          		 '<span aria-hidden="true">&raquo;</span>'+
				          		 '</a>'+
				          		 '</li>';
					$("#role_page").append(nextPage);
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