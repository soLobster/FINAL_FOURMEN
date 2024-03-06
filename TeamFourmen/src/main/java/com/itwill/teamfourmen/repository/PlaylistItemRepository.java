package com.itwill.teamfourmen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itwill.teamfourmen.domain.PlaylistItem;

public interface PlaylistItemRepository extends JpaRepository<PlaylistItem, Long> {
	
	List<PlaylistItem> findAllByPlaylistPlaylistIdOrderByNthInPlaylist(Long playlistId);
	
	Long countByPlaylistPlaylistId(Long playlistId);
}
