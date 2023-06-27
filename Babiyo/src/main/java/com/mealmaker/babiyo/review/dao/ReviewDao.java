package com.mealmaker.babiyo.review.dao;

import java.util.List;
import java.util.Map;

import com.mealmaker.babiyo.review.model.BuyProductDto;
import com.mealmaker.babiyo.review.model.ReviewDto;
import com.mealmaker.babiyo.util.ImageDto;

public interface ReviewDao {
	//목록
	public List<ReviewDto> reviewList();
	//목록-리뷰 수
	public int reviewQuantity(int productNo);
	//목록-리뷰 평점
	public double reviewEvaluation(int productNo);
	//상세
	public ReviewDto reviewDetail(int no);
	//이미지 첨삭
	public void insertFile(Map<String, Object> map);
	
	public List<ImageDto> fileSelectList(int no);
	
	public List<String> fileSelectStoredFileName(int parentSeq);
	
	public void fileDelete(String storedName);
	
	public List<ReviewDto> productReviewList(int productNo, String sort, int end);
	
	//내정보 리뷰 카운트
	public int memberReviewCount(String id);
	
	public List<BuyProductDto> buyProductList(String memberId);
	
	public void reviewWrite(ReviewDto reviewDto);
	
	public List<ReviewDto> reviewCollectionList();
	
	public void reviewModify(ReviewDto reviewDto);
	
	public void reviewDelete(int reviewNo);
	
	public Integer reviewCheck(String memberId, int productNo);
	
}

