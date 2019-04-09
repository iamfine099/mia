$().ready(function() {
	$("input[name='status']").change(function(){
		  if($("input[name='status']:checked").val() == "1"){
			  $("#expert_div").show();
		  }else{
			  $("#expert_div").hide();
		  }
	});
});

function checkInfo() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/common/article/articleReviewScoreUpdateCheck",
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

/**
 * 选择打分专家
 * @returns
 */
function selectexpert(){
	layer.open({
		type : 2,
		title : '选择专家',
		maxmin : true,
		btn: ['确定', '取消'],
		shadeClose : false, // 点击遮罩关闭层
		area : [ '700px', '460px' ],
		content : '/mia/expert/selectexpert' ,
		yes: function(index, layero){
			//子级弹出框
			var childiframe = window[layero.find('iframe')[0]['name']]; 
			//子级弹出框中的方法
			var objs = childiframe.getValues();
			var expertIds = "";
			var expertNames = "";
			if(objs.length == 0){
				layer.msg("请选择导师");
			}else{
				for(var i=0; i<objs.length; i++){
					expertIds += objs[i].expertId+",";
					expertNames += objs[i].expertName+",";
				}
				$('#expertIds').val(expertIds.substring(0,expertIds.length-1));
				$('#expertNames').val(expertNames.substring(0,expertNames.length-1));
				layer.close(index); //如果设定了yes回调，需进行手工关闭
			}
		},
		btn2: function(index, layero){
			layer.close(index); 
		}
	});
}