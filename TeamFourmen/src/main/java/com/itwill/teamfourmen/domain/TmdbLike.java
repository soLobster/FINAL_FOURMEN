package com.itwill.teamfourmen.domain;

import jakarta.persistence.Basic;
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

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "tmdb_likes_seq", sequenceName = "tmdb_likes_seq", allocationSize = 1)
@Table(name = "tmdb_likes")
public class TmdbLike {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tmdb_likes_seq")
	private Long id;
	
	@Basic(optional = false)
	@ManyToOne
	@JoinColumn(name="email")
	private Member member;
	
	@Basic(optional = false)
	private String category;
	
	@Basic(optional = false)
	private Integer tmdbId;
	
}
