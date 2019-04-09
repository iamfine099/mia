
var prefix = "/common/article"
$(function() {
	load();
});

function load() {
	$('#exampleTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : prefix + "/articleCommentsList", // 服务器数据的加载地址
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
							searchForm['limit']=params.limit,
							searchForm['offset']=params.offset,
							searchForm['categoryId']=4,
							searchForm['status']=1,
							searchForm['isPublish']=1
							return searchForm;
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
																{
									field : 'title', 
									title : '标题' 
								},
																{
									field : 'create.name', 
									title : '作者' 
								},
																{
									field : 'publishTime', 
									title : '发布时间' 
								},
																{
									field : 'company', 
									title : '所在单位' 
								},								{
									field : 'phone', 
									title : '手机号' 
								},								{
									field : 'score', 
									title : '评分' 
								},								{
									field : 'ranking', 
									title : '名次' 
								} ]
					});
}
function reLoad() {
	$('#exampleTable').bootstrapTable('refresh');
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
function detail(){
	layer.open({
		type : 2,
		title : '专家评荐详情',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '100%','100%'],
		content : prefix + '/articleCommentsDetail' // iframe的url
	});
	
}