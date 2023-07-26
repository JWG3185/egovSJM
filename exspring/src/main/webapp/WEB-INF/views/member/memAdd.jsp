<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
<form action='${pageContext.request.contextPath}/member/add.do' method="post">
	아이디 : <input type="text" name="memId" value=""/><br>
	비밀번호 : <input type="password" name="memPass" value=""/><br>
	이름 : <input type="text" name="memName" value=""/><br>
	포인트 : <input type="number" name="memPoint" value="0"/><br>
	<input type="submit" value="입력완료">
	<a href='${pageContext.request.contextPath}/member/list.do'><button type='button'>목록</button></a>
</form>
<!-- </body>
</html> -->