package com.itwill.teamfourmen.repository;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.itwill.teamfourmen.domain.ReviewLike;

import java.util.List;
import java.util.Optional;


public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
  
  	Optional<ReviewLike> findByReviewReviewIdAndMemberEmail(Long reviewId, String email);

    ReviewLike findByReviewAndMember(Review review, Member member);

    Long countByReview(Review review);

    Page<ReviewLike> findByReview(Review review, Pageable pageable);
}
