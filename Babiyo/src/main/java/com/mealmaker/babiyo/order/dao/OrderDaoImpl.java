package com.mealmaker.babiyo.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mealmaker.babiyo.order.model.OrderDetailDto;
import com.mealmaker.babiyo.order.model.OrderDto;
import com.mealmaker.babiyo.order.model.OrderStateDto;
import com.mealmaker.babiyo.order.model.ProductChartDto;
import com.mealmaker.babiyo.order.model.SalesCharDto;
import com.mealmaker.babiyo.util.SearchOption;

@Repository
public class OrderDaoImpl implements OrderDao {
	String namespace = "com.mealmaker.babiyo.order.";

	private SqlSessionTemplate sqlSession;
	private SqlSession sqlSessionSimple;
	
	@Autowired
	public void setSqlSession(SqlSessionTemplate sqlSession, SqlSessionTemplate sqlSessionSimple) {
		this.sqlSession = sqlSession;
		this.sqlSessionSimple = sqlSessionSimple;
	}
	
	
	// 주문하기 (기본정보)
	@Override
	public void order(OrderDto orderDto) {
		// TODO Auto-generated method stub
		sqlSessionSimple.insert(namespace + "order", orderDto);
	}

	// 주문하기 (상세정보)
	@Override
	public void orderDetail(OrderDetailDto orderDetailDto) {
		// TODO Auto-generated method stub
		sqlSessionSimple.insert(namespace + "orderDetail", orderDetailDto);
	}

	// 마지막으로 주문한 정보 조회
	@Override
	public OrderDto lastOrder(String memberId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "lastOrder", memberId);
	}

	// 페이징을 위한 주문목록 총 갯수 조회
	@Override
	public int orderCount(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		
		return sqlSession.selectOne(namespace + "orderCount", paraMap);
	}
	
	// 일반회원 주문목록
	@Override
	public List<OrderDto> orderList(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		
		return sqlSession.selectList(namespace + "orderList", paraMap);
	}
	
	// 주문 상세화면 (기본정보)
	@Override
	public OrderDto orderView(int orderNo) {
		// TODO Auto-generated method stub
		
		return sqlSession.selectOne(namespace + "orderView", orderNo);
	}

	// 주문 상세화면 (상세정보)
	@Override
	public List<OrderDetailDto> orderDetailView(int orderNo) {
		// TODO Auto-generated method stub
		
		return sqlSessionSimple.selectList(namespace + "orderDetailView", orderNo);
	}

	// 주문 취소
	@Override
	public void orderCancel(int orderNo) {
		// TODO Auto-generated method stub
		sqlSessionSimple.update(namespace + "orderCancel", orderNo);
	}

	// 주문상태 종류 조회
	@Override
	public List<OrderStateDto> orderStateList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "orderStateList");
	}
	
	// 주문 수락
	@Override
	public void orderAccept(List<OrderDetailDto> orderList, int orderNo) {
		// TODO Auto-generated method stub
		
		sqlSession.insert(namespace + "orderInsert", orderList);
		sqlSession.update(namespace + "orderAccept", orderNo);
	}


	@Override
	public int waitOrderCount() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "waitOrderCount");
	}

	@Override
	public List<OrderDetailDto> orderDetailList(SearchOption searchOption) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "orderDetailList" , searchOption);
	}

	@Override
	public OrderDto totalAmountView(int orderNo) {
		// TODO Auto-generated method stub
		return sqlSessionSimple.selectOne(namespace + "totalAmountView", orderNo);
	}
	
	@Override
	public List<SalesCharDto> salesChart() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "salesChart");
	}

	@Override
	public List<ProductChartDto> productChart() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "productChart");
	}
	
	@Override
	public int memberTotalOrder(String memberId) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "memberHomeOrderCount", memberId);
	}

	@Override
	public String orderId(int orderNo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace + "orderId", orderNo);
	}

}
