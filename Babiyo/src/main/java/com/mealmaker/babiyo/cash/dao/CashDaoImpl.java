package com.mealmaker.babiyo.cash.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CashDaoImpl implements CashDao{
	String namespace = "com.mealmaker.babiyo.cash.";

	private final SqlSessionTemplate sqlSessionSimple;
	
	@Autowired
	public CashDaoImpl(SqlSessionTemplate sqlSessionSimple) {
		this.sqlSessionSimple = sqlSessionSimple;
	}
	
	@Override
	public void cashUpdateOne(String memberId, int cash) {
		// TODO Auto-generated method stub
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("memberId", memberId);
		paraMap.put("cash", cash);
		
		sqlSessionSimple.update(namespace + "cashUpdateOne", paraMap);
	}


}
