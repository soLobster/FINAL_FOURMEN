package com.itwill.teamfourmen.dto.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieSpokenLanguagesDto {
	
	private String english_name;
	private String iso_639_1;
	private String name;
	
	
}
