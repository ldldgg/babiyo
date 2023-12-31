<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 탈퇴</title>
<style type="text/css">
	#infoBox2{
	width: 950px;
	}
	
	#infoBox {
		text-align: center;
		width: 530px;
		margin: auto;
	}
	
	#chkWord{
	width: 25%;
	height: 40px;
	text-align: center;
	margin-bottom: 100px;
	margin-top: 70px;
	font-size: 20px;
	}
	
	.button{
	width: 120px;
	height: 40px;
	font-size: 17px;
	font-weight: bold;
	background-color: #FF9436;
	color: #fff;
	border: none;
	border-radius: 8px;
	}
	
	a{
	color: orange;
	}
	
	#lastCheck{
	height: 200px;
	margin-bottom: 22px;
	}
	
	#warn{
	font-weight: bolder;
	font-size: 20px;
	width: 100%;
	}
	#btnWrap{
	
	}
	
	


</style>
<link rel="stylesheet" type="text/css" href="/babiyo/resources/css/common.css"/>
<script type="text/javascript" src="/babiyo/resources/js/jquery-3.6.1.js"></script>


<script type="text/javascript">

		
function deleteInfoFnc() {
	var chkWord = document.getElementById('chkWord').value;
	var deleteWord = document.getElementById('deleteWord').value;
	var form = document.forms;
	console.log(chkWord);
	console.log(deleteWord);
	if (chkWord == ''){
		alert("문구를 입력해주세요");
	}else if(chkWord !== deleteWord) {
		alert("문구를 확인해주세요");
	}else {
		form[0].submit();
	};
	
};

function moveBackFnc() {
	location.href ='./home';
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
			<div id="middleDiv">
				<!--여기서 작성 -->
				<div id='infoBox2'>
					<div id='infoBox'>
						<p id='warn'>탈퇴하시면 저희의 서비스를 전부 사용하실 수 없게 됩니다 그래도 탈퇴를 원하시면 아래 입력란에
						<a> 회원탈퇴 </a>라고 적어주세요</p>
						<p>
							<input id='deleteWord' type="hidden" value="회원탈퇴">
						</p>
						<p id='lastCheck'>
							<input id='chkWord'  type="text" placeholder="회원탈퇴">
						</p>
						<form method="post">
							<input type="hidden" name='id' value="${_memberDto_.id}">
						
						</form>
						<div id='btnWrap'>
							<p>
								<input type="button" class="button" value="탈퇴하기" onclick="deleteInfoFnc();">
								<input type="button" class="button" value="이전" onclick="moveBackFnc();">
							</p>
						</div>
					</div>
				</div>
				<div id="underPadding"></div>
				
			</div>
		
		
		
			<div id="underPadding"></div>
			
		</div> <!--middelMain 끝 -->
	
	</div> <!--middleDiv 끝 -->

	<jsp:include page="/WEB-INF/views/footer.jsp" />

</div> <!-- rootDiv 끝 -->


</body>
</html>