package com.itwill.teamfourmen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.teamfourmen.domain.PlaylistLike;

public interface PlaylistLikeRepository extends JpaRepository<PlaylistLike, Long> {
	
	List<PlaylistLike> findAllByPlaylistPlaylistId(Long playlistId);
	
	void deleteByMemberEmailAndPlaylistPlaylistId(String email, Long playlistId);
	
	List<PlaylistLike> findAllByMemberMemberIdOrderByPlaylistLikeId(Long memberId);
}
