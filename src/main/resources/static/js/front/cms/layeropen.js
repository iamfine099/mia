

$().ready(function () {

    getAllCollegeTree();
});

/**
 * 加载全部分类
 * @returns
 */
function getAllCollegeTree() {

    $('#jstree').jstree({
        "plugins": ["checkbox"],
        'core': {
            'check_callback': true,
//			"data" :[{id:1,text:'一'},{id:2,text:'二'}]
            "data": function (obj, callback) {
                $.ajax({
                    url: "/common/userMessage/getAllCategoryTree",//接口
                    dataType: "json",
                    type: "POST",
                    data: {
                        'parentId': "s_1"
                    },
                    success: function (data) {
                        if (data) {
                            callback.call(this, data);
                        } else {
                            $("#jstree").html("暂无数据！");
                        }
                    }
                });
            }
        }
    });

}

$('#jstree').on("click.jstree", function (e, data) {

    var ref = $('#jstree').jstree(true);//获得整个树
    var nodes = ref.get_selected(false); //如果设置为true返回的数组将包含完整的节点对象，否则  仅返回ID
    var idArr = [];
    var valueArr = [];
    var textsArr = [];
    var valueArr = [];
    $.each(nodes, function (i, n) {

        var thisnode = ref.get_node(n);//通过使用任何输入（子DOM元素，ID字符串，选择器等）获取节点（或实际的jQuery扩展DOM节点）的JSON表示
        console.log(thisnode);
        if(thisnode.original.hasChildren) {

        } else {

            idArr.push(thisnode.id);
            textsArr.push(thisnode.text);
            valueArr.push(thisnode.original.attributes.value);
        }

    })

    $("#speciality_id").val(idArr.join(','));
    $("#f_name").val(textsArr.join(','));
    $("#speciality_value").val(valueArr.join(','));
})
