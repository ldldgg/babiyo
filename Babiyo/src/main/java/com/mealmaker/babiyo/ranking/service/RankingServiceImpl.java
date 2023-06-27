package com.mealmaker.babiyo.ranking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mealmaker.babiyo.product.model.ProductDto;
import com.mealmaker.babiyo.ranking.dao.RankingDao;

@Service
public class RankingServiceImpl implements RankingService{


	@Autowired
	RankingDao rankingDao;

	@Override
	public List<ProductDto> toDayList() {
		// TODO Auto-generated method stub
		return rankingDao.toDayList();
	}

	@Override
	public List<ProductDto> weeklyList() {
		// TODO Auto-generated method stub
		return rankingDao.weeklyList();
	}

	@Override
	public List<ProductDto> manList() {
		// TODO Auto-generated method stub
		return rankingDao.manList();
	}

	@Override
	public List<ProductDto> womanList() {
		// TODO Auto-generated method stub
		return rankingDao.womanList();
	}
	


}
