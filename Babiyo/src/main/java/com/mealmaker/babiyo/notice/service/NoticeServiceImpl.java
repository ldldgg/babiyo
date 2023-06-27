package com.mealmaker.babiyo.notice.service;

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

import com.mealmaker.babiyo.notice.dao.NoticeDao;
import com.mealmaker.babiyo.notice.model.NoticeDto;
import com.mealmaker.babiyo.util.FileUtils;
import com.mealmaker.babiyo.util.ImageDto;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

@Service
public class NoticeServiceImpl implements NoticeService {

	private static final Logger log = LoggerFactory.getLogger(NoticeServiceImpl.class);
	
	@Autowired
	private NoticeDao noticeDao;

	@Resource(name = "fileUtils")
	private FileUtils fileUtils;

	// 관리자

	@Override
	public Paging noticePaging(SearchOption searchOption, int curPage) {
		// TODO Auto-generated method stub
		int totalCount = noticeDao.noticeCount(searchOption);
		return new Paging(totalCount, curPage);
	}
	
	@Override
	public List<NoticeDto> noticeList(SearchOption searchOption, Paging paging) {
		// TODO Auto-generated method stub
		return noticeDao.noticeList(searchOption, paging.getPageBegin());
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void noticeWrite(NoticeDto noticeDto, MultipartHttpServletRequest mulRequest) throws Exception {
		noticeDao.noticeWrite(noticeDto);
		int noticeNo = noticeDto.getNo();
		log.debug("noticeNo : {}", noticeNo);

		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(noticeNo, mulRequest);

		for (int i = 0; i < list.size(); i++) {
			noticeDao.insertFile(list.get(i));
		}
	}

	@Override
	public Map<String, Object> noticeSelectOne(int no) {
		// TODO Auto-generated method stub

		Map<String, Object> map = new HashMap<String, Object>();

		NoticeDto noticeDto = noticeDao.noticeSelectOne(no);
		ImageDto imgMap = noticeDao.fileSelectOne(no);

		map.put("noticeDto", noticeDto);
		map.put("imgMap", imgMap);

		return map;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void noticeDeleteOne(int no) throws Exception {
		// TODO Auto-generated method stub
		ImageDto imgDto = noticeDao.fileSelectOne(no);
		
		if(imgDto != null) {
			fileUtils.parseUpdateFileInfo(imgDto);
		}
		
		noticeDao.noticeDeleteOne(no);
	}

	// 메인에 공지 리스트 가져오게 시키기
	@Override
	public NoticeDto mainLatestNotice() {
		NoticeDto mainLatestNotice = noticeDao.mainLatestNotice();

		return mainLatestNotice;
	}
	
	// 메인에 이벤트 이미지 가져오게 시키기
	@Override
	public List<ImageDto> eventBannerImage() {
		return noticeDao.eventBannerImage();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void noticeUpdate(int imgNo, NoticeDto noticeDto, 
			MultipartHttpServletRequest mulRequest) throws Exception {
		
		int noticeNo = noticeDto.getNo(); 
		int CategoryCode = noticeDto.getCategoryCode();
	
		noticeDao.noticeUpdate(noticeDto);
		if (CategoryCode == 1) { // 공지 일때
			noticeDao.fileDelete(noticeNo);
		}else { // 이벤트 일때
			List<Map<String, Object>> list 
				= fileUtils.parseInsertFileInfo(noticeNo, mulRequest);

			ImageDto imgMap = noticeDao.fileSelectOne(noticeNo);
			
			if(imgMap != null) {
				
				if (imgNo != imgMap.getNo()) {
					noticeDao.fileDelete(noticeNo);
					fileUtils.parseUpdateFileInfo(imgMap);
					
					for (Map<String, Object> map : list) {
						noticeDao.insertFile(map);
					}
				}
			}else {
				for (Map<String, Object> map : list) {
					noticeDao.insertFile(map);
				}
			}
		}
	}

	@Override
	public int noticeHitPlus(int no) {

		return noticeDao.noticeHitPlus(no);
	}
}
