package com.mealmaker.babiyo.member.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mealmaker.babiyo.member.model.InterestDeleteListDto;
import com.mealmaker.babiyo.member.model.InterestDto;
import com.mealmaker.babiyo.member.model.MemberDto;
import com.mealmaker.babiyo.member.service.MemberService;
import com.mealmaker.babiyo.product.model.ProductCategoryDto;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

// 어노테이션 드리븐
@Controller
public class MemberController {

	private static final Logger logger 
		= LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/auth/login", method = RequestMethod.GET)
	public String login(HttpSession session, Model model, String uri) {
		logger.info("로그인 화면");
		
		model.addAttribute("uri", uri);
		return "auth/loginForm";
	}
	//로그인
	@RequestMapping(value = "/auth/login", method = RequestMethod.POST)
	public String loginCtr(String id, String password, String uri
			, HttpSession session, Model model) {
		logger.info("로그인 시도 id: {} , uri: {}", id, uri);
		
		MemberDto memberDto = memberService.memberExist(id, password);
			
		if(memberDto == null) {
			return "auth/loginFail";
		}
		
		session.setAttribute("_memberDto_", memberDto);
		if(memberDto.getGrade() == 1){
			return "redirect:/admin/home";
		}
		
		if(uri != null) {
			return "redirect:/" + uri;
		}else {
			return "redirect:/";
		}
		
	}
	
	@RequestMapping(value = "/auth/findId", method = RequestMethod.GET)
	public String findId(HttpSession session, Model model) {
		logger.info("아이디 찾기");
		
		return "auth/memberFindId";
	}
	//아이디 찾기
	@RequestMapping(value = "/auth/findId", method = RequestMethod.POST)
	public String findId(String email, HttpSession session, Model model) {
		logger.info("아이디찾기 시도 email: {}", email);
		
		MemberDto memberDto = memberService.findId(email);
		
		model.addAttribute("memberId", memberDto.getId());
		
		return "auth/completeFindId";
	}
	
	@RequestMapping(value = "/auth/findPwd", method = RequestMethod.GET)
	public String findPwd(HttpSession session, Model model) {
		logger.info("비밀번호 찾기");
		
		return "auth/memberFindPwd";
	}
	
	@RequestMapping(value = "/auth/findPwd", method = RequestMethod.POST)
	public String findPwd(String email, HttpSession session, Model model) {
		logger.info("비밀번호 찾기 시도 email: {}", email);
		
		MemberDto memberDto = memberService.findPwd(email);
		
		model.addAttribute("memberId", memberDto.getId());
			
		return "auth/memberNewPwd";
	}
	
	@RequestMapping(value = "/auth/newPwd", method = RequestMethod.POST)
	public String newPwd(MemberDto memberDto, HttpSession session, Model model) {
		logger.info("새 비밀번호 설정 {}", memberDto);
		
		memberService.newPwd(memberDto);
		
		return "redirect:/auth/completeNewPwd";
	}
	
	@RequestMapping(value = "/auth/completeNewPwd", method = RequestMethod.GET)
	public String completePwd(HttpSession session, Model model) {
		logger.info("비밀번호 설정 완료");
		
		return "auth/completeNewPwd";
	}
	
	@RequestMapping(value = "/auth/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, Model model) {
		logger.info("회원 로그아웃");
		MemberDto memberDto = (MemberDto)session.getAttribute("_memberDto_");
		
		if(memberDto != null) {
			logger.debug("ID: {}", memberDto.getId());
			session.invalidate();
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/auth/member/add", method = RequestMethod.GET)
	public String memberAdd(Model model) {
		logger.debug("회원가입");
		
		return "member/memberJoin";
	}
	
	@PostMapping("/auth/member/idCheckCtr")
	@ResponseBody
	public int idCheck(@RequestParam("id") String id) {
		int cnt = memberService.idCheck(id);
		
		return cnt;
	}
	
	@PostMapping("/auth/member/emailCheckCtr")
	@ResponseBody
	public int emailCheck(@RequestParam("email") String email) {
		int cnt = memberService.emailCheck(email);
		
		return cnt;
	}
	
	@PostMapping("/auth/member/phoneCheckCtr")
	@ResponseBody
	public int phoneCheck(@RequestParam("phone") String phone) {
		int cnt = memberService.phoneCheck(phone);
		return cnt;
	}
	
	@PostMapping("/auth/member/nicknameCheckCtr")
	@ResponseBody
	public int nicknameCheck(@RequestParam("nickname") String nickname) {
		int cnt = memberService.nicknameCheck(nickname);
		return cnt;
	}
	
	@RequestMapping(value = "/auth/member/add", method = RequestMethod.POST)
	public String memberAdd(MemberDto memberDto, Model model, RedirectAttributes redirect) {
		logger.info("회원가입 시도{}", memberDto);
		memberService.memberInsertOne(memberDto);
		
		String memberId = memberDto.getId();
		
		redirect.addFlashAttribute("memberId", memberId);
		
		return "redirect:/auth/member/interest/add";
	}
	
	@RequestMapping(value = "/auth/member/interest/add", method = RequestMethod.GET)
	public String memberAddInterest(Model model) {
		logger.info("관심사 등록 ");
		
		List<ProductCategoryDto> categoryCodeList = memberService.categoryCodeList();
		
		model.addAttribute("categoryCodeList", categoryCodeList);
		
		return "member/memberInterest";
	}
	
	
	@RequestMapping(value = "/auth/member/interest/add", method = RequestMethod.POST)
	public String addInterest(InterestDto interestDto, HttpSession session
			, Model model) {
		logger.info("관심사 등록 시도 {}", interestDto);
		
		String memberId = interestDto.getMemberId();
		
		if(memberId != null) {
			memberService.addInterest(interestDto);
		}
		
		return "redirect:/auth/member/addComplete";
	}
	
	@RequestMapping(value = "/auth/member/addComplete", method = RequestMethod.GET)
	public String addInterest(HttpSession session, Model model) {
		logger.info("회원가입 완료");
		
		return "member/memberJoinComplete";
	}
	
	// 회원정보
	@RequestMapping(value = "/member/home", method = RequestMethod.GET)
	public String memberInfo(HttpSession session, Model model) {
		logger.info("Welcome MemberController memberInfo! ");
		
		MemberDto sessionMemberDto = (MemberDto) session.getAttribute("_memberDto_");
		String id = sessionMemberDto.getId();
		
		MemberDto memberDto = memberService.memberInfo(id);
		
		Map<String, Integer> countMap = memberService.memberListCount(id);
		
		model.addAttribute("countMap", countMap);
		session.setAttribute("_memberDto_", memberDto); // 캐쉬가 변경된 것을 반영
		
		return "/member/memberInfo";
	}
	
	@RequestMapping(value = "/member/checkInfo", method = RequestMethod.GET)
	public String checkInfo(HttpSession session, Model model) {
		logger.info("비밀번호 확인");
		
		return "member/checkInfo";
	}
	
	
	@ResponseBody
	@PostMapping(value="/member/ajax/passwordCheck")
	public boolean infoPwdCheck(HttpSession session, @RequestParam String pwd) {
		MemberDto memberDto = (MemberDto)session.getAttribute("_memberDto_");
		String memberId = memberDto.getId();
		
		MemberDto memberDtoCheck = memberService.memberExist(memberId, pwd);
		
		boolean pwdCheck =  memberDtoCheck != null;
		
		return pwdCheck;
	}
	
	
	@RequestMapping(value = "/member/modify", method = RequestMethod.GET)
	public String memberUpdate(HttpSession session, Model model) {
		logger.info("회원정보 수정화면");
		
		MemberDto memberDto = (MemberDto)session.getAttribute("_memberDto_");
		String memberId = memberDto.getId();
		
		List<ProductCategoryDto> categoryCodeList = memberService.categoryCodeList();
		List<InterestDto> interestList = memberService.selectInterest(memberId);
		
		model.addAttribute("categoryCodeList", categoryCodeList);
		model.addAttribute("interestList", interestList);
		
		return "member/memberUpdate";
	}
	
	@GetMapping("/member/interestCheck")
	@ResponseBody
	public List<InterestDto> check(HttpSession session){
		logger.info("관심사 확인 ajax");
		
		MemberDto memberDto = (MemberDto)session.getAttribute("_memberDto_");
		String memberId = memberDto.getId();
		
		List<InterestDto> interestList = memberService.selectInterest(memberId);
		
		return interestList;
	}
	
	@RequestMapping(value = "/member/modify", method = RequestMethod.POST)
	public String memberUpdateOne(MemberDto memberDto,
				InterestDeleteListDto deleteListDto,
				InterestDto interestDto,
				HttpSession session) {
		logger.info("회원정보 업데이트 {}", memberDto);
		
		memberService.memberUpdateOne(memberDto);
		
		String memberId = memberDto.getId();
		
		memberService.UpdateInterest(memberId, interestDto, deleteListDto);
	
		session.setAttribute("_memberDto_", memberService.memberInfo(memberId));
		
		return "redirect:/member/home";
	}
	
	@RequestMapping(value = "/member/remove", method = RequestMethod.GET)
	public String memberDelete(HttpSession session, Model model) {
		logger.info("회원탈퇴");
		
		return "/member/checkOut";
	}
	
	// 회원탈퇴
	@RequestMapping(value = "/member/remove", method = RequestMethod.POST)
	public String memberDelete(MemberDto memberDto, Model model, HttpSession session) {
		logger.info("Welcome memberDelete! {}", memberDto);
		memberService.memberDeleteOne(memberDto);
		
		if(!memberDto.getId().equals("admin")) {
			session.invalidate();
		}
		
		return "redirect:/";
	}
	
	@GetMapping(value = "/admin/member")
	public String adminMemberList(@RequestParam(defaultValue = "1") int curPage
			, SearchOption searchOption
			, HttpSession session, Model model) {
		logger.info("Welcome NoticeController Memberlist!");
		
		Paging paging = memberService.memberPaging(searchOption, curPage);
		List<MemberDto> memberList = memberService.memberList(paging, searchOption);
		
		model.addAttribute("memberList", memberList);
		model.addAttribute("paging", paging);

		return "/admin/member/memberList";
	}
	
}
