$(document).ready(function() {
	//提交
	$("#submit_btn").click(function(){
		//验证手机号
		var phone=$("#phone").val();
		var phoneRex = new RegExp("1[34578]{1}[0-9]{9}");
		if(phone==""){
			layer.alert("请输入手机号");
			return false;
		}
		if(phoneRex.test(phone)==false){
    		layer.alert("请输入格式正确的手机号");
    		return false;
    	}
		
		//验证邮箱
		var email=$("#email").val();
		var emailRex = new RegExp("([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})");
		if(email==""){
			layer.alert("请输入邮箱");
			return false;
		}
		if(emailRex.test(email)==false){
			layer.alert("请输入格式正确的邮箱格式");
    		return false;
		}
		
		//验证密码
		var pwd=$("#pwd").val();
		var pwdRex = new RegExp("[a-zA-Z0-9]{6,16}");
		if(pwd==""){
			layer.alert("请设置密码");
			return false;
		}
    	if(pwdRex.test(pwd)==false){
    		layer.alert("密码长度为6~16位的字母或数字");
    		return false;
    	}
		$.ajax({
			cache : true,
			type : "POST",
			url : "/front/cms/forgetPwdPost",
			data: $('#signupForm').serialize(),
			async : false,
			error : function(request) {
				parent.layer.alert("Connection error");
			},
			success : function(data) {
				if (data.code == 0) {
					layer.msg(data.msg);
					//找回密码成功跳转页面
					parent.location.href = '/front/cms';
				} else {
					layer.alert(data.msg);
				}
			}
		});
	});
	
	//发送验证码
	$("#send_code").click(function(){
		//验证邮箱
		var email=$("#email").val();
		var emailRex = new RegExp("([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})");
		if(email==""){
			layer.alert("请输入邮箱");
			return false;
		}
		if(emailRex.test(email)==false){
			layer.alert("请输入格式正确的邮箱格式");
    		return false;
		}
		
		$.ajax({
			cache : true,
			type : "POST",
			url : "/front/cms/sendCode",
			data: {
				"email":email
			},
			async : false,
			error : function(request) {
				parent.layer.alert("Connection error");
			},
			success : function(data) {
				if (data.code == 0) {
					layer.msg(data.msg);
				} else {
					layer.alert(data.msg);
				}
			}
		});
		
	});
	
});