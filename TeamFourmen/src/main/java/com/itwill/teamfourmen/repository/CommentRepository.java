package com.itwill.teamfourmen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.itwill.teamfourmen.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	// 카테고리의 가장 부모댓글 찾음
	List<Comment> findByPostPostIdAndReplyToOrderByCommentIdAsc(@Param("postId") Long postId, @Param("replyTo") Long replyTo);
	
	
	// 댓글에 대한 대댓글 댓글들 찾음
	List<Comment> findAllByReplyTo(@Param("replyTo") Long replyTo);
	
	
	// postId에 해당하는 게시물의 댓글 개수 가져옴
	int countByPostPostId(Long postId);
}
