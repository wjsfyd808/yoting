<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix='fn'%>
<%
   request.setCharacterEncoding("UTF-8");
%>
<%@ include file="../includes/tempnav.jsp" %>
<%
Long loggedInUserNo = (Long)session.getAttribute("loggedInUserNo");        
%>

<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
<title>profile</title>
 <!-- Bootstrap Core CSS -->
    <link href="/resources/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="/resources/vendor/metisMenu/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="/resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="/resources/vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

</head>
<body>
   <script
      src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

   <div style="text-align: center;">
      <div>
      </div>
         <div>
            <a href="1"><img src="<c:out value="${user.profilePhoto}"/>"
               width="300" height="300" onerror="imgError(this);" /></a>
         </div>
         <div>
            <c:if test="${keyword == null && empty recipeList}">
                <b><c:out value="${user.nickName}" />님</b>은 아직 작성한 레시피가 없어요.
            </c:if>
            <c:if test="${keyword == null && !empty recipeList}">
               <b><c:out value="${user.nickName}" />님</b>은  <c:out value="${fn:length(recipeList)}" />개의 레시피가 있습니다.
            </c:if>
            <c:if test="${keyword != null && empty recipeList}">
               <b><c:out value="${user.nickName}" />님</b>의 레시피 중에서 <b><c:out value="${keyword}" /></b>로 검색한 결과는 없어요.
            </c:if>
            <c:if test="${keyword != null && !empty recipeList}">
               <b><c:out value="${user.nickName}" />님</b>의 레시피 중에서 <b><c:out value="${keyword}" /></b>로 검색한 결과, <c:out value="${fn:length(recipeList)}" />개의 레시피가 있습니다.
            </c:if>
            <form action="/user/follow" method="post">
               <input type="hidden" name="followee" value='<c:out value="${user.userNo}" />'>
               <input type="hidden" name="follower" value='<c:out value="${loggedInUserNo}" />'>
            </form>
         </div>
         <div>
            <label>아이디:</label><c:out value="${user.userId}" />
         </div>
         <div>
            <label>닉네임:</label><c:out value="${user.nickName}" />
         </div>
         <div>
            <label>웹사이트:</label><a href="<c:out value="${user.webUrl}" />"><c:out value="${user.webUrl}" /></a>
         </div>
         <div>
            <label>이메일:</label>${user.email}
         </div>
         <div>
            <label>자기소개:</label>${user.bio}
         </div>
   </div> 
   
    <input type="text" name="recipeKeyword" placeholder='<c:out value="${user.nickName}" />님의 레시피 검색'>
		<input type="hidden" name="userNo" value='<c:out value="${user.userNo}" />'>
		<input type="hidden" name="where" value="profile">
		<button id="searchBtn">검색</button>		
		<button id="initBtn">초기화</button>	<br>
	<c:forEach items="${recipeList}" var="recipe">
	<div data-title="<c:out value="${recipe.title}" />" style="border: 1px solid; width: 200px; height: 100px; padding: 15px; margin: 33px; float: left;">
	<div style="float:left;"><a href="/recipe/detail?rno=${recipe.rno}"><img src="<c:out value="${recipe.thumbnail }"/>"
					width="100" height="100" /></a></div>
						<div style="padding: 15px;"><c:out value="${recipe.title}" /></div>
						<div><c:out value="${recipe.userNo}" /></div>
						</div>
	</c:forEach>

</body>
<script type="text/javascript">
$(document).ready(function(){
	let title = document.querySelectorAll("[data-title]");
$("#searchBtn").on("click", function(e){
	e.preventDefault();
	var keyword = $("input[name=recipeKeyword]").val();
    if(keyword==''){
       alert("키워드를 입력하세요.");
       return false;
    }
	for(i=0;i<title.length;i++){
		title[i].style.display='none';
	var titleVal = title[i].getAttribute("data-title");
	if(!(titleVal.search(keyword)==-1)){
		title[i].style.display='block';
		}
	}
	$("#initBtn").on("click",function(e){
		e.preventDefault();
		 for(i=0;i<title.length;i++){
			 title[i].style.display='block';
		 }
	});
});
});
</script>

</html>