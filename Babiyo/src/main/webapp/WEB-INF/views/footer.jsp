<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<footer>
	<div>
		<p class='footerPTag'>바비요는 포트폴리오 목적으로 제작되었습니다.</p>
		<p class='footerPTag'></p>
		<p id='footerSmallPTag' class='footerPTag'></p>
	</div>
</footer>
	
<script type="text/javascript">
$(function(){
	if(document.getElementById('sideTitle')){
		var side = $('#verticalSideMenuBarArea').text();
		var titleObj = document.getElementById('sideTitle');
		titleObj.innerHTML = '<p>' + document.title + '</p>';
	}
});
</script>
	