<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 쓰기</title>

<link rel="stylesheet" type="text/css" href="/babiyo/resources/css/common.css"/>

<style type="text/css">
#writeDiv{
	margin: 0 auto;	
	width: 1200px;
	min-height: 550px; 
}

#upperInfoDiv{
}

#productInfoDiv{
	margin-left: 80px;
	padding: 25px;
	width: 250px;
	text-align: center;
	float: left;
}

#productImage{
	width: 200px;
	height: 200px;
	overflow: hidden;
	border-radius: 10px;
}

#productImage > img{
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

#guideDiv{
	margin: 50px 0 0 20px;
	float: left;
}

#firstRowGuide{
	margin: 0px;
	font-size: 16px;
	font-weight: bold;	
}

#evaluationDiv{
	float: left;
	margin: 40px 0 0 20px;
	width: 250px;
	height: 30px;
}

.star{
	position: relative;
	font-size: 1.5rem;
	color: #ddd;
}

.star input{
	width: 100%;
	height: 100%;
	position: absolute;
	left: -10px;
   	opacity: 0; 
	cursor: pointer;
}

.star span{
	width: 0;
	position: absolute; 
	left: 0;
	color: red;
	overflow: hidden;
	pointer-events: none;
}

#uploadImageDiv{
	position: relative;
	margin: 40px 0 0 10px;
	width: 700px;
	height: 150px;
	border: 2px dashed #BDBDBD;
	border-radius: 10px;
	float: left;
}

#uploadImageDiv > #imageId{
	display: none;
}

#uploadImageDiv > label{
	position: absolute;
	display: inline-block;
	width: 100%;
	height: 100%;
}

#userUploadImage{
	padding: 5px 35px;
	height: 100%;
	text-align: center;
}

#userUploadImage > span{
	font-weight: bold;
	line-height: 30px;
}

.drag {
	background-color: #EAEAEA;
}

#imageContainer{
	text-align: center;
}

#imageContainer > ul{
	margin: 0px;
	padding: 0px;
	font-size: 0;
	list-style-type: none;
	text-align: left;
}

#imageContainer > ul > li{
	display: inline-block;
	position: relative;
}

#imageContainer > ul > li > span{
	display: inline-block;
	width: 25px;
	height: 25px;
	line-height: 25px;
	font-size: 30px;
	cursor: pointer;
}

#imageContainer > ul > li > img{
	width: 100px;
	height: 100px;
}

#contentDiv{
	clear: both;
	width: 1200px;
	margin-top: 20px;
}

#contentTextBox{
	display: inline-block;
	margin: 0px 100px;
	padding: 25px;
	font-size: 16px;
	width: 1000px;
	border: 2px solid black;
	border-radius: 10px;
	min-height: 200px;
}

#lowerButtonDiv{
	width: 1200px;
	line-height: 50px;
	text-align: center;
}

.lowerButton{
	width: 150px;
	height: 35px;
	border: 0px;
	border-radius: 5px;
	color: #fff;
	background-color: #FF9436;
	font-size: 16px;
	font-weight: bold;
}


</style>

<script type="text/javascript" src="/babiyo/resources/js/jquery-3.6.1.js"></script>

<script type="text/javascript">
	const drawStar = (target) => {
		var widthVal = $(target).val() * 20 + '%';
		
		$('.star span').css({width: widthVal});
	}
	function formSubmit() {	
		if (frm.starRating.value == 0) {
			alert("별점을 정해주세요");
			frm.starRating.focus();
			
			return false;
		}
	}
	function modifyBtn(no){ // 수정버튼 누를시 함수
		const imgForm = new FormData();
		
		const dataTransfer = new DataTransfer();
		
		for (let file of fileArr) {
			dataTransfer.items.add(file)
		}
		
		for (let file of dataTransfer.files) {
			imgForm.append("file", file);
		}
		
		
		$.ajax({ // put 요청으로 리뷰 내용수정
			type : 'put',
			url : '../' + no,
			data : $('#reviewForm').serialize(),
			success: function(){
				$.ajax({ // post 요청으로 이미지 변경
				    type : 'post', 
				    url : '../' + no + '/image',
				    data : imgForm,
				    contentType : false,
				    processData : false,
				    success : function(){
				    	location.href='../' + no;
				    }
				});
			}
		});
	}
	function setThumbnail() {
       	$('#imageContainer > span').hide();
		
		let thumbFiles;
		
		if(event.target.files != null){
			thumbFiles = event.target.files;
			
			if(thumbFiles.length + fileArr.length > 5){
				alert('사진은 최대 5장 까지 업로드 할 수 있습니다.')
				return;
			}
			
		}else {
			thumbFiles = files;
		}
		
		fileArr = fileArr.concat(Array.from(thumbFiles));
		fileArrPush();
		
		let i = 0; //for문으로 실행하면 이미지 순서가 섞여버려서 interval을 사용
       	const imgAdd = setInterval(() => {
       		if(i < thumbFiles.length){
	           	let reader = new FileReader();
	           	
	            reader.onload = function() {
	            	addImg(reader.result)
	            };
	            
	            reader.readAsDataURL(thumbFiles[i])
	            
	            i++;
       		} else {
       			clearInterval(imgAdd);
       		}
		}, i);
	}
	function addImg(result){
	    let htmlStr = '<li><img src="' + result + '"/><span>&times;</span></li>';
	        	
	    $('#imageContainer > ul').append(htmlStr);
         
		$('#imageContainer > ul span').off().click(imageRemove);
	}
	function imageRemove(){
		let index = $(this).parents().index();
		
		$('#imageContainer > ul > li').eq(index).remove();
		
		fileArr.splice(index, 1);
		
		fileArrPush();
	}
	function fileArrPush(){
		const dataTransfer = new DataTransfer();
		
		for (let file of fileArr) {
			dataTransfer.items.add(file)
		}
		
		$('#imageId')[0].files = dataTransfer.files;
		console.log($('#imageId')[0].files);
	}
	let files;
	let fileArr = [];
	$(function(){
		drawStar("#starRange");
		
		let count = 0;
		
		$('#uploadImageDiv').bind({
			dragenter: function(e){
				e.preventDefault();
				count++;
				$(this).addClass('drag');
			},
			dragleave: function(e){
				count--;
				if(count == 0){
					$(this).removeClass('drag');
				}
			},
			dragover : function(e){
				e.preventDefault();
			},
			drop : function(e){
				e.preventDefault();
				$(this).removeClass('drag');
				count = 0;
				
				files = event.dataTransfer.files;
				
				let imgCount = $('#imageContainer > ul > li').length + files.length;
				
				if(imgCount > 5){
					alert('사진은 최대 5장 까지 업로드 할 수 있습니다.');
					return;
				}
				
				setThumbnail(files);
			}
		});
		
		$('#imageContainer > ul span').off().click(imageRemove);
		
		$('.storedName').each(function(){
			const stordName = $(this).val();
			const file = new File([stordName], stordName, {type:"text"});
			
			fileArr.push(file);
		});
		
	});
	
	
</script>

<c:choose>
	<c:when test="${empty review}">
		<c:set var="starRating" value="0"/>
		<c:set var="productImg" value="${productMap.imgMap.storedName}"/>
		<c:set var="no" value="${productMap.productDto.no}"/>
	</c:when>
	<c:otherwise>
		<c:set var="starRating" value="${review.reviewDto.starRating}"/>
		<c:set var="productImg" value="${review.reviewDto.productImg}"/>
		<c:set var="no" value="${review.reviewDto.no}"/>
	</c:otherwise>
</c:choose>

</head>
<body>

<div id="rootDiv">

	<jsp:include page="/WEB-INF/views/header.jsp" />

	<div id="middleDiv">
	
		<div id="middleDiv">
			<!--여기서 작성 -->
			<div id='writeDiv'>
				<form id="reviewForm" method='post' name='frm' enctype="multipart/form-data" 
					onsubmit="return formSubmit();">
					<div id='upperInfoDiv'>
						<div id='productInfoDiv'>
							<div id='productImage'>
								<img id="productImg" src="/babiyo/img/${productImg}">
							</div>
							<div class='productName'>${review.reviewDto.productName}</div>
						</div>
						<div id='guideDiv'>
							<p id='firstRowGuide'>밀키트에 대한 리뷰를 작성해주세요.</p>
						</div>

						<div id='evaluationDiv'>
							<span class='star'>★★★★★
								<span>★★★★★</span>
								<input id="starRange" type="range" name="starRating" oninput="drawStar(this)" 
									value='${starRating}' step='1' min='0' max='5'>
							</span>
						</div>
						
						<div id='uploadImageDiv'>
							<label for="imageId"></label>
							<input type="file" name="file" id="imageId" accept="image/*" 
								onchange="setThumbnail();" multiple/>
							<c:forEach items="${review.imgList}" var="img" varStatus="i">
								<input class="storedName" type="hidden" value="${img.storedName}"/>
							</c:forEach>
							<div id='userUploadImage'>
								<span>이곳에 사진을 드래그 하세요.(최대 5장)</span>
								<div id="imageContainer">
									<ul>
									<c:if test="${not empty review.imgList}">
										<c:forEach items="${review.imgList}" var="img">
											<li><img src="/babiyo/img/${img.storedName}"/><span>&times;</span></li>
										</c:forEach>
									</c:if>
									</ul>
								</div>
							</div>
						</div>
					</div>
					
					<div id='contentDiv'>
						<textarea name="content" id='contentTextBox'>${review.reviewDto.content}</textarea>
					</div>
					<div id='lowerButtonDiv'>
	  					<input type='button' value='이전'  class='lowerButton' onclick='location.href="../${no}"'>
	  					
	  					<c:choose>
							<c:when test="${empty review}">
								<input type="submit" value='등록' onclick="submitBtn();" class='lowerButton'>
							</c:when>
	  						<c:otherwise>
	  							<input type="button" value='수정' onclick="modifyBtn(${review.reviewDto.no});" class='lowerButton'>
	  						</c:otherwise>
	  					</c:choose>
					</div>
				</form> 
			</div>
		
			<div id="underPadding"></div>
					
		</div> <!--middelMain 끝 -->
	
	</div> <!--middleDiv 끝 -->

	<jsp:include page="/WEB-INF/views/footer.jsp" />

</div> <!-- rootDiv 끝 -->

</body>
</html>