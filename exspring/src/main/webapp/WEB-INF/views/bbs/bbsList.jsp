<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<style>
body {text-align: center;}
a {text-decoration: none; color: black; }
a:hover {color: blue; font-weight: bolder;}
#add:hover {color: blue;}
table {margin: 0 auto; margin-top: 20px;}
.tb1, th, td {border: 1px solid black; border-collapse: collapse; padding: 5px;}
.menu{text-align: right; margin-right: 100px; padding: 20px;}
button:hover {font-weight: bolder;}
</style>
</head>
<body>

<%-- <jsp:include page="/WEB-INF/views/menu.jsp" /> --%>

<h2>게시글 목록</h2>
<a href='${pageContext.request.contextPath}/bbs/add.do'><button type='button' id="add">글쓰기</button></a>
<c:url value="/bbs/add.do" var="addUrl"/>
<a href='${addUrl}'><button type='button' id="add">글쓰기</button></a>

<table class="tb1">
	<thead>
		<tr>
			<th>글번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일시</th>
			<th>조회수</th>
		</tr>
	</thead>
	<tbody>
<c:forEach var="vo" items="${bbsList}">
		<tr>
			<td><c:out value="${vo.bbsNo}" /></td>
			<c:url value="/bbs/edit.do" var="editUrl">
				<c:param name="bbsNo">${vo.bbsNo}</c:param>
			</c:url>
			<td><a href='${editUrl}'><c:out value="${vo.bbsTitle}" /></a></td>
			<td><c:out value="${vo.bbsWriter}" /></td>
			<%-- <td><c:out value="" /></td> --%>
			<td><fmt:formatDate value="${vo.bbsRegDate}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
			<td><c:out value="${vo.bbsCount}" /></td>
		</tr>
</c:forEach>
	</tbody>
</table>
	
<script type="text/javascript">
<c:if test="${not empty message}">
	alert("${message}");
</c:if>
</script>

<form id="searchForm" action="${pageContext.request.contextPath}/bbs/list.do">
	<select name="searchType">
	<!--<option value="title" ${searchVo.searchType == 'title' ? "selected" : ""}>제목</option>
		<option value="content" ${searchVo.searchType == 'content' ? "selected" : ""}>내용</option>
		<option value="total" ${searchVo.searchType == 'total' ? "selected" : ""}>제목+내용</option> -->
		<option value="title">제목</option>
		<option value="content">내용</option>
		<option value="total">제목+내용</option>
	</select>
	<script type="text/javascript">
		if ('${searchVo.searchType}') {
		//	document.querySelector('[name="searchType"]').value = '${searchVo.searchType}';
			$('[name="searchType"]').val('${searchVo.searchType}');
		}
	
	</script>
	<input type="text" name="searchWord" value="${searchVo.searchWord }"/>
	<input type="hidden" name="currentPageNo" value="1">
	<input type="submit" value="검색">

</form>

${searchVo.pageHtml}
<script>
function goPage(n) {
	document.querySelector('[name="currentPageNo"]').value = n;
	document.querySelector('#searchForm').submit();
	
	//$('[name="currentPageNo"]').val(n);
	//$('#searchForm').submit();
	// location.href = location.pathname +'?currentPageNo=' + n;	
}
</script>
	
</body>
</html>





