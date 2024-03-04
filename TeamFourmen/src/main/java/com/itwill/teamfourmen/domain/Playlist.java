package com.itwill.teamfourmen.domain;

import org.hibernate.annotations.DynamicInsert;

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

@Builder
@Data
@Entity
@DynamicInsert
@Table(name = "playlist")
@NoArgsConstructor
@SequenceGenerator(name = "playlist_seq", sequenceName = "playlist_seq", allocationSize = 1)
@AllArgsConstructor
public class Playlist {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlist_seq")	
	@Id
	private Long playlistId;
	
	private String playlistName;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Member member;
	
	private Long likes;
	
	private String isPrivate;
	
}
