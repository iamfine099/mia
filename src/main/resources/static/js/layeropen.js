function layeropen(){
	layer.open({
		type : 2,
		title : '选择学生',
		maxmin : true,
		btn: ['确定', '取消'],
		shadeClose : false, // 点击遮罩关闭层
		area : [ '90%', '90%' ],
		content : '/student/student/layeropen' ,
		yes: function(index, layero){
			var body = layer.getChildFrame('body', index);
			var searchName=body.find('.searchName');
			var showSelect=[];
			var hiddenSelect=[];
			searchName.each(function(i,e){
				var text=$(this).find('.forName').html();
				var level=$(this).find('.oldSelect').attr('aria-level');
				var id=$(this).find('.oldSelect').attr('id');
				var combineId=id;
				showSelect.push(text);
				hiddenSelect.push(combineId);
			})
			var showSelectString=showSelect.join(',');
			var hiddenSelectString=hiddenSelect.join(',');
			$('#showSelect').val(showSelectString);
			$('#fto').val(hiddenSelectString);
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		},
		btn2: function(index, layero){
			layer.close(index); 
		}
	});

	}
/**
 * 学生选择-单个学生选择
 * @returns
 */
function selectstudent(){
	layer.open({
		type : 2,
		title : '选择学生',
		maxmin : true,
		btn: ['确定', '取消'],
		shadeClose : false, // 点击遮罩关闭层
		area : [ '700px', '460px' ],
		content : '/student/student/selectstudent' ,
		yes: function(index, layero){
			//子级弹出框
			var childiframe = window[layero.find('iframe')[0]['name']]; 
			//子级弹出框中的方法
			var objs = childiframe.getValues();
			$('#fStuId').val(objs[0].fStuId);
			$('#fStuName').val(objs[0].fName);
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		},
		btn2: function(index, layero){
			layer.close(index); 
		}
	});
}

/**
 * 问卷调查题目录入选择调查问卷
 * @returns
 */
function selectQuestionnaire(){
	layer.open({
		type : 2,
		title : '选择调查问卷',
		maxmin : true,
		btn: ['确定', '取消'],
		shadeClose : false, // 点击遮罩关闭层
		area : [ '700px', '460px' ],
		content : '/student/questionnaire/selectQuestionnaire' ,
		yes: function(index, layero){
			
			
/*//			调用此方法的js文件中的方法
			consoletest();
//			此页面的元素
			var mineDocu=$(document);
			
			
//			此页面的父级列表页
			parent.consoletest2();
//			此页面的父级元素
			var parentDocu=$(parent.document);
			
			*/
//			子级弹出框
			var childiframe = window[layero.find('iframe')[0]['name']]; 
//			子级弹出框中的方法
			var objs = childiframe.getValues();
			console.log(objs[0]);
			console.log(objs[0].fQid);
			console.log(objs[0].fName);
//			子级弹出框中的元素
			/*var childDocu = $(childiframe.document);*/
			/*$('#fStuId').val(childDocu.find('#searchIds').val())*/
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		},
		btn2: function(index, layero){
			layer.close(index); 
		}
	});
}

//活动选择场地
function chooseVenue(){
	if($('#fTimeStart').val() == '' || $('#fTimeEnd').val() == ''){
		layer.alert("请先选择活动时间！");
	}else{
		layer.open({
			type : 2,
			title : '选择场地',
			maxmin : true,
			btn: ['确定', '取消'],
			shadeClose : false, // 点击遮罩关闭层
			area : [ '700px', '460px' ],
			content : '/activity/venue/chooseVenue?fTimeStart='+$('#fTimeStart').val()+'&fTimeEnd='+$('#fTimeEnd').val()+'&fSpecialJobfairId='+$('#fSpecialJobfairId').val()+'&fActivityId='+$('#fActivityId').val() ,
			yes: function(index, layero){
				//子级弹出框
				var childiframe = window[layero.find('iframe')[0]['name']]; 
				//子级弹出框中的方法
				var objs = childiframe.getValues();
				$('#fVenueId').val(objs[0].fVenueId)
				$('#fVenueName').val(objs[0].fName)
				layer.close(index); //如果设定了yes回调，需进行手工关闭
			},
			btn2: function(index, layero){
				layer.close(index); 
			}
		});
	}
}
/**
 *选择单位
 * @returns
 */
function selectEmployer(){
	layer.open({
		type : 2,
		title : '选择单位',
		maxmin : true,
		btn: ['确定', '取消'],
		shadeClose : false, // 点击遮罩关闭层
		area : [ '700px', '460px' ],
		content : '/employer/employer/selectEmployer' ,
		yes: function(index, layero){
			var childiframe = window[layero.find('iframe')[0]['name']]; 
//			子级弹出框中的方法
			var objs = childiframe.getValues();
			/*console.log(objs[0].fQid);
			console.log(objs[0].fName);*/
			$("#fEmpId").val(objs[0].fEmpId);
			$("#fEmpName").val(objs[0].fEmpName);
			
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		},
		btn2: function(index, layero){
			layer.close(index); 
		}
	});
}
function selectEmployerTree(showType){
	layer.open({
		type : 2,
		title : '选择单位',
		maxmin : true,
		btn: ['确定', '取消'],
		shadeClose : false, // 点击遮罩关闭层
		area : [ '90%', '90%' ],
		content : '/employer/employer/selectEmployerTree?showType='+showType ,
		yes: function(index, layero){
			var body = layer.getChildFrame('body', index);
			var searchName=body.find('.searchName');
			var showSelect=[];
			var hiddenSelect=[];
			searchName.each(function(i,e){
				var text=$(this).find('.forName').html();
				var level=$(this).find('.oldSelect').attr('aria-level');
				var id=$(this).find('.oldSelect').attr('id');
				var combineId=id;
				showSelect.push(text);
				hiddenSelect.push(combineId);
			})
			var showSelectString=showSelect.join(',');
			var hiddenSelectString=hiddenSelect.join(',');
			$('#empName').val(showSelectString);
			$('#empIds').val(hiddenSelectString);
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		},
		btn2: function(index, layero){
			layer.close(index); 
		}
	});
}
/**
 *选择定向发送对象
 * @returns
 */
function selectQuestionClass(){
	layer.open({
		type : 2,
		title : '选择问卷类型',
		maxmin : true,
		btn: ['确定', '取消'],
		shadeClose : false, // 点击遮罩关闭层
		area : [ '90%', '90%' ],
		content : '/student/questionnaire/layeropen?fPartType='+$('#fPartType').val(),
		yes: function(index, layero){
			var body = layer.getChildFrame('body', index);
			var searchName=body.find('.searchName');
			var showSelect=[];
			var hiddenSelect=[];
			searchName.each(function(i,e){
				var text=$(this).find('.forName').html();
				var level=$(this).find('.oldSelect').attr('aria-level');
				var id=$(this).find('.oldSelect').attr('id');
				var combineId=id;
				showSelect.push(text);
				hiddenSelect.push(combineId);
			})
			var showSelectString=showSelect.join(',');
			var hiddenSelectString=hiddenSelect.join(',');
			$('#participantText').val(showSelectString);
			$('#participant').val(hiddenSelectString);
			layer.close(index); //如果设定了yes回调，需进行手工关闭
		},
		btn2: function(index, layero){
			layer.close(index); 
		}
	});
}

//专业弹出框
function selectfMajor(fMajor,fMajorText){
	layer.open({
		type : 2,
		title : '选择专业',
		maxmin : true,
		btn: ['确定', '取消'],
		shadeClose : false, // 点击遮罩关闭层
		area : [ '350px', '460px' ],
		content : '/common/speciality/layeropenht',
		yes: function(index, layero){
			var body = layer.getChildFrame('body', index);
			var id = body.find('#speciality_id').val();
			var text = body.find('#f_name').val();
			if(id != ''){
				$("#"+fMajor).val(id);
				$("#"+fMajorText).val(text);
				layer.close(index); //如果设定了yes回调，需进行手工关闭
			}else{
				layer.alert("请选择专业！");
			}
		},
		btn2: function(index, layero){
			layer.close(index); 
		}
	});
}



















