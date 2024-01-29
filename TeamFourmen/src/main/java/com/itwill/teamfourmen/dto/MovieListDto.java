package com.itwill.teamfourmen.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieListDto {
	
	private int page;
	
	private List<MovieListItemDto> results; 
	
	private int totalPages;
	
	private int totalResults;
	
}
