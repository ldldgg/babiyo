package com.mealmaker.babiyo.order.service;

import java.util.List;
import java.util.Map;

import com.mealmaker.babiyo.order.model.OrderDetailDto;
import com.mealmaker.babiyo.order.model.OrderDto;
import com.mealmaker.babiyo.order.model.OrderStateDto;
import com.mealmaker.babiyo.util.SearchOption;

public interface OrderService {

	public int order(OrderDto orderDto, OrderDetailDto orderDetailDto) throws Exception;

	public OrderDto lastOrder(String memberId);

	public Map<String, Object> orderList(String memberId, SearchOption searchOption, int curPage);

	public Map<String, Object> orderView(int orderNo);

	public int orderCancel(int orderNo);

	public List<OrderStateDto> orderStateList();

	public Map<String, Object> adminOrderList(SearchOption searchOption, int curPage);

	public void orderAccept(int orderNo);
	
	public List<OrderDetailDto> salesView(SearchOption searchOption);

	public String orderId(int orderNo);

}
