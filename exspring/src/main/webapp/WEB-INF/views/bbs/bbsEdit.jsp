<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원관리</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
<!-- <script src="https://code.jquery.com/jquery-latest.min.js"></script> -->
<script src="https://code.jquery.com/jquery-3.7.0.js"></script>
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

<%-- <jsp:include page="/WEB-INF/views/menu.jsp" /> --%>

<h3>게시글 수정</h3>

<form action='${pageContext.request.contextPath}/bbs/edit.do' method="post">
	<input type="hidden" name="bbsNo" readonly="readonly" value='<c:out value="${bbsVo.bbsNo}" />'/>
	<c:set value="${sessionScope.loginUser.memId != bbsVo.bbsWriter}" var="isMine" />
	제목 : 	<input type="text" <c:if test="${isMine}">readonly</c:if>
				 name="bbsTitle"  value='<c:out value="${bbsVo.bbsTitle}" />'/> <br>
	
	작성자 : 	<c:out value="${bbsVo.bbsWriter}" /> <br>
	
	등록일 : 	<fmt:formatDate value="${bbsVo.bbsRegDate}" pattern="yyyy/MM/dd HH:mm:ss"/> <br>
	
	<!-- 삼항연산자 사용 -->
	내용 	<textarea ${isMine ? 'readonly' : ''}  id="bbsContent" name="bbsContent" rows="5" cols="30"><c:out value="${bbsVo.bbsContent}" /></textarea> <br>
	
	<h4>첨부파일 목록</h4>
	<c:forEach var="avo" items="${bbsVo.attachList}">
		첨부파일 : <a href="${pageContext.request.contextPath}/bbs/down.do?attNo=${avo.attNo}" ><c:out value="${avo.attOrgName}" /></a> <br>
		<%-- <c:import url="${pageContext.request.contextPath}/bbs/down.do" charEncoding="utf-8">
			<c:param name="attNo" value="${avo.attNo}" ></c:param>
		</c:import> --%>
	</c:forEach>
	<c:if test="${sessionScope.loginUser.memId == bbsVo.bbsWriter}">
		<input type="submit" value="저장">
		<a href="${pageContext.request.contextPath}/bbs/del.do?bbsNo=${bbsVo.bbsNo}" id="delLink"><button type='button'>삭제</button></a>
	</c:if>
</form>
	<a href='${pageContext.request.contextPath}/bbs/list.do'><button type='button'>목록</button></a>

<hr>
<form id="replyForm" action="${pageContext.request.contextPath}/reply/add.do" method="post">
	<input type="hidden" name="repBbsNo"  value='<c:out value="${bbsVo.bbsNo}" />'/>
	<textarea id="repContent" name="repContent" rows="1" cols="50" ></textarea>
	<input id="repSaveBtn" type="button" value="등록">
</form>

<hr>

<div id="replyList">
</div>

<template id="replyTemp">
	<div class="repWriter"></div>
	<div class="repContent"></div>
	<div class="repRegDate"></div>
	<input data-no="" type="button" class="delBtn" value="삭제">
	<hr>
</template>


<!-- private int repNo;               
private String repContent;                    
private String repWriter;             
private Date repRegDate;  
private int repBbsNo; -->

<script type="text/javascript">
	<c:if test="${not empty message}">
		alert("${message}");
	</c:if>

	$('#delLink').on('click', function() {
		var ok = confirm("삭제하시겠습니까?");
		if (!ok) {
			//ev.preventDefault();	// 이벤트에 대한 브라우저의 기본동작을 취소
			return false; 	// 이벤트 전파를 중단하고, 이벤트에 대한 브라우저의 기본동작을 취소
		}
	})
	
	// <template> 엘리먼트의 내용은 content 속성을 사용하여 접근
	// document.querySelector('#replyTemp')
	var $repTemp = $(document.querySelector('#replyTemp').content);	
	// $뒤에 변수명 선언하는 것은 안에 제이쿼리 객체를 담고 있다는 뜻임, 꼭 안써도 됨.
	
	
	// 1. 로그인한 사용자가 작성한 댓글에만 삭제버튼 출력
	// 2. 삭제버튼 클릭시, 삭제여부 묻는 창 띄우기
	// 3. 댓글 저장 성공시, 댓글 입력란의 내용 초기화
	
	function refreshReplyList(){
		$.ajax({
		  url: "${pageContext.request.contextPath}/reply/list.do",							
		  method: "GET",																	
		  data: { repBbsNo : ${bbsVo.bbsNo} },	
		  dataType: "json",																	
		  success: function( data ) {
		  	/*	직접 넣는 방식
		  	var listHtml = '';
		  	$.each(data, function(index, item) {
		  		listHtml += '<div>' + item.repWriter + '</div>';
		  		listHtml += '<div>' + item.repContent + '</div>';
		  		listHtml += '<div>' + item.repRegDate + '</div>';
		  		if(item.repWriter == '${sessionScope.loginUser.memId}'){
		  			listHtml += '<input data-no="' + item.repNo + '"class="delBtn" button type="button" value="삭제">'
		  		}
		  		listHtml += '<hr>';
		  	});
		  	*/
		  		
		  	/* var listHtml = [];
		  	$.each(data, function(index, item) {
		  		var repVo = data[index];
		  		listHtml.push( $('<div>').text(repVo.repWriter) );
		  		listHtml.push( $('<div>').text(repVo.repContent) );
		  		listHtml.push( $('<div>').text(repVo.repRegDate) );
		  		if(repVo.repWriter == '${sessionScope.loginUser.memId}'){
		  			listHtml.push( $('<input>')	//.attr('data-no', repVo.repNo)
		  										//.attr('type','button')
		  										.attr({'data-no': repVo.repNo, 'type': 'button'})
		  										.addClass('delBtn')			//.attr('class', 'delBtn')
		  										.val('삭제')					//('value', '삭제') );
		  			);
		  		}
		  		listHtml.push( $('<hr>'));
		  	}); */
		  	
		  	
		 
		  	// 템플릿 사용
		   	var listHtml = [];
		  	$.each(data, function(index, item) {
		  		var repVo = data[index];
			  	var $newRep = $repTemp.clone();
			  	console.log($newRep.find('.delBtn').attr('type'));
			  	$newRep.find('.repWriter').text(repVo.repWriter);
			  	$newRep.find('.repContent').text(repVo.repContent);
			  	$newRep.find('.repRegDate').text(repVo.repRegDate);
			  	if(repVo.repWriter == '${sessionScope.loginUser.memId}'){
				  	$newRep.find('.delBtn').attr('data-no', repVo.repNo);
			  	}
			  	else{
			  		$newRep.find('.delBtn').remove();
			  	}
			
				listHtml.push( $newRep);
		  	}); 
		  
		  	
		  	$("#replyList").empty().append(listHtml);
		  	
		  },
	 	  error: function() {	
		  	alert("error");
		  }
		});
	};
	
	refreshReplyList();

	// 등록버튼을 클릭했을 때, AJAX로 댓글 저장 요청을 전송
	// AJAX
	// (1)XmlHttpRequest 객체 사용
	// (2)fetch() 함수 사용
	// (3)$.ajax() 메서드 사용
	
	$("#repSaveBtn").click(function() {
		
		$.ajax({
		  	url: "${pageContext.request.contextPath}/reply/add.do",							// 요청주소
		  	method: "POST",																	// 요청 방식
		  	data: { repBbsNo : ${bbsVo.bbsNo}
		  			, repContent : $('[name="repContent"]').val() },						// 요청 파라미터
		 // data: $('#replyForm').serialize(),		// 한번에 파라미터들 읽어서 전송
		  	dataType: "json"																	// 응답데이터타입
			// "json"으로 설정하면, 응답으로 받은 JSON 문자열을 자바스크립트 객체로 변환하여
		  	// 응답처리함수( done() )에게 인자로 전달				
		}).done(function( msg ) {				// 요청 전송 후 성공적으로 응답을 받았을 때 실행
			alert(msg.result + "개의 댓글 저장");
			refreshReplyList();
			 $('[name="repContent"]').val('');
		}).fail(function( jqXHR, textStatus ) {	// 요청 처리에 오류가 발생했을 때 실행
		  	alert("error");
		});
		
	});

	
	// 삭제버튼을 클릭하면 해당 댓글이 삭제되도록,
	// Reply컨트롤러, Service, Serviceimpl, Dao, Mapper 파일 변경
	$("#replyList").on("click" , ".delBtn", function() {
		
		/* alert('삭제!' +  this.getAttribute("data-no")); */
		/* alert('삭제!' +  $(this).attr("data-no")); */ 
		var ok = confirm('댓글번호 : ' +  $(this).data("no") + '번을 삭제하시겠습니까?');
		
		if(!ok) return;
			
		$.ajax({
			url: "${pageContext.request.contextPath}/reply/del.do",							
		  	method: "GET",																	
		  	data: { repNo :  $(this).data("no") },	
		  	dataType: "json",
		  	success: function( msg ) {	
				alert(msg.num + "번 댓글 삭제");
				refreshReplyList();
			},
			error : function( jqXHR, textStatus ) {	
		 		alert( "error" );
			},
		});		
		
	});

	
</script>

</body>
</html>
