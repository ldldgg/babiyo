
package com.mealmaker.babiyo.member.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mealmaker.babiyo.inquiry.dao.InquiryDao;
import com.mealmaker.babiyo.member.dao.MemberDao;
import com.mealmaker.babiyo.member.model.InterestDeleteListDto;
import com.mealmaker.babiyo.member.model.InterestDto;
import com.mealmaker.babiyo.member.model.MemberDto;
import com.mealmaker.babiyo.order.dao.OrderDao;
import com.mealmaker.babiyo.product.dao.ProductDao;
import com.mealmaker.babiyo.product.model.ProductCategoryDto;
import com.mealmaker.babiyo.review.dao.ReviewDao;
import com.mealmaker.babiyo.util.EncodePassword;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

@Service
public class MemberServiceImpl implements MemberService{
	private static final Logger log = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	private MemberDao memberDao;
	private OrderDao orderDao;
	private InquiryDao inquiryDao;
	private ReviewDao reviewDao;
	private ProductDao productDao;
	
	@Autowired
	public MemberServiceImpl(MemberDao memberDao, OrderDao orderDao, InquiryDao inquiryDao
			, ReviewDao reviewDao, ProductDao productDao) {
		this.memberDao = memberDao;
		this.orderDao = orderDao;
		this.inquiryDao = inquiryDao;
		this.reviewDao = reviewDao;
		this.productDao = productDao;
	}

	public MemberDto memberExist(String id, String password) {
		EncodePassword ep = new EncodePassword();
		String encodingPassword = "";
		try {
			encodingPassword = ep.encrypt(password); // SHA-256 암호화
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return memberDao.memberExist(id, encodingPassword);
	}
	
	@Override
	public int idCheck(String id) {
		int cnt = memberDao.idCheck(id);
		return cnt;
	}
	
	
	@Override
	public int emailCheck(String email) {
		int cnt = memberDao.emailCheck(email);
		return cnt;
	}
	
	@Override
	public int phoneCheck(String phone) {
		int cnt = memberDao.phoneCheck(phone);
		return cnt;
	}
	
	@Override
	public int nicknameCheck(String nickname) {
		int cnt = memberDao.nicknameCheck(nickname);
		return cnt;
	}

	@Override
	public void memberInsertOne(MemberDto memberDto){
		String password = memberDto.getPassword();
		EncodePassword ep = new EncodePassword();
		try {
			String encodingPassword = ep.encrypt(password); // SHA-256 암호화
			memberDto.setPassword(encodingPassword);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		memberDao.memberInsertOne(memberDto);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addInterest(InterestDto interestDto) {
		List<InterestDto> list = interestDto.getInterestList();
		
		if(list != null) {
			String memberId = interestDto.getMemberId();
		
			for (InterestDto interest : list) {
				interest.setMemberId(memberId);
			}
			
			memberDao.addInterest(list);
		}
	}
	
	@Override
	public List<ProductCategoryDto> categoryCodeList() {
		return productDao.productCategory();
	}

	@Override
	public MemberDto memberInfo(String memberId) {
		return memberDao.memberInfo(memberId);
	}

	@Override
	public void memberUpdateOne(MemberDto memberDto) {
		if(!memberDto.getPassword().isEmpty()) {
			String password = memberDto.getPassword();
			EncodePassword ep = new EncodePassword();
			
			try {
				String encodingPassword = ep.encrypt(password); // SHA-256 암호화
				memberDto.setPassword(encodingPassword);
				
			} catch (NoSuchAlgorithmException e) {
				
				e.printStackTrace();
			}
		}
		memberDao.memberUpdateOne(memberDto);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void UpdateInterest(String memberId, InterestDto interestDto
				, InterestDeleteListDto deleteListDto){
		List<InterestDto> addList = interestDto.getInterestList();
		List<InterestDto> subList = deleteListDto.getDeleteList();
		
		if(addList != null) {
			for (InterestDto list : addList) {
				list.setMemberId(memberId);
			}
			memberDao.addInterest(addList);
			log.debug("추가후");
		}
		
		List<Integer> deleteList = new ArrayList<Integer>();

		if(subList != null) {
			for (InterestDto list : subList) {
				deleteList.add(list.getProductCode());
			}
			memberDao.deleteInterest(deleteList, memberId);
			log.debug("삭제후");
		}
	}

	@Override
	public List<InterestDto> selectInterest(String memberId) {
		return memberDao.selectInterest(memberId);
	}

	@Override
	public void memberDeleteOne(MemberDto memberDto) {
		memberDao.memberDeleteOne(memberDto);
	}

	@Override
	public MemberDto findId(String email) {
		return memberDao.findId(email);
	}
	
	@Override
	public MemberDto findPwd(String email) {
		return memberDao.findPwd(email);
	}

	@Override
	public void newPwd(MemberDto memberDto) {
		EncodePassword ep = new EncodePassword();
		String pwd = memberDto.getPassword();
		try {
			String encodePwd = ep.encrypt(pwd);
			memberDto.setPassword(encodePwd);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		memberDao.newPwd(memberDto);
	}

	@Override
	public Map<String, Integer> memberListCount(String id) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		int orderCount = orderDao.memberTotalOrder(id);
		int inquiryCount = inquiryDao.memberInquiryCount(id);
		int reviewCount = reviewDao.memberReviewCount(id);
		
		map.put("orderCount", orderCount);
		map.put("inquiryCount", inquiryCount);
		map.put("reviewCount", reviewCount);
		
		return map;
	}

	@Override
	public Paging memberPaging(SearchOption searchOption, int curPage) {
		int totalCount = memberDao.memberCount(searchOption);
		
		return new Paging(totalCount, curPage);
	}
	
	@Override
	public List<MemberDto> memberList(Paging paging, SearchOption searchOption) {
		return memberDao.memberList(paging.getPageBegin(), searchOption);
	}
}
