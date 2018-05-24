//@ sourceURL=course.js
var courseId;
var picture_new;
$(function(){
	$("#addCourse_ui li:eq(0)").click(function(){
		findCourse(1);
	});
	findCourse(1);
	$("#course_button").click(function(){
		findCourse(1);
	});
	//添加课程信息
	$("#addCoursePanel form").submit(function(){
		//alert("form");
		return addCourse();
	});
	//删除课程信息
	$("#delete_button").click(function(){
		var dom=getCourseTr(courseId,"delCourse");
		deleteCourse(dom);
	});
	//修改课程信息
	$("#update_button").click(function(){
		//alert("1");
		var dom=getCourseTr(courseId,"updCourse");
		return updateCourse(courseId,dom);
	});
});
//修改课程信息的方法
function updateCourse(courseId,dom){
	var name=$("#editCourse #courseName").val();
	var order=$("#editCourse #recommended").val();
	var desc=$("#editCourse #update_text").val();
	$.ajaxFileUpload({
		url:basePath+"course/update",
		secureuri:false,
		fileElementId:"picture_update",
		type:"post",
		dataType:"text",
		data:{"id":courseId,"name":name,"order":order,"desc":desc,"picture":picture_new},
		success:function(data,status){
			data=data.replace(/<PRE.*?>/g,'');
			data=data.replace("<PRE>",'');
			data=data.replace("</PRE",'');
			data=data.replace(/<pre.*?>/g,'');
			data=data.replace("<pre>",'');
			data=data.replace("</pre>",'');
			alert(data);
			dom.find("img").attr("src","images/"+result.picture);
			dom.find("td:eq(2)").html(result.name);
			dom.find("td:eq(3)").html(result.order);
			dom.find("td:eq(5)").html(result.desc);
		},
		error:function(){
			alert("求情失败!");
		}
	});
	$("#editCourse").modal("toggle");
	return false;
}
//获取修改数据
function getupdCourseId(id,name,order,desc,picture){
	courseId=id;
	picture_new=picture;
	var courseName=name;
	var courseOrder=order;
	var courseDesc=desc;
	$("#editCourse #courseName").val(courseName);
	$("#editCourse #recommended").val(courseOrder);
	$("#editCourse #update_text").val(courseDesc);
}
//删除课程信息的方法
function deleteCourse(dom){
	$.ajax({
		url:basePath+"course/del/"+courseId,
		type:"delete",
		dataType:"json",
		success:function(result){
			alert(result.message);
			//alert(dom);
			dom.remove();
		},
		error:function(){
			alert("请求失败!");
		}
	});
	$("#deleteCourseModal").modal("toggle");
}
//获取当前行tr
function getCourseTr(courseId,type){
	return $("#"+type+courseId).parent().parent();
}
//获取id
function getCourseId(id){
	//alert(id);
	courseId=id;
}
//添加课程信息
function addCourse(){
	var name=$("#addCoursePanel #courseName").val();
	var order=$("#addCoursePanel #recommended").val();
	var desc=$("#desc_text").val();
	//alert(desc);
	//alert(name+"  "+order+"  "+desc);
	$.ajaxFileUpload({
		url:basePath+"course/new",
		secureuri:false,
		fileElementId:'picture_add',
		type:"post",
		dataType:"text",
		data:{"name":name,"order":order,"desc":desc},
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
function findCourse(currentPage){
	var courseKeyWord=$("#course_keyword").val();
	if(courseKeyWord==""){
		courseKeyWord="undefined";
	}
	//alert(userKeyWord);
	$.ajax({
		url:basePath+"course/page",
		type:"get",
		data:{"currentPage":currentPage,"courseKeyword":courseKeyWord},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//alert("成功");
				$("#course_table tbody").html("");
				$("#course_page").html("");
				var page=result.data;
				var users=page.data;
				$(users).each(function(n,value){
					value.regDate=getMyDate(value.regDate);
					//alert(value.regDate);
					var tr='<tr>'+
	                '<td>'+(n+1)+'</td>'+
	                '<td><img src="images/'+value.picture+'"></td>'+
	                '<td>'+value.name+'</td>'+
	                '<td>'+value.order+'</td>'+
	                '<td>'+value.regDate+'</td>'+
	                '<td>'+value.desc+'</td>'+
	                '<td>'+
	                  '<a href="" id="updCourse'+value.id+'" onclick=\"getupdCourseId(\''+value.id+'\',\''+value.name+'\',\''+value.order+'\',\''+value.desc+'\',\''+value.picture+'\')\" data-toggle="modal" data-target="#editCourse"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>'+
	                  '<a href="" id="delCourse'+value.id+'" onclick=\"getCourseId(\''+value.id+'\')\" data-toggle="modal" data-target=".bs-example-modal-sm"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'+
	                '</td>'+
	              '</tr>';
					$("#course_table tbody").append(tr);
				});
				if(page.aNum.length>0){
					var previousPage='<li>'+
				        '<a href="javascript:findCourse('+page.previousPage+')" aria-label="Previous">'+
			          '<span aria-hidden="true">&laquo;</span>'+
			        '</a>'+
			      '</li>';
					$("#course_page").append(previousPage);
					$(page.aNum).each(function(n,value){
						var middlePage='<li><a href="javascript:findCourse('+value+')">'+value+'</a></li>';
						$("#course_page").append(middlePage);
					});
					var nextPage='<li>'+
			            '<a href="javascript:findCourse('+page.nextPage+')" aria-label="Next">'+
		              '<span aria-hidden="true">&raquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#course_page").append(nextPage);
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