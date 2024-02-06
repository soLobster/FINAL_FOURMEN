package com.itwill.teamfourmen.dto.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class MovieExternalIdDto {
	
	private int id;
	private String imdbId;
	private String wikidataId;
	private String facebookId;
	private String instagramId;
	private String twitterId;
	
}
