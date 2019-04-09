$().ready(function() {
	$("#changePwd").on("change",function(){
		var item = $("#changePwd").val();
		console.log(item);
		if(item=="固定密码"){
			gudingmima();
		}
		if(item=="输入密码"){
			shurumima();
		}
	})
	function gudingmima(){
		$("#password").val(666666).attr("disabled","disabled");
	}
	function shurumima(){
		$("#password").val('').removeAttr("disabled");
	}
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});

function update() {
	var password=$("#password").val()+'';//使其变成字符串
	var memId=$("#memId").val()+'';//使其变成字符串
	var formData = new FormData($("#signupForm")[0]);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/mia/member/resetpwdupdate",
		data : {
			'password':password,
			'memId':memId,
		},// 你的formid
//		contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
//		processData: false, //必须false才会自动加上正确的Content-Type
//		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		ignore:".note-image-input,.note-group-select-from-files",
		rules : {
			password:{
				required:true,
				minlength:6,
				maxlength:16,
				
			}
			
			},
			messages : {
				
				password:{
					required:icon+"密码不能为空",
					minlength:icon+"密码长度不能小于6",
					maxlength:icon+"密码长度不能大于16",
				},
				
			}
	})
}