package com.mealmaker.babiyo.review.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mealmaker.babiyo.member.model.MemberDto;
import com.mealmaker.babiyo.product.service.ProductService;
import com.mealmaker.babiyo.review.model.ReviewDto;
import com.mealmaker.babiyo.review.service.ReviewService;


@Controller
public class ReviewController {

	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	
	@Autowired
	private ReviewService reviewService;
	
	@Resource
	private ProductService productService;
	
	//회원-밀키트 상세-리뷰 등록 페이지로 이동
	@RequestMapping(value = "/product/{productNo}/review", method = RequestMethod.GET)
	public String writeReview(@PathVariable int productNo, Model model
			, @SessionAttribute(name="_memberDto_", required = false) MemberDto sessionMemberDto
			, HttpServletResponse response, HttpServletRequest request) {
		logger.info("writeReview productNo : {}", productNo);
		
		if(sessionMemberDto == null) {
			try {
				String uri = request.getRequestURI().substring(8);
				
				response.sendError(401, uri);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			Integer reviewNo = reviewService.reviewCheck(sessionMemberDto.getId(), productNo);
			
			if(reviewNo != null) {
				return "redirect:/reviews/" + reviewNo;
			}
		}
		Map<String, Object> productMap = productService.productDetail(productNo);
		
		model.addAttribute("productMap", productMap);
		
		return "review/reviewForm";
	}

	//회원-밀키트 상세-리뷰 등록(등록 후 리뷰 쓰기 버튼이 있던 회원-밀키트 상세로 돌아감.)
	@PostMapping(value = "/product/{productNo}/review")
	public String reviewRegistration(
		ReviewDto reviewDto, 
		Model model,
		MultipartHttpServletRequest mulRequest,	
		HttpSession session) {
		logger.info("ReviewController reviewRegistration 리뷰 등록 완료!" + reviewDto);
		
		MemberDto memberDto = (MemberDto) session.getAttribute("_memberDto_");
		reviewDto.setMemberId(memberDto.getId());
		
		int productNo = reviewDto.getProductNo();
		
		try {
			reviewService.reviewRegistration(reviewDto, mulRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/product/" + productNo;
	}
	
	@GetMapping(value="/product/{productNo}/review-check")
	@ResponseBody
	public Integer reviewCheck(@PathVariable int productNo, HttpSession session) {
		
		MemberDto memberDto = (MemberDto) session.getAttribute("_memberDto_");
		
		return reviewService.reviewCheck(memberDto.getId(), productNo);
	}
	
	@GetMapping(value="/product/{productNo}/reviews")
	@ResponseBody
	public List<ReviewDto> productReviewList(@PathVariable int productNo
			, @RequestParam(defaultValue = "new") String sort
			, int count, int reviewCurCount) {
		logger.debug("ajax: 상품 리뷰목록 불러오기");
		
		return reviewService.productReviewList(productNo, sort, count, reviewCurCount);
	}
	
	//헤더-리뷰 모음
	@RequestMapping(value = "/reviews", method = RequestMethod.GET)
	public String reviewCollection(Model model) {
		logger.info("ReviewController collection!");
		
		List<ReviewDto> reviewCollectionList = reviewService.reviewCollectionList();
		
		model.addAttribute("reviewCollectionList", reviewCollectionList);
		
		return "review/collection";
	}
	
	//헤더-리뷰 모음-리뷰 상세(회원)
	@GetMapping(value = "/reviews/{no}")
	public String reviewDetail(@PathVariable int no, HttpSession session, Model model) {
		logger.info("ReviewController reviewDetail! - {}", no);
		
		Map<String, Object> reveiewDto = reviewService.detail(no);

		model.addAttribute("review", reveiewDto);
			
		return "review/reviewDetail";
	}
	
	//오븐 39p 회원-리뷰 상세-리뷰 수정 페이지로 이동
	@GetMapping(value = "/reviews/{no}/modify")
	public String reviewModification(@PathVariable int no, HttpServletResponse response, HttpSession session, Model model) {
		logger.debug("ReviewController reviewModification!" + no);
		
		MemberDto sessionMember = (MemberDto) session.getAttribute("_memberDto_");
		Map<String, Object> reviewMap = reviewService.detail(no);
		ReviewDto reviewDto = (ReviewDto) reviewMap.get("reviewDto");
		if(!reviewDto.getMemberId().equals(sessionMember.getId())) {
			try {
				response.sendError(403);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		model.addAttribute("review", reviewMap);
		
		return "review/reviewForm";
	}
	
	//회원-리뷰 상세-리뷰 수정
	@PutMapping(value = "/reviews/{no}")
	@ResponseBody
	public void reviewModify (ReviewDto reviewDto) {
		logger.info("reviewModify {}" , reviewDto);
		//DB에서 리뷰가 수정됐는지 증명 
		reviewService.reviewModification(reviewDto);
	}
	
	//회원-리뷰 상세-리뷰 수정
	@PostMapping(value = "/reviews/{no}/image")
	@ResponseBody
	public void reviewImageUpdate (MultipartHttpServletRequest mulRequest, @PathVariable int no) {
		logger.info("ajax: reviewImageUpdate");
		try {
			reviewService.reviewImageModify(no, mulRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//회원-리뷰 상세-리뷰 삭제
	@DeleteMapping(value = "/reviews/{no}")
	@ResponseBody
	public String reviewDelete(@PathVariable int no, String backPage, Model model) {
		logger.info("ReviewController reviewDelete! " + no);
		
		try {
			reviewService.reviewDelete(no);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return backPage;
	}
}