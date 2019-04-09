$(function() {
	$('.summernote').summernote({
		height: '350px',
		minHeight: 350,
		maxHeight: 350,
		lang: 'zh-CN',
        callbacks: {
            onImageUpload: function(files, editor, $editable) {
                sendFile(files);
            }
        }
    });
	var content = $("#content").val();
	$('#content_summernote').summernote('code', content);
	
	var num = $("#demoList").find('tr').length;
	$('#testList').on('click', function() {
		num++;
		$("#imgpreBox").stop().show();
		var tr = $(['<tr>',
//					'<td id="imgPre' + num + '"></td>',
					'<td id="textPre' + num + '"></td>',
					'<td><button type="button" style="margin-right:5px;" class="layui-btn layui-btn-xs fImg'+num+'" >修改</button><button type="button" class="layui-btn layui-btn-xs" onclick="deleteTr(this)">删除</button></td>',
					'</tr>'
				].join(''));
		$('#demoList').append($(tr));
		layui.use('upload', function() {
			var	upload = layui.upload;
			var uploadInst = upload.render({
				elem: '.fImg' + num,
				choose: function(obj) {
					obj.preview(function(index, file, result) {
//						$('#imgPre' + num).html('<img width="100" height="50" src="' + result + '">');
						$('#textPre' + num).html(file.name);
					});
				},
				auto: false,
				accept: 'file',
				field: 'file',
				multiple: false,
				done: function(res) {
					console.log(res);
				}
			});
		});
		$('.fImg'+ num).click();
	});

	$('#tijiao').on('click', function() {
		saveArticle();
	});
	$('#zancun').on('click', function() {
		saveDrafts();
	});

	var thisNum=$('#demoList').find('tr').length;
	if(thisNum<=0){
		$("#imgpreBox").stop().hide();
	}
});
function deleteTr(item){
	var parent = $(item).parents('tr');
	var id = $(parent).find(".fileId").val();
	
	if(id != undefined){
		$(parent).find(".isDel").val(1);
		$(parent).hide();
	}else{
		$(parent).remove();
	}
	var thisNum=$('#demoList').find('tr:visible').length;
	if(thisNum<=0){
		$("#imgpreBox").hide();
	}
}
//发布文章
function saveArticle() {
	var content_summernote = $("#content_summernote").summernote('code');
	$("#content").val(content_summernote);
	var formData = new FormData($("#signupForm")[0]);
	$.ajax({
		cache: true,
		type: "POST",
		url: "/front/cms/releasePostEdit", //接口
		data: formData, // 表单序列化  使表单变成一个字符串
		contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
		processData: false, //必须false才会自动加上正确的Content-Type
		async: false,
		error: function(request) {
			parent.layer.alert("Connection error");
		},
		success: function(data) {
			if(data.code == 0) {
				layer.msg("操作成功");
				window.location.href="/front/cms/myArticle";
			} else {
				layer.alert(data.msg);
			}
		}
	});
}

function saveDrafts() {

	var content_summernote = $("#content_summernote").summernote('code');
	$("#content").val(content_summernote);
	var formData = new FormData($("#signupForm")[0]);
	$.ajax({
		cache: true,
		type: "POST",
		url: "/front/cms/articleStoragEdit", //接口
		data: formData, // 表单序列化  使表单变成一个字符串
		contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理 
		processData: false, //必须false才会自动加上正确的Content-Type
		async: false,
		error: function(request) {
			parent.layer.alert("Connection error");
		},
		success: function(data) {
			if(data.code == 0) {
				layer.msg("操作成功");
				window.location.href="/front/cms/drafts";
			} else {
				layer.alert(data.msg);
			}
		}
	});

}