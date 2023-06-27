package com.mealmaker.babiyo.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mealmaker.babiyo.review.model.BuyProductDto;
import com.mealmaker.babiyo.review.model.ReviewDto;
import com.mealmaker.babiyo.util.ImageDto;

@Repository
public class ReviewDaoImpl implements ReviewDao{
	String namespace = "com.mealmaker.babiyo.review.";
	
	private final SqlSessionTemplate sqlSession;
	private final SqlSessionTemplate sqlSessionSimple;
	
	@Autowired
	public ReviewDaoImpl(SqlSessionTemplate sqlSession, SqlSessionTemplate sqlSessionSimple) {
		super();
		this.sqlSession = sqlSession;
		this.sqlSessionSimple = sqlSessionSimple;
	}
	
	//DB에서 리뷰 목록 퍼오기
	@Override
	public List<ReviewDto> reviewList() {
		
		return sqlSession.selectList(namespace + "reviewList");
	}
	
	//DB에서 리뷰 목록-리뷰 수 퍼오기
	@Override
	public int reviewQuantity(int productNo) {
		
		return sqlSession.selectOne(namespace + "reviewQuantity", productNo);
	}
	
	//DB에서 리뷰 목록-리뷰 평점 퍼오기
	@Override
	public double reviewEvaluation(int productNo) {
		
		return sqlSession.selectOne(namespace + "reviewEvaluation", productNo);
	}
	
	//DB에 있는 리뷰 상세 퍼오기
	@Override
	public ReviewDto reviewDetail(int no) {
		
		return sqlSession.selectOne(namespace + "reviewDetail", no);
	}
	
	//파일 삽입
	@Override
	public void insertFile(Map<String, Object> map) {
	
		sqlSessionSimple.insert(namespace + "insertFile", map);
	}

	//파일 묶음 불러오기
	@Override
	public List<ImageDto> fileSelectList(int productNo) {
		
		return sqlSession.selectList(namespace + "fileSelectList", productNo);
	}

	//파일 저장된 이름 불러오기??
	@Override
	public List<String> fileSelectStoredFileName(int parentSeq) {

		return sqlSessionSimple.selectList(namespace + "fileSelectStoredFileName", parentSeq);
	}

	//파일 삭제
	@Override
	public void fileDelete(String storedName) {
		
		sqlSessionSimple.delete(namespace + "fileDelete", storedName);
	}

	@Override
	public List<ReviewDto> productReviewList(int productNo, String sort, int end) {
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("productNo", productNo);
		paraMap.put("sort", sort);
		paraMap.put("end", end);
		
		return sqlSession.selectList(namespace + "productReviewList", paraMap);
	}

	@Override
	public int memberReviewCount(String id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "reviewCount", id);
	}
	
	@Override
	public List<BuyProductDto> buyProductList(String memberId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "buyProductList", memberId);
	}
	
	@Override
	public void reviewWrite(ReviewDto reviewDto) {
		// TODO Auto-generated method stub
		sqlSessionSimple.insert(namespace + "reviewWrite", reviewDto);
	}

	@Override
	public List<ReviewDto> reviewCollectionList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "reviewCollectionList");
	}

	@Override
	public void reviewModify(ReviewDto reviewDto) {
		// TODO Auto-generated method stub
		sqlSession.update(namespace + "reviewModify", reviewDto);
	}

	@Override
	public void reviewDelete(int reviewNo) {
		// TODO Auto-generated method stub
		sqlSessionSimple.delete(namespace + "reviewDelete", reviewNo);
	}

	@Override
	public Integer reviewCheck(String memberId, int productNo) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("memberId", memberId);
		map.put("productNo", productNo);
		
		return sqlSession.selectOne(namespace + "reviewCheck", map);
	}


}
