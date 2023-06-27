package com.mealmaker.babiyo.favorite.service;

import java.util.List;
import java.util.Map;

import com.mealmaker.babiyo.favorite.model.FavoriteDto;
import com.mealmaker.babiyo.util.Paging;

public interface FavoriteService {


	void favoriteDelete(FavoriteDto favoriteDto, String memberId);

	boolean favoriteBtn(String memberId, int productNo);

	Paging favoritePaging(String memberId, int curPage);

	List<Map<String, Object>> favoriteList(String memberId, Paging paging);

	boolean favoriteCheck(String id, int productNo);

}
