<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="/babiyo/resources/css/common.css"/>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>

<script type="text/javascript">
function logoutFnc(){
	event.preventDefault();
	
	if(confirm('로그아웃을 하시겠습니까?')){
		location.href= '${contextPath}/auth/logout';
	};
}
</script>

<header>
	<div style="width: 1200px; height: 100%; margin: auto;">
	
	<div id="serviceAreaDiv">
		<div id='serviceAreaMenuBar'>
				<div id='serviceAreaManagerMenuBar'>
		<c:choose>
			<c:when test="${_memberDto_.grade eq 1}">
					<a id='managerInfo' href="${contextPath}/admin/home">관리자</a>
					<a id='logout' href="#" onclick="logoutFnc();">로그아웃</a>
			</c:when>
			
			<c:when test="${_memberDto_.grade eq 2}">
					<a id='cash' href="${contextPath}/member/cash/charge">충전</a>
					<a id='memberInfo' href="${contextPath}/member/home">내 정보</a>
					<a id='shoppingBasket' href="${contextPath}/member/cart">장바구니</a>
					<a id='bookMark' href="${contextPath}/member/favorite">즐겨찾기</a>
					<a id='logout' href="#" onclick="logoutFnc();">로그아웃</a>
			</c:when>
			
			<c:otherwise>
					<a href="${contextPath}/auth/login">로그인</a>
			</c:otherwise>
		</c:choose>
				</div>
		</div>
	
		<div id='logoDiv'>
			<a href="${contextPath}">
			<img id='logo' alt="메인으로 이동" src="/babiyo/resources/img/logo.png">
			</a>
		</div>
	</div>
	
	<div id='categoryMenuBar'>
		<div>
			<a id='mealKit' href="${contextPath}/category">밀키트</a>
		</div>
		
		<div class='categoryVerticalLine'></div>
		
		<div>
			<a id='ranking' href="${contextPath}/ranking">랭킹</a>
		</div>
		
		<div class='categoryVerticalLine'></div>
		
		<div>
			<a id='review' href="${contextPath}/reviews">리뷰</a>
		</div>
		
		<div class='categoryVerticalLine'></div>
		
		<div>
			<a id='notice' href="${contextPath}/notice">공지</a>
		</div>
		<div class='clearBlock'></div>
	</div>
	
	</div>
	
	
</header>