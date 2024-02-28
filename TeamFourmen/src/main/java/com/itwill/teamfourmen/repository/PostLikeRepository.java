package com.itwill.teamfourmen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.teamfourmen.domain.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
	
	Optional<PostLike> findByMemberEmailAndPostPostId(String email, Long postId);
	
	void deleteByMemberEmailAndPostPostId(String email, Long postId);
	
	Long countByPostPostId(Long postId);
}
