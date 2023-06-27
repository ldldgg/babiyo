package com.mealmaker.babiyo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Component("fileUtils")
public class FileUtils {

	private static final String filePath = "babiyo/upload";
	
	public List<Map<String, Object>> parseInsertFileInfo(int parentSeq
		, MultipartHttpServletRequest mulRequest) // file에 각 한개씩 담겨올때
			throws Exception{
		
		Iterator<String> iterator = mulRequest.getFileNames();
		MultipartFile multipartFile = null;
		
		String originalFileName = null;  // 등록한 파일의 실제 이름
		String originalFileExtension = null; // 파일의 확장자
		String storedFileName = null; // 서버에 저장시킬 파일 이름
		
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> fileInfoMap = null;
		
		File file = new File(filePath);
		
		if(file.exists() == false) { // 파일이 존재하지 않으면
			file.mkdirs(); // 디렉토리 생성
		}
		
		while(iterator.hasNext()) {
			
			multipartFile = mulRequest.getFile(iterator.next());
			
			if(multipartFile.isEmpty() == false) {
				fileInfoMap = new HashMap<String, Object>(); // db에 담을 정보 저장
				originalFileName = multipartFile.getOriginalFilename();
				
				if(multipartFile.getContentType().equals("text")) {
					storedFileName = originalFileName;
				}else {
					originalFileExtension = 
							originalFileName.substring(originalFileName.lastIndexOf("."));
					
					storedFileName = CommonUtils.getRandomString() + originalFileExtension;
					
					file = new File(filePath, storedFileName); // 경로와 파일이름 지정
					multipartFile.transferTo(file); // 위에 지정한 경로와 이름으로 파일 생성
					
					fileInfoMap.put("parentSeq", parentSeq);
					fileInfoMap.put("original_file_name", originalFileName);
					fileInfoMap.put("file_size", multipartFile.getSize());
				}
				fileInfoMap.put("stored_file_name", storedFileName);
				
				fileList.add(fileInfoMap);
			}
			
		}
		
		return fileList; // db에 담을 정보 반환
	}
	
	public List<Map<String, Object>> parseInsertFileInfoList(int parentSeq
			, MultipartHttpServletRequest mulRequest) // 한 file multiple로 여러 파일이 담겨올때
				throws Exception{
			
			Iterator<String> iterator = mulRequest.getFileNames();
		
			List<MultipartFile> fileList = null;
			
			String originalFileName = null;  // 등록한 파일의 실제 이름
			String originalFileExtension = null; // 파일의 확장자
			String storedFileName = null; // 서버에 저장시킬 파일 이름
			
			List<Map<String, Object>> fileInfoList = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> fileInfoMap = null;
			
			File file = new File(filePath);
			
			if(file.exists() == false) { // 파일이 존재하지 않으면
				file.mkdirs(); // 디렉토리 생성
			}
			while (iterator.hasNext()) {
				
				String name = iterator.next();
				
				fileList = mulRequest.getFiles(name);
				
				System.out.println("파일이름" + name);
				
				for (MultipartFile multipartFile : fileList) {
					
					
					if(multipartFile.isEmpty() == false) {
						fileInfoMap = new HashMap<String, Object>(); // db에 담을 정보 저장
						originalFileName = multipartFile.getOriginalFilename();
						
						if(multipartFile.getContentType().equals("text")) {
							storedFileName = originalFileName;
						}else {
							originalFileExtension = 
									originalFileName.substring(originalFileName.lastIndexOf("."));
							
							storedFileName = CommonUtils.getRandomString() + originalFileExtension;
							
							file = new File(filePath, storedFileName); // 경로와 파일이름 지정
							multipartFile.transferTo(file); // 위에 지정한 경로와 이름으로 파일 생성
							
							fileInfoMap.put("parentSeq", parentSeq);
							fileInfoMap.put("original_file_name", originalFileName);
							fileInfoMap.put("file_size", multipartFile.getSize());
						}
						fileInfoMap.put("stored_file_name", storedFileName);
						
						fileInfoList.add(fileInfoMap);
					}
				}
				
			}
			
			return fileInfoList; // db에 담을 정보 반환
		}
	
	// 기존의 파일이 존재하는데 새로운 파일로 변경되는 경우
	public void parseUpdateFileInfo(ImageDto tempFileMap) throws Exception{

		String storedFileName = (String)tempFileMap.getStoredName();
		
		File file = new File(filePath + "/" + storedFileName);
		
		if(file.exists()) {
			file.delete();
		}
	}

}
