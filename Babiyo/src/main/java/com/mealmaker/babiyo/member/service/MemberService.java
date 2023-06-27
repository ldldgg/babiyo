package com.mealmaker.babiyo.member.service;

import java.util.List;
import java.util.Map;

import com.mealmaker.babiyo.member.model.InterestDeleteListDto;
import com.mealmaker.babiyo.member.model.InterestDto;
import com.mealmaker.babiyo.member.model.MemberDto;
import com.mealmaker.babiyo.product.model.ProductCategoryDto;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

public interface MemberService {

	public MemberDto memberExist(String id, String password);
	public int idCheck(String id);
	public int emailCheck(String email);
	public int phoneCheck(String phone);
	public int nicknameCheck(String nickname);
	public void memberInsertOne(MemberDto memberDto);
	public List<ProductCategoryDto> categoryCodeList();
	void addInterest(InterestDto interestDto);
	public MemberDto memberInfo(String memberId);
	public void memberUpdateOne(MemberDto memberDto);
	public void UpdateInterest(String memberId, InterestDto interestDto, InterestDeleteListDto deleteListDto);
	public List<InterestDto> selectInterest(String memberId);
	public void memberDeleteOne(MemberDto memberDto);
	public MemberDto findId(String email);
	public MemberDto findPwd(String email);
	public void newPwd(MemberDto memberDto);
	//회원 문의 건수
	public Map<String, Integer> memberListCount(String id);
	//회원 목록
	public Paging memberPaging(SearchOption searchOption, int curPage);
	public List<MemberDto> memberList(Paging paging, SearchOption searchOption);
	
	
	
	
	
	

	

	
	
//	public Map<String, Object> memberSelectOne(int no);
//	
//	public int memberUpdateOne(MemberDto memberDto
//		, MultipartHttpServletRequest multipartHttpServletRequest
//		, int fileIdx) throws Exception;
//	
//	public void memberDeleteOne(int no);
//	public int memberSelectTotalCount(String searchOption, String keyword);
	
}
