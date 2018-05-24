//@ sourceURL=comment.js
$(function(){
	$('[data-toggle="popover"]').popover();
	
	/**
	$("#chk_all").click(function() {
		if ($(":checkbox").prop("checked")){
			$(":checkbox").prop("checked",true);
		}
		else{
			$(":checkbox").removeAttr("checked");
		}
	});
	*/
	
	$(".btn-group button").click(function(){
		active($(this));
	});
	
	$("#pushPanel .btn-group button:last").click(function(){
		check_all(this);
	});
	
	$("#refresh_btn").click(function(){
		if($(this).children("i").hasClass("icon-spin")) {
			$(this).children("i").removeClass("icon-spin");
			end_push();
		} else {
			$(this).children("i").addClass("icon-spin");
			start_push();
		}
	});
	
	$(".nav-tabs li:last").click(function(){
		$("#refresh_btn i").removeClass("icon-spin");
		end_push();
	});
	
	$("#push_panels .btn-success").click(function(){
		pass(this);
	});
	
	$("#push_panels .btn-danger").click(function(){
		reject(this);
	});
	
	$("#comment_time").click(function(){
		time_order(this);
	});
	
	
	
});
var div_aa;
var number_aa=0;
var type;
$(function(){
	$("#button_keyword").click(function(){
		//alert(123);
		if($("#button_div button").hasClass("active")){
			//alert(div_aa.html());
			findCommentByCourseId(1,div_aa.html());
		}else{
			findComment(1);
		}
	});
	$("#button_div button").click(function(){
		//alert($(this).html());
		$("#button_div button").removeClass("active");
		$(this).addClass("active");
		div_aa=$(this);
		findCommentByCourseId(1,$(this).html());
	});
	//alert($("#comment_li").hasClass("active"));
	$("#push_panels").empty();
	$("#ispass_all button").click(function(){
		ispass_all($(this).html());
	});
})

function ispass_all(type){
	$.ajax({
		url:basePath+"comment/ispass_all",
		type:"post",
		data:{"type":type},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				alert(result.message);
				findComment(1);
			}else if(result.status==1){
				alert(result.message);
			}
		},
		error:function(){
			alert("请求失败");
		}
	});
}

function isPossById(id){
	//alert(id);
	$.ajax({
		url:basePath+"comment/isposs",
		type:"post",
		data:{"comment_id":id},
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
			alert("请求失败");
		}
	});
}

function deleteById(id){
	//alert(id);
	$.ajax({
		url:basePath+"comment/delete",
		type:"delete",
		data:{"comment_id":id},
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
			alert("请求失败");
		}
	});
}

function findCommentByCourseId(currentPage,name){
	//alert(name);
	var chk_value =[];
	$('input[type="checkbox"]:checked').each(function(){ 
		chk_value.push($(this).val()); 
		}); 
	//alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value); 
	$.ajax({
		url:basePath+"comment/commentFindByCourseId",
		type:"post",
		data:{"currentPage":currentPage,"courseName":name,"value":JSON.stringify(chk_value)},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//alert("成功");
				$("#div_ele").html("");
				$("#comment_page").html("");
				var page=result.data;
				var comments=page.data;
				$(comments).each(function(n,value){
					//alert(1);
					var comment_data=getMyDate(value.comment_timestamp);
					var div_ele='<div id="rem'+value.comment_id+'" class="panel panel-default">'+
			            '<div class="text-center">'+
		              '<h3><a href="#">'+value.video_title+'</a></h3>'+
		              '<div>'+
		                  '<span>用户：<a href="#">'+value.user_nickname+'</a></span> • <span>'+comment_data+'</span>'+
		              '</div>  '+
		            '</div>'+
		            '<div class="panel-body">'+
		              '<h4>'+value.comment_content+'</h4>'+
		              '<div id="'+value.comment_id+'" class="text-right">'+
		              '</div>'+
		            '</div>'+
		          '</div>';
					$("#div_ele").append(div_ele);
					if(value.comment_ispass=="否"){
						var div_ispass= '<button onclick=\"isPossById(\''+value.comment_id+'\')\" type="button" class="btn btn-success">&nbsp;&nbsp;通&nbsp;&nbsp;过&nbsp;&nbsp;</button>'+
		                  '<button onclick=\"deleteById(\''+value.comment_id+'\')\" type="button" class="btn btn-danger">&nbsp;&nbsp;拒&nbsp;&nbsp;绝&nbsp;&nbsp;</button>';
						$("#"+value.comment_id).append(div_ispass);
					}
				});
				if(page.aNum.length>0){
					var previousPage='<li>'+
			            '<a href="javascript:findCommentByCourseId(\''+page.previousPage+'\',\''+page.commentKeyWord+'\')" aria-label="Previous">'+
		              '<span aria-hidden="true">&laquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#comment_page").append(previousPage);
					$(page.aNum).each(function(n,value){
						var middlePage='<li><a href="javascript:findCommentByCourseId(\''+value+'\',\''+page.commentKeyWord+'\')">'+value+'</a></li>';
						$("#comment_page").append(middlePage);
					});
					var nextPage='<li>'+
			            '<a href="javascript:findCommentByCourseId(\''+page.nextPage+'\',\''+page.commentKeyWord+'\')" aria-label="Next">'+
		              '<span aria-hidden="true">&raquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#comment_page").append(nextPage);
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
function findComment(currentPage){
	var chk_value =[];
	$('input[type="checkbox"]:checked').each(function(){ 
		chk_value.push($(this).val()); 
		}); 
	//alert(chk_value.length==0 ?'你还没有选择任何内容！':chk_value); 
	
	var commentKeyWord=$("#button_text").val();
	if(commentKeyWord==""){
		commentKeyWord="undefined";
	}
	$.ajax({
		url:basePath+"comment/commentFindByPage",
		type:"post",
		data:{"currentPage":currentPage,"commentKeyWord":commentKeyWord,"value":JSON.stringify(chk_value)},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//alert("成功");
				$("#div_ele").html("");
				$("#comment_page").html("");
				var page=result.data;
				var comments=page.data;
				$(comments).each(function(n,value){
					//alert(1);
					var comment_data=getMyDate(value.comment_timestamp);
					var div_ele='<div id="rem'+value.comment_id+'" class="panel panel-default">'+
			            '<div class="text-center">'+
		              '<h3><a href="#">'+value.video_title+'</a></h3>'+
		              '<div>'+
		                  '<span>用户：<a href="#">'+value.user_nickname+'</a></span> • <span>'+comment_data+'</span>'+
		              '</div>  '+
		            '</div>'+
		            '<div class="panel-body">'+
		              '<h4>'+value.comment_content+'</h4>'+
		              '<div id="'+value.comment_id+'" class="text-right">'+
		              '</div>'+
		            '</div>'+
		          '</div>';
					$("#div_ele").append(div_ele);
					if(value.comment_ispass=="否"){
						var div_ispass= '<button onclick=\"isPossById(\''+value.comment_id+'\')\" type="button" class="btn btn-success">&nbsp;&nbsp;通&nbsp;&nbsp;过&nbsp;&nbsp;</button>'+
		                  '<button onclick=\"deleteById(\''+value.comment_id+'\')\" type="button" class="btn btn-danger">&nbsp;&nbsp;拒&nbsp;&nbsp;绝&nbsp;&nbsp;</button>';
						$("#"+value.comment_id).append(div_ispass);
					}
				});
				if(page.aNum.length>0){
					var previousPage='<li>'+
			            '<a href="javascript:findComment('+page.previousPage+')" aria-label="Previous">'+
		              '<span aria-hidden="true">&laquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#comment_page").append(previousPage);
					$(page.aNum).each(function(n,value){
						var middlePage='<li><a href="javascript:findComment('+value+')">'+value+'</a></li>';
						$("#comment_page").append(middlePage);
					});
					var nextPage='<li>'+
			            '<a href="javascript:findComment('+page.nextPage+')" aria-label="Next">'+
		              '<span aria-hidden="true">&raquo;</span>'+
		            '</a>'+
		          '</li>';
					$("#comment_page").append(nextPage);
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
function check_all(btn) {
	if($(btn).hasClass("active")) {
		$(btn).siblings("button").addClass("active");
	} else {
		$(btn).siblings("button").removeClass("active");
	}
}
var push_timer;
function start_push() {
	// 拉取数据
	push_timer =setInterval(function(){
		//alert(1);
		//alert(number_aa);
		$.ajax({
			url:basePath+"comment/showtime",
			type:"post",
			dataType:"json",
			data:{"currentPage":number_aa},
			success:function(result){
				if(result.status==0){
					//alert("成功");
					var comments=result.data;
					$(comments).each(function(n,value){
						//alert(1);
						var comment_data=getMyDate(value.comment_timestamp);
						var div_ele='<div class="panel panel-default">'+
				            '<div class="text-center">'+
			              '<h3><a href="#">'+value.video_title+'</a></h3>'+
			              '<div>'+
			                  '<span>用户：<a href="#">'+value.user_nickname+'</a></span> • <span>'+comment_data+'</span>'+
			              '</div>  '+
			            '</div>'+
			            '<div class="panel-body">'+
			              '<h4>'+value.comment_content+'</h4>'+
			              '<div id="isPass'+n+'" class="text-right">'+
			              '<button id="isposs_1" type="button" class="btn btn-success">&nbsp;&nbsp;通&nbsp;&nbsp;过&nbsp;&nbsp;</button>'+
		                  '<button id="deleteComment_1" type="button" class="btn btn-danger">&nbsp;&nbsp;拒&nbsp;&nbsp;绝&nbsp;&nbsp;</button>'+
			              '</div>'+
			            '</div>'+
			          '</div>';
						$("#push_panels").append(div_ele);
					});
				}
			}
		});
		number_aa++;
		var n = $("#push_panels").children("div").length;
		if(n <= 30) {
			$("#push_panels .btn-success").click(function(){
				pass(this);
			});
			
			$("#push_panels .btn-danger").click(function(){
				reject(this);
			});
		}
	},2000);
}

function end_push() {
	clearInterval(push_timer);
}

function pass(btn) {
	$(btn).parent().parent().parent().remove();
}

function reject(btn) {
	$(btn).parent().parent().parent().remove();
}

function time_order(btn) {
	var $span = $(btn).children("span");
	if($span.hasClass("glyphicon-chevron-up")) {
		$span.removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
	} else {
		$span.removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
	}
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