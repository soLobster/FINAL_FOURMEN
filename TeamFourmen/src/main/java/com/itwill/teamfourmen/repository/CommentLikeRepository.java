package com.itwill.teamfourmen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.teamfourmen.domain.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
	
	List<CommentLike> findAllByCommentCommentId(Long commentId);
	
	void deleteByCommentCommentIdAndMemberEmail(Long commentId, String email);
	
}
