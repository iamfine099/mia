/*制作时钟*/
var today = new Date();
var year = today.getFullYear();
var month = today.getMonth() + 1;
var date = today.getDate();
var day = today.getDay();
switch (day) {
    case 1:
        day = "星期一";
        break;
    case 2:
        day = "星期二";
        break;
    case 3:
        day = "星期三";
        break;
    case 4:
        day = "星期四";
        break;
    case 5:
        day = "星期五";
        break;
    case 6:
        day = "星期六";
        break;
    case 0:
        day = "星期日";
        break;
}
$(function () {

    $("#clock").html(year + "年" + month + "月" + date + "日" + "&nbsp;" + day);
    $('.secondNav').each(function () {

        var index = $(this).parent().index('.firstNavItem');
        var widthItem = $(this).parents('.firstNavItem').width();
        $(this).css('left', '-' + index * widthItem + 'px');
    })
})
