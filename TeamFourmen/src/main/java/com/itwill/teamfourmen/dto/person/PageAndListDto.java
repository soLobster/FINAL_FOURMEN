package com.itwill.teamfourmen.dto.person;

import java.util.List;

import lombok.Data;

@Data
public class PageAndListDto {
	
	private int page;
	private List<PopularPersonDto> results;
	private int total_pages;
	private int total_results;
	
}
