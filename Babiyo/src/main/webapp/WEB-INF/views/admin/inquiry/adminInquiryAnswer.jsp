<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 문의 상세</title>		

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

#createDateSpan{
	margin-left: 50px;
}

#divisionId, #titleDiv, #contentDiv, #answerDiv{
	margin-top: 13px;
}

#divisionId, #titleId{
	margin-left: 15px;
	border: 1px solid;
}

#memberId{
	width: 145px;
	height: 32px;
	border: 1px solid;
}

#createDateId{
	width: 145px;
	height: 32px;
	border: 1px solid;	
}

#divisionId{
	margin-left: 15px;
	width: 120px;
	height: 32px;
	border: 1px solid;	
}


#titleId{
	width: 600px;
	height: 32px;
	border: 1px solid;	
}

#contentId{
	margin-top:10px;
	width: 900px;
	height: 180px;
	border: 1px solid;	
}

#answerId{
	margin-top:10px;
	width: 900px;
	height: 180px;
	border: 1px solid;	
}

#btnDiv{
	width: 900px;
	margin-top: 25px;
}

.backBtn, #modifytBtn, .deleteBtn, #answerBtn{
	width: 62px;
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

#modifytBtn{
	float: right;
}

#answerBtn{
	float: right;
}

.deleteBtn{
	float: right;
	margin-right: 20px;
}
</style>

<link rel="stylesheet" type="text/css" href="/babiyo/resources/css/common.css"/>
<script type="text/javascript" src="/babiyo/resources/js/jquery-3.6.1.js"></script>

<script type="text/javascript">

function backBtn() {
	location.href = "../inquiry"
}

function formSubmit() {
	
	var answer = document.getElementById('answerBtn');
	
	if (frm.answer.value == "") {
		alert("답변을 입력하세요.");
		frm.answer.focus();
		
		return false;
	}
	
	if (answer) {
		alert('등록되었습니다');
	}else {
		alert('변경되었습니다');
	}
	
};


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
			<form id="answerForm" name="frm" onsubmit="return formSubmit();" action="./${inquiryDto.no}/answer" method="post">
				<input type="hidden" name="no" value="${inquiryDto.no}">
				<input type="hidden" name="categoryCode" value="${inquiryDto.categoryCode}">
				<div>
					<span>작성자</span>
					<input type="text" name="memberId" id="memberId"
					 value="${inquiryDto.memberId}" readonly>
					 
					<span id="createDateSpan">작성일</span>
					<input type="text" name="createDate" id="createDateId"
					 value="<fmt:formatDate pattern='yyyy년MM월dd일 ' value='${inquiryDto.createDate}'/>"readonly>
				</div>
				<div>
					<span>분류</span>
					<input type="text" name="name" id="divisionId"
					 value="${inquiryDto.name}" readonly>
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
				<div id="answerDiv">
					<span>답변</span><br>
					<textarea name="answer" id="answerId">${inquiryDto.answer}</textarea>
				</div>
				<div id="btnDiv">
				<input class="backBtn" type="button" value="뒤로가기" onclick="backBtn()"> 
				<c:choose>
					<c:when test="${!empty inquiryDto.answer}">
						<input id="modifytBtn" type="submit" value="수정하기" onclick="modifytBtn()">
					</c:when>
					<c:otherwise>
						<input id="answerBtn" type="submit" value="답변하기">
					</c:otherwise>
				</c:choose>	
				</div>
			</form>
			</div><!-- 박스div 끝 -->
			<div id="underPadding"></div>
			
		</div> <!--middelMain 끝 -->
	
	</div> <!--middleDiv 끝 -->

	<jsp:include page="/WEB-INF/views/footer.jsp" />

</div> <!-- rootDiv 끝 -->

</body>
</html>