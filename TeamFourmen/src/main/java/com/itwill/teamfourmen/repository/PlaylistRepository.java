package com.itwill.teamfourmen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.teamfourmen.domain.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
	
	List<Playlist> findAllByMemberEmailOrderByPlaylistId(String email);
	
	List<Playlist> findAllByMemberMemberIdOrderByPlaylistId(Long memberId);
	
	
}
