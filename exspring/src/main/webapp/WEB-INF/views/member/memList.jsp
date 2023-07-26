<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> -->
<title>회원목록</title>
<style>
body {text-align: center;}
a {text-decoration: none; color: black; }
a:hover {color: blue;}
#del:hover {color: red;}
#add:hover {color: blue;}
button:visited {color: grey;}
table {margin: 0 auto; margin-top: 20px;}
.tb1, th, td {border: 1px solid black; border-collapse: collapse; padding: 5px;}
.menu{text-align: right; margin-right: 100px; padding: 20px;}
button:hover {font-weight: bolder;}
</style>
<!-- </head>
<body> -->

<h2>회원목록</h2>
<a href='<%=request.getContextPath()%>/member/add.do'><button type='button'  id="add">회원추가</button></a>
<a href='${pageContext.request.contextPath}/member/add.do'><button type='button' id="add">회원추가</button></a>
<c:url value="/member/add.do" var="addUrl"/>
<a href='${addUrl}'><button type='button' id="add">회원추가</button></a>

<table class="tb1">
	<tbody>
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>포인트</th>
			<th>삭제여부</th>
		</tr>
<c:forEach var="vo" items="${memberList}">
		<tr>
		<%-- ${vo.memId} : ${vo.memPass} : ${vo.memName} : ${vo.memPoint} --%>
		<c:url value="/member/edit.do" var="editUrl">
			<c:param name="memId">${vo.memId}</c:param>
		</c:url>
			<td><a href='${editUrl}'><c:out value="${vo.memId}" /></a></td>
			<td><c:out value="${vo.memName}" /></td>
			<td>${vo.memPoint}</td>
			<%-- <a href='${pageContext.request.contextPath}/member/del.do?memId=${vo.memId}'><button type='button'>삭제</button></a> --%>
			<!-- JSTL 태그의 scope와 var 속성을 사용하면, -->
			<!-- JSTL 태그 실행 결과를 현재 위치에 출력하지 않고, -->
			<!-- 지정한 scope에 지정한 이름(var)의 속성을 저장한 후, -->
			<!-- EL에서 읽어서 사용 가능 -->
			<c:url value="/member/del.do" var="delUrl">
				<c:param name="memId">${vo.memId}</c:param>
			</c:url>
			<td><a href='${delUrl}'><button type='button' id="del">삭제</button></a></td>
		</tr>
</c:forEach>
	</tbody>
</table>
	
<!-- 	
</body>
</html> -->





