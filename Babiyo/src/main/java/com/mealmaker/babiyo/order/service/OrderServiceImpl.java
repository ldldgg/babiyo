
package com.mealmaker.babiyo.order.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mealmaker.babiyo.cart.dao.CartDao;
import com.mealmaker.babiyo.cart.model.CartDto;
import com.mealmaker.babiyo.cash.dao.CashDao;
import com.mealmaker.babiyo.order.dao.OrderDao;
import com.mealmaker.babiyo.order.model.OrderDetailDto;
import com.mealmaker.babiyo.order.model.OrderDto;
import com.mealmaker.babiyo.order.model.OrderStateDto;
import com.mealmaker.babiyo.product.dao.ProductDao;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

@Service
public class OrderServiceImpl implements OrderService{
	
	private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	private final OrderDao orderDao;
	
	@Autowired
	public OrderServiceImpl(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	@Resource 
	private CashDao cashDao;
	
	@Resource
	private CartDao cartDao;
	
	@Resource
	private ProductDao productDao;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int order(OrderDto orderDto, OrderDetailDto orderDetailDto) throws Exception{
		// TODO Auto-generated method stub
		List<OrderDetailDto> detailList = orderDetailDto.getOrderDetailList();
		
		for (OrderDetailDto detail : detailList) {
			int productNo = detail.getProductNo();
			int quantity = detail.getQuantity();
			int stock = productDao.stockView(productNo);
			
			if(stock < quantity) {
				log.info("주문오류: 구매하려는 상품수가 재고보다 많습니다");
				return -1;
			}
			// map에 제품번호를 키로 판매하고 남은 개수를 밸류에 저장
		}
		// 금액 차감
		orderCashUpdate(orderDto);
		
		// 주문정보를 db에 저장
		orderDao.order(orderDto);
		
		int orderNo = orderDto.getNo();
		orderDetailDto.setOrderNo(orderNo);
		log.debug("주문번호 : {}", orderNo);
		orderDao.orderDetail(orderDetailDto);
		
		CartDto cartDto = new CartDto();
		
		String memeberId = orderDto.getMemberId();
		cartDto.setMemberId(memeberId);
		
		List<CartDto> cartList = new ArrayList<CartDto>();
		
		// 구매한 물품을 장바구니 db에서 삭제하기 위해서 배열에 저장
		for (OrderDetailDto detail : detailList) {
			CartDto cart = new CartDto();
			cart.setProductNo(detail.getProductNo());
			
			int productNo = detail.getProductNo();
			int quantity = detail.getQuantity() * -1;
			// 제품마다 남은개수를 맵에서 불러옴
			
			// 구매한 상품개수 만큼 재고 차감
			productDao.stockUpdate(productNo, quantity);
			
			cartList.add(cart);
		}
		
		cartDto.setCartList(cartList);
		
		// 배열에 들어있는 장바구니 삭제
		cartDao.cartDelete(cartDto);
		
		return orderNo;
	}

	@Override
	public OrderDto lastOrder(String memberId) {
		// TODO Auto-generated method stub
		return orderDao.lastOrder(memberId);
	}
	

	@Override
	public Map<String, Object> orderList(String memberId, SearchOption searchOption, int curPage) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<>();
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("id", memberId);
		paraMap.put("stateCode", searchOption.getStateCode());
		
		int totalCount = orderDao.orderCount(paraMap);
		
		Paging paging = new Paging(totalCount, curPage);
		
		paraMap.put("begin", paging.getPageBegin());
		paraMap.put("end", paging.getPageEnd());
		
		List<OrderDto> orderList = orderDao.orderList(paraMap);
		
		map.put("orderList", orderList);
		map.put("paging", paging);
		
		return map;
	}
	
	@Override
	public Map<String, Object> adminOrderList(SearchOption searchOption, int curPage) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("beginDate", searchOption.getBeginDate());
		paraMap.put("endDate", searchOption.getEndDate());
		paraMap.put("search", searchOption.getSearch());
		paraMap.put("stateCode", searchOption.getStateCode());
		
		int totalCount = orderDao.orderCount(paraMap);
		
		Paging paging = new Paging(totalCount, curPage);
		
		paraMap.put("begin", paging.getPageBegin());
		paraMap.put("end", paging.getPageEnd());
		
		List<OrderDto> orderList = orderDao.orderList(paraMap);
		
		map.put("paging", paging);
		map.put("orderList", orderList);
		
		return map;
	}

	@Override
	public Map<String, Object> orderView(int orderNo) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		OrderDto orderDto = orderDao.orderView(orderNo);
		
		List<OrderDetailDto> orderDetailList = orderDao.orderDetailView(orderNo);
		
		map.put("orderDto", orderDto);
		map.put("orderDetailList", orderDetailList);
		
		return map;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int orderCancel(int orderNo) {
		// TODO Auto-generated method stub
		orderDao.orderCancel(orderNo);
		
		OrderDto orderDto = orderDao.totalAmountView(orderNo);
		List<OrderDetailDto> detailList = orderDao.orderDetailView(orderNo);
		
		// 주문취소시 상품의 개수 반환
		for (OrderDetailDto detail : detailList) {
			int productNo = detail.getProductNo();
			int quantity = detail.getQuantity();
			
			productDao.stockUpdate(productNo, quantity);
		}
		
		String memberId = orderDto.getMemberId();
		int totalAmount = orderDto.getTotalAmount();
		
		cancelCashUpdate(memberId, totalAmount);
		
		return totalAmount;
	}

	@Override
	public List<OrderStateDto> orderStateList() {
		// TODO Auto-generated method stub
		return orderDao.orderStateList();
	}

	@Override
	public void orderAccept(int orderNo) {
		// TODO Auto-generated method stub
		List<OrderDetailDto> orderList = orderDao.orderDetailView(orderNo);

		orderDao.orderAccept(orderList, orderNo);
	}

	@Override
	public List<OrderDetailDto> salesView(SearchOption searchOption) {
		// TODO Auto-generated method stub
		return orderDao.orderDetailList(searchOption);
	}

	@Override
	public String orderId(int orderNo) {
		// TODO Auto-generated method stub
		return orderDao.orderId(orderNo);
	}

	public void orderCashUpdate(OrderDto orderDto) {
		
		String memberId = orderDto.getMemberId();
		int totalAmount = orderDto.getTotalAmount();
		
		// 유저의 돈을 뺌
		cashDao.cashUpdateOne(memberId, totalAmount * -1);
		
		// 받은돈을 증가시킴
		cashDao.cashUpdateOne("admin", totalAmount);
	}
	
	public void cancelCashUpdate(String memberId, int totalAmount) {
		
		// 취소한 주문금액만큼 차감
		cashDao.cashUpdateOne("admin", totalAmount * -1);
		
		// 유저의 돈을 돌려줌
		cashDao.cashUpdateOne(memberId, totalAmount);
		
	}
}
