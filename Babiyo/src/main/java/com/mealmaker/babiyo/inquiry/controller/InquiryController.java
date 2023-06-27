package com.mealmaker.babiyo.inquiry.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mealmaker.babiyo.inquiry.model.InquiryCategoryDto;
import com.mealmaker.babiyo.inquiry.model.InquiryDto;
import com.mealmaker.babiyo.inquiry.service.InquiryService;
import com.mealmaker.babiyo.member.dao.MemberDao;
import com.mealmaker.babiyo.member.model.MemberDto;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

// 어노테이션 드리븐
@Controller
public class InquiryController {

	private static final Logger logger = LoggerFactory.getLogger(InquiryController.class);

	@Autowired
	private InquiryService inquiryService;
	
	@Resource
	private MemberDao memberDao;
	//회원
	// 문의 게시글 목록
	@RequestMapping(value = "/member/inquiry", method = RequestMethod.GET)
	public String memberInquiry(@RequestParam(defaultValue = "1") int curPage
			, @ModelAttribute SearchOption searchOption
			, @SessionAttribute(name="_memberDto_") MemberDto memberDto
			, Model model) {
		logger.info("memberInquiry {}", memberDto);

		searchOption.setSearch(memberDto.getId());
		
		Paging paging = inquiryService.inquiryPaging(searchOption, curPage);
		List<InquiryDto> inquiryList = inquiryService.inquiryList(searchOption, paging);
		
		List<InquiryCategoryDto> categoryCodeList = inquiryService.categoryCodeList();
		
		model.addAttribute("inquiryList", inquiryList);
		model.addAttribute("categoryCodeList", categoryCodeList);
		model.addAttribute("paging", paging);

		return "inquiry/memberInquiryListView";
	}

	// 문의 게시글 작성 화면으로
	@RequestMapping(value = "/member/inquiry/write", method = RequestMethod.GET)
	public String memberWrite(HttpSession session, Model model) {
		logger.info("Welcome InquiryMemberController write! ");

		return "inquiry/memberInquiryWrite";
	}
	// 문의 게시글 작성
	@RequestMapping(value = "/member/inquiry/write", method = RequestMethod.POST)
	public String memberWriteCtr(InquiryDto inquiryDto, MemberDto memberDto, Model model) {
		logger.info("Welcome InquiryMemberController memberWrite 신규 문의 작성! ");

		inquiryService.inquiryWrite(inquiryDto);

		return "redirect:/member/inquiry";
	}
	
	// 문의 게시글 상세
	@RequestMapping(value = "/member/inquiry/{no}", method = RequestMethod.GET)
	public String memberDetail(@PathVariable int no, HttpSession session, Model model
			, HttpServletResponse response) {
		logger.info("Welcome InquiryMemberController detail! ");
		
		MemberDto memberDto = (MemberDto) session.getAttribute("_memberDto_");
		InquiryDto inquiryDto = inquiryService.inquirySelectOne(no);
		
		if (!memberDto.getId().equals(inquiryDto.getMemberId())) {
			try {
				response.sendError(403, "다른 회원의 문의는 확인할 수 없습니다.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		model.addAttribute("inquiryDto", inquiryDto); 
		 
		return "inquiry/memberInquiryDetail";
	}

	// 문의 게시글 수정 화면으로
	@RequestMapping(value = "/member/inquiry/{no}/modify", method = RequestMethod.GET)
	public String memberUpdate(@PathVariable int no, HttpSession session, Model model
			, HttpServletResponse response) {
		logger.info("Welcome InquiryMemberController update! ");
		
		MemberDto memberDto = (MemberDto) session.getAttribute("_memberDto_");
		InquiryDto inquiryDto = inquiryService.inquirySelectOne(no);
		
		if (!memberDto.getId().equals(inquiryDto.getMemberId())) {
			try {
				response.sendError(403, "다른 회원의 문의는 확인할 수 없습니다.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		model.addAttribute("inquiryDto", inquiryDto);
		
		return "inquiry/memberInquiryUpdate";
	}
	
	// 문의 게시글 수정
	@RequestMapping(value = "/member/inquiry/{no}/modify", method = RequestMethod.POST)
	public String memberUpdateCtr(InquiryDto inquiryDto, HttpSession session, Model model) {
		logger.info("Welcome InquiryMemberController updateCtr! {}", inquiryDto);
		
		inquiryService.inquiryUpdateOne(inquiryDto);
		
		int no = inquiryDto.getNo();
		
		return "redirect:/member/inquiry/" + no;
	}
	
	
	// 문의 게시글 삭제
	@RequestMapping(value = "/member/inquiry/{no}/remove", method = RequestMethod.POST)
	public String memberDelete(@PathVariable int no, HttpSession session, Model model) {
		logger.info("Welcome InquiryMemberController delete! ");
		
		inquiryService.inquiryDeleteOne(no);
		
		return "redirect:/member/inquiry";
	}
	
	// 관리자
	// 문의 게시글 목록
	@RequestMapping(value = "/admin/inquiry", method = RequestMethod.GET)
	public String adminInquiry(@RequestParam(defaultValue = "1") int curPage
			, @ModelAttribute SearchOption searchOption
			, Model model) {
		logger.info("adminInquiry");
		
		Paging paging = inquiryService.inquiryPaging(searchOption, curPage);
		List<InquiryDto> inquiryList = inquiryService.inquiryList(searchOption, paging);
		
		//분류 리스트
		List<InquiryCategoryDto> categoryCodeList = inquiryService.categoryCodeList();
		
		model.addAttribute("inquiryList", inquiryList);
		model.addAttribute("categoryCodeList", categoryCodeList);
		model.addAttribute("paging", paging);

		return "admin/inquiry/adminInquiryListView";
	}
	
	
	//문의 답변 화면 
	@RequestMapping(value = "/admin/inquiry/{no}", method = RequestMethod.GET)
	public String adminAnswer(@PathVariable int no, HttpSession session, Model model) {
		logger.info("Welcome adminInquiryController write! ");

		Map<String, Object> map = inquiryService.adminInquirySelectOne(no);
		
		InquiryDto inquiryDto = (InquiryDto) map.get("inquiryDto");
		
		model.addAttribute("inquiryDto", inquiryDto); 
		
		
		return "admin/inquiry/adminInquiryAnswer";
	}
	
	// 문의 답변, 수정
	@RequestMapping(value = "/admin/inquiry/{no}/answer", method = RequestMethod.POST)
	public String adminAnswerCtr(InquiryDto inquiryDto, Model model) {
		logger.info("Welcome adminInquiryController memberWrite 문의 답변! ");
		
		inquiryService.adminAnswer(inquiryDto);
		
		int no = inquiryDto.getNo();

		return "redirect:/admin/inquiry/" + no;
	}
	
}
