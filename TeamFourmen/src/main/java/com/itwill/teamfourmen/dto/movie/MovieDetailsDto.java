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
import com.itwill.teamfourmen.dto.TmdbWorkDetailsDto;
import com.itwill.teamfourmen.dto.tvshow.TvShowDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailsDto extends TmdbWorkDetailsDto {
	
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
	
	private String mediaType;	// 컬랙션 리스트에 포함돼 있는 필드값
	
	
	public static MovieDetailsDto fromTvShowDto(TvShowDTO tvShowDto) {
		
		return MovieDetailsDto.builder()
							.backdropPath(tvShowDto.getBackdrop_path())
							.id(tvShowDto.getId())
							.originalLanguage(tvShowDto.getOriginal_language())
							.posterPath(tvShowDto.getPoster_path())
							.title(tvShowDto.getName())
							.build();
		
	}
	
}
