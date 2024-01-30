package com.itwill.teamfourmen.dto.movie;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@NoArgsConstructor
public class MovieDetailsDto {
	
	private boolean adult;
	private String backdropPath;
	private MovieCollectionDto belongsToCollection;
	private Long budget;
	private List<MovieGenreDto> genres;
	private String homepage;
	private int id;
	private String imdbId;
	private String originalLanguage;
	private String originalTitle;
	private String overview;
	private double popularity;
	private String posterPath;
	private List<MovieProductionCompaniesDto> productionCompanies;
	private List<MovieProductionCountryDto> productionCountries;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate releaseDate;
	
	private Long revenue;
	private int runtime;
	private List<MovieSpokenLanguagesDto> spokenLanguages;
	private String status;
	private String tagline;
	private String title;
	private boolean video;
	private double voteAverage;
	private int voteCount;
	
}
