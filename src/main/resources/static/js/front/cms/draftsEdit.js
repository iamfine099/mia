$(function() {

    $('#achievementIntro_sn').summernote({
        height: '350px',
        minHeight: 350,
        maxHeight: 350,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                send_File(files, editor, $editable, $('#achievementIntro_sn'));
            }
        }
    });
    var achievementIntroContent = $("#achievementIntro").val();
    $('#achievementIntro_sn').summernote('code', achievementIntroContent);

    $('#applicationCategory_sn').summernote({
        height: '350px',
        minHeight: 350,
        maxHeight: 350,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                send_File(files, editor, $editable, $('#applicationCategory_sn'));
            }
        }
    });
    var applicationCategoryContent = $("#applicationCategory").val();
    $('#applicationCategory_sn').summernote('code', applicationCategoryContent);

    $('#prospectAnalysis_sn').summernote({
        height: '350px',
        minHeight: 350,
        maxHeight: 350,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                send_File(files, editor, $editable, $('#prospectAnalysis_sn'));
            }
        }
    });
    var prospectAnalysisContent = $("#prospectAnalysis").val();
    $('#prospectAnalysis_sn').summernote('code', prospectAnalysisContent);

    $('#detailInformation_sn').summernote({
        height: '350px',
        minHeight: 350,
        maxHeight: 350,
        lang: 'zh-CN',
        callbacks: {
            onImageUpload: function (files, editor, $editable) {
                send_File(files, editor, $editable, $('#detailInformation_sn'));
            }
        }
    });
    var detailInformationContent = $("#detailInformation").val();
    $('#detailInformation_sn').summernote('code', detailInformationContent);

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
        tr.hide();
		$('#demoList').append($(tr));
		layui.use('upload', function() {
			var	upload = layui.upload;
			var uploadInst = upload.render({
				elem: '.fImg' + num,
				choose: function(obj) {
					obj.preview(function(index, file, result) {

                        tr.show();
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


/*	var content_summernote = $("#content_summernote").summernote('code');
	$("#content").val(content_summernote);*/

    var achievement_intro_sn = $("#achievementIntro_sn").summernote('code');
    $("#achievementIntro").val(achievement_intro_sn);

    var application_category_sn = $("#applicationCategory_sn").summernote('code');
    $("#applicationCategory").val(application_category_sn);

    var prospect_analysis_sn = $("#prospectAnalysis_sn").summernote('code');
    $("#prospectAnalysis").val(prospect_analysis_sn);

    var detail_information_sn = $("#detailInformation_sn").summernote('code');
    $("#detailInformation").val(detail_information_sn);


	var formData = new FormData($("#signupForm")[0]);
	$.ajax({
		cache: true,
		type: "POST",
		url: "/releasePostEdit", //接口
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
				window.location.href="/myArticle";
			} else {
				layer.alert(data.msg);
			}
		}
	});
}

function saveDrafts() {

	/*var content_summernote = $("#content_summernote").summernote('code');
	$("#content").val(content_summernote);*/

    var achievement_intro_sn = $("#achievementIntro_sn").summernote('code');
    $("#achievementIntro").val(achievement_intro_sn);

    var application_category_sn = $("#applicationCategory_sn").summernote('code');
    $("#applicationCategory").val(application_category_sn);

    var prospect_analysis_sn = $("#prospectAnalysis_sn").summernote('code');
    $("#prospectAnalysis").val(prospect_analysis_sn);

    var detail_information_sn = $("#detailInformation_sn").summernote('code');
    $("#detailInformation").val(detail_information_sn);


	var formData = new FormData($("#signupForm")[0]);
	$.ajax({
		cache: true,
		type: "POST",
		url: "/articleStoragEdit", //接口
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
				window.location.href="/drafts";
			} else {
				layer.alert(data.msg);
			}
		}
	});

}



function send_File(files, editor, $editable, summernote) {

    var size = files[0].size;
    if((size / 1024 / 1024) > 2) {
        alert("图片大小不能超过2M...");
        return false;
    }
    console.log("size="+size);
    var formData = new FormData();
    formData.append("file", files[0]);
    $.ajax({
        data : formData,
        type : "POST",
        url : "/common/sysFile/upload",    // 图片上传出来的url，返回的是图片上传后的路径，http格式
        cache : false,
        contentType : false,
        processData : false,
        dataType : "json",
        success: function(data) {//data是返回的hash,key之类的值，key是定义的文件名

            summernote.summernote('insertImage',data.fileName);
        },
        error:function(){
            alert("上传失败");
        }
    });
}