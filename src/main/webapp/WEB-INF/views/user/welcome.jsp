<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../includes/tempnav.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입 완료</title>
</head>
<body>
<h1>쿡스타그램 회원 가입을 축하합니다.</h1>
<div>
</div>
<div>
<h1>새회원 정보</h1>
<label>userNo</label> <c:out value="${user.userNo}" /><br>
<label>userId</label> <c:out value="${user.userId}" /><br>
<label>userNickName</label> <c:out value="${user.nickName}" /><br>
<label>userEmail</label> <c:out value="${user.email}" /><br>
<!-- <label>userEmailAuth</label> <c:out value="${user.emailAuth}" /><br> -->
<label>userProfilePhoto</label> <img src='<c:out value="${user.profilePhoto}" />' onerror="imgError(this);" ><br>
<label>userWeb</label> <c:out value="${user.webUrl}" /><br>
<label>userBio</label> <c:out value="${user.bio}" /><br>
<label>userEmailSub</label> <c:out value="${user.emailSub}" /><br>
</div>

<div>
이메일 인증만 완료하면 회원 가입이 완료됩니다. 이메일을 확인해주세요.

<br>
<br>                        
인증 이메일이 안왔나요? 

<a href="/user/resendEmail?userNo=<c:out value="${user.userNo}" />">인증 이메일 다시 보내기</a>
</div>


</body>
</html>