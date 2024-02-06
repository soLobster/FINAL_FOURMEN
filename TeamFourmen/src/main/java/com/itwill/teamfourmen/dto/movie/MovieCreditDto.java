package com.itwill.teamfourmen.dto.movie;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class MovieCreditDto {
	
	private int id;
	private List<MovieCastDto> cast;
	private List<MovieCrewDto> crew;		
		
}
