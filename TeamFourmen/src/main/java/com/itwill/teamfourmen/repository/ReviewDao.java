package com.itwill.teamfourmen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.teamfourmen.domain.Review;

public interface ReviewDao extends JpaRepository<Review, Long> {
	
	// 해당 작품에 달린 리뷰들의 리스트를 가져옴
	List<Review> findByCategoryAndTmdbId(String category, int tmdbId);
	
	Optional<Review> findByMemberEmailAndCategoryAndTmdbId(String email, String category, int tmdbId);

	// MY PAGE에서 유저가 작성한 모든 리뷰를 가져오기 위함
	// 일단은 리스트로 가져옴.
	List<Review> findByMemberEmail(String email);
	// memberId를 사용하여 모든 reviews를 가져옴
	List<Review> findAllByMemberMemberId(Long memberId);
	
	
	
	Review findByReviewId(long reviewId);

}
