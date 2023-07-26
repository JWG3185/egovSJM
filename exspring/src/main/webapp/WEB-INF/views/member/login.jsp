<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> -->
<title>로그인</title>
<style>
.menu{text-align: right; margin-right: 100px; padding: 20px;}
button:hover {font-weight: bolder;}
</style>
<!-- </head>
<body> -->

<%-- <jsp:include page="/WEB-INF/views/comm/menu.jsp" /> --%>

<h3>로그인</h3>
<form action='${pageContext.request.contextPath}/member/login.do' method="post">
	아이디 : <input type="text" name="memId" value=""/><br>
	비밀번호 : <input type="password" name="memPass" value=""/><br>
	<input type="submit" value="로그인">
	<a href='${pageContext.request.contextPath}/member/list.do'><button type='button'>목록</button></a>
</form>

<script type="text/javascript">
<c:if test="${not empty message}">
	alert("${message}");
</c:if>
</script>
<!-- 
</body>
</html> -->