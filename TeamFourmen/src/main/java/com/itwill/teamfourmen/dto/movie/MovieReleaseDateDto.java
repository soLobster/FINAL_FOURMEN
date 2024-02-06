package com.itwill.teamfourmen.dto.movie;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

// MoiveReleaseDateDto
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieReleaseDateDto {
	private String iso_3166_1;
	private List<MovieReleaseDateItemDto> release_dates;
}
