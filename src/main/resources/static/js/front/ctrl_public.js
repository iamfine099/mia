$(function() {
	jsToStyle();
	function jsToStyle() { //	js加载样式
		//		蒙版
		$('.coverall').height($(window).height());
		$('.coverall').width($(window).width());
//		item
		$('.publicItem-contentRight').each(function(){
			var that=$(this);
			that.width(that.parents('.publicItem-content').width()-that.siblings('.publicItem-contentLeft').width())
		})
	}

})

//调起二维码扫描
function scanCode() {
    if (/android/i.test(navigator.userAgent)) {
        try {
        	window.android.scanQRCode()
        } catch (e) {
            console.log(e)
        }
    } else if (/ios|iphone|ipod|pad/i.test(navigator.userAgent)) {
        try {
        	window.webkit.messageHandlers.scanQRCode.postMessage()
        } catch (e) {
            console.log(e)
        }
    }else{
    	console.log('pc端无法扫描')
    }
}
//java调取js方法对页面赋值
function setCodeInfo(info){
	$('#getCode').html(info);
	alert(info);
}