package com.mealmaker.babiyo.notice.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mealmaker.babiyo.notice.model.NoticeDto;
import com.mealmaker.babiyo.util.ImageDto;
import com.mealmaker.babiyo.util.SearchOption;

@Repository
public class NoticeDaoImpl implements NoticeDao{
	String namespace = "com.mealmaker.babiyo.notice.";
	
	private final SqlSessionTemplate sqlSession;
	private final SqlSessionTemplate sqlSessionSimple;
		
	@Autowired
	public NoticeDaoImpl(SqlSessionTemplate sqlSession, SqlSessionTemplate sqlSessionSimple) {
		this.sqlSession = sqlSession;
		this.sqlSessionSimple = sqlSessionSimple;
	}

	@Override
	public int noticeCount(SearchOption searchOption) {
		// TODO Auto-generated method stub
		
		return sqlSession.selectOne(namespace + "noticeCount", searchOption);
	}
	
	@Override
	public List<NoticeDto> noticeList(SearchOption searchOption	, int begin) {
		// TODO Auto-generated method stub
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("begin", begin);
		paraMap.put("search", searchOption.getSearch());
		paraMap.put("searchOption", searchOption.getSearchOption());
		paraMap.put("stateCode", searchOption.getStateCode());
		
		return sqlSession.selectList(namespace + "noticeList", paraMap);
	}

	//파일추가
	@Override
	public void insertFile(Map<String, Object> map) {
		// TODO Auto-generated method stub
		sqlSessionSimple.insert(namespace + "insertFile", map);
	}
	//게시글 등록
	@Override
	public int noticeWrite(NoticeDto noticeDto) {
		// TODO Auto-generated method stub
		return sqlSessionSimple.insert(namespace + "noticeWrite", noticeDto);
	}
	//상세
	@Override
	public NoticeDto noticeSelectOne(int no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace +"noticeSelectOne", no);
	}
	
	@Override
	public ImageDto fileSelectOne(int no) {
		// TODO Auto-generated method stub
		
		return sqlSessionSimple.selectOne(namespace + "fileSelectOne", no);
	}
	
	@Override
	public List<ImageDto> eventBannerImage() {
		// TODO Auto-generated method stub
		
		return sqlSession.selectList(namespace + "eventBannerImage");
	}
	
	@Override
	public NoticeDto mainLatestNotice() {
		
		return sqlSession.selectOne(namespace + "mainLatestNotice");
	}

	@Override
	public int noticeUpdate(NoticeDto noticeDto) {
		// TODO Auto-generated method stub
		return sqlSessionSimple.update(namespace + "noticeUpdate", noticeDto);
	}
	
	@Override
	public int noticeHitPlus(int no) {
		// TODO Auto-generated method stub
		return sqlSession.update(namespace + "noticeHitPlus", no);
	}


	@Override
	public int fileDelete(int parentSeq) {
		// TODO Auto-generated method stub
		return sqlSessionSimple.delete(namespace + "fileDelete", parentSeq);
	}

	@Override
	public void noticeDeleteOne(int no) {
		// TODO Auto-generated method stub
		sqlSessionSimple.delete(namespace + "noticeDeleteOne", no);
	}


}
