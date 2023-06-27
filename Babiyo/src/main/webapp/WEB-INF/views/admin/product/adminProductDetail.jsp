<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>밀키트 상세</title>

<link rel="stylesheet" type="text/css" href="/babiyo/resources/css/common.css"/>

<style type="text/css">
#productModificationDiv{
	margin: 0px auto;
	width: 900px;
	min-height: 550px;
	float: left;
}

#imageDiv{
	width: 250px;
	height: 250px;
	margin-left: 80px;
/*   	background-color: #EAEAEA;  */
	float: left;
}

#imageDiv > img{
	width: 250px;
	height: 250px;
	
}
.insertFileButton{
	width: 200px;
	float: left;
}

#upperInsertDataDiv{
	margin-top: 20px;
	margin-left: 40px;
	width: 530px;
	height: 220px;	
	float: left;
}

.insertDataDiv{
	margin-bottom: 20px;
	width: 680px;
	height: 60px;
}

.classificationDiv ,.stockDiv{
	margin-top: 10px;
	width: 220px;
	height: 60px;
	float: left;
}

.pTagName{
	margin: 0px auto 5px auto;	
	line-height: 20px;
	font-size: 18px;
	font-weight: bold;
}

.sidePTagName{
	margin: 0px auto 5px auto;	
	line-height: 30px;
	font-size: 18px;
	font-weight: bold;
	float: left;
}

.inputBox{
	width: 250px;
	height: 30px;
}

.smallInputBox{
	margin-left: 20px;
	width: 120px;
	height: 30px;
	float: left;
}

#contentDiv{
	margin-top: 50px;
	margin-left: 80px;
	width: 970px;
	min-height: 200px;
	float: left;
}

.contentTextBox{
	width: 900px;
	height: 244px;
	text-align: left;
	vertical-align: top;
}

#lowerButtonDiv{
	margin-top: 10px;
	width: 900px;
	height: 50px;	
	line-height: 50px;
	text-align: center;
	float: left;
}

.lowerButton{
	margin-top: 10px;
	margin-left: 10px;
	width: 150px;
	height: 35px;
	border: 0px;
	border-radius: 5px;
	color: #fff;
	background-color: #FF9436;
	font-size: 16px;
	font-weight: bold;
}

.dataSpace{
	line-height: 30px;
	font-size: 16px;
}

.horizonDataSpace{
	line-height: 30px;
	font-size: 16px;
	margin-left: 25px;
}

</style>

<script type="text/javascript" src="/babiyo/resources/js/jquery-3.6.1.js"></script>

<script type="text/javascript">
	function pageMoveListFnc() {
		$('#pagingForm').attr('action', '../product');
		
		$('#pagingForm').submit();
	}

	function pageMoveDeleteFnc(no){
		$('#pagingForm').attr('action', './' + no + '/remove');
		$('#pagingForm').attr('method', 'post');
		
		$('#pagingForm').submit();
	}
	
	function pageMoveModifyFnc(no){
		$('#pagingForm').attr('action', './' + no + '/modify');
		
		$('#pagingForm').submit();
	}


</script>

</head>
<body>

<div id="rootDiv">

	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div id="middleDiv">
	
		<jsp:include page="/WEB-INF/views/commonMiddleDiv.jsp" />
		
		<div id="middleMainDiv">
			<div id="sideTitle"></div>
			<!--여기서 작성 -->
			<div id='productModificationDiv'>
				
				<div id='imageDiv'>
					<img alt='${productDto.name}' src="/babiyo/img/${productImg.storedName}">
				</div>
				<div id='upperInsertDataDiv'>
					<div class='insertDataDiv'>
						<p class='pTagName'>밀키트명</p>
						<span class='dataSpace'>${productDto.name}</span>
					</div>
					<div class='insertDataDiv'>
						<p class='pTagName'>가격</p>
						<span class='dataSpace'>
							<fmt:formatNumber value="${productDto.price}" pattern="#,### 원"/>
						</span>
					</div>
					<div class='classificationDiv'>
						<p class='sidePTagName'>분류</p>
						<span id='classification' class='horizonDataSpace'>
							${productDto.categoryName}</span>
					</div>
					<div class='stockDiv'>
						<p class='sidePTagName'>재고</p>
						<span class='horizonDataSpace'>
							<fmt:formatNumber value="${productDto.stock}" pattern="#,###"/> 개
						</span>
					</div>
				</div>
				<div id='contentDiv'>
					<p class='pTagName'>설명</p>
					<span class='dataSpace'>${productDto.content}</span>
				</div>
				<div id='lowerButtonDiv'>
					<input type="button" value="수정" class='lowerButton' onclick="pageMoveModifyFnc(${productDto.no});">
					<input type='button' value='삭제' class='lowerButton' onclick='pageMoveDeleteFnc(${productDto.no});'>  
					<input type="button" value="뒤로가기" class='lowerButton' onclick='pageMoveListFnc();'>		
				</div>
			</div>
			
			<form id="pagingForm" method="get">
				<input type="hidden" id="curPage" name="curPage" value="${curPage}">
				<input type="hidden" name="search" value="${searchOption.search}">
				<input type="hidden" name="searchOption" value="${searchOption.searchOption}">
				<input type="hidden" name="sort" value="${searchOption.sort}">
			</form>
			
			<div id="underPadding"></div>
			
		</div> <!--middelMain 끝 -->
	
	</div> <!--middleDiv 끝 -->

	<jsp:include page="/WEB-INF/views/footer.jsp" />

</div> <!-- rootDiv 끝 -->


</body>
</html>