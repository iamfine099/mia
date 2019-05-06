$().ready(function() {

    $("#specialty").on('click', function () {
        layeropen();
    })

	$('.summernote').summernote({
		height : '440px',
		lang : 'zh-CN',
        callbacks: {
            onImageUpload: function(files, editor, $editable) {
                sendFile(files);
            }
        }
    });
	$('.summernote1').summernote({
		height : '440px',
		lang : 'zh-CN',
        callbacks: {
            onImageUpload: function(files, editor, $editable) {
                sendFile1(files);
            }
        }
    });
	var experience = $("#experience").val();
	$('#experience_sn').summernote('code', experience);
	var achievement = $("#achievement").val();
	$('#achievement_sn').summernote('code', achievement);
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
		update();
	}
});

function update() {
	var experience_sn = $("#experience_sn").summernote('code');
	$("#experience").val(experience_sn);
	var achievement_sn = $("#achievement_sn").summernote('code');
	$("#achievement").val(achievement_sn);
	var formData = new FormData($("#signupForm")[0]);
    formData.set("specialty", $("#sp_id").val());
	$.ajax({
		cache : true,
		type : "POST",
		url : "/mia/expert/update",
		data : formData,// 你的formid
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
	//密码的验证
	jQuery.validator.addMethod("pwd", function(value, element) {
	    var length = value.length;
	    var pwd =  /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$/
	    return this.optional(element) || !pwd.test(value);
	}, "密码必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间");
	//手机号码的验证
	jQuery.validator.addMethod("mobile", function(value, element) {
	    var length = value.length;
	    var mobile =  /^1[34578]{1}[0-9]{9}$/
	    return this.optional(element) || (length == 11 && mobile.test(value));
	}, "手机号码格式错误"); 
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		ignore:".note-image-input,.note-group-select-from-files",
		rules : {
			expertName:{
				required:true,
				minlength:2,
			},
			phone:{
				required:true,
				minlength:11,
				maxlength:11,
				mobile:true,
			},
			email:{
				required:true,
				email:true,
			},
			numberCard:{
				required:true,
			},
			password:{
				required:true,
				minlength:6,
				maxlength:16,
				pwd:true,
			},
			specialty:{
				required:true,
			},
			company:{
				required:true,
			},
			grll:{
				required:true,
			},
			xscj:{
				required:true,
			}
			},
			messages : {
				expertName:{
					required:icon+"姓名不能为空",
					minlength:icon+"姓名不能少于2个字",
				},
				phone:{
					required:icon+"手机号不能为空",
					minlength:icon+"手机号不能少于11位",
					maxlength:icon+"手机号不能多于11位",
					mobile:icon+"手机号码格式错误"
				},
				email:{
					required:icon+"邮箱不能为空",
					email:icon+"请输入正确的邮箱格式",
				},
				numberCard:{
					required:icon+"编号不能为空",
				},
				password:{
					required:icon+"密码不能为空",
					minlength:icon+"密码长度不能小于6",
					maxlength:icon+"密码长度不能大于16",
					pwd:icon+"密码必须包含大小写字母和数字的组合，不能使用特殊字符，长度在6-16之间",
				},
				specialty:{
					required:icon+"专长不能为空",
				},
				company:{
					required:icon+"工作单位不能为空",
				},
				grll:{
					required:icon+"个人履历不能为空",
				},
				xscj:{
					required:icon+"学术成就不能为空",
				}
			}
	})
}


function layeropen() {
    layer.open({
        type: 2,
        title: '选择专长',
        maxmin: true,
        btn: ['确定', '取消'],
        shadeClose: false, // 点击遮罩关闭层
        area: ['40%', '65%'],
        content: '/front/cms/layeropen',//这里链接到打开的弹出框  layeropen
        yes: function (index, layero) {
            var body = layer.getChildFrame('body', index);
            var id = body.find("#speciality_id").val();
            var text = body.find("#f_name").val();
            var value = body.find("#speciality_value").val();
            if (id != '') {

                $('#specialty').val(text);
                $('#sp_id').val(value);
            }
            layer.close(index); //如果设定了yes回调，需进行手工关闭
        },
        btn2: function (index, layero) {
            layer.close(index);
        }
    });

}