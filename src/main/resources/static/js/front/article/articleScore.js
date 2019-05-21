//真评分
$(document).ready(function() {
	$(".score_btn").click(function(){
		var id = $("#id").val(); 
		var score = $("#score").val();
		if(score == ""){
			parent.layer.alert("请填写分数");
			return false;
		}else if(isNaN(score) == true){
			parent.layer.alert("请填写正确格式的分数");
			return false;
		}
		$.ajax({
			cache : true,
			type : "POST",
			url : "/score",
			data : {
				"id":id,
				"score":score
			},
			async : false,
			error : function(request) {
				parent.layer.alert("Connection error");
			},
			success : function(data) {
				if (data.code == 0) {
					parent.layer.msg("操作成功");
				} else {
					parent.layer.alert(data.msg)
				}

			}
		});
	});
});

function score() {
	var id = $("#id").val(); 
	var score = $("#score").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/score",
		data : {
			"id":id,
			"score":score
		},
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}

//评论
function comment() {
	var id = $("#id").val(); 
	var cId = $("#cId").val();
	var cContent = $("#cContent").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/comment",
		data : {
			"id":id,
			"cId":cId,
			"cContent":cContent
		},
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");

			} else {
				parent.layer.alert("无法评论");
			}

		}
	});

}
//点赞
function recommendnum() {
	var id = $("#id").val(); 
	var isLike = $("#isLike").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/recommendnum",
		data : {
			"id":id,
			"isLike":isLike
		},
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				if(isLike == 1){
					parent.layer.msg("操作成功");
					$(".support_btn").html("<img src='/img/xiaotu_03.png'>点赞");
					$("#isLike").val(0);
				}else{
					parent.layer.msg("操作成功");
					$(".support_btn").html("<img src='/img/xiaotu_03.png'>取消点赞");
					$("#isLike").val(1);
				}
			}else{
				parent.layer.alert(data.msg);
			}

		}
	});

}
//推荐
function recommend() {
	var id = $("#id").val();
	var isRecommend = $("#isRecommend").val();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/recommend",
		data : {
			"id":id,
			"isRecommend":isRecommend
		},
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
		},
		success : function(data) {
			if (data.code == 0) {
				if(isRecommend == 1){
					parent.layer.msg("操作成功");
					$(".tuijian_btn").html("<img src='/img/xiaotu_06.png'>推荐");
					$("#isRecommend").val(0);
				}else{
					parent.layer.msg("操作成功");
					$(".tuijian_btn").html("<img src='/img/xiaotu_06.png'>取消推荐");
					$("#isRecommend").val(1);
				}
				
			} else{
				parent.layer.alert(data.msg);
			}

		}
	});

}