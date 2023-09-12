<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> -->
<title>회원추가</title>
<style>
.menu{text-align: right; margin-right: 100px; padding: 20px;}
button:hover {font-weight: bolder;}
</style>
<!-- </head>
<body> -->

<h3>회원추가</h3>
<%-- <form action='${pageContext.request.contextPath}/member/add.do' method="post">
	아이디 : <input type="text" name="memId" value="${mvo.memId}"/><br>
	비밀번호 : <input type="password" name="memPass" value="${mvo.memPass}"/><br>
	이름 : <input type="text" name="memName" value="${mvo.memName}"/><br>
	포인트 : <input type="number" name="memPoint" value="${mvo.memPoint}"/><br>
	<input type="submit" value="입력완료">
</form> --%>

<!-- form 내부에서 사용할 모델의 이름을 modelAttribute 속성값으로 지정 -->
<form:form modelAttribute="mvo" action='add.do' method="post">
	아이디 : <form:input  path="memId"/> <form:errors path="memId" /> <br>
	비밀번호 : <form:password path="memPass"/> <form:errors path="memPass" /><br>
	이름 : <form:input path="memName"/> <form:errors path="memName" /><br>
	포인트 : <form:input path="memPoint"/> <form:errors path="memPoint" /><br>
	<input type="submit" value="입력완료">
</form:form>

<a href='list.do'><button type='button'>목록</button></a>
<!-- </body>
</html> -->