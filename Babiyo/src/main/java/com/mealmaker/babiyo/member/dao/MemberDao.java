package com.mealmaker.babiyo.member.dao;

import java.util.List;

import com.mealmaker.babiyo.member.model.InterestDto;
import com.mealmaker.babiyo.member.model.MemberDto;
import com.mealmaker.babiyo.util.SearchOption;

public interface MemberDao {

	public MemberDto memberExist(String id, String password);
	public int idCheck(String id);
	public int emailCheck(String email);
	public int phoneCheck(String phone);
	public int nicknameCheck(String nickname);
	public int memberInsertOne(MemberDto memberDto);
	public void addInterest(List<InterestDto> interestDto);
	public void deleteInterest(List<Integer> deleteList, String memberId);
	public MemberDto memberInfo(String memberId);
	public void memberUpdateOne(MemberDto memberDto);
	public List<InterestDto> selectInterest(String memberId);
	public void memberDeleteOne(MemberDto memberDto);
	public MemberDto findId(String email);
	public MemberDto findPwd(String email);
	public void newPwd(MemberDto memberDto);
	public int memberCount(SearchOption searchOption);
	public List<MemberDto> memberList(int begin, SearchOption searchOption);
	
	
	
	
	
	
	
	
	
	
}
