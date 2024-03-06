package com.itwill.teamfourmen.dto.playlist;

import com.itwill.teamfourmen.domain.Playlist;
import com.itwill.teamfourmen.domain.PlaylistItem;
import com.itwill.teamfourmen.dto.TmdbWorkDetailsDto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistItemDto {
	
	private Long playlistItemId;
	
	private Playlist playlist;
	
	private String category;
	
	private int tmdbId;
	
	private Long nthInPlaylist;
	
	private TmdbWorkDetailsDto workDetails;
	
	
	
	public static PlaylistItemDto fromEntity(PlaylistItem playlistItem) {
		
		return PlaylistItemDto.builder()
							.playlistItemId(playlistItem.getPlaylistItemId())
							.playlist(playlistItem.getPlaylist())
							.category(playlistItem.getCategory())
							.tmdbId(playlistItem.getTmdbId())
							.nthInPlaylist(playlistItem.getNthInPlaylist())
							.build();
							
							
	}
	
}
