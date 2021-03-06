<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/tempnav.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Recipe List</title>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>게시물 번호</th>
				<th>제목</th>
				<th>썸네일</th>
				<th>작성자No</th>
				<th>작성일</th>
				<th>수정일</th>
				<th>수정</th>
				<th>삭제</th>
			</tr>
		</thead>
		<c:forEach items="${list}" var="recipe">
			<tr>
				<td><c:out value="${recipe.rno }" /></td>
				<td><a class='move' href='<c:out value="${recipe.rno }"/>'>
						<c:out value="${recipe.title }" />
				</a></td>
				<td><img src="<c:out value='${recipe.thumbnail }'/>" width=30px height=30px onerror="imgError(this);" ></td>
				<td><c:out value="${recipe.userNo }" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${recipe.regDate }" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd"
						value="${recipe.updateDate }" /></td>
				<td><a href='/admin/modiRecipe?rno=<c:out value="${recipe.rno }"/>'>수정</a></td>						
				<td><a href='/admin/delRecipe?rno=<c:out value="${recipe.rno }"/>'>삭제</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>