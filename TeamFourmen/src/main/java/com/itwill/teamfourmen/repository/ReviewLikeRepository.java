package com.itwill.teamfourmen.repository;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import com.itwill.teamfourmen.domain.ReviewLike;

import java.util.List;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    ReviewLike findByReviewAndMember(Review review, Member member);

    Long countByReview(Review review);
}