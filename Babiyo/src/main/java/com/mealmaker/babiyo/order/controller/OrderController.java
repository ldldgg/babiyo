package com.mealmaker.babiyo.order.controller;

import java.io.IOException;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mealmaker.babiyo.cart.service.CartService;
import com.mealmaker.babiyo.member.model.MemberDto;
import com.mealmaker.babiyo.order.model.OrderDetailDto;
import com.mealmaker.babiyo.order.model.OrderDto;
import com.mealmaker.babiyo.order.model.OrderStateDto;
import com.mealmaker.babiyo.order.service.OrderService;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

// 어노테이션 드리븐
@Controller
@SessionAttributes("_memberDto_")
public class OrderController {

	private static final Logger logger 
		= LoggerFactory.getLogger(OrderController.class);
	
	private final OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	@Resource
	private CartService cartService;
	
	@RequestMapping(value = "/member/order", method = RequestMethod.POST)
	public String order(OrderDetailDto orderDetailDto, Model model) {
		logger.info("주문화면 {}", orderDetailDto.getOrderDetailList());

		List<OrderDetailDto> orderDetailList = orderDetailDto.getOrderDetailList();
		
		model.addAttribute("orderDetailList", orderDetailList);
		
		return "order/orderForm";
	}
	
	@RequestMapping(value = "/member/order/{memberId}", method = RequestMethod.POST)
	public String orderCtr(@ModelAttribute("_memberDto_") MemberDto memberDto,
			@PathVariable String memberId
			,OrderDto orderDto, OrderDetailDto orderDetailDto, Model model) {
		logger.info("orderCtr 주문 전송" + orderDto + orderDetailDto);
		
		orderDto.setMemberId(memberId);
		
		int orderNo;
		
		try {
			orderNo = orderService.order(orderDto, orderDetailDto);
			
			if(orderNo == -1) {
				return "redirect:/member/order/fail";
			}
			
			int balance = memberDto.getCash() - orderDto.getTotalAmount();
			memberDto.setCash(balance);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "redirect:/member/order/fail";
		}
		
		return "redirect:/member/order/complete";
	}
	
	@RequestMapping(value = "/member/order/complete", method = RequestMethod.GET)
	public String orderComplete(@ModelAttribute("_memberDto_") MemberDto memberDto
			,HttpSession session, Model model) {
		logger.info("orderComplete");
		
		String memberId = memberDto.getId();
		OrderDto orderDto = orderService.lastOrder(memberId);
		
		model.addAttribute("orderDto", orderDto);
		
		return "order/orderComplete";
	}
	
	@RequestMapping(value = "/member/order/fail", method = RequestMethod.GET)
	public String orderFail(HttpSession session, Model model) {
		logger.info("orederFail");
		
		return "order/orderFail";
	}
	
	@RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
	public String adminOrderList(@RequestParam(defaultValue = "1") int curPage
			,SearchOption searchOption, HttpSession session, Model model) {
		logger.info("관리자 주문목록{}", searchOption);
		
		Map<String, Object> map = orderService.adminOrderList(searchOption, curPage);
		
		@SuppressWarnings("unchecked")
		List<OrderDto> orderList = (List<OrderDto>) map.get("orderList");
		
		Paging paging = (Paging) map.get("paging");
		
		List<OrderStateDto> stateList = orderService.orderStateList();
		
		LocalDate today = LocalDate.now();
		
		model.addAttribute("today", today);
		model.addAttribute("paging", paging);
		model.addAttribute("orderList", orderList);
		model.addAttribute("stateList", stateList);
		
		return "admin/order/adminOrderList";
	}
	
	@RequestMapping(value="/admin/orders/{orderNo}", method = RequestMethod.GET)
	public String adminOrderDetail(@ModelAttribute("_memberDto_") MemberDto memberDto
			,@PathVariable int orderNo,	HttpSession session, Model model) {
		
		Map<String, Object> orderMap = orderService.orderView(orderNo);
		
		model.addAttribute("orderMap", orderMap);
		
		return "order/orderDetail";
	}
	
	@RequestMapping(value="/admin/orders/{orderNo}/cancel", method = RequestMethod.POST)
	public String adminOrderCancel(@PathVariable int orderNo, 
			HttpSession session, Model model) {
		
		orderService.orderCancel(orderNo);
		
		return "redirect:/admin/orders";
		
	}
	
	@RequestMapping(value="/admin/orders/{orderNo}/accept", method = RequestMethod.POST)
	public String adminOrderAccept(@PathVariable int orderNo, HttpSession session, Model model) {
		
		orderService.orderAccept(orderNo);
		
		return "redirect:/admin/orders/" + orderNo;
	}
	
	@RequestMapping(value = "/admin/sales", method = {RequestMethod.GET, RequestMethod.POST})
	public String sales(Model model, SearchOption searchOption, String yearSel) {
		logger.info("관리자 매출관리");
		
		List<OrderDetailDto> salesList = orderService.salesView(searchOption);
		
		LocalDate today = LocalDate.now();
		
		model.addAttribute("today", today);
		model.addAttribute("salesList", salesList);
		model.addAttribute("yearSel", yearSel);
		
		return "admin/order/sales";
	}
	
	
	@RequestMapping(value = "/member/orders", method = RequestMethod.GET)
	public String memberOrderList(@ModelAttribute("_memberDto_") MemberDto memberDto
			, @RequestParam(defaultValue = "1") int curPage
			, SearchOption searchOption
			, HttpSession session, Model model) {
		logger.info("회원 주문목록 {}", searchOption);
		
		List<OrderStateDto> stateList = orderService.orderStateList();
		
		Map<String, Object> map = orderService.orderList(memberDto.getId(), searchOption, curPage);
		
		@SuppressWarnings("unchecked")
		List<OrderDto> orderList = (List<OrderDto>) map.get("orderList");
				
		Paging paging = (Paging) map.get("paging");
		
		model.addAttribute("paging", paging);
		model.addAttribute("orderList", orderList);
		model.addAttribute("stateList", stateList);
		
		return "order/memberOrderList";
	}
	
	@RequestMapping(value="/member/orders/{orderNo}", method = RequestMethod.GET)
	public String memberOrderDetail(@ModelAttribute("_memberDto_") MemberDto memberDto, 
			@PathVariable int orderNo, HttpSession session, Model model,
			HttpServletResponse response) {
		logger.info("memberOrderDetail - orderNo: {}", orderNo);
		
		String orderId = orderService.orderId(orderNo);
		
		if(!memberDto.getId().equals(orderId)) {
			try {
				response.sendError(403);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Map<String, Object> orderMap = orderService.orderView(orderNo);
		model.addAttribute("orderMap", orderMap);
		
		return "order/orderDetail";
	}
	
	@RequestMapping(value="/member/orders/{orderNo}/cancel", method = RequestMethod.POST)
	public String memberOrderCancel(@ModelAttribute("_memberDto_") MemberDto memberDto,
			@PathVariable int orderNo, 
			HttpSession session, Model model) {
		
		int totalAmount;
		totalAmount = orderService.orderCancel(orderNo);
		
		int balance = memberDto.getCash() + totalAmount;
		memberDto.setCash(balance);
		
		return "redirect:/member/orders";
	}
	
	
}
