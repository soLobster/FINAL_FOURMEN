package com.itwill.teamfourmen.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.service.FeatureService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feature")
public class FeatureRestController {
    
	private final FeatureService reviewService;
	
	@PostMapping("/review/post")
	public void postReview(@RequestBody Review review) {
		 
		 log.info("postReview(reviewdto={})", review);
		 reviewService.postReview(review);
		 		 		 
	}
	
		
	
}