var prefix = "/mia/member"
$(function () {
    load();
});

function load() {
    $('#exampleTable')
        .bootstrapTable(
            {
                method: 'get', // 服务器数据的请求方式 get or post
                url: prefix + "/list", // 服务器数据的加载地址
                //	showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize: 'outline',
                toolbar: '#exampleToolbar',
                striped: true, // 设置为true会有隔行变色效果
                dataType: "json", // 服务器返回的数据类型
                pagination: true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数
                singleSelect: false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize: 10, // 如果设置了分页，每页数据条数
                pageNumber: 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns: false, // 是否显示内容下拉框（选择显示的列）
                sidePagination: "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams: function (params) {
                    var searchForm = {};
                    $('#searchForm').find('input').each(function (i, e) {
                        var name = $(e).attr('name');
                        var value = $(e).val();
                        searchForm[name] = value
                    });
                    $('#searchForm').find('select').each(function (i, e) {
                        var name = $(e).attr('name');
                        var value = $(e).val();
                        searchForm[name] = value
                    });
                    searchForm['sort'] = params.sort,
                        searchForm['order'] = params.order,
                        searchForm['limit'] = params.limit,
                        searchForm['offset'] = params.offset
                    return searchForm;
                    ///return {
                    //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                    ///limit: params.limit,
                    ///offset:params.offset
                    // name:$('#searchName').val(),
                    // username:$('#searchName').val()
                    ///};
                },
                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                // queryParamsType = 'limit' ,返回参数必须包含
                // limit, offset, search, sort, order 否则, 需要包含:
                // pageSize, pageNumber, searchText, sortName,
                // sortOrder.
                // 返回false将会终止请求
                columns: [
                    {
                        checkbox: true
                    },
                    {
                        field: 'memName',
                        title: '姓名'
                    },
                    {
                        field: 'sex',
                        title: '性别 ',
                        formatter: function (value, row, index) {
                            return getDictLabelByValue(sexTypeJson, value);
                        }
                    },
                    {
                        field: 'phone',
                        title: '手机号'
                    },
                    {
                        field: 'email',
                        title: '邮箱'
                    },
                    {
                        field: 'status',
                        title: '状态',
                        formatter: function (value, row, index) {
                            return getDictLabelByValue(statusTypeJson, value);
                        }
                    },
                    {
                        field: 'company',
                        title: '单位'
                    },
                    {
                        field: 'specialtyName',
                        title: '专长'
                    },
                    {
                        field: 'numberCard',
                        title: '号码牌'
                    },
                    {
                        field: 'createDate',
                        title: '创建时间'
                    },
                    {
                        field: 'lastTime',
                        title: '最后登录时间'
                    },
                    {
                        field: 'auditUser.username',
                        title: '审核人'
                    },
                    {
                        field: 'auditDate',
                        title: '审核时间'
                    },
                    {
                        field: 'auditContent',
                        title: '审核内容'
                    },
                    {
                        title: '操作',
                        field: 'memId',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + row.memId
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" title="删除"  mce_href="#" onclick="removeRecord(\''
                                + row.memId
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            var f = '<a class="btn btn-success btn-sm ' + s_resetPwd_h + '" href="#" title="重置密码"  mce_href="#" onclick="resetPwd(\''
                                + row.memId
                                + '\')"><i class="fa fa-refresh"></i></a> ';

                            var g = '<span class="btn btn-primary btn-sm ' + s_edit_h + '"  title="打印" onclick="print(\''
                                + row.headImg + '\',\'' + row.memName + '\',\'' + row.specialty + '\',\'' + row.company + '\',\'' + row.numberCard
                                + '\',\'' + row.headUrl + '\', true)"><i class="fa fa-file-text"></i></span> ';
                            //return e + d + f + g;
                            return e + d + f;
                        }
                    }]
            });
}

function reLoad() {
    $('#exampleTable').bootstrapTable('refresh');
}

function add() {
    layer.open({
        type: 2,
        title: '增加',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '490px'],
        content: prefix + '/add' // iframe的url
    });
}

function edit(id) {
    layer.open({
        type: 2,
        title: '编辑',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/edit/' + id // iframe的url
    });
}

function audit(id) {
    layer.open({
        type: 2,
        title: '审核',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/audit/' + id // iframe的urlhttp://localhost/mia/member
    });
}

function removeRecord(id) {
    layer.confirm('确定要删除选中的记录？', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url: prefix + "/remove",
            type: "post",
            data: {
                'memId': id
            },
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    })
}

function resetPwd(id) {
    layer.open({
        type: 2,
        title: '重置密码',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/resetpwd/' + id // iframe的url
    });

}

function print(headImg, memName, specialty, company, numberCard, headUrl) {

    console.log(memName)
    if (headImg != 'null' && headImg != '') {
        $('.headImg').attr('src', headImg);
    }
    $('.memName').html(memName);
    $('.specialty').html(specialty);
    $('.company').html(company);
    $('.numberCard').html(numberCard);
    $('.headImg').attr("src", headUrl);
    Print('#user_card');
}

function batchRemove() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要删除的数据");
        return;
    }
    layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
        btn: ['确定', '取消']
        // 按钮
    }, function () {
        var ids = new Array();
        // 遍历所有选择的行数据，取每条数据对应的ID
        $.each(rows, function (i, row) {
            ids[i] = row['memId'];
        });
        $.ajax({
            type: 'POST',
            data: {
                "ids": ids
            },
            url: prefix + '/batchRemove',
            success: function (r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    reLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function () {

    });
}

/**
 * 导出excel
 * @returns
 */
function exportExcel() {
    layer.confirm("确认要导出数据吗？", {
        btn: ['确定', '取消']
        // 按钮
    }, function (index) {
        layer.close(index);
        //创建表单
        form = $("<form></form>");
        form.attr('action', prefix + '/exportExcel/');
        form.attr('method', 'post');
        input1 = $("<input type='hidden' name='searchName'  />");
        input1.attr('value', $('#searchName').val());
        form.append(input1);
        form.appendTo("body");
        form.css('display', 'none');
        form.submit();

        form.remove();

    }, function () {

    });
}

function importExcel() {
    layer.open({
        type: 2,
        title: '导入',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: prefix + '/importView' // iframe的url
    });

}

/**
 * 批量审核
 * @returns
 */
function checkInfo() {
    var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    if (rows.length == 0) {
        layer.msg("请选择要审核的数据");
        return;
    }
    var ids = "";
    // 遍历所有选择的行数据，取每条数据对应的ID
    $.each(rows, function (i, row) {
        ids += row['memId'] + ",";
    });
    var index = layer.open({
        type: 2,
        title: '会员审核',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '65%'],
        content: prefix + '/memberCheckInit?ids=' + ids // iframe的url
    });
    layer.full(index);
}