package com.itwill.teamfourmen.repository;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.ReviewCommentsLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewCommentLikeRepository extends JpaRepository<ReviewCommentsLike, Long> {

    ReviewCommentsLike findByMemberAndReviewComments_CommentId(Member member, Long commentId);
}
