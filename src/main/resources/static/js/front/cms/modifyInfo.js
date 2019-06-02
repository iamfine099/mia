$().ready(function () {

    $("#specialty").on('click', function () {
        layeropen();
    });

    $("#modifyInfo_btn").click(function () {
        //手机号码的正则表达式
        var phoneRex = new RegExp('1[34578]{1}[0-9]{9}');
        //邮箱的正则表达式
        var emailRex = new RegExp("^[a-z0-9A-Z]+[- | a-z0-9A-Z . _]+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-z]{2,}$");
        var memId = $('#memId').val();
        //获取专长ID和值
        var sp_id = $("#sp_id").val();
        var specialty = $("#specialty").val();
        //邮箱
        var email = $("#email").val();
        if (email == "") {
            layer.alert("请输入邮箱");
            return false;
        }
        //手机号
        var phone = $('#phone').val();
        if (phone == '') {
            layer.alert('请输入手机号');
            return false;
        }
        if (!phoneRex.test(phone)) {
            layer.alert('手机号格式不对');
            return false;
        }
        //名字
        var name = $("#memName").val();
        if (name == '') {
            layer.alert('请输入真实名字');
            return false;
        }
        //工作地点
        var company = $("#company").val();
        if (company == '') {
            layer.alert('请输入工作单位');
            return false;
        }

        var formData = new FormData($("#signupFormR")[0]);
        //formData.append("specialty", sp_id);

        $.ajax({
            cache: true,
            type: "POST",
            url: "/modifyPersonalInfo/update",
            data: formData,
            async: false,
            contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理
            processData: false, //必须false才会自动加上正确的Content-Type
            error: function (request) {
                parent.layer.alert("Connection error");
            },
            success: function (data) {
                if (data.code == 0) {
                    parent.layer.msg("操作成功");
                    window.location.href = "/personalInfo";
//    				parent.reLoad();
//    				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
//    				parent.layer.close(index);

                } else {
                    parent.layer.alert(data.msg)
                }

            }
        });
    });

    layui.use('upload', function () {
        var $ = layui.jquery, upload = layui.upload;
        var uploadInst = upload.render({
            elem: '#headImg'
            , choose: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#headImg_img').attr('src', result); //图片链接（base64）
                });
            }
            , auto: false//自动上传
            , accept: 'file' //普通文件     不添加默认为图片
            , field: 'file'//input字段名称 默认file
            , done: function (res) {
                console.log(res);
            }
        })
    });
});

function layeropen() {
    layer.open({
        type: 2,
        title: '选择专长',
        maxmin: true,
        btn: ['确定', '取消'],
        shadeClose: false, // 点击遮罩关闭层
        area: ['40%', '65%'],
        content: '/layeropen',//这里链接到打开的弹出框  layeropen
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