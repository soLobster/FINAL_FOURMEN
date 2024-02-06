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
public class MovieCrewDto {
	
	private boolean adult;
	private int gender;
	private int id;
	private String knownForDepartment;
	private String name;
	private String originalName;
	private double popularity;
	private String profilePath;
	private String creditId;
	private String department;
	private String job;
	
}
