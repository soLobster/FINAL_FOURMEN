package com.itwill.teamfourmen.service;

import org.springframework.stereotype.Service;

import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.repository.ReviewDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {
	
	private final ReviewDao reviewDao;
	
	public void postReview(Review review) {
		
		log.info("postReview(reviewDto={})", review);
		
		review = reviewDao.save(review);
		
	}
	
}
