$(function() {
	// alert("login.js");
	$("#inputName").val(getCookie("loginName"));
	$(".container form").submit(function() {
		return login_form();
	});
});
function login_form() {
	// 获取用户名和密码
	var name = $("#inputName").val();
	var pwd = $("#inputPassword").val();
	var remember = $(".container form input[type=checkbox]").get(0).checked;
	// alert("login_form");
	// alert(name+" "+pwd);
	$.ajax({
		url : basePath + "user/login/" + name + "/" + pwd,
		type : "get",
		dataType : "json",
		success : function(result) {
			// alert("成功进入");
			if (result.status == 0) {
				window.location.href = "index.html";
				addCookie("loginName", name, 5)
			} else if (result.status == 1) {
				alert(result.message);
			}
		},
		error : function() {
			alert("请求失败!");
		}
	});
	return false;
}