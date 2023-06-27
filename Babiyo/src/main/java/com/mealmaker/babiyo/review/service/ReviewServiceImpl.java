package com.mealmaker.babiyo.review.service;

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

import com.mealmaker.babiyo.order.dao.OrderDao;
import com.mealmaker.babiyo.product.dao.ProductDao;
import com.mealmaker.babiyo.review.dao.ReviewDao;
import com.mealmaker.babiyo.review.model.ReviewDto;
import com.mealmaker.babiyo.util.FileUtils;
import com.mealmaker.babiyo.util.ImageDto;

@Service
public class ReviewServiceImpl implements ReviewService{

	private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);
	
	@Resource(name="fileUtils")
	private FileUtils fileUtils;
	
	@Autowired
	private ReviewDao reviewDao;
	
	@Resource
	private ProductDao productDao;
	
	@Resource
	private OrderDao orderDao;

	@Override
	public List<Map<String, Object>> reviewList() {
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		List<ReviewDto> reviewList = reviewDao.reviewList();
		
		for (ReviewDto reviewDto : reviewList) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			int productNo = reviewDto.getProductNo();
			int reviewQuantity = reviewDao.reviewQuantity(productNo);
			
			map.put("reviewDto", reviewDto);
			map.put("reviewQuantity", reviewQuantity);
			
			resultList.add(map);
		}
		return resultList;
	}
	
	@Override
	public Map<String, Object> productReviewInfo(int productNo) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int reviewQuantity = reviewDao.reviewQuantity(productNo);
		double reviewEvaluation = reviewDao.reviewEvaluation(productNo);
		
		map.put("reviewQuantity", reviewQuantity);
		map.put("reviewEvaluation", reviewEvaluation);
		
		return map;
	}
	
	@Override
	public Map<String, Object> detail(int no) {
		ReviewDto reviewDto = reviewDao.reviewDetail(no);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<ImageDto> imgList = reviewDao.fileSelectList(no);
		
		map.put("reviewDto", reviewDto);
		map.put("imgList", imgList);
	
		return map;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public void reviewRegistration(ReviewDto reviewDto, MultipartHttpServletRequest mulRequest)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("reivewRegisteration {}", reviewDto);
		
		reviewDao.reviewWrite(reviewDto);
		
		int reviewNo = reviewDto.getNo(); // mybatis에서 작성한 번호를 바로 받음
		logger.debug("reviewNo: {}", reviewNo);
		
		List<Map<String, Object>> list 
			= fileUtils.parseInsertFileInfoList(reviewNo, mulRequest);
		
		for (int i = 0; i < list.size(); i++) {
			reviewDao.insertFile(list.get(i));
		}
		
	}

	@Override
	public List<ReviewDto> productReviewList(int productNo, String sort, int count, int reviewCurCount) {
		
		int end = reviewCurCount + count;
		
		if(sort.equals("new")) {
			sort = "CREATE_DATE DESC";
		}else if(sort.equals("top")) {
			sort = "STAR_RATING DESC";
		}
		
		List<ReviewDto> reviews = reviewDao.productReviewList(productNo, sort, end);
		
		for (ReviewDto reviewDto : reviews) {
			int reviewNo = reviewDto.getNo();
			
			List<String> imgList = reviewDao.fileSelectStoredFileName(reviewNo);
					
			reviewDto.setReviewImg(imgList);
		}
		
		return reviews;
	}
	
	@Override
	public List<ReviewDto> reviewCollectionList() {
		// TODO Auto-generated method stub
		
		List<ReviewDto> reviewList = reviewDao.reviewCollectionList();
		 
		for (ReviewDto review : reviewList) {
			 
			int reviewNo = review.getNo();
			int productNo = review.getProductNo();
			
			List<String> reviewImg = reviewDao.fileSelectStoredFileName(reviewNo);
			String productImg = productDao.fileSelectStoredFileName(productNo);
			
			review.setReviewImg(reviewImg);
			review.setProductImg(productImg);
		}
		
		return reviewList;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void reviewImageModify(int no, MultipartHttpServletRequest mulRequest)
			throws Exception {
		logger.debug("reviewImageModify");
		
		List<Map<String, Object>> imgInfoList = fileUtils.parseInsertFileInfoList(no, mulRequest);
		logger.debug("{}", imgInfoList);
		
		List<String> storedNameList = reviewDao.fileSelectStoredFileName(no);
		List<String> deleteImgList = new ArrayList<String>();
		
		for (Map<String, Object> imgInfo : imgInfoList) {
			
			if(imgInfo.get("original_file_name") != null) {
				logger.debug("original {}",imgInfo.get("original_file_name"));
				reviewDao.insertFile(imgInfo);
			}else {
				logger.debug("original {}",imgInfo.get("original_file_name"));
				String storedName = (String) imgInfo.get("stored_file_name");
				deleteImgList.add(storedName);
			}
		}
		
		if(!storedNameList.isEmpty()) {
			for (String storedName : storedNameList) {
				if(!deleteImgList.contains(storedName)) {
					ImageDto imgDto = new ImageDto();
					imgDto.setStoredName(storedName);
					fileUtils.parseUpdateFileInfo(imgDto);
					
					reviewDao.fileDelete(storedName);
				}
			}
		}
	}
	
	@Override
	public Map<String, Object> reviewAdminDetail(int no) {
		return null;
	}

	@Override
	public void reviewModification(ReviewDto reviewDto){
		reviewDao.reviewModify(reviewDto);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void reviewDelete(int reviewNo) throws Exception{
		List<String> imgList = reviewDao.fileSelectStoredFileName(reviewNo);
		reviewDao.reviewDelete(reviewNo);
		for (String storedName : imgList) {
			ImageDto imgDto = new ImageDto();
			imgDto.setStoredName(storedName);
			fileUtils.parseUpdateFileInfo(imgDto);
		}
	}

	@Override
	public Integer reviewCheck(String memberId, int productNo) {
		// TODO Auto-generated method stub
		return reviewDao.reviewCheck(memberId, productNo);
	}

}
