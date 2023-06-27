package com.mealmaker.babiyo.notice.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mealmaker.babiyo.notice.model.NoticeDto;
import com.mealmaker.babiyo.notice.service.NoticeService;
import com.mealmaker.babiyo.util.ImageDto;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

// 어노테이션 드리븐
@Controller
public class NoticeController {

	private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);

	@Autowired
	private NoticeService noticeService;
	
	//관리자
	//공지 게시판
	@RequestMapping(value = "/admin/notice", method = RequestMethod.GET)
	public String adminNoticeList(@RequestParam(defaultValue = "1") int curPage
			, @ModelAttribute SearchOption searchOption
			, HttpSession session, Model model) {
		logger.info("Welcome NoticeController list! ");

		Paging paging = noticeService.noticePaging(searchOption, curPage);
		List<NoticeDto> noticeList = noticeService.noticeList(searchOption, paging);
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("paging", paging);

		return "admin/notice/adminNoticeList";
	}
	
	//공지 게시글 작성 화면으로
	@RequestMapping(value = "/admin/notice/write", method = RequestMethod.GET)
	public String noticeWrite(HttpSession session, Model model) {
		logger.info("Welcome NoticeController write! ");
		
		return "admin/notice/adminNoticeWrite";
	}
	
	//공지 게시글 작성
	@RequestMapping(value = "/admin/notice/write", method = RequestMethod.POST)
	public String noticeWriteCtr(MultipartHttpServletRequest mulRequest
			, NoticeDto noticeDto, Model model) throws Exception {
		logger.info("Welcome NoticeController WriteCtr 신규 공지 작성! ");

		noticeService.noticeWrite(noticeDto, mulRequest);
	
		return "redirect:/admin/notice";
	}
	

	//공지 수정화면으로
	@RequestMapping(value = "/admin/notice/{no}", method = RequestMethod.GET)
	public String noticeUpdate(@PathVariable int no, Model model) {
		logger.info("Welcome NoticeController update! ");

		Map<String, Object> map = noticeService.noticeSelectOne(no);
		
		NoticeDto noticeDto = (NoticeDto) map.get("noticeDto");
		
		model.addAttribute("noticeDto", noticeDto);
		ImageDto noticeImg = (ImageDto) map.get("imgMap");
		
		model.addAttribute("noticeDto", noticeDto);
		model.addAttribute("noticeImg", noticeImg); 
		
		return "admin/notice/adminNoticeUpate";
	}
	
	
	//공지 수정
	@RequestMapping(value = "/admin/notice/{noticeNo}/modify", method = RequestMethod.POST)
	public String noticeUpdateCtr(HttpSession session, NoticeDto noticeDto
			, @RequestParam(defaultValue = "-1") int imgNo
			, MultipartHttpServletRequest mulRequest
			, Model model) {
		logger.info("Welcome NoticeController updateCtr!");
		
		try {
			noticeService.noticeUpdate(imgNo, noticeDto, mulRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int noticeNo = noticeDto.getNo();
	
		return "redirect:/admin/notice/" + noticeNo;
	}
	
	//공지 게시글 삭제
	@RequestMapping(value = "/admin/notice/{no}/remove", method = RequestMethod.POST)
	public String noticeDelete(@PathVariable int no, Model model) {
		logger.info("Welcome NoticeControllerdelete delete! ");
		
		try {
			noticeService.noticeDeleteOne(no);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:/admin/notice";
	}
	
	
	
	//일반 공지 게시판
	@RequestMapping(value = "/notice", method = RequestMethod.GET)
	public String noticeList(@RequestParam(defaultValue = "1") int curPage
			, @ModelAttribute SearchOption searchOption
			, HttpSession session, Model model) {
		logger.info("Welcome NoticeController list! ");
		
		Paging paging = noticeService.noticePaging(searchOption, curPage);
		List<NoticeDto> noticeList = noticeService.noticeList(searchOption, paging);
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("paging", paging);

		return "notice/noticeListView";
	}
	
	//공지 상세 
	@RequestMapping(value = "/notice/{no}", method = RequestMethod.GET)
	public String noticeDetail(@PathVariable int no, HttpSession session, Model model) {
		logger.info("Welcome NoticeController detail! ");

		noticeService.noticeHitPlus(no);	
		Map<String, Object> map = noticeService.noticeSelectOne(no);
		
		NoticeDto notice = (NoticeDto) map.get("noticeDto");
		ImageDto noticeImg = (ImageDto) map.get("imgMap");
		
		model.addAttribute("noticeDto", notice);
		model.addAttribute("noticeImg", noticeImg);
		 
		return "notice/noticeDetail";
	}
	
	
}	