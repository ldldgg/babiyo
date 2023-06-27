package com.mealmaker.babiyo.inquiry.dao;

import java.util.List;

import com.mealmaker.babiyo.inquiry.model.InquiryCategoryDto;
import com.mealmaker.babiyo.inquiry.model.InquiryDto;
import com.mealmaker.babiyo.util.SearchOption;

public interface InquiryDao {
	
	public List<InquiryCategoryDto> categoryCodeList(); 
	
	//리스트
	public int inquiryCount(SearchOption searchOption);
	public List<InquiryDto> inquiryList(SearchOption searchOption, int begin);
	//작성
	public int inquiryWrite(InquiryDto inquiryDto);
	//상세
	public InquiryDto inquirySelectOne(int no);
	//수정
	public int inquiryUpdateOne(InquiryDto inquiryDto);
	//삭제
	public void inquiryDeleteOne(int no);
	
	//관리자
	//상세
	public InquiryDto adminInquirySelectOne(int no);
	//문의 답변  , 수정
	public int adminAnswer(InquiryDto inquiryDto);
	//관리홈 문의 카운트
	public int waitInquiryCount();
	//내정보 문의 카운트
	public int memberInquiryCount(String id);

}
