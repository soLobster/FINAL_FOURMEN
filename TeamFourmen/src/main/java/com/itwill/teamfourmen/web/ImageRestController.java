package com.itwill.teamfourmen.web;

import java.io.IOException;
import java.net.URLDecoder;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.teamfourmen.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ImageRestController {
	
	private final ImageService imageService;
	
	@GetMapping("/image/proxy")
	public ResponseEntity<ByteArrayResource> renderFromUrlImage(@RequestParam(name = "url") String url) throws IOException {
		
		log.info("renderFromUrlImage(url={})", url);
		String decodedUrl = URLDecoder.decode(url, "UTF-8");
		log.info("decoded url={}", decodedUrl);
		
		ByteArrayResource imageResource = imageService.renderFromUrlImage(decodedUrl);
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.IMAGE_JPEG);
		
		return ResponseEntity.ok().headers(header).body(imageResource);
	}
	
	
}
