package com.mealmaker.babiyo.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealmaker.babiyo.inquiry.dao.InquiryDao;
import com.mealmaker.babiyo.order.dao.OrderDao;
import com.mealmaker.babiyo.order.model.ProductChartDto;
import com.mealmaker.babiyo.order.model.SalesCharDto;

@Service
public class AdminServiceImpl implements AdminService {

	
	@Autowired
	private OrderDao orderDao;
	
	@Resource
	private InquiryDao inquiryDao;
	
	@Override
	public Map<String, Integer> waitCount() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		int orderCount = orderDao.waitOrderCount();
		int inquiryCount = inquiryDao.waitInquiryCount();
		
		map.put("orderCount", orderCount);
		map.put("inquiryCount", inquiryCount);
		
		return map;
	}

	@Override
	public Map<String, List<Object>> salesChart() {
		// TODO Auto-generated method stub
		
		List<SalesCharDto> list = orderDao.salesChart();
		
		Map<String, List<Object>> resultMap = new HashMap<>();
		
		List<Object> weekList = new ArrayList<>();
		List<Object> amountList = new ArrayList<>();
		
		for (SalesCharDto sales : list) {
			weekList.add(sales.getWeek());
			amountList.add(sales.getTotalAmount());
		}
		
		resultMap.put("weekList", weekList);
		resultMap.put("amountList", amountList);
		
		return resultMap;
	}

	@Override
	public Map<String, List<Object>> productChart() {
		// TODO Auto-generated method stub
		
		List<ProductChartDto> list = orderDao.productChart();
		
		Map<String, List<Object>> resultMap = new HashMap<>();
		
		List<Object> nameList = new ArrayList<>();
		List<Object> countList = new ArrayList<>();
		
		for (ProductChartDto chart : list) {
			nameList.add(chart.getName());
			countList.add(chart.getCount());
		}
		
		resultMap.put("nameList", nameList);
		resultMap.put("countList", countList);
		
		return resultMap;
	}
	
	
}
