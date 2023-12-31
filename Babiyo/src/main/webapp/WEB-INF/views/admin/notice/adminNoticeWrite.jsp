<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지 작성</title>

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

#categoryId{
	margin-left: 4px;
	width: 100px;
	height: 35px;
	border-radius: 5px;
	border: 1px solid black;
}
#daySpan{
	margin-left: 10px;
}

#titleId{
	margin:10px 0px 0px 8px;
	width: 842px;
	height: 40px;
	border: 1px solid black;	
}

#contentsId{
	margin-top:10px;
	width: 900px;
	height: 350px;
	border: 1px solid black;	
}

#eventStartDateId, #eventEndDateId{
	margin-left: 4px;
	width: 100px;
	height: 35px;
	border-radius: 5px;
	border: 1px solid black;
}

#imageDiv{
	margin-top: 10px;
}

.eventCl, #imageDiv{
	display: none;
}

#btnDiv{
	width: 850px;
	height:20px; 
	margin: 30px 0px 50px 50px;
}

.backBtn {
	margin-left: 390px;
}

.sumbitBtn {
	float: right;
}

.backBtn, .sumbitBtn{
	width: 62px;
    height: 35px;
    border: 0px;
    border-radius: 5px;
    color: #fff;
    background-color: #FF9436;
	cursor: pointer;
}

</style>

<link rel="stylesheet" type="text/css" href="/babiyo/resources/css/common.css"/>
<script type="text/javascript" src="/babiyo/resources/js/jquery-3.6.1.js"></script>

<script type="text/javascript">



$(function() {
	$('#categoryId').change(function() {
	  var result = $("select[name=categoryCode]").val();
	  if (result == 2) {
	    $('.eventCl').show();
	    $('#imageDiv').show();
	  } else {
	    $('.eventCl').hide();
	    $('#imageDiv').hide();
	  }
	}); 
	
	$('#eventStartDateId').val(new Date().toISOString().slice(0, 10));
}); 

function formSubmit() {
	var today = new Date();  
	var todayCut = new Date(today.getFullYear(),today.getMonth(),today.getDate());
	var eventStart = new Date(frm.eventStartDate.value);
	var target = document.getElementById("categoryId");
	
	if (target.value == 2) {
		
		if (frm.eventStartDate.value == "") {
			alert("시작일을 선택하세요.");
			frm.eventStartDate.focus();
			
			return false;
		}
		
		if (frm.eventEndDate.value == "") {
			alert("종료일을 선택하세요.");
			frm.eventEndDate.focus();
			
			return false;
		}
 
		 if (frm.eventEndDate.value < frm.eventStartDate.value) {
			alert("종료일을 시작일 이상으로 하세요.");
			frm.eventEndDate.focus();
			
			return false;
		}	
			
	}
	if (frm.title.value == "") {
		alert("제목을 입력하세요.");
		frm.title.focus();
		
		return false;
	}
	
	if (frm.content.value == "") {
		alert("내용을 입력하세요.");
		frm.content.focus();
		
		return false;
	}
	
} //formSubmit() end

function backBtn(){
	location.href = '../notice';
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
			
		
			<form name="frm" method="post" enctype="multipart/form-data"
				onsubmit="return formSubmit();">
			<div>
				<span>분류</span> 
				<select name="categoryCode" id="categoryId">
					<option value="1">공지</option>
					<option value="2">이벤트</option>
				</select>
				
				<span id="daySpan" class="eventCl">기간</span>
				<input name="eventStartDate" id="eventStartDateId" class="eventCl" type="date">
				<span id="daySpan" class="eventCl">~</span>
				<input name="eventEndDate" id="eventEndDateId" class="eventCl" type="date">
			</div>
			<div>      
				<span>제목</span><input name="title" id="titleId" type="text">
			</div>
			<div id="imageDiv">
				<span>사진</span> <input name="originalName" class="imageId" type="file"><br>
			</div>
			<div id="contentsDiv">
				<span>내용</span><br> 
				<textarea name="content" id="contentsId"></textarea>
			</div>
			<div id="btnDiv">
				<input class="backBtn" type="button" value="뒤로가기" onclick="backBtn()"> 
				<input class="sumbitBtn" type="submit" value="작성">
			</div>
		
			</form>
			</div><!--  boxDivEnd -->
			<div id="underPadding"></div>
			
		</div> <!--middelMain 끝 -->
	
	</div> <!--middleDiv 끝 -->

	<jsp:include page="/WEB-INF/views/footer.jsp" />

</div> <!-- rootDiv 끝 -->

</body>
</html>