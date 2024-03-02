//package com.itwill.teamfourmen.service;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartRequest;
//
//import com.itwill.teamfourmen.config.S3Config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RequiredArgsConstructor
//@Service
//public class CkEditorService {
//
//	private final S3Config s3Config;
//
//	@Value("${cloud.aws.s3.bucket}")
//	private String bucketName;
//
//
//
//	/**
//	 * 로컬에 파일 저장하고 저장된 파일 이름 반환함. 수정. s3 url반환
//	 * @param request
//	 * @return
//	 * @throws IllegalStateException
//	 * @throws IOException
//	 */
//	public String imageUpload(MultipartRequest request, String sDirectory) throws IllegalStateException, IOException {
//
//		MultipartFile file = request.getFile("upload");
//		String fileName = file.getOriginalFilename();
//
//		String ext = fileName.substring(fileName.lastIndexOf("."));
//		String newName = UUID.randomUUID() + ext;
//		log.info("newName={}", newName);
//
////		String localPath = "C:\\images\\ckeditor";
//
//
////		String absolutePath = sDirectory + File.separator +newName;
//		String absolutePath = sDirectory + newName;
//		log.info("absolute path={}", absolutePath);
//
//		File fileAbsolutePath = new File(absolutePath);
//		file.transferTo(fileAbsolutePath);
//
//		s3Config.amazonS3Client().putObject(bucketName, "images/" + newName, fileAbsolutePath);
//		String s3Url = s3Config.amazonS3Client().getUrl(bucketName, "images/" + newName).toString();
//		log.info("s3Url={}", s3Url);
//
//		fileAbsolutePath.delete();
//
//		return s3Url;
//	}
//
//}
