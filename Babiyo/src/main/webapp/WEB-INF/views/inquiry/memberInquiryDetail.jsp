<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 한 문의</title>		
<style type="text/css">
input{
	border-radius: 5px;
}

#boxDiv{
	margin-left: 50px;
}

span{
	font-weight: bold;
	margin-right: 20px;
}

#titleDiv, #contentDiv, #answerDiv{
	margin-top: 13px;
}

#divisionId{
	width: 120px;
	height: 32px;
	border: 1px solid black;
}

#createDateSpan{
	margin-left: 20px;
}

#createDateId{
	width: 145px;
	height: 32px;
	border: 1px solid black;
}

#titleId{
	width: 600px;
	height: 32px;
	border: 1px solid black;
}

#contentId{
	margin-top:10px;
	width: 900px;
	height: 180px;
	border: 1px solid black;
}

#answerId{
	margin-top:10px;
	width: 900px;
	height: 180px;
	border: 1px solid black;
}

#btnDiv{
	width: 900px;
	margin-top: 25px;
}

.backBtn, .modifytBtn, .deleteBtn{
	width: 60px;
    height: 35px;
    border: 0px;
    border-radius: 5px;
    color: #fff;
    background-color: #FF9436;
	cursor: pointer;
}

.backBtn{
	margin-left: 390px;
}

.modifytBtn{
	float: right;
	margin-right: 20px;
}

.deleteBtn{
	float: right;
}
</style>
<link rel="stylesheet" type="text/css" href="/babiyo/resources/css/common.css"/>
<script type="text/javascript" src="/babiyo/resources/js/jquery-3.6.1.js"></script>

<script type="text/javascript">
	function backBtn() {
		location.href = "../inquiry"
	}
	function modifytBtn(no) {
		location.href = "./" + no + "/modify";
	}
	function deleteBtn(){
		if (confirm('삭제하시겠습니까?')) {
			$('#removeForm').submit();
		}
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
			<div id="boxDiv">
			<div>
				<span>분류</span>
				<input type="text" name="categoryCode" id="divisionId"
				 value="${inquiryDto.name}" readonly>
		
				<span id="createDateSpan">작성일</span>
				<input type="text" name="createDate" id="createDateId"
				 value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${inquiryDto.createDate}'/>" readonly>
			</div>
			
			<div id="titleDiv">
				<span>제목</span>
				<input type="text" name="title" id="titleId"
				 value="${inquiryDto.title}" readonly>
			</div>

			<div id="contentDiv">
				<span>내용</span><br>
				<input type="text" name="content" id="contentId"
				 value="${inquiryDto.content}" readonly>
			</div>

			<c:if test="${!empty inquiryDto.answer}">
			<div id="answerDiv">
				<span>답변</span><br>
				<input type="text" name="answer" id="answerId"
				 value="${inquiryDto.answer}" readonly>
			</div>	
			</c:if>
			<div id="btnDiv">
			<input class="backBtn" type="button" value="뒤로가기" onclick="backBtn()"> 
			<input class="deleteBtn" type="button" value="삭제하기" onclick="deleteBtn(${inquiryDto.no})">
			
			<c:if test="${empty inquiryDto.answer}">
				<input class="modifytBtn"  type="button" value="수정하기" onclick="modifytBtn(${inquiryDto.no})">
			</c:if>
			
			<form id="removeForm" action="./${inquiryDto.no}/remove" method="post">
			</form>
			
			</div>
			</div><!-- 박스div 끝 -->
			<div id="underPadding"></div>
			
		</div> <!--middelMain 끝 -->
	
	</div> <!--middleDiv 끝 -->

	<jsp:include page="/WEB-INF/views/footer.jsp" />

</div> <!-- rootDiv 끝 -->


</body>
</html>