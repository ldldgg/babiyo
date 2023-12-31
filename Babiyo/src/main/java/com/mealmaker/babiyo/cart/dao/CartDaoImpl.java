package com.mealmaker.babiyo.cart.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mealmaker.babiyo.cart.model.CartDto;

@Repository
public class CartDaoImpl implements CartDao {

	String namespace = "com.mealmaker.babiyo.cart.";
	
	private SqlSessionTemplate sqlSession;
	private SqlSessionTemplate sqlSessionSimple;
	
	@Autowired 
	public CartDaoImpl(SqlSessionTemplate sqlSession, SqlSessionTemplate sqlSessionSimple) {
		this.sqlSession = sqlSession;
		this.sqlSessionSimple = sqlSessionSimple;
	}

	@Override
	public List<CartDto> cartList(String memberId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "cartList", memberId);
	}

	@Override
	public void cartDelete(CartDto cartDto) {
		// TODO Auto-generated method stub
		sqlSessionSimple.delete(namespace + "cartDelete", cartDto);
	}

	@Override
	public void quantityModify(CartDto cartDto) {
		// TODO Auto-generated method stub
		
		sqlSession.update(namespace + "quantityModify", cartDto);
	}

	@Override
	public void cartAdd(Map<String, Object> map) {
		// TODO Auto-generated method stub
		sqlSession.insert(namespace + "cartAdd", map);
	}

	@Override
	public List<Integer> cartProductList(String memberId) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(namespace + "cartProductList", memberId);
	}

	@Override
	public void cartAddModify(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		sqlSession.update(namespace + "cartAddModify", map);
	}
		

	
}
