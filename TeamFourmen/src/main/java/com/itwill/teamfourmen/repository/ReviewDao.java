package com.itwill.teamfourmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.teamfourmen.domain.Review;

public interface ReviewDao extends JpaRepository<Review, Long> {
	
	
	
}
