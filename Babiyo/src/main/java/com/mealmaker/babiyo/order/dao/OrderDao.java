package com.mealmaker.babiyo.order.dao;

import java.util.List;
import java.util.Map;

import com.mealmaker.babiyo.order.model.OrderDetailDto;
import com.mealmaker.babiyo.order.model.OrderDto;
import com.mealmaker.babiyo.order.model.OrderStateDto;
import com.mealmaker.babiyo.order.model.ProductChartDto;
import com.mealmaker.babiyo.order.model.SalesCharDto;
import com.mealmaker.babiyo.util.SearchOption;

public interface OrderDao {

	public void order(OrderDto orderDto);
	
	public void orderDetail(OrderDetailDto orderDetailDto);

	public OrderDto lastOrder(String memberId);

	public OrderDto orderView(int orderNo);

	public List<OrderDetailDto> orderDetailView(int orderNo);

	public void orderCancel(int orderNo);

	public List<OrderStateDto> orderStateList();

	public void orderAccept(List<OrderDetailDto> orderList, int orderNo);

	public int waitOrderCount();

	public List<SalesCharDto> salesChart();

	public List<OrderDetailDto> orderDetailList(SearchOption searchOption);

	public OrderDto totalAmountView(int orderNo);

	public List<ProductChartDto> productChart();

	public int memberTotalOrder(String memberId);

	public int orderCount(Map<String, Object> paraMap);

	public List<OrderDto> orderList(Map<String, Object> paraMap);

	public String orderId(int orderNo);

	
	
}
