<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<script src="https://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdn.tiny.cloud/1/62u72nxq6gmuahsbeykab0krga3rt9khf9uul9v2irleard1/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
<script>
$(function(){
    var plugins = [
        "advlist", "autolink", "lists", "link", "image", "charmap", "print", "preview", "anchor",
        "searchreplace", "visualblocks", "code", "fullscreen", "insertdatetime", "media", "table",
        "paste", "code", "help", "wordcount", "save"
    ];
    var edit_toolbar = ' blocks fontfamily fontsize |' 
    		   + 'formatselect fontselect fontsizeselect |'
               + ' forecolor backcolor |'
               + ' bold italic underline strikethrough |'
               + ' alignjustify alignleft aligncenter alignright |'
               + ' bullist numlist |'
               + ' table tabledelete |'
               + ' link image ';

    tinymce.init({
    language: "ko_KR", //한글판으로 변경
        selector: '#bbsContent',
        height: 300,
        menubar: false,
        plugins: plugins,
        toolbar: edit_toolbar,
        
        /*** image upload ***/
        image_title: true,
        /* enable automatic uploads of images represented by blob or data URIs*/
        automatic_uploads: true,
        /*
            URL of our upload handler (for more details check: https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_url)
            images_upload_url: 'postAcceptor.php',
            here we add custom filepicker only to Image dialog
        */
        file_picker_types: 'image',
        /* and here's our custom image picker*/
        file_picker_callback: function (cb, value, meta) {
            var input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'image/*');

            /*
            Note: In modern browsers input[type="file"] is functional without
            even adding it to the DOM, but that might not be the case in some older
            or quirky browsers like IE, so you might want to add it to the DOM
            just in case, and visually hide it. And do not forget do remove it
            once you do not need it anymore.
            */
            input.onchange = function () {
                var file = this.files[0];

                var reader = new FileReader();
                reader.onload = function () {
                    /*
                    Note: Now we need to register the blob in TinyMCEs image blob
                    registry. In the next release this part hopefully won't be
                    necessary, as we are looking to handle it internally.
                    */
                    var id = 'blobid' + (new Date()).getTime();
                    var blobCache =  tinymce.activeEditor.editorUpload.blobCache;
                    var base64 = reader.result.split(',')[1];
                    var blobInfo = blobCache.create(id, file, base64);
                    blobCache.add(blobInfo);

                    /* call the callback and populate the Title field with the file name */
                    cb(blobInfo.blobUri(), { title: file.name });
                };
                reader.readAsDataURL(file);
            };
            input.click();
        },
        /*** image upload ***/
        
        content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
    });
});
</script>
<style>
.menu{text-align: right; margin-right: 100px; padding: 20px;}
button:hover {font-weight: bolder;}
</style>
</head>
<body>

<jsp:include page="/WEB-INF/views/menu.jsp" />

<h3>게시글 작성</h3>
<!-- 파일을 포함하여 전송하는 form 엘리먼트는 enctype="multipart/form-data"으로 설정 -->
<form action='${pageContext.request.contextPath}/bbs/add.do' method="post" enctype="multipart/form-data">
	제목 : <input type="text" name="bbsTitle" value=""/><br>
	내용 <textarea id="bbsContent" name="bbsContent" rows="5" cols="30"></textarea><br>
	첨부파일1 : <input type="file" name="bbsFile"><br>
	첨부파일2 : <input type="file" name="bbsFile"><br>
	<input type="submit" value="입력완료">
</form>
	<a href='${pageContext.request.contextPath}/member/list.do'><button type='button'>목록</button></a>
</body>
</html>