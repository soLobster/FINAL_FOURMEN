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
public class MovieCastDto {
	
	private boolean adult;
	private int id;
	private int gender;
	private String knownForDepartment;
	private String name;
	private String originalName;
	private double popularity;
	private String profilePath;
	private int castId;
	private String character;
	private String creditId;
	private int order;
	
}
