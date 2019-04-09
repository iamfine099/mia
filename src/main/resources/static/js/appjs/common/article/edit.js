$().ready(function() {
	$('.summernote').summernote({
		height : '440px',
		lang : 'zh-CN',
        callbacks: {
            onImageUpload: function(files, editor, $editable) {
                console.log("onImageUpload");
                sendFile(files);
            }
        }
    });
	var content = $("#content").val();

	$('#content_sn').summernote('code', content);
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
	validateRule();
	layui.use('upload', function(){
		var $ = layui.jquery
		  ,upload = layui.upload;
			var uploadInst =  upload.render({
		    elem: '#imgupload',
		    choose: function(obj){
			      //预读本地文件示例，不支持ie8
			      obj.preview(function(index, file, result){
			        $('#imguploaddemo').attr('src', result); //图片链接（base64）
			      });
			 }
		    ,auto: false//自动上传
		    ,field:'file'//input字段名称 默认file
		    ,done: function(res){
		      console.log(res)
		    }
		  });
	});
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function deleteTr(item){
	var parent = $(item).parents('tr');
	var id = $(parent).find("#fileId").val();
	
	if(id != undefined){
		$(parent).find("#isDel").val(1);
		$(parent).hide();
	}else{
		$(parent).remove();
	}
	var thisNum=$('#demoList').find('tr:visible').length;
	if(thisNum<=0){
		$("#imgpreBox").hide();
	}
}
function update() {
	var content_sn = $("#content_sn").summernote('code');
	$("#content").val(content_sn);
	var formData = new FormData($("#signupForm")[0]);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/common/article/update",
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
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
					},
		messages : {
					}
	})
}

var openCategory = function(){
	layer.open({
		type:2,
		title:"选择栏目",
		area : [ '300px', '450px' ],
		content:"/common/category/treeView"
	})
}

function loadCategory(categoryId,categoryName){
	$("#categoryId").val(categoryId);
	$("#categoryName").val(categoryName);
}