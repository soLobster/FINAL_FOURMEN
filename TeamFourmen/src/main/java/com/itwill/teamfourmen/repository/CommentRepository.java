package com.itwill.teamfourmen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.teamfourmen.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByPostPostIdOrderByCommentIdAsc(Long postId);
	
}
