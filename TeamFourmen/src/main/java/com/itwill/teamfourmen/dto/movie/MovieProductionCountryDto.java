package com.itwill.teamfourmen.dto.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieProductionCountryDto {
	
	private String iso_3166_1;
	private String name;
	
}
