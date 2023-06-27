package com.mealmaker.babiyo.product.service;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mealmaker.babiyo.favorite.dao.FavoriteDao;
import com.mealmaker.babiyo.product.dao.ProductDao;
import com.mealmaker.babiyo.product.model.ProductCategoryDto;
import com.mealmaker.babiyo.product.model.ProductDto;
import com.mealmaker.babiyo.util.FileUtils;
import com.mealmaker.babiyo.util.ImageDto;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

@Service
public class ProductServiceImpl implements ProductService{

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Resource
	private FavoriteDao favoriteDao;
	
	@Autowired
	public ProductDao productDao;
	
	@Override
	public Map<String, Object> adminProductList(SearchOption searchOption, int curPage) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int totalCount = productDao.productCount(searchOption);

		Paging paging = new Paging(totalCount, curPage);
		
		int begin = paging.getPageBegin();
		int end = paging.getPageEnd();
		
		List<ProductDto> productList = productDao.productList(searchOption, begin, end);
		
		map.put("productList", productList);
		map.put("paging", paging);
		
		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void productRegistration(ProductDto productDto, 
			MultipartHttpServletRequest mulRequest) throws Exception {
		productDao.productRegistration(productDto);
		
		int parentSeq = productDto.getNo();
		logger.debug("productNo: {}", parentSeq);
		
		List<Map<String, Object>> list 
			= fileUtils.parseInsertFileInfo(parentSeq, mulRequest);
		
		for (int i = 0; i < list.size(); i++) {
			productDao.insertFile(list.get(i));
		}
	}

	@Override
	public Map<String, Object> productAdminDetail(int no) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ProductDto productDto = productDao.productDetail(no);
		ImageDto imgMap = productDao.fileSelectOne(no);
		
		resultMap.put("productDto", productDto);
		resultMap.put("imgMap", imgMap);
		
		return resultMap;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void productModification(ProductDto productDto, MultipartHttpServletRequest mulRequest) 
		throws Exception {
		productDao.productModification(productDto);
		
		int productNo = productDto.getNo();
		
		List<Map<String, Object>> list 
			= fileUtils.parseInsertFileInfo(productNo, mulRequest);

		ImageDto imgMap = productDao.fileSelectOne(productNo);
		
		if(!list.isEmpty()) {
			if (imgMap != null) {
				productDao.fileDelete(productNo);
				fileUtils.parseUpdateFileInfo(imgMap);
				
				for (Map<String, Object> map : list) {
					productDao.insertFile(map);
				}
			}
		}
	}

	//DAO에서 밀키트 삭제하게 시키기
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void productDelete(int no) throws Exception {
		ImageDto imgMap = productDao.fileSelectOne(no);
		
		if (imgMap != null) {
			productDao.fileDelete(no);
			fileUtils.parseUpdateFileInfo(imgMap);
			productDao.productDelete(no);
		}
	}
	
	//검색옵션, 분류 있는 애들의 개수 계산
	@Override
	public int productCount(SearchOption searchOption) {

		return productDao.productCount(searchOption);
	}
	
	//카테고리 페이지에서의 개수 계산
	@Override
	public int categoryCount(SearchOption searchOption) {

		return productDao.categoryCount(searchOption);
	}

	//DAO에서 카테고리 정보 가져오게 시키기
	@Override
	public List<ProductCategoryDto> productCategory() {
		
		return productDao.productCategory();
	}
	
	//DAO에서 카테고리별 밀키트 가져오게 시키기
	@Override
	public List<Map<String, Object>> categoryList(SearchOption searchOption, int begin, int end) {
		List<ProductDto> categoryList = productDao.categoryList(searchOption, begin, end);
		
		List<Map<String, Object>> productCategory = new ArrayList<Map<String,Object>>();
		
		for (ProductDto productDto : categoryList) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			int productNo = productDto.getNo();
			ImageDto imgMap = productDao.fileSelectOne(productNo);
			
			map.put("productDto", productDto);
			map.put("imgMap", imgMap);
			
			productCategory.add(map);
		}
		
		return productCategory;
	}
	
	//main 신상밀키트
	@Override
	public List<Map<String, Object>> newProductList() {
		List<ProductDto> newProductList = productDao.newProductList();
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		for (ProductDto productDto : newProductList) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			int productNo = productDto.getNo();
			ImageDto imgMap = productDao.fileSelectOne(productNo);
			
			map.put("productDto", productDto);
			map.put("imgMap", imgMap);
			
			list.add(map);
		}
		
		return list;
	}

	//main 추천밀키트
	@Override
	public List<Map<String, Object>> recommendProductList(String memberId) {

		List<ProductDto> recommendProductList = productDao.recommendProductList(memberId);
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		for (ProductDto productDto : recommendProductList) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			int productNo = productDto.getNo();
			
			ImageDto imgMap = productDao.fileSelectOne(productNo);
			
			map.put("productDto", productDto);
			map.put("imgMap", imgMap);
			
			list.add(map);
		}
		
		return list;
	}
	
	//회원 밀키트 상세보기
	@Override
	public Map<String, Object> productDetail(int productNo) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		ProductDto productDto = productDao.productDetail(productNo);
		ImageDto imgMap = productDao.fileSelectOne(productNo);
		
		map.put("productDto", productDto);
		map.put("imgMap", imgMap);
		
		return map;
	}

	@Override
	public int stockView(int productNo) {
		// TODO Auto-generated method stub
		return productDao.stockView(productNo);
	}
}
