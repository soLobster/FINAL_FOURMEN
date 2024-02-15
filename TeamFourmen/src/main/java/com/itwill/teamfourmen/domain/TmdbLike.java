package com.itwill.teamfourmen.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
public class TmdbLike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tmdb_likes_seq")
	private Long id;
	
	@Basic(optional = false)
	private String email;
	
	@Basic(optional = false)
	private String category;
	
	@Basic(optional = false)
	private Long tmdbId;
	
}
