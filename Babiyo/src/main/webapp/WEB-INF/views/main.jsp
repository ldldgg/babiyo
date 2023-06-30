<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인화면</title>

<link rel="stylesheet" type="text/css" href="/babiyo/resources/css/common.css"/>

<style type="text/css">
#mainPageNoticeAreaDiv{
	margin: 40px 0px 20px 0px;
	width: 1200px;
	height: 30px;
}


#emptyMainNotice{
	margin: 0px;
	padding-left: 50px;
	width: 700px;
	height: 30px;
	font-size: 16px;
	font-weight: bold;
	line-height: 30px;
	float: left;
}

#noticeName{
	width: 100px;
	height: 30px;
	font-size: 18px;
	float: left;
	text-align: center;
	line-height: 30px;
}

#noticeTitle{
	width: 750px;
	height: 30px;
	padding-left: 80px;
	line-height: 30px;
	font-size: 14px;
	float: left;
}

#noticeTitle > a{
	color: #000000;
	text-decoration: none;
	cursor: pointer;
}

#noticeDate{
	width: 200px;
	height: 30px;
	padding-left: 80px;
	line-height: 30px;
	font-size: 14px;
	text-align: center;
	float: left;
}

#goToNotice{
	width: 80px;
	height: 30px;
	padding-left: 30px;
	line-height: 30px;
	font-size: 14px;
	float: right;
	text-decoration: none;
}


#goToNotice > a{
	text-decoration: none;
	color: #666666;
}

#eventBannerDiv{
	position: relative;
}


#eventImgDiv{
	width: 1200px;
	height: 300px;
	margin: 0px auto 30px auto;
	text-align: center;
	line-height: 300px;
 	overflow: hidden;
 	white-space: nowrap;
}

#eventImgDiv > div > a{
	position: relative;
	width: 1200px;
	height: 300px;
	display: inline-block;
}

.eventImg{
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	object-fit: cover;
}

#bannerBtn{
	width: 1200px;
	text-align: center;
	position: absolute;
	bottom: 20px;
}

#bannerBtn > a{
	color: #FF9436;
	font-size: 16px;
	text-decoration: none;
}

#bannerBtn > a:hover{
	color: #FF0000;
}

#bannerBtn > .curBanner{
	color: #FF0000;
}

#emptyEventList{
	font-size: 32px;
	font-weight: bold;
	text-align: center;
}

#emptyProductList{
	margin: 0px;
	width: 1200px;
	height: 250px;
	font-size: 32px;
	font-weight: bold;
	text-align: center;
	line-height: 250px;
}

#emptyInterestList{
	margin-top: 100px;
	width: 1200px;
	height: 150px;
	font-size: 32px;
	font-weight: bold;
	text-align: center;
	line-height: 50px;
}

#emptyInterestList > p > input{
	border: 0px;
	font-size: 20px;
	color: #FF5E00;  
	background-color: transparent;
	cursor: pointer;
}

.productDiv{
	width: 1200px;
	float: left;
}

.productTitle{
	height: 50px;
	font-size: 18px;
	font-weight: bold;
}

.productTitle > span{
	margin-left: 15px;
}
.productTitle > hr{
	height: 3px;
	margin: 10px 0 0;
}

.productContentDiv{
	width: 1200px;
	margin: 0px auto 30px auto;
	float: left;
}

.productContent{
	width: 300px;
	height: 350px;
	font-size: 16px;
	float: left;
}

.productContentImage > a{
	width: 300px;
	height: 300px;
	text-align: center;
	line-height: 200px;
	float: left;
}

.productContentImage > a > img{
	width: 280px;
	height: 280px;
	text-align: center;
	border-radius: 10px;
}

.productContentName{
	margin-top: 10px;
	width: 300px;
	height: 20px;
	font-size: 14px;
	font-weight: bold;
	text-align: center;
	float: left;
}

.productContentPrice{
	width: 300px;
	height: 20px;
	font-size: 12px;
	text-align: center;
	float: left;
}

</style>

<script type="text/javascript" src="/babiyo/resources/js/jquery-3.6.1.js"></script>

<script type="text/javascript">



$(function(){
	$('#bannerBtn0').attr('class', 'curBanner');
	
	console.log($('#bannerBtn0').scrollTop());
	
	bannerMove = setInterval(bannerAutoMoveFnc, 8000); // 8초 주기로 배너 자동넘김
});

function bannerAutoMoveFnc(){
	let bannerLength = $('.eventImg').length;
	
	let offset = $('#eventImgDiv').scrollLeft() + 1200;
	
	if(offset >= bannerLength*1200) {
		offset = 0;
	}
	
	let no = offset / 1200;
	
	bannerAnimationFnc(no, offset);
}

function eventBannerMoveFnc(no){
	clearInterval(bannerMove); // 배너 선택하면 자동넘김 정지
	
	let offset = no * 1200;
	
	bannerAnimationFnc(no, offset);
	
	bannerMove = setInterval(bannerAutoMoveFnc, 8000); // 자동 넘김 시작
}

function bannerAnimationFnc(no, offset){
	const intervalTime = 400;
	
	$('a[id^="bannerBtn"]').attr('class', '');
	$('#bannerBtn' + no).attr('class', 'curBanner');
	
	$('#eventImgDiv').animate({scrollLeft: offset}, intervalTime);
}
</script>

</head>

<body>

<div id="rootDiv">

	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div id="middleDiv">
		<!--여기서 작성 -->
		<div id='mainPageNoticeAreaDiv'>
			<div id='noticeName'>
				<span><strong>공지사항</strong></span>
			</div>
			<c:choose>
				<c:when test="${empty mainLatestNotice}">
					<p id='emptyMainNotice'>
						공지가 없습니다.
					</p>	
				</c:when>
				<c:otherwise>
					<div id='noticeTitle'>
						<a href="/babiyo/notice/${mainLatestNotice.no}">
							${mainLatestNotice.title}</a>
					</div>
					<div id='noticeDate'>
						<fmt:formatDate pattern="yyyy-MM-dd" value="${mainLatestNotice.createDate}" />
					</div>
				</c:otherwise>
			</c:choose>
			<div id='goToNotice'>
				<a href="/babiyo/notice"><strong>더보기</strong></a>
			</div>
		</div>
		
		<div id='eventBannerDiv'>
			<div id='eventImgDiv'>
				<c:choose>
					<c:when test="${empty eventImgList}">
						<img alt="main image" src="/babiyo/resources/img/main.png">
					</c:when>
					<c:otherwise>
					<div style="font-size: 0">
					<a href="/babiyo">
						<img alt="main image" class="eventImg" src="/babiyo/resources/img/main.png">
					</a>
					<c:forEach items="${eventImgList}" var="img" varStatus="i">
						<a href="/babiyo/notice/${img.targetNo}">
							<img id="eventImg${i.index}" class="eventImg" alt="이벤트 이미지"
								 src="/babiyo/img/${img.storedName}"/>
						</a>
					</c:forEach>
					</div>
					</c:otherwise>
				</c:choose>
			</div>
			<div id="bannerBtn">
				<c:forEach var="i" begin="0" end="${fn:length(eventImgList)}">
					<a id="bannerBtn${i}" onclick="eventBannerMoveFnc(${i});">●</a>
				</c:forEach>
			</div>
		</div>
		
		<c:if test="${not empty _memberDto_}">
		<div class='productDiv'>
			<div class='productTitle'>
				<span>추천 밀키트</span>
				<hr>
			</div>
			<div class='productContentDiv'>
				<c:choose>
					<c:when test="${empty recommendProductList}">
						<form action='/babiyo/member/checkInfo' method='get'>
							<div id='emptyInterestList'>
								<p>
									관심사를 등록해 주시면 밀키트를 추천해 드려요!<br />
									<input type="submit" value='관심사 등록하러 가기'>
								</p>
							</div>
						</form>
					</c:when>
					<c:otherwise>
						<c:forEach var="recommend" items="${recommendProductList}"> 
							<div class='productContent'>
								<div class='productContentImage'>
									<a href="/babiyo/product/${recommend.productDto.no}">
									<img alt="${recommend.productDto.name}" 
										src="/babiyo/img/${recommend.imgMap.storedName}"></a>
								</div>
								<div class='productContentName'>
									${recommend.productDto.name}
								</div>
								<div class='productContentPrice'>
									<fmt:formatNumber value="${recommend.productDto.price}" 
										pattern="#,###"/> 원
								</div>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		</c:if>
		
		<div class='productDiv'>
			<div class='productTitle'>
				<span>신상 밀키트</span>
				<hr>
			</div>
			<c:choose>
				<c:when test="${empty newProductList}">
					<p id='emptyProductList'>
						등록된 밀키트가 없습니다.
					</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="product" items="${newProductList}"> 
						<div class='productContent'>
							<div class='productContentImage'>
								<a href="/babiyo/product/${product.productDto.no}">
								<img alt="${product.productDto.name}" src="/babiyo/img/${product.imgMap.storedName}"></a>
							</div>
							<div class='productContentName'>
								${product.productDto.name}
							</div>
							<div class='productContentPrice'>
								<fmt:formatNumber value="${product.productDto.price}" pattern="#,###"/> 원
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>

		<div id="underPadding"></div>
		
	</div> <!--middleDiv 끝 -->

	<jsp:include page="/WEB-INF/views/footer.jsp" />

</div>  <!--rootDiv 끝 -->

</body>
</html>