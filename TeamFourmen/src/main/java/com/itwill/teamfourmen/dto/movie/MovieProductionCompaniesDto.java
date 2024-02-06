package com.itwill.teamfourmen.dto.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieProductionCompaniesDto {
	
	private int id;
	private String logoPath;
	private String name;
	private String originCountry;
	
}
