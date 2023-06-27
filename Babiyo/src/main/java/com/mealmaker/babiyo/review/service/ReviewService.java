package com.mealmaker.babiyo.review.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mealmaker.babiyo.review.model.ReviewDto;

public interface ReviewService {
	
	//리뷰 모음에 리스트 쏴주기
	List<ReviewDto> reviewCollectionList();
	//목록
	List<Map<String, Object>> reviewList();
	//등록
	public void reviewRegistration(ReviewDto reviewDto
		, MultipartHttpServletRequest mulRequest) throws Exception;
	//관리자 상세
	Map<String, Object> reviewAdminDetail(int no);
	//리뷰 상세보기
	Map<String, Object> detail(int no);
	//수정	
	public void reviewModification(ReviewDto reviewDto);
	//삭제
	public void reviewDelete(int no) throws Exception;
	List<ReviewDto> productReviewList(int productNo, String sort, int count, int reviewCurCount);
	void reviewImageModify(int no, MultipartHttpServletRequest mulRequest) throws Exception;
	Integer reviewCheck(String memberId, int productNo);
	Map<String, Object> productReviewInfo(int productNo);
}
