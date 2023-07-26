<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"> -->
<title>회원정보변경</title>
<style>
.menu{text-align: right; margin-right: 100px; padding: 20px;}
button:hover {font-weight: bolder;}
</style>
<!-- </head>
<body> -->


<h3>회원정보변경</h3>
<form action='${pageContext.request.contextPath}/member/edit.do' method="post">
아이디 : <input type="text" name="memId" readonly="readonly" value='<c:out value="${mvo.memId}" />'/><br>
<%-- 비밀번호 : <input type="password" name="memPass" value="${mvo.memPass}"/><br> --%>
이름 : <input type="text" name="memName" value='<c:out value="${mvo.memName}" />'/><br>
포인트 : <input type="number" name="memPoint" value="${mvo.memPoint}"/><br>
<input type="submit" value="저장">
</form>

<form action="${pageContext.request.contextPath}/member/del.do">
<input type="hidden" name="memId" value='<c:out value="${mvo.memId}" />'/>
<input type="submit" value="삭제">
</form>
<br/>
a 태그 삭제 버튼 : 
<a href='${pageContext.request.contextPath}/member/del.do?memId=<c:out value="${mvo.memId}" />'>
<button type='button'>삭제</button>
</a>
<br/>
<a href='${pageContext.request.contextPath}/member/list.do'><button type='button'>목록</button></a>

<!-- </body>
</html> -->

<!-- 1.회원정보변경 화면에서 이름과 포인트를 변경하고 submit 버튼을 클릭하면,
	MemEditServlet 클래스의 doPost 메서드가 실핼되도록 memEdit.jsp 파일을 변경하세요.
2. 회원정보 변경 화면에서 아이디는 키보드로 값을 입력(변경)할 수 없도록
	memEdit.jsp 파일을 변경하세요.
3. MemEditServlet 클래스의 doPost 메서드에서 사용자가 입력한 정보에 따라서
	데이터베이스의 회원 정보 (이름, 포인트)가 변경되도록
	MemEditServlet.java, MemberDao.java, MemberDaoBatis.java, MemberMapper.xml 파일을 변경하세요. -->
