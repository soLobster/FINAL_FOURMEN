package com.itwill.teamfourmen.dto.tvshow;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowListDTO {

	private int page;
	private List<TvShowDTO> results;
	private int total_pages;
	private int total_results;

}
