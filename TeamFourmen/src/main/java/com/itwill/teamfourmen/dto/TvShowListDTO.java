package com.itwill.teamfourmen.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowListDTO {

	private List<TvShowDTO> results;
	
}
