function removeRecord(id){
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            cache : true,
            type : "POST",
            url : "/draftsDelete",
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
    })
}
