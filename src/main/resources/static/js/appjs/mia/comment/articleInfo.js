$().ready(function() {
	$('.summernote').summernote('disable');
	$('.summernote').summernote({
		height : '440px',
		lang : 'zh-CN',
        callbacks: {
            onImageUpload: function(files, editor, $editable) {
                console.log("onImageUpload");
                sendFile(files);
            }
        }
    });
	var content = $("#content").val();

	$('#content_sn').summernote('code', content);
});