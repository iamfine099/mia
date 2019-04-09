$(function(){
		$("#modify_Pwd").click(function(){
			//密码的正则表达式
			var pwdRex=new RegExp('[a-zA-Z0-9]{6,16}');
            var old_Pwd = $('#old_pwd').val();
            var new_Pwd = $('#new_pwd').val();
            var confirmPwd = $('#confirmPwd').val();
            if(old_Pwd==''){
            	layer.alert('请输入原密码');
				return false;
            }
            if(!pwdRex.test(old_Pwd)){
            	layer.alert('旧密码长度为6-16个数字或字母');
				return false;
            }
            if(new_Pwd==''){
            	layer.alert('请输入新密码');
				return false;
            }
            if(!pwdRex.test(new_Pwd)){
            	layer.alert('新密码长度为6-16个数字或字母');
				return false;
            }
            if(confirmPwd==''){
            	layer.alert('请输入确认密码');
				return false;
            }
            if (confirmPwd!= new_Pwd) {
            	layer.alert('新密码与确认密码不一致');
				return false;
			}
			$.ajax({
				type : "POST",
				url : "/front/cms/modifyPwd/update",
				data :{
					old_Pwd:old_Pwd,
					new_Pwd:new_Pwd
				},
				error : function(request) {
					parent.layer.alert("Connection error");
				},
				success : function(data) {
					if (data.code == 0) {
						layer.msg("操作成功");
						window.location.href="/front/cms/personalInfo";
					} else {
						layer.alert(data.msg);
					}
				}
			}); 
     	})
		
     });
