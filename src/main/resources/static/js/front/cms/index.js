/*tab栏切换*/
$(function(){
	$("#tab .title-l").click(function(){
		 
		$(this).css({
			"background":"#037EDB",
			"color":"#ffffff"
	    });
		$("#tab .title-l span").addClass("trangle");
	    $("#tab .title-c").css({
	    	"background":"#efefef",
	    	"color":"#000000"
	    });
	    $("#tab .title-c span").removeClass("trangle");
	    $('.newsNew').stop().hide();
	    $('.newsNew').eq(0).stop().show();
	    $('#news_a').prop("href","/front/cms/open/page/15");
	})
	$("#tab .title-c").click(function(){
		$(this).css({
			"background":"#037EDB",
			"color":"#ffffff"
		});
	    $("#tab .title-c span").addClass("trangle");
	    $("#tab .title-l").css({
	    	"background":"#efefef",
	    	"color":"#000000"
	    });
	    $("#tab .title-l span").removeClass("trangle");
	    $('.newsNew').stop().hide();
	    $('.newsNew').eq(1).stop().show();
	    $('#news_a').prop("href","/front/cms/open/page/16");
	})
	
	//轮播
	var swiper = new Swiper('#lunbo .swiper-container', {
        spaceBetween:30,
        centeredSlides:true,
        autoplay:{
            delay:3000,
            disableOnInteraction:false,
        },
        pagination:{
            el:'.swiper-pagination',
            clickable:true,
        },
        on: {
			transitionEnd: function(swiper) {
				var index = this.activeIndex;
				$("#showTitle").html($(".bannerTitle").eq(index).val())
			},
		},
    });
	
	//专家展示轮播
    var swiper = new Swiper('.photoList .swiper-container', {
        slidesPerView:5,
        spaceBetween:20,
        slidesPerGroup:5,
        loop:true,
        loopFillGroupWithBlank:true,
        autoplay:{
            delay:3000,
            disableOnInteraction:false,
        },
        navigation: {
            nextEl:'.swiper-button-next',
            prevEl:'.swiper-button-prev',
        },
    });
    //使input获取到storage里面的东西
	var auto=localStorage.getItem("auto");
	if(auto){
		try{
			var username = localStorage.getItem("username");
			$("#phone").val(username);
			var password = localStorage.getItem("password");
			$("#pwd").val(password);
			$("#auto")[0].checked=true;
		}catch(e){}
	}
	//登陆
    $("#login_btn").click(function(){
    	var phoneRex = new RegExp("1[34578]{1}[0-9]{9}");
    	var pwdRex = new RegExp("[a-zA-Z0-9]{6,16}");
    	var username = $("#phone").val();
    	var password = $("#pwd").val();
    	var yz=$("#yz").val();
    	var auto=$("#auto")[0].checked;//获得checked是否被选中
//    	console.log(auto);打印出来checkbox的状态  false或者true
    	if(username==""){
    		layer.alert("请输入手机号");
    		return false;
    	}
    	if(password==""){
    		layer.alert("请输入密码");
    		return false;
    	}
    	if(yz==""){
    		layer.alert("请输入验证码");
    		return false;
    	}
    	if(phoneRex.test(username)==false){
    		layer.alert("请输入正确的手机号");
    		return false;
    	}
    	if(pwdRex.test(password)==false){
    		layer.alert("请输入正确格式的密码");
    		return false;
    	}
		$.ajax({
			cache : true,
			type : "POST",
			url : "/front/cms/login",
			data: {
				'username':username,
				'password':password,
				'yz':yz,
			},
			async : false,
			error : function(request) {
				parent.layer.alert("Connection error");
			},
			success : function(data) {
				console.log(data)
				if (data.code == 0) {
					if(auto){
						localStorage.setItem("username",username);
						localStorage.setItem("password",password);
						localStorage.setItem("auto",auto);
					}else{
						localStorage.removeItem("username");
						localStorage.removeItem("password");
						localStorage.removeItem("auto");
					}
					//layer.msg(data.msg);
					//注册成功跳转页面
					parent.location.href = '/index';
				} else {
					$('.yz').val('');
					getcode();
					layer.alert(data.msg);
				}
			}
		});
	});
    //注册
    $("#register_btn").click(function(){
    	parent.location.href = '/front/cms/register';
	})
})

function getcode(){
	$("#codeImg").attr("src","/code/image?"+new Date().getTime());
}
