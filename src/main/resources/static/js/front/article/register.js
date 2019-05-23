$(document).ready(function() {
	$("#specialty").on('click',function(){
		layeropen();
	})
	$("#register_btn").click(function(){
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
		
		//验证真实姓名
		var trueName=$("#trueName").val();
		if(trueName==""){
			layer.alert("请输入真实姓名");
			return false;
		}
		//验证工作地址
		var workplace=$("#workplace").val();
		if(workplace==""){
			layer.alert("请输入工作地址");
			return false;
		}
		
		//验证邮箱
		var email=$("#email").val();
		var emailRex = new RegExp("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
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
    	
    	//验证验证码
    	var code=$("#code").val()
    	if(code==""){
    		layer.alert("请输入验证码");
    		getcode();
    		return false;
    	}
    	
    	//验证确认密码
    	var rpwd=$("#rpwd").val();
    	var pwd=$("#pwd").val();
    	if(rpwd!=pwd){
    		layer.alert("您两次输入的密码不一致");
    		return false;
    	}
    	
    	//验证是否勾选用户协议
    	var read_check=$('.read_check')[0].checked;
    	if(!read_check){
    		layer.alert("请购选阅读并接受《用户协议》");
    		return false;
    	}
        var formData = $('#signupForm').serialize();
        //formData.set("specialty", $("#sp_id").val());
        $.ajax({
			cache : true,
			type : "POST",
			url : "/registerPost",
			data: formData,
			async : false,
			error : function(request) {
				parent.layer.alert("Connection error");
			},
			success : function(data) {
				if (data.code == 0) {
					layer.msg(data.msg);
					//注册成功跳转页面
					parent.location.href = '/front/cms';
				} else {
					getcode();
					layer.alert(data.msg);
					
				}
			}
		});
	});
	
});
function getcode(){
	$("#codeImg").attr("src","/code/image?"+new Date().getTime());
}

function layeropen(){
	layer.open({
		type : 2,
		title : '选择专长',
		maxmin : true,
		btn: ['确定', '取消'],
		shadeClose : false, // 点击遮罩关闭层
		area : [ '40%', '65%' ],
		content : '/layeropen' ,//这里链接到打开的弹出框  layeropen
		yes: function(index, layero){
			var body = layer.getChildFrame('body', index);
			var id=body.find("#speciality_id").val();
			var text=body.find("#f_name").val();
            var value = body.find("#speciality_value").val();

            if(id!=''){
				$('#specialty').val(text);
				$('#sp_id').val(value);
			}
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		},
		btn2: function(index, layero){
			layer.close(index); 
		}
	});

}


