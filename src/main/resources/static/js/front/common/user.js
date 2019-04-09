/**
 * 学生基本信息功能使用js功能
 */
$(document).ready(function() {
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		updateUserPassword()
	}
});
/**
 * 用户密码修改
 */
function updateUserPassword() {
	var pwdNew = $("#pwdNew").val();
	var confirm_password = $("#confirm_password").val();
	if(pwdNew==""){
		layer.alert("请输入密码");
		return false;
	}
	if(confirm_password==""){
		layer.alert("请输入确认密码");
		return false;
	}
	if(pwdNew.length>18||pwdNew.length<6){
		layer.alert("密码为6到18位");
		return false;
	}
	if(confirm_password != pwdNew){
		layer.alert("两次输入的密码不一致");
		return false;
	}
	$.ajax({
		cache : true,
		type : "POST",
		url : "/sys/user/front/frontUpdate",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				layer.msg("操作成功");
				$('input').val('');
				window.location.href='/login';
			} else {
				layer.alert(data.msg)
			}

		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			pwdOld : {
				required : true,
				rangelength : [6,20]
			},
			pwdNew : {
				required : true,
				rangelength : [6,20]
			},
			confirm_password: {
				required : true,
				rangelength : [6,20]
			},
		},
		messages : {
			pwdOld : {
				required : icon + "请输入旧密码",
				required : icon + " 请输入旧密码的长度为6个字符到20个字符之间"
			},
			pwdNew : {
				required : icon + "请输入密码",
				required : icon + "请输入新密码的长度在6个字符到20个字符之间"
			},
			confirm_password: {
				required : icon + "请输入确认密码",
				required : icon + "请输入确定密码的长度在6个字符到20个字符之间"
			},
		}
	})
}

