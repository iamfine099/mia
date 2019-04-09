$(function(){
				var phoneNum=$("#shoujihao").html();
				var phone=$("#shoujihao")
				var arr1='';
				var arr2="*****";
				var arr3='';
				for(var i=0;i<3;i++){
					arr1+=phoneNum[i];
				}
				for(var j=7;j<phoneNum.length;j++){
					arr3+=phoneNum[j];
				}
				var str=arr1+arr2+arr3;
				
				phone.html(str);
})
