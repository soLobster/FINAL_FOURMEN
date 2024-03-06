package com.itwill.teamfourmen.dto.playlist;

import java.util.List;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Playlist;
import com.itwill.teamfourmen.domain.PlaylistItem;
import com.itwill.teamfourmen.domain.PlaylistLike;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlaylistDto {
	

	private Long playlistId;
	
	private String playlistName;

	private Member member;
	
	private Long likes;
	
	private String isPrivate;
	
	private List<PlaylistItemDto> playlistItemDtoList;
	private List<PlaylistLike> playlistLikeList;
	
	public static PlaylistDto fromEntity(Playlist playlist) {
		
		return PlaylistDto.builder()
					.playlistId(playlist.getPlaylistId())
					.playlistName(playlist.getPlaylistName())
					.member(playlist.getMember())
					.likes(playlist.getLikes())
					.isPrivate(playlist.getIsPrivate())
					.build();
		
	}
	
}
