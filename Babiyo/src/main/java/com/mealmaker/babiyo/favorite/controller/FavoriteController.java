package com.mealmaker.babiyo.favorite.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mealmaker.babiyo.cart.controller.CartController;
import com.mealmaker.babiyo.favorite.model.FavoriteDto;
import com.mealmaker.babiyo.favorite.service.FavoriteService;
import com.mealmaker.babiyo.member.model.MemberDto;
import com.mealmaker.babiyo.util.Paging;

@Controller
@SessionAttributes("_memberDto_")
public class FavoriteController {

	private static final Logger logger 
	= LoggerFactory.getLogger(CartController.class);

	private final FavoriteService favoriteService;
	
	@Autowired
	public FavoriteController(FavoriteService favoriteService) {
		this.favoriteService = favoriteService;
	}

	@RequestMapping(value="/member/favorite", method = RequestMethod.GET)
	public String favoritList(@ModelAttribute("_memberDto_")MemberDto memberDto,
			@RequestParam(defaultValue = "1")int curPage,
			HttpSession session , Model model) {
		logger.info("즐겨찾기 목록 보기 {}", memberDto);
		String memberId = memberDto.getId();
		
		Paging paging = favoriteService.favoritePaging(memberId, curPage);
		List<Map<String, Object>> favoriteList = favoriteService.favoriteList(memberId, paging);
		
		model.addAttribute("favoriteList", favoriteList);
		model.addAttribute("paging", paging);
		
		return "favorite/favoriteList";
	}
	
	@RequestMapping(value="/member/favorite/remove", method = RequestMethod.POST)
	public String favoritDelete(@ModelAttribute("_memberDto_")MemberDto memberDto
			, FavoriteDto favoriteDto, Model model) {
		logger.info("즐겨찾기 제거 {}", favoriteDto);
		String memberId = memberDto.getId();

		favoriteService.favoriteDelete(favoriteDto, memberId);
		
		return "redirect:/member/favorite";
	}
	
	
	@GetMapping(value="/product/{productNo}/favorite")
	@ResponseBody
	public boolean favoritCheck(@ModelAttribute("_memberDto_")MemberDto memberDto, @PathVariable int productNo) {
		logger.info("ajax: favoriteCheck {}", productNo);
		// 즐겨찾기 되어있으면 true를 반환
		return favoriteService.favoriteCheck(memberDto.getId(), productNo);
	}
	
	@PutMapping(value="/product/{productNo}/favorite")
	@ResponseBody
	public boolean favoritBtn(@ModelAttribute("_memberDto_")MemberDto memberDto, @PathVariable int productNo) {
		logger.info("ajax: 즐겨찾기 추가또는 제거버튼 제품번호 : {}", productNo);
		
		// 삭제가 됬으면 false 추가됬으면 true
		return favoriteService.favoriteBtn(memberDto.getId(), productNo);
	}
	
}
