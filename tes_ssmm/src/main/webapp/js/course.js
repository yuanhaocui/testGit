//@ sourceURL=course.js
var courseId;
var picture_new;
var userId;
var type_global;
var semester_global;
var duration_global;
$(function() {
	$("#getAllCourses select").change(function(){
		selectAddMyCourse();
	});
	$("#addCourse_ui li:eq(2)").click(function() {
		getAllMyCourses(1);
	});
	$("#getAllCourses button:eq(0)").click(function() {
		addMyCourseSave();
	});
	$("#addCourse_ui li:eq(0)").click(function() {
		findCourse(1);
	});
	findCourse(1);
	$("#course_button").click(function() {
		findCourse(1);
	});

	$("#getAllCourses_btn").click(function() {
		getAllCourses();
	});

	$("#mycourse_button").click(function() {
		getAllMyCourses(1);
	});
	// 添加课程信息
	$("#addCoursePanel button:eq(0)").click(function() {
		// alert("form");
		return addCourse();
	});
	// 删除课程信息
	$("#delete_button").click(function() {
		var dom = getCourseTr(courseId, "delCourse");
		deleteCourse(dom);
	});

	$("#deleteMyCourse_button").click(function() {
		deleteMyCourse();
	});
	// 修改课程信息
	$("#editCourse button:eq(0)").click(function() {
		return updateCourse();
	});
});

function selectAddMyCourse(){
	var myCourseId = $("#getAllCourses select").val();
	$.ajax({
		url : basePath + "course/getCourseById",
		type : "get",
		data : {
			"id" : myCourseId,
		},
		dataType : "json",
		success : function(result) {
			var data = result.data;
			$("#getAllCourses input:eq(0)").val(data.semsterString);
			$("#getAllCourses input:eq(1)").val(data.typeString);
			$("#getAllCourses input:eq(2)").val(data.score);
			$("#getAllCourses input:eq(3)").val(data.durantionString);
			$("#getAllCourses input:eq(4)").val(data.order);
			$("#getAllCourses textarea").val(data.desc);
			
		},
		error : function() {
			alert("请求失败!");
		}
	});
}

function addMyCourseSave() {
	var courseId = $("#getAllCourses select").val();
	$.ajax({
		url : basePath + "course/addMyCourse/" + courseId + "/" + userId,
		type : "post",
		dataType : "json",
		success : function(result) {
			$("#addCourse_ui li:eq(2)").click();
		},
		error : function() {
			alert("请求失败!");
		}
	});
	$("#getAllCourses").modal("toggle");
	return false;
}
function deleteMyCourse() {
	$.ajax({
		url : basePath + "course/delmycourse/" + courseId,
		type : "delete",
		dataType : "json",
		success : function(result) {
			$("#addCourse_ui li:eq(2)").click();
		},
		error : function() {
			alert("请求失败!");
		}
	});
	$("#deleteMyCourseModal").modal('toggle');
	return false;
}
// 修改课程信息的方法
function updateCourse() {
	var courseName = $("#editCourse input:eq(0)").val();
	var courseOrder = $("#editCourse input:eq(2)").val();
	var courseDesc = $("#editCourse textarea").val();
	var semsterString = $("#editCourse select:eq(0)").val();
	var typeString = $("#editCourse select:eq(1)").val();
	var durantionString = $("#editCourse select:eq(2)").val();
	var score = $("#editCourse input:eq(1)").val();
	$.ajaxFileUpload({
		url : basePath + "course/update",
		secureuri : false,
		fileElementId : "picture_edit",
		type : "post",
		dataType : "text",
		data : {
			"id" : courseId,
			"name" : courseName,
			"order" : courseOrder,
			"desc" : courseDesc,
			"semester" : semsterString,
			"type" : typeString,
			"score" : score,
			"duration" : durantionString,
			"picture" : picture_new
		},
		success : function(data, status) {
			data = data.replace(/<PRE.*?>/g, '');
			data = data.replace("<PRE>", '');
			data = data.replace("</PRE", '');
			data = data.replace(/<pre.*?>/g, '');
			data = data.replace("<pre>", '');
			data = data.replace("</pre>", '');
			alert(data);
			findCourse(1);
		},
		error : function() {
			alert("求情失败!");
		}
	});
	
	$("#editCourse").modal("toggle");
	return false;
}
// 获取修改数据
function getupdCourseId(id, name, semsterString, typeString, durantionString, score, order, desc, type, semester, duration, picture) {
	courseId = id;
	picture_new = picture;
	type_global = type;
	semester_global = semester;
	duration_global = duration;
	var courseName = name;
	var courseOrder = order;
	var courseDesc = desc;
	$("#nameEdit").val(courseName);
	$("#editCourse #orderEdit").val(courseOrder);
	$("#editCourse #scoreEdit").val(score);
	$("#editCourse #descEdit").val(courseDesc);
	$("#editCourse #semEdit").val(semester);
	$("#editCourse #typeEdit").val(type);
	$("#editCourse #durEdit").val(duration);
}
// 删除课程信息的方法
function deleteCourse(dom) {
	$.ajax({
		url : basePath + "course/del/" + courseId,
		type : "delete",
		dataType : "json",
		success : function(result) {
			alert(result.message);
			// alert(dom);
			dom.remove();
		},
		error : function() {
			alert("请求失败!");
		}
	});
	$("#deleteCourseModal").modal("toggle");
}
// 获取当前行tr
function getCourseTr(courseId, type) {
	return $("#" + type + courseId).parent().parent();
}
// 获取id
function getCourseId(id) {
	// alert(id);
	courseId = id;
}
// 添加课程信息
function addCourse() {
	var courseName = $("#addCoursePanel input:eq(0)").val();
	var courseOrder = $("#addCoursePanel input:eq(2)").val();
	var courseDesc = $("#addCoursePanel textarea").val();
	var semsterString = $("#addCoursePanel select:eq(0)").val();
	var typeString = $("#addCoursePanel select:eq(1)").val();
	var durantionString = $("#addCoursePanel select:eq(2)").val();
	var score = $("#addCoursePanel input:eq(1)").val();
	$.ajaxFileUpload({
		url : basePath + "course/new",
		secureuri : false,
		fileElementId : "picture_add",
		type : "post",
		dataType : "text",
		data : {
			"id" : courseId,
			"name" : courseName,
			"order" : courseOrder,
			"desc" : courseDesc,
			"semester" : semsterString,
			"type" : typeString,
			"score" : score,
			"duration" : durantionString,
			"picture" : picture_new
		},
		success : function(data, status) {
			data = data.replace(/<PRE.*?>/g, '');
			data = data.replace("<PRE>", '');
			data = data.replace("</PRE", '');
			data = data.replace(/<pre.*?>/g, '');
			data = data.replace("<pre>", '');
			data = data.replace("</pre>", '');
			alert(data);
			$("#addCourse_ui a:eq(0)").click();
		},
		error : function() {
			alert("求情失败!");
		}
	});
	
	return false;
}

function getAllCourses() {
	$.ajax({
		url : basePath + "course/queryAll",
		type : "get",
		dataType : "json",
		success : function(result) {
			data = result.data;
			$(data).each(
				function(n, value) {
					var op = '<option value="' + value.id + '">'
						+ value.name + '</option>';
					$("#getAllCourses select").append(op);
				});
		},
		error : function() {
			alert("请求失败!");
		}
	});
}

function getAllMyCourses(currentPage) {
	var courseKeyWord = $("#mycourse_keyword").val();
	if (courseKeyWord == "") {
		courseKeyWord = "undefined";
	}
	$
		.ajax({
			url : basePath + "course/mycourse",
			type : "get",
			data : {
				"currentPage" : currentPage,
				"courseKeyword" : courseKeyWord,
				"userId" : userId
			},
			dataType : "json",
			success : function(result) {
				if (result.status == 0) {
					// alert("成功");
					$("#mycourse_table tbody").html("");
					$("#mycourse_page").html("");
					var page = result.data;
					var users = page.data;
					$(users)
						.each(
							function(n, value) {
								value.regDate = getMyDate(value.regDate);
								// alert(value.regDate);
								var tr = '<tr>' + '<td>'
									+ (n + 1)
									+ '</td>'
									+ '<td><img src="images/'
									+ value.picture
									+ '"></td>'
									+ '<td>'
									+ value.name
									+ '</td>'
									+ '<td>'
									+ value.score
									+ '</td>'
									+ '<td>'
									+ value.typeString
									+ '</td>'
									+ '<td>'
									+ value.semsterString
									+ '</td>'
									+ '<td>'
									+ value.durantionString
									+ '</td>'
									+ '<td>'
									+ value.order
									+ '</td>'
									+ '<td>'
									+ value.regDate
									+ '</td>'
									+ '<td>'
									+ value.desc
									+ '</td>'
									+ '<td>'
									+ '<a href="" id="delCourse'
									+ value.id
									+ '" onclick=\"getCourseId(\''
									+ value.id
									+ '\')\" data-toggle="modal" data-target="#deleteMyCourseModal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'
									+ '</td>' + '</tr>';
								$("#mycourse_table tbody").append(
									tr);
							});
					if (page.aNum.length > 0) {
						var previousPage = '<li>'
							+ '<a href="javascript:getAllMyCourses('
							+ page.previousPage
							+ ')" aria-label="Previous">'
							+ '<span aria-hidden="true">&laquo;</span>'
							+ '</a>' + '</li>';
						$("#mycourse_page").append(previousPage);
						$(page.aNum)
							.each(
								function(n, value) {
									var middlePage = '<li><a href="javascript:getAllMyCourses('
										+ value
										+ ')">'
										+ value
										+ '</a></li>';
									$("#mycourse_page").append(
										middlePage);
								});
						var nextPage = '<li>'
							+ '<a href="javascript:getAllMyCourses('
							+ page.nextPage + ')" aria-label="Next">'
							+ '<span aria-hidden="true">&raquo;</span>'
							+ '</a>' + '</li>';
						$("#mycourse_page").append(nextPage);
					}
				} else if (result.status == 1) {
					alert(result.message);
				}
			},
			error : function() {
				alert("求情失败");
			}
		});

}
function ifTeacher(userId) {
	$.ajax({
		url : basePath + "user/query/",
		type : "post",
		data : {
			'userId' : userId
		},
		dataType : "json",
		success : function(result) {
			if (result.status == 0) {
				document.getElementById("my_course").setAttribute("hidden",
					false);
			// $("#addCourse_ui li:eq(2)").html('<li role="presentation"
			// hidden=true><a href="#addCoursePanel"
			// aria-controls="addCoursePanel" role="tab"
			// data-toggle="tab">我的</a></li>');
			} else {
				//				document.getElementById("my_course").setAttribute("hidden",
				//						true);
				$("#my_course").html("");
			}
		},
		error : function() {
			alert("请求失败!");
		}
	});
}
function findCourse(currentPage) {
	var cookie = getCookie("loginName");
	userId = cookie;
	ifTeacher(cookie);
	var courseKeyWord = $("#course_keyword").val();
	if (courseKeyWord == "") {
		courseKeyWord = "undefined";
	}
	// alert(userKeyWord);
	$
		.ajax({
			url : basePath + "course/page",
			type : "get",
			data : {
				"currentPage" : currentPage,
				"courseKeyword" : courseKeyWord
			},
			dataType : "json",
			success : function(result) {
				if (result.status == 0) {
					// alert("成功");
					$("#course_table tbody").html("");
					$("#course_page").html("");
					var page = result.data;
					var users = page.data;
					$(users)
						.each(
							function(n, value) {
								value.regDate = getMyDate(value.regDate);
								// alert(value.regDate);
								var tr = '<tr>' + '<td>'
									+ (n + 1)
									+ '</td>'
									+ '<td><img src="images/'
									+ value.picture
									+ '"></td>'
									+ '<td>'
									+ value.name
									+ '</td>'
									+ '<td>'
									+ value.score
									+ '</td>'
									+ '<td>'
									+ value.typeString
									+ '</td>'
									+ '<td>'
									+ value.semsterString
									+ '</td>'
									+ '<td>'
									+ value.durantionString
									+ '</td>'
									+ '<td>'
									+ value.order
									+ '</td>'
									+ '<td>'
									+ value.regDate
									+ '</td>'
									+ '<td>'
									+ value.desc
									+ '</td>'
									+ '<td>'
									+ '<a href="" id="updCourse'
									+ value.id
									+ '" onclick=\"getupdCourseId(\''
									+ value.id
									+ '\',\''
									+ value.name
									+ '\',\''
									+ value.semsterString
									+ '\',\''
									+ value.typeString
									+ '\',\''
									+ value.durantionString
									+ '\',\''
									+ value.score
									+ '\',\''
									+ value.order
									+ '\',\''
									+ value.desc
									+ '\',\''
									+ value.type
									+ '\',\''
									+ value.semester
									+ '\',\''
									+ value.duration
									+ '\',\''
									+ value.picture
									+ '\')\" data-toggle="modal" data-target="#editCourse"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</a>'
									+ '<a href="" id="delCourse'
									+ value.id
									+ '" onclick=\"getCourseId(\''
									+ value.id
									+ '\')\" data-toggle="modal" data-target="#deleteCourseModal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除</a>'
									+ '</td>' + '</tr>';
								$("#course_table tbody").append(tr);
							});
					if (page.aNum.length > 0) {
						var previousPage = '<li>'
							+ '<a href="javascript:findCourse('
							+ page.previousPage
							+ ')" aria-label="Previous">'
							+ '<span aria-hidden="true">&laquo;</span>'
							+ '</a>' + '</li>';
						$("#course_page").append(previousPage);
						$(page.aNum)
							.each(
								function(n, value) {
									var middlePage = '<li><a href="javascript:findCourse('
										+ value
										+ ')">'
										+ value
										+ '</a></li>';
									$("#course_page").append(
										middlePage);
								});
						var nextPage = '<li>'
							+ '<a href="javascript:findCourse('
							+ page.nextPage + ')" aria-label="Next">'
							+ '<span aria-hidden="true">&raquo;</span>'
							+ '</a>' + '</li>';
						$("#course_page").append(nextPage);
					}
				} else if (result.status == 1) {
					alert(result.message);
				}
			},
			error : function() {
				alert("求情失败");
			}
		});
}
// 修改时间方法
function getMyDate(str) {
	var oDate = new Date(str),
		oYear = oDate.getFullYear(),
		oMonth = oDate
				.getMonth() + 1,
		oDay = oDate.getDate(),
		oTime = oYear + '-'
		+ getzf(oMonth) + '-' + getzf(oDay); // 最后拼接时间
	return oTime;
}
;
// 补0操作
function getzf(num) {
	if (parseInt(num) < 10) {
		num = '0' + num;
	}
	return num;
}