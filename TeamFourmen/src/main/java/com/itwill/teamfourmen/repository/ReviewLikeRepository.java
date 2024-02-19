package com.itwill.teamfourmen.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.teamfourmen.domain.ReviewLike;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

//    Optional<ReviewLike> findByReviewReviewIdAndMemberEmail(String reviewId, String email);

}