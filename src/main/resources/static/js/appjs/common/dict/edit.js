$().ready(function() {
	validateRule();
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
		url : "/common/dict/update",
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
            name : {
                required : true,
                maxlength:20
            },
            sort : {
                digits:true
            },
            description : {
                maxlength:45
            },
            parentId : {
                digits:true
            }
		},
		messages : {
			name : {
				required : icon + "请输入名字",
				maxlength : icon + "最多输入45个字符"
			},
            name : {
                digits : icon + "只能输入数字"
            },
            description : {
                maxlength : icon + "最多输入45个字符"
            },
            parentId : {
                digits : icon + "只能输入数字"
            }
		}
	})
}