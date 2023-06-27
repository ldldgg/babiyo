package com.mealmaker.babiyo.inquiry.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealmaker.babiyo.inquiry.dao.InquiryDao;
import com.mealmaker.babiyo.inquiry.model.InquiryCategoryDto;
import com.mealmaker.babiyo.inquiry.model.InquiryDto;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;


@Service
public class InquiryServiceImpl implements InquiryService{
	@Autowired
	InquiryDao inquiryDao;
	//회원
	
	@Override
	public Paging inquiryPaging(SearchOption searchOption, int curPage) {
		// TODO Auto-generated method stub
		int totalCount = inquiryDao.inquiryCount(searchOption);
		return new Paging(totalCount, curPage);
	}
	
	@Override
	public List<InquiryDto> inquiryList(SearchOption searchOption, Paging paging) {
		// TODO Auto-generated method stub
		return inquiryDao.inquiryList(searchOption, paging.getPageBegin());
	}
	
	@Override
	public int inquiryWrite(InquiryDto inquiryDto) {
		// TODO Auto-generated method stub
		
		return inquiryDao.inquiryWrite(inquiryDto);
	}

	@Override
	public InquiryDto inquirySelectOne(int no) {
		// TODO Auto-generated method stub
		
		return inquiryDao.inquirySelectOne(no);
	}

	@Override
	public int inquiryUpdateOne(InquiryDto inquiryDto) {
		// TODO Auto-generated method stub
		return inquiryDao.inquiryUpdateOne(inquiryDto);
	}
	
	@Override
	public void inquiryDeleteOne(int no) {
		// TODO Auto-generated method stub
		
		inquiryDao.inquiryDeleteOne(no);
	}
	
	//관리자

	@Override
	public Map<String, Object> adminInquirySelectOne(int no) {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		InquiryDto inquiryDto = inquiryDao.adminInquirySelectOne(no);
		
		resultMap.put("inquiryDto", inquiryDto);
		
		return resultMap;
	}
	
	@Override
	public int adminAnswer(InquiryDto inquiryDto) {
		// TODO Auto-generated method stub
		return inquiryDao.adminAnswer(inquiryDto);
	}

	
	@Override
	public List<InquiryCategoryDto> categoryCodeList() {
		// TODO Auto-generated method stub
		return inquiryDao.categoryCodeList();
	}

}
