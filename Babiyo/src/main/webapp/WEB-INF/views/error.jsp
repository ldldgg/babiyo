<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${code} 오류</title>

<link rel="stylesheet" type="text/css" href="/babiyo/resources/css/common.css"/>
<script type="text/javascript" src="/babiyo/resources/js/jquery-3.6.1.js"></script>

<style type="text/css">
h1{
	margin-top: 100px;
	text-align: center;
}

input{
	width: 100px;
	height: 40px;
	border: 0px;
	border-radius: 5px;
	background-color: #FF9436;
	font-weight: bold;
	color: white;
}

</style>

<script type="text/javascript">

</script>

</head>
<body>

<c:set value="${requestScope['javax.servlet.error.status_code']}" var="code"></c:set>
<c:set value="${requestScope['javax.servlet.error.message']}" var="message"></c:set>
<div id="rootDiv">

	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div id="middleDiv">
		<div id='centerTitle'></div>
		<!--여기서 작성 -->
		<c:choose>
			<c:when test="${code == 401}">
				<div style="text-align: center; margin-top: 150px;">
				<h1>로그인을 하셔야 이용이 가능합니다.</h1>
				<input type="button" value="로그인 이동" onclick="location.href='/babiyo/auth/login?uri=${message}'">
				</div>
			</c:when>
			<c:when test="${code == 403}">
				<h1>권한이 부족하여 접근할 수 없습니다.</h1>
			</c:when>
			<c:when test="${code == 404}">
				<h1>페이지를 찾을 수 없습니다.</h1>
			</c:when>
			<c:when test="${code == 410}">
				<h1>삭제된 페이지 입니다.</h1>
			</c:when>
			<c:when test="${code == 500}">
				<h1>서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.</h1>
			</c:when>
		</c:choose>
		
		<div id="underPadding"></div>
		
	</div> <!--middleDiv 끝 -->

	<jsp:include page="/WEB-INF/views/footer.jsp" />

</div>  <!--rootDiv 끝 -->

</body>
</html>