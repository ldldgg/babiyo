package com.mealmaker.babiyo.product.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mealmaker.babiyo.product.model.ProductCategoryDto;
import com.mealmaker.babiyo.product.model.ProductDto;
import com.mealmaker.babiyo.product.service.ProductService;
import com.mealmaker.babiyo.review.service.ReviewService;
import com.mealmaker.babiyo.util.ImageDto;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

@Controller
public class ProductController {

	private static final Logger logger 
		= LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ReviewService reviewService;
	
	//헤더-밀키트 카테고리
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String productCategory(@RequestParam(defaultValue = "1") int curPage
			,SearchOption searchOption, Model model) {
		logger.info("ProductController category! curPage: {}, searchOption: {}", curPage, searchOption);
		
		//카테고리 목록을 불러옴
		List<ProductCategoryDto> productCategory = productService.productCategory();
		
		int categoryCount = productService.categoryCount(searchOption);	
		
		Paging paging = new Paging(categoryCount, curPage, 8, 10);
		
		int begin = paging.getPageBegin();
		int end = paging.getPageEnd();
		
		//선택된 카테고리의 상품을 불러옴
		List<Map<String, Object>> categoryList = productService.categoryList(searchOption, begin, end);
		
		model.addAttribute("paging", paging);
		model.addAttribute("productCategory", productCategory);
		model.addAttribute("categoryList", categoryList);
		
		return "product/category";
	}
	
	//헤더-밀키트 카테고리-밀키트 상세(회원)
	@RequestMapping(value = "/product/{productNo}", method = RequestMethod.GET)
	public String productDetail(
			Model model
			, HttpServletResponse response
			, @PathVariable int productNo
		) {
		logger.info("ProductController productDetail! - productNo: {}", productNo);

		Map<String, Object> productMap = productService.productDetail(productNo);
		
		ProductDto productDto = (ProductDto) productMap.get("productDto");
		ImageDto productImg = (ImageDto) productMap.get("imgMap");
		
		Map<String, Object> reviewMap = reviewService.productReviewInfo(productNo);
		
		if(productDto == null) {
			try {
				response.sendError(410);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		model.addAttribute("productDto", productDto);
		model.addAttribute("reviewMap", reviewMap);
		model.addAttribute("productImg", productImg);
		
		return "product/productDetail";
	}	
	
	@GetMapping(value="/product/{productNo}/quantity")
	@ResponseBody
	public int productQuantityView(@PathVariable int productNo) {
		logger.info("ajax: productQuantityView productNo : {}" , productNo);
		
		return productService.stockView(productNo);
	};
	
	//관리자-밀키트 관리(목록)
	@RequestMapping(value = "/admin/product", method = RequestMethod.GET)
	public String adminProductList(@RequestParam(defaultValue = "1") int curPage
			,SearchOption searchOption, Model model) {
		logger.info("관리자 밀키트관리 페이지 curPage: {}, searchOption: {}", curPage, searchOption);
		
		Map<String, Object> map = productService.adminProductList(searchOption, curPage);
		
		@SuppressWarnings("unchecked")
		List<ProductDto> productList = (List<ProductDto>) map.get("productList");
		Paging paging = (Paging) map.get("paging");
		
		model.addAttribute("productList", productList);
		model.addAttribute("paging", paging);
		
		return "admin/product/adminProductList";
	}
	
	//관리자-밀키트 관리-밀키트 등록 페이지로 이동
	@RequestMapping(value = "/admin/product/add", method = RequestMethod.GET)
	public String productRegistration(Model model) {
		logger.info("ProductController productRegistration!");
		
		return "admin/product/adminProductRegistration";
	}

	//관리자-밀키트 관리-밀키트 등록
	@RequestMapping(value = "/admin/product/add", method = RequestMethod.POST)
	public String productRegistration(ProductDto productDto, 
		MultipartHttpServletRequest mulRequest, Model model) {
		logger.info("ProductController productRegistration 밀키트 등록 완료!" + productDto);
		
		try {
			productService.productRegistration(productDto, mulRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/product";
	}	
	
	//관리자-밀키트 관리-밀키트 상세
	@RequestMapping(value = "/admin/product/{no}", method = RequestMethod.GET)
	public String productAdminDetail(@PathVariable int no, SearchOption searchOption, int curPage, Model model) {
		logger.info("ProductController productAdminDetail! - {}", no);
		
		Map<String, Object> map = productService.productAdminDetail(no);
		
		ProductDto productDto = (ProductDto) map.get("productDto");
		
		ImageDto productImg = (ImageDto) map.get("imgMap");
		
		model.addAttribute("productDto", productDto);
		model.addAttribute("productImg", productImg);
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("curPage", curPage);
		
		return "admin/product/adminProductDetail";
	}
	
	//관리자-밀키트 관리-밀키트 상세-밀키트 수정으로 이동
	@RequestMapping(value = "/admin/product/{no}/modify", method = RequestMethod.GET)
	public String productModification(@PathVariable int no, SearchOption searchOption, int curPage, Model model) {
		logger.debug("ProductController productModification!" + no);
		
		Map<String, Object> map = productService.productAdminDetail(no);
		
		ProductDto productDto = (ProductDto)map.get("productDto");
		
		ImageDto productImg = (ImageDto) map.get("imgMap");
		
		model.addAttribute("productDto", productDto);
		model.addAttribute("productImg", productImg);
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("curPage", curPage);
		
		return "admin/product/adminProductModification";
	}

	//관리자-밀키트 관리-밀키트 상세-밀키트 수정
	@RequestMapping(value = "/admin/product/{no}/modify", method = RequestMethod.POST)
	public String productModificationCtr(ProductDto productDto, SearchOption searchOption,
			int curPage, HttpSession session, Model model, RedirectAttributes redirect,
			MultipartHttpServletRequest mulRequest) {
		logger.info("ProductController productModificationCtr {}" , productDto);
		
		try {
			productService.productModification(productDto, mulRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int productNo = productDto.getNo();
		
		//DB에서 밀키트 정보가 수정됐는지 증명 
		
		redirect.addAttribute("curPage", curPage);
		redirect.addAttribute("search", searchOption.getSearch());
		redirect.addAttribute("searchOption", searchOption.getSearchOption());
		redirect.addAttribute("sort", searchOption.getSort());
		
		return "redirect:/admin/product/" + productNo;
	}
	
	//관리자-밀키트 관리-밀키트 상세-밀키트 삭제
	@RequestMapping(value = "/admin/product/{no}/remove", method = RequestMethod.POST)
	public String productDelete(@PathVariable int no,SearchOption searchOption,
			int curPage, Model model, RedirectAttributes redirect) {
		logger.info("ProductController productDelete! " + no);
		
		try {
			productService.productDelete(no);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		redirect.addAttribute("curPage", curPage);
		redirect.addAttribute("search", searchOption.getSearch());
		redirect.addAttribute("searchOption", searchOption.getSearchOption());
		redirect.addAttribute("sort", searchOption.getSort());
		
		return "redirect:/admin/product";
	}
	

	
}