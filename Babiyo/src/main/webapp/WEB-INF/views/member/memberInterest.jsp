<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>관심사 설정</title>
		<script type="text/javascript" src="/babiyo/resources/js/jquery-3.6.1.js"></script>
		
		<style type="text/css">
		body{
			margin: 0 auto;
		}
		
		form{
			width: 400px;
			margin: 0 auto;
		}
		
		#wrap{
			margin: 0 auto;
		}
		
		#header{
			 text-align: center;
		}
		
		
		.chk_plz{
			color: red;
		}
		
		.bir_yy{
			display: table-cell;
			width: 25%;
		}
		.bir_mm{
			display: table-cell;
			width: 25%;
		}
		.bir_dd{
			display: table-cell;
			width: 25%;
		}
		.user_input{
			border: none;
			outline: 0;
		}
		.input_box{
			border: solid 1px #dadada;
			outline: 0;
			background-color: white;
			padding: 10px;
			margin: 3px;
		}
		
		.birth{
			width: 130px; height: 40px;
			margin: 7px;
			border: solid 1px #dadada;
		}
		
		#all_chk{
			width: 200px;
			margin-top: 20px;
			padding-bottom: 20px;
			padding-top: 20px;
			padding-right: 10px;
			border: none;
			border-radius: 12px;
			background: orange;
			color: white;
			font-size: 30px;
			text-align: center;
			float: left;
		}
		#jump{
			width: 200px;
			margin-top: 20px;
			padding-bottom: 20px;
			padding-top: 20px;
			padding-right: 10px;
			border: none;
			border-radius: 12px;
			background: orange;
			color: white;
			font-size: 30px;
			text-align: center;
			float: left;
		}
		</style>
		
		<script type="text/javascript">
			
			function submitCheck(){
				
				if(!$('.checkBox').is(':checked')){
					alert('관심사를 하나 이상 선택해주세요.');
					return;
				}
				
				$('.checkBox:checked').each(function(index){
					var name = 'interestList[' + index + '].productCode'; 
					
					$(this).attr('name', name);
// 					console.log(name);
				})
					
				interestForm.submit();
			}
			
		</script>
		
	</head>
	
	<body>
		<div id='wrap'>
			<jsp:include page="/WEB-INF/views/loginHeader.jsp"/>
			
			<h3 style="text-align: center;">관심사 선택</h3>
			<h4 style="text-align: center;">관심사는 고객님께서 원하시는 밀키트를 추천해 드리기 위해서 사용됩니다.</h4>
			<form id="interestForm" method='post'>
			<input type="hidden" name="memberId" value="${memberId}">
				<div class="bir_wrap">
					<h3>관심있는 카테고리 선택</h3>
					<div class="bir_yy">
						<c:forEach items="${categoryCodeList}" var="interest">
							<input type="checkbox" class="checkBox"
								value="${interest.code}">${interest.name}
						</c:forEach>
					</div>
				</div>	
				<input id='all_chk' type="button" value='결정' onclick="submitCheck();">
			</form>	
				<input id='jump' type="button"  value='건너뛰기' onclick="location.href='/auth/member/addComplete'">
		</div>	
	</body>
</html>