<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
</script>
<c:set value="${pageContext.request.contextPath}" var="contextPath"/>

<div id='verticalSideMenuBarArea'>
 	<c:choose>
		<c:when test="${_memberDto_.grade eq 1}">
			<div id='verticalSideManagerMenuBar'>
				<div><a id='managerInfo' href="${contextPath}/admin/home">관리자</a></div>
				<div><a id='memberManagement' href="${contextPath}/admin/member">회원 관리</a></div>
				<div><a id='mealkitManagement' href="${contextPath}/admin/product">밀키트 관리</a></div>
				<div><a id='orderManagement' href="${contextPath}/admin/orders">주문 관리</a></div>
				<div><a id='salesManagement' href="${contextPath}/admin/sales">매출 관리</a></div>
				<div><a id='noticeManagement' href="${contextPath}/admin/notice">공지 관리</a></div>
				<div><a id='inquiryManagement' href="${contextPath}/admin/inquiry">문의 관리</a></div>
				</div>
			</c:when>
			
			<c:otherwise>
				<div id='verticalSideMemberMenuBar'>
					<div><a id='memberInfo' href="${contextPath}/member/home">내 정보</a></div>
					<div><a id='memberOrderList' href="${contextPath}/member/orders">주문내역</a></div>
					<div><a id='memberInquiryList' href="${contextPath}/member/inquiry">내 문의</a></div>
					<div><a id='memberShoppingCart' href="${contextPath}/member/cart">장바구니</a></div>
					<div><a id='memberBookmark' href="${contextPath}/member/favorite">즐겨찾기</a></div>
				</div>
 			</c:otherwise>
 	</c:choose>
</div>
