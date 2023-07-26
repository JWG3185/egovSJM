<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!-- 로그인이 된 경우, 로그인한 사용자 이름과 로그아웃 링크를 출력
로그인이 되지 않은 경우, 로그인과 회원가입(추가) 링크 출력 -->
<c:if test="${loginUser != null}">
	<div class="menu">
		사용자 : <c:out value="${loginUser.memName}" />
		아이디 : <c:out value="${loginUser.memId}" />
		<a href="${pageContext.request.contextPath}/member/logout.do"><button type='button'>로그아웃</button></a>
		<a href="${pageContext.request.contextPath}/member/list.do"><button type='button'>회원관리</button></a>
		<a href="${pageContext.request.contextPath}/bbs/list.do"><button type='button'>게시판</button></a>
	</div>
</c:if>
<c:if test="${loginUser == null}">
	<div class="menu">
		<a href="${pageContext.request.contextPath}/member/login.do"><button type='button'>로그인</button></a>
		<a href="${pageContext.request.contextPath}/member/add.do"><button type='button'>회원가입</button></a>
		<a href="${pageContext.request.contextPath}/bbs/list.do"><button type='button'>게시판</button></a>
		<a href="${pageContext.request.contextPath}/member/list.do"><button type='button'>회원목록</button></a>
	</div>
</c:if>
<hr>