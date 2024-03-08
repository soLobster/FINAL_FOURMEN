package com.itwill.teamfourmen.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@SequenceGenerator(name = "playlist_item_seq", sequenceName = "playlist_item_seq", allocationSize = 1)
@Table(name = "playlist_item")
public class PlaylistItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlist_item_seq")
	private Long playlistItemId;
	
	@ManyToOne
	@JoinColumn(name = "playlist_id")
	private Playlist playlist;
	
	private String category;

	private int tmdbId;

	private Long nthInPlaylist;

}
