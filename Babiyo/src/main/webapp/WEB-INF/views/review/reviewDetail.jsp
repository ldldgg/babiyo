<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 상세</title>

<link rel="stylesheet" type="text/css" href="/babiyo/resources/css/common.css"/>

<script type="text/javascript" src="/babiyo/resources/js/jquery-3.6.1.js"></script>

<style type="text/css">

#reviewDetailDiv{
	width: 1200px;
	margin: 0px auto;
	min-height: 550px;
}

#productInfoDiv{
	margin-left: 80px;
	padding: 25px;
	width: 250px;
	text-align: center;
	float: left;
}

.productImage{
	width: 200px;
	height: 200px;
	overflow: hidden;
	border-radius: 10px;
}

.productImage img{
	margin: auto;
	width: 200px;
	height: 200px;
	text-align: center;
 	background-color: lightgray; 
}

.productName{
	margin-top: 20px;
	width: 200px;
	height: 25px;
	font-size: 16px;
	text-align: center;
}

#reviewInfoDiv{
	margin: 0px;
	padding: 25px;
	width: 870px;
	height: 250px;
	float: left;
}

.nickname{
	margin-top: 20px;
	height: 30px;
	font-size: 18px;
	font-weight: bold;
	line-height: 30px;
	float: left;	
}

.userEvaluation{
	margin-top: 20px;
	margin-left: 50px;
	width: 150px;
	height: 30px;
	float: left;
}

.starRating{
	position: relative;
	color: #ddd;
}

.starRating > span{
	position: absolute;
	left: 0;
	color: red;
	overflow: hidden;
}

.submissionDate{
	margin-top: 20px;
	margin-left: 50px;
	height: 30px;
	font-size: 16px;
	line-height: 30px;
	float: left;
}

.reviewImage{
	margin-top: 30px;
	width: 820px;
	height: 100px;
	float: left;
}

.reviewImage > a{
	margin-right: 20px;
	width: 100px;
	height: 100px;
	text-align: center;
	float: left;
 	background-color: lightgray;
}

.reviewImage > a > img{
	border: 0px;
	width: 100px;
	height: 100px;
}

.reviewContentDiv{
	padding: 25px;
	margin: 0 auto;
	font-size: 16px;
	width: 1000px;
	clear: both;
	border: 2px solid black;
	border-radius: 10px;
	min-height: 200px;
}

.lowerButtonDiv{
	margin-top: 10px;
	width: 1200px;
	height: 50px;	
	line-height: 50px;
	text-align: center;
	float: left;
}

.lowerButton{
	margin: 0px;
	border: 0px;
	border-radius: 5px;
	width: 100px;
	height: 40px;
	color: #fff;
	background-color: #FF9436;
	font-size: 16px;
	font-weight: bold;
}
</style>
<script type="text/javascript">


function reviewDeleteBtn(no, productNo){
	if(confirm('리뷰를 삭제하시겠습니까?')){
		$.ajax({
			url : './' + no,
			type : 'delete',
			success : (data) => {
				alert('리뷰가 삭제되었습니다.');
				location.href = '/babiyo/product/' + productNo;
			}
		});
	}
}


</script>
</head>

<body>

<div id="rootDiv">

	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div id="middleDiv">
		<!--여기서 작성 -->
		<div id='reviewDetailDiv'>
			<div id='productInfoDiv'>
				<div class='productImage'>
					<a href="/babiyo/product/${review.reviewDto.productNo}">
						<img alt="밀키트 이미지 " src="/babiyo/img/${review.reviewDto.productImg}">
					</a>
				</div>				
				<div class='productName'>${review.reviewDto.productName}</div>
			</div>
			<div id='reviewInfoDiv'>
				<div class='reviewInfo'>
					<div class='nickname'>${review.reviewDto.nickname}</div>
					<div class='userEvaluation'>
						<div class='wrapStar'>
							<span class='starRating' >
								★★★★★<span style="width:${review.reviewDto.starRating * 20}%">★★★★★</span>
							</span>
						</div>
					</div>
					<div class='submissionDate'>
						<fmt:formatDate value="${review.reviewDto.createDate}" pattern="yyyy년MM월dd일 a hh:mm:ss" var="createDate"/>
						<fmt:formatDate value="${review.reviewDto.modifyDate}" pattern="yyyy년MM월dd일 a hh:mm:ss" var="modifyDate"/>
						<c:choose>
							<c:when test="${createDate eq modifyDate}">${createDate}</c:when>
							<c:otherwise>${modifyDate}(수정됨)</c:otherwise>
						</c:choose>
					
					</div>
				</div>
				<div class='reviewImage'>
					<c:forEach items="${review.imgList}" var="img">
						<a href="#"><img alt="밀키트 이미지 " src="/babiyo/img/${img.storedName}"></a>
					</c:forEach> 
				</div>
			</div>
			<div class='reviewContentDiv'>
				${review.reviewDto.content}
			</div>
			<div class='lowerButtonDiv'>
					<input type="button" value="뒤로" class='lowerButton' 
						onclick="location.href='/babiyo/product/${review.reviewDto.productNo}'">
			<c:if test="${_memberDto_.id eq review.reviewDto.memberId}">
					<input type="button" value="수정" class='lowerButton' 
						onclick="location.href='./${review.reviewDto.no}/modify'">
					<input type='button' value='삭제' class='lowerButton' 
						onclick='reviewDeleteBtn(${review.reviewDto.no}, ${review.reviewDto.productNo});'>  
			</c:if>
			</div>
		</div>
		<div id="underPadding"></div>
		
	</div> <!--middleDiv 끝 -->

	<jsp:include page="/WEB-INF/views/footer.jsp" />

</div>  <!--rootDiv 끝 -->

</body>
</html>