package com.itwill.teamfourmen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.teamfourmen.domain.Review;

public interface ReviewDao extends JpaRepository<Review, Long> {
	
	// 해당 작품에 달린 리뷰들의 리스트를 가져옴
	List<Review> findByCategoryAndTmdbId(String category, int tmdbId);
	
	Optional<Review> findByMemberEmailAndCategoryAndTmdbId(String email, String category, int tmdbId);
	
}
