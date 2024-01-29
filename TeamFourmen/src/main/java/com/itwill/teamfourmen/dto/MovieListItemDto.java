package com.itwill.teamfourmen.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieListItemDto {
	
	
	private boolean adult;
	
	private String backdropPath;
	
	private List<Integer> genreIds;
	
	private int id;
	
	private String originalLanguage;
	
	private String originalTitle;
	
	private String overview;
	
	private double popularity;
	
	private String posterPath;
	
	private LocalDate releaseDate;
	
	private String title;
	
	private boolean video;
	
	private double voteAverage;
	
	private int voteCount;

}
