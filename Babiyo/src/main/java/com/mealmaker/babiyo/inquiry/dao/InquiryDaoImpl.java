package com.mealmaker.babiyo.inquiry.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mealmaker.babiyo.inquiry.model.InquiryCategoryDto;
import com.mealmaker.babiyo.inquiry.model.InquiryDto;
import com.mealmaker.babiyo.util.SearchOption;

@Repository
public class InquiryDaoImpl implements InquiryDao{
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	String namespace = "com.mealmaker.babiyo.inquiry.";
	//회원
	@Override
	public int inquiryCount(SearchOption searchOption) {
		// TODO Auto-generated method stub

		return sqlSession.selectOne(namespace + "inquiryCount", searchOption);
	}

	@Override
	public List<InquiryDto> inquiryList(SearchOption searchOption, int begin) {
		// TODO Auto-generated method stub
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("search", searchOption.getSearch());
		paraMap.put("stateCode", searchOption.getStateCode());
		paraMap.put("categoryCode", searchOption.getCategoryCode());
		paraMap.put("begin", begin);
		
		return sqlSession.selectList(namespace + "inquiryList", paraMap);
	}
	
	@Override
	public int inquiryWrite(InquiryDto inquiryDto) {
		return sqlSession.insert(namespace + "inquiryWrite", inquiryDto);
		
	}

	@Override
	public InquiryDto inquirySelectOne(int no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace +"inquirySelectOne", no);
	}

	@Override
	public int inquiryUpdateOne(InquiryDto inquiryDto) {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace + "inquiryUpdateOne", inquiryDto);
	}
	
	@Override
	public void inquiryDeleteOne(int no) {
		// TODO Auto-generated method stub
		 sqlSession.delete(namespace + "inquiryDeleteOne", no);
	}
	
	//관리자
	
	@Override
	public InquiryDto adminInquirySelectOne(int no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace +"adminInquirySelectOne", no);
	}

	@Override
	public int adminAnswer(InquiryDto inquiryDto) {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace + "adminAnswer", inquiryDto);
	}

	@Override
	public List<InquiryCategoryDto> categoryCodeList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "inquiryCategoeyList");
	}

	@Override
	public int waitInquiryCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "waitInquiryCount");
	}

	@Override
	public int memberInquiryCount(String id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "homeInquiryCount", id);
	}


}
