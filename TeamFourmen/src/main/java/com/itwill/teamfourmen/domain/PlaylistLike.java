package com.itwill.teamfourmen.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@Entity
@Table(name = "playlist_likes")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "playlist_likes_seq", sequenceName = "playlist_likes_seq", allocationSize = 1)
public class PlaylistLike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlist_likes_seq")
	private Long playlistLikeId;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name= "playlist_id")
	private Playlist playlist;
	
}
