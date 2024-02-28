package com.itwill.teamfourmen.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CkEditorService {

	/**
	 * 로컬에 파일 저장하고 저장된 파일 이름 반환함.
	 * @param request
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public String imageUpload(MultipartRequest request) throws IllegalStateException, IOException {
		
		MultipartFile file = request.getFile("upload");
		String fileName = file.getOriginalFilename();
		
		String ext = fileName.substring(fileName.lastIndexOf("."));
		String newName = UUID.randomUUID() + ext;
		log.info("newName={}", newName);
		
		String localPath = "C:\\images\\ckeditor";
		
		
		String absolutePath = localPath + File.separator +newName;
		log.info("absolute path={}", absolutePath);				
		
		file.transferTo(new File(absolutePath));
		
		return newName;
	}
	
}
