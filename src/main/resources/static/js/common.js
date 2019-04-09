/**
 * 系统常用js 方法
 */

//数据字典根据value值获取到显示内容
function getDictLabelByValue(objList,value){
	
	 var label = "";
	if(objList != null && objList.length>0 ){
		
		for(var i=0;i<objList.length;i++){
			if(objList[i].value == value){
				
				label = objList[i].name;
				break;
			}
		}
	} 
	return label;
}

//单位性质根据value值获取到显示内容
function getUnitCategoryByValue(objList,value){
	
	 var label = "";
	if(objList != null && objList.length>0 ){
		
		for(var i=0;i<objList.length;i++){
			if(objList[i].fUnitCategoryId == value){
				
				label = objList[i].fUnitCategory;
				break;
			}
		}
	} 
	return label;
}

//行业类别根据value值获取到显示内容
function getIndustryCategoryByValue(objList,value){
	
	 var label = "";
	if(objList != null && objList.length>0 ){
		
		for(var i=0;i<objList.length;i++){
			if(objList[i].fIndustryCategoryId == value){
				
				label = objList[i].fIndustryCategory;
				break;
			}
		}
	} 
	return label;
}
//高校课程根据value值获取到显示内容
function courseByValue(objList,value){
	var label = "";
	if(objList != null && objList.length>0 ){
		
		for(var i=0;i<objList.length;i++){
			if(objList[i].fCourseId == value){
				
				label = objList[i].fName;
				break;
			}
		}
	} 
	return label;
}
//高校学院根据value值获取到显示内容
function collegeByValue(objList,value){
	var label = "";
	if(objList != null && objList.length>0 ){
		
		for(var i=0;i<objList.length;i++){
			if(objList[i].fCollegeId == value){
				
				label = objList[i].fName;
				break;
			}
		}
	} 
	return label;
}

//学院专业根据value值获取到显示内容
function specialityByValue(objList,value){
	var label = "";
	if(objList != null && objList.length>0 ){
		
		for(var i=0;i<objList.length;i++){
			if(objList[i].fSpecialityId == value){
				
				label = objList[i].fName;
				break;
			}
		}
	} 
	return label;
}

//数据字典根据value值获取到显示内容
/**
 * 列表显示 格式化时间
 * @param time string 时间串
 * @param shownum
 * @returns
 */
function getTime(time,shownum){
	
	if(time.length<1)return "";
	
	return time.substring(0,shownum);
}
/**
 * 下载excel
 * @param templateName
 * @returns
 */
function downExcel(templateName){
	
	document.location.href = "/common/sysFile/downET/"+templateName;
	
}
/**
 * 下载excel
 * @param templateName
 * @returns
 */
function downFile(fileId){
	
	document.location.href = "/common/sysFile/down/"+fileId;
	
}
//查询条件--年级
function getYear(){
	$.ajax({
		type : 'POST',
		url : '/counsellor/counsellor/getYearForCounsellorList',
		success : function(r) {
			for(var i=0; i<r.length; i++){
				$("#fYear").append("<option value='"+r[i]+"'>"+r[i]+"</option>");
			}
		}
	});
}

//查询条件--专业
function getSpeciality(){
	$.ajax({
		type : 'POST',
		url : '/common/speciality/getSpecialityForCounsellorList',
		success : function(r) {
			for(var i=0; i<r.length; i++){
				$("#fSpecialityId").append("<option value='"+r[i].fSpecialityId+"'>"+r[i].fName+"</option>");
			}
		}
	});
}

//查询条件--专业
function getSpeciality(){
	$.ajax({
		type : 'POST',
		url : '/common/speciality/getSpecialityForCounsellorList',
		success : function(r) {
			for(var i=0; i<r.length; i++){
				$("#fSpecialityId").append("<option value='"+r[i].fSpecialityId+"'>"+r[i].fName+"</option>");
			}
		}
	});
}

//查询条件--学年
function getstuYear(){
	$.ajax({
		type : 'POST',
		url : '/student/student/getstuYear',
		success : function(r) {
			for(var i=0; i<r.length; i++){
				$("#fSpecialityId").append("<option value='"+r[i].fSpecialityId+"'>"+r[i].fName+"</option>");
			}
		}
	});
}

/*******************地区选择开始**************************/
//type为0，显示请选择
function getProvince(type){
	$.ajax({
		cache : true,
		type : "POST",
		url : "/common/areaCode/getAreaCodeList",
		data: {
			fAreaCodes : 0
		},
		async : false,
		error : function(request) {
			layer.alert("Connection error");
		},
		success : function(data) {
			$("#province").empty();
			if(data.length == 0){
				$("#province").append("<option value='"+0+"'>请选择</option>");
			}else{
				
				if(type == 0){
					$("#province").append("<option value='"+0+"'>请选择</option>");
				}
				
				for(var i = 0; i<data.length; i++){
					$("#province").append("<option value='"+data[i].fAreaCode+"'>"+data[i].fName+"</option>"); 
				}
				
				if($("#province").val() != 0){
					getCity();
				}
			}
		}
	});
}

function getCity(){
	$.ajax({
		cache : true,
		type : "POST",
		url : "/common/areaCode/getAreaCodeList",
		data: {
			fAreaCodes : $("#province").val()
		},
		async : false,
		error : function(request) {
			layer.alert("Connection error");
		},
		success : function(data) {
			$("#city").empty();
			$("#city").append("<option value='"+0+"'>请选择</option>");
			for(var i = 0; i<data.length; i++){
				$("#city").append("<option value='"+data[i].fAreaCode+"'>"+data[i].fName+"</option>"); 
			}
			$("#county").empty();
			$("#county").append("<option value='"+0+"'>请选择</option>");
		}
	});
}

function getCounty(){
	$.ajax({
		cache : true,
		type : "POST",
		url : "/common/areaCode/getAreaCodeList",
		data: {
			fAreaCodes : $("#city").val()
		},
		async : false,
		error : function(request) {
			layer.alert("Connection error");
		},
		success : function(data) {
			$("#county").empty();
			$("#county").append("<option value='"+0+"'>请选择</option>");
			for(var i = 0; i<data.length; i++){
				$("#county").append("<option value='"+data[i].fAreaCode+"'>"+data[i].fName+"</option>"); 
			}
		}
	});
}
/*******************地区选择结束**************************/

//重置
function resetBtn(obj){
	var parent = $(obj).parents('.wrapper-content');
	$(parent).find("input").val("");
	$(parent).find('select').prop('selectedIndex', 0);
	$('#exampleTable').bootstrapTable('refresh');
}
/*******************后台导出选择开始**************************/
/**
 * 导出初始化
 * @param form
 * @returns
 */
function excelParamInit(form){
	
	$('#searchForm').find('input').each(function(i,e){
		var name=$("<input type='hidden' name='"+$(e).attr('name')+"'  />") ;
		name.attr('value',$(e).val());
	    form.append(name);
	});
	$('#searchForm').find('select').each(function(i,e){
		var name=$("<input type='hidden' name='"+$(e).attr('name')+"'  />") ;
		name.attr('value',$(e).val());
	    form.append(name);
	});
	return form;
}
/**
 * 导出EXCEL
 * @param exportUrl
 * @returns
 */
function exportExcel(exportUrl){
	
	layer.confirm("确认要导出数据吗？", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function(index) {
		   layer.close(index);
		 //创建表单
		 	form = $("<form></form>");
	        form.attr('action',exportUrl);
	        form.attr('method','post');
	        form =   excelParamInit(form); 
	        form.appendTo("body");
	        form.css('display','none');
	        form.submit();
	        form.remove();
		
	}, function() {

	});
}
/*******************后台导出选择结束**************************/
