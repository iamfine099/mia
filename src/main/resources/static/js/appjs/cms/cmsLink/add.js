$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	var formData = new FormData($("#signupForm")[0]);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/cms/cmsLink/save",
		data: formData,
		contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
		processData: false, //必须false才会自动加上正确的Content-Type
		async : false,
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
	//对网址的验证
//	jQuery.validator.addMethod("fUrl", function(value, element) {  
//	    var length = value.length;  
//	    var regUrl = /^((https|http)://)?[a-z0-9A-Z]{3}\.[a-z0-9A-Z][a-z0-9A-Z]{0,61}?[a-z0-9A-Z]\.com|net|cn|cc (:s[0-9]{1-4})?$/;  
//	    return this.optional(element) || !regUrl.test( value );    
//	}, "请输入正确的链接格式"); 
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		
		rules : {
			fUrl : {
				required : true,
				url : true
			},
			fName:{
				required : true
			}
		},
		messages : {
			fUrl : {
				required : icon + "网址不能为空",
				url: icon + "请输入正确的链接格式，比如http://www.baidu.com"
			},
			fName:{
				required : "不能为空"
			}
		}
	})
}