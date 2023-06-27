package com.mealmaker.babiyo.cash.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mealmaker.babiyo.cash.service.CashService;
import com.mealmaker.babiyo.member.model.MemberDto;
import com.mealmaker.babiyo.member.service.MemberService;

// 어노테이션 드리븐
@Controller
public class CashController {

	private static final Logger logger 
		= LoggerFactory.getLogger(CashController.class);
	
	@Autowired
	private CashService cashService;
	
	@Resource
	private MemberService memberService;
	
	
	@RequestMapping(value = "/member/cash", method = RequestMethod.GET)
	public String memberCash(HttpSession session, Model model) {
		logger.info("Welcome MemberController memberCash! ");
		
		MemberDto sessionMemberDto = (MemberDto) session.getAttribute("_memberDto_");
		String id = sessionMemberDto.getId();
		
		MemberDto memberDto = memberService.memberInfo(id);
		session.setAttribute("_memberDto_", memberDto); // 캐쉬가 변경된 것을 반영
		
		return "cash/memberCash";
	}
	
	@RequestMapping(value = "/member/cash/charge", method = RequestMethod.GET)
	public String chargeCash(HttpSession session, Model model) {
		logger.info("Welcome CashController chargeCash! ");
		
		return "cash/chargeCash";
	}
	
	@RequestMapping(value = "/member/cash/charge", method = RequestMethod.POST)
	public String cashAdd(MemberDto memberDto, HttpSession session, Model model) {
		logger.info("Welcome  CashController chargeCashOne! " + memberDto.getCash());
		
		MemberDto memberDtoSession = cashService.cashChargeOne(memberDto);
		
		session.setAttribute("_memberDto_", memberDtoSession);
		
		return "redirect:/member/cash";
	}
	
}
