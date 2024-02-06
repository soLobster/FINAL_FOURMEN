package com.itwill.teamfourmen.dto.movie;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieProviderDto {
	private String link;
	private List<MovieProviderItemDto> rent;
	private List<MovieProviderItemDto> buy;
	private List<MovieProviderItemDto> flatrate;
	private List<MovieProviderItemDto> free;
	private List<MovieProviderItemDto> ads;	
}
