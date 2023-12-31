package com.mealmaker.babiyo.notice.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mealmaker.babiyo.notice.model.NoticeDto;
import com.mealmaker.babiyo.util.ImageDto;
import com.mealmaker.babiyo.util.Paging;
import com.mealmaker.babiyo.util.SearchOption;

public interface NoticeService {
	//관리자
	//리스트
	public List<NoticeDto> noticeList(SearchOption searchOption, Paging paging);
	//작성
	public void noticeWrite(NoticeDto noticeDto
			, MultipartHttpServletRequest mulRequest)throws Exception;
	//상세
	public Map<String, Object> noticeSelectOne(int no);
	//수정
	public void noticeUpdate(int no, NoticeDto noticeDto
	, MultipartHttpServletRequest mulRequest) throws Exception;
	//조회수
	public int noticeHitPlus(int no);
	//삭제
	public void noticeDeleteOne(int no) throws Exception;
	//메인에 공지 리스트 가져오기
	NoticeDto mainLatestNotice();
	//메인에 이벤트 사진 쏴주기
	public List<ImageDto> eventBannerImage();
	public Paging noticePaging(SearchOption searchOption, int curPage);
}
