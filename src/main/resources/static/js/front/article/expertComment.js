function removeRecord(id) {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/front/cms/remove",
		data:{ "id" : id},// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				window.parent.location.reload();
				/*parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);*/

			} else {
				parent.layer.alert(data.msg)
				window.parent.location.reload();
			}

		}
	});

}
