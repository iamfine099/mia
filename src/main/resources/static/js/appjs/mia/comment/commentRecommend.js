
var prefix = "/mia/comment"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/commentRecommendList", // 服务器数据的加载地址
					//	showRefresh : true,
					//	showToggle : true,
					//	showColumns : true,
						iconSize : 'outline',
						toolbar : '#exampleToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 10, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
						queryParams : function(params) {
							var searchForm={};
							$('#searchForm').find('input').each(function(i,e){
								var name=$(e).attr('name');
								var value=$(e).val();
								searchForm[name]=value
							});
							$('#searchForm').find('select').each(function(i,e){
								var name=$(e).attr('name');
								var value=$(e).val();
								searchForm[name]=value
							});
							searchForm['sort']="c.create_date",
							searchForm['order']="desc",
							searchForm['limit']=params.limit,
							searchForm['offset']=params.offset,
							searchForm['status']=1
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
						columns : [
								{
									checkbox : true
								},
																/*{
									field : 'cId', 
									title : '评论标识' 
								},*/
																{
									field : 'expert.expertName', 
									title : '专家名称' 
								},
																{
									field : 'article.title', 
									title : '文章标题' 
								},
																{
									field : 'cContent', 
									title : '评论内容' 
								},
																{
									field : 'createDate', 
									title : '创建时间' 
								},
																{
									field : 'user.username', 
									title : '审核人' 
								},
																{
									field : 'auditDate', 
									title : '审核时间' 
								},
																{
									field : 'auditContent', 
									title : '审核内容' 
								},
																{
									field : 'isRecommend', 
									title : '是否推荐',
									formatter : function(value, row, index) {
										return getDictLabelByValue(yesNoJson,value);
									}  
								},
																{
									title : '操作',
									field : 'cId',
									align : 'center',
									formatter : function(value, row, index) {
										var s = '<a class="btn btn-primary btn-sm" href="#" mce_href="#" title="查看文章" onclick="select(\''
												+ row.articleId
												+ '\')"><i class="fa fa-file-text"></i></a> ';
										var r = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="推荐" onclick="operation(\''
												+ row.cId 
												+ '\',1)"><i class="fa fa-thumbs-up"></i></a> ';
										
										var nr = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="取消推荐" onclick="operation(\''
												+ row.cId 
												+ '\',0)"><i class="fa fa-thumbs-down"></i></a> ';
										if(row.isRecommend == 1){
											return s + nr ;
										}else if(row.isRecommend == 0){
											return s + r ;
										}else{
											return s ;
										}
										
									}
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/commentAuditEdit/' + id // iframe的url
	});
}
function remove(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/remove",
			type : "post",
			data : {
				'cId' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}

function resetPwd(id) {
}
function batchRemove() {
	var rows = $('#exampleTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['cId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchRemove',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {

	});
}
/**
 * 导出excel
 * @returns
 */
function exportExcel(){
	layer.confirm("确认要导出数据吗？", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function(index) {
		   layer.close(index);
		 //创建表单
		 	form = $("<form></form>");
	        form.attr('action',prefix + '/exportExcel/');
	        form.attr('method','post');
	        input1 = $("<input type='hidden' name='searchName'  />");
	        input1.attr('value',$('#searchName').val());
	        form.append(input1);
	        form.appendTo("body");
	        form.css('display','none');
	        form.submit();
	        
	        form.remove();
		
	}, function() {

	});
}	
function importExcel(){
	layer.open({
		type : 2,
		title : '导入',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/importView' // iframe的url
	});
	
}

/**
 * 查看文章
 * @param id
 */
function select(id) {
	layer.open({
		type : 2,
		title : '查看',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/selArticle/' + id // iframe的url
	});
}

function operation(id,val){
	layer.confirm('确定执行该操作吗？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/recommendOperation",
			type : "post",
			data : {
				'id' : id,
				'val' : val
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}