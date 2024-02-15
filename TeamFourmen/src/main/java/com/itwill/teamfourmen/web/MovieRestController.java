package com.itwill.teamfourmen.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.itwill.teamfourmen.dto.movie.MovieQueryParamDto;
import com.itwill.teamfourmen.dto.movie.MovieListDto;
import com.itwill.teamfourmen.service.MovieApiUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movie")
public class MovieRestController {
	
	private final MovieApiUtil util;
	
	@GetMapping("/list")
	public ResponseEntity<MovieListDto> getAdditionalList(@ModelAttribute MovieQueryParamDto paramDto) throws JsonMappingException, JsonProcessingException {
		log.info("getAdditionalList(param={})", paramDto);
		
		MovieListDto listDto = util.getMovieList(paramDto);
		// log.info("listDto={}", listDto);
		
		
		return ResponseEntity.ok(listDto);		
	}
	
}
