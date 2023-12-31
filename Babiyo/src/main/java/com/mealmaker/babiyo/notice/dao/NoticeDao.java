package com.mealmaker.babiyo.notice.dao;

import java.util.List;
import java.util.Map;

import com.mealmaker.babiyo.notice.model.NoticeDto;
import com.mealmaker.babiyo.util.ImageDto;
import com.mealmaker.babiyo.util.SearchOption;


public interface NoticeDao {
	//관리자
	//분류
	int noticeCount(SearchOption searchOption);
	//리스트
	List<NoticeDto> noticeList(SearchOption searchOption, int begin);
	//작성
	public int noticeWrite(NoticeDto noticeDto);
	//상세
	public NoticeDto noticeSelectOne(int no);
	//수정
	int noticeUpdate(NoticeDto noticeDto);
	//조회수
	int noticeHitPlus(int no);
	//삭제
	public void noticeDeleteOne(int no);
	//이미지 파일 리스트
	public ImageDto fileSelectOne(int no);
	public void insertFile(Map<String, Object> map);
	public int fileDelete(int parentSeq);
	public NoticeDto mainLatestNotice();
	public List<ImageDto> eventBannerImage();
}
