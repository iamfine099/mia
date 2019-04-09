$().ready(function() {
	validateRule();
	parent.reLoad();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/common/userMessage/update",
		data : $('#signupForm').serialize(),// 你的formid
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
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			showSelect: {
				required : true
			},
			fto: {
				required : true
			},
			fMessage: {
				required : true
			},
		},
		messages : {
			showSelect: {
				required : icon + "请选择消息发送方"
			},
			fto: {
				required : icon + "请选择消息发送方"
			},
			fMessage: {
				required : icon + "请输入消息内容"
			},
		}
	})
}