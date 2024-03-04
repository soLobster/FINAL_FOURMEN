package com.itwill.teamfourmen.repository;

import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.domain.ReviewComments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewCommentsRepository extends JpaRepository<ReviewComments, Long> {

    Page<ReviewComments> findByReview(Review review, Pageable pageable);

    List<ReviewComments> findByReview(Review review);

}
