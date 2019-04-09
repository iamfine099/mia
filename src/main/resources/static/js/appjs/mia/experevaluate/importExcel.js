function importExcel() {

	 var fileObj = document.getElementById("file").files[0]; // js 获取文件对象
	 var form = new FormData(); // FormData 对象
	 form.append("file", fileObj); // 文件对象
	 
	$.ajax({
		cache : true,
		type : "POST",
		url : "/mia/experevaluate/importExcel",
		data :  form, // 你的formid
		async : false,
		contentType: false, //不设置内容类型
		processData: false, //不处理数据
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			console.log(data);
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
