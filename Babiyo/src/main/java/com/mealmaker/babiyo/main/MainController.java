package com.mealmaker.babiyo.main;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.mealmaker.babiyo.member.model.MemberDto;
import com.mealmaker.babiyo.notice.model.NoticeDto;
import com.mealmaker.babiyo.notice.service.NoticeService;
import com.mealmaker.babiyo.product.service.ProductService;
import com.mealmaker.babiyo.util.ImageDto;

@Controller
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Resource
	private ProductService productService;
	
	@Resource
	private NoticeService noticeService;

	//오븐 14p, 메인
	@GetMapping("/")
	public String main(@SessionAttribute(name = "_memberDto_", required = false) MemberDto memberDto ,Model model) {
		logger.info("바비요 메인화면 출력");
		
		if(memberDto != null) {
			String memberId = memberDto.getId();
			
			List<Map<String, Object>> recommendProductList = 
					productService.recommendProductList(memberId);
			
			model.addAttribute("recommendProductList", recommendProductList);
		}
		
		List<Map<String, Object>> newProductList = productService.newProductList();
		
		NoticeDto mainLatestNotice = noticeService.mainLatestNotice();
		List<ImageDto> eventImgList	= noticeService.eventBannerImage();
		
		model.addAttribute("mainLatestNotice", mainLatestNotice);
		model.addAttribute("eventImgList", eventImgList);
		model.addAttribute("newProductList", newProductList);
			
		return "main";
	}
}