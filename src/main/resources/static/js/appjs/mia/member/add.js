$().ready(function() {
	validateRule();
	layui.use('upload', function(){
		var $ = layui.jquery,upload = layui.upload;
			var uploadInst =  upload.render({
		    elem: '#headImg'
		    ,choose: function(obj){
				//预读本地文件示例，不支持ie8
				obj.preview(function(index, file, result){
				   $('#headImg_img').attr('src', result); //图片链接（base64）
				});
		    }
		    ,auto: false//自动上传
		    ,accept: 'file' //普通文件     不添加默认为图片
		    ,field:'file'//input字段名称 默认file
		    ,done: function(res){
		      console.log(res);
		    }
			})
		  });
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save() {
	
	$.ajax({
		cache : true,
		type : "POST",
		url : "/mia/member/save",
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
					},
		messages : {
					}
	})
}