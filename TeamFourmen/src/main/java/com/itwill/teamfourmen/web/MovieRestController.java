package com.itwill.teamfourmen.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.itwill.teamfourmen.dto.movie.MovieAdditionalListDto;
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
	public ResponseEntity<MovieListDto> getAdditionalList(@RequestParam(name = "listCategory") String listCategory, @RequestParam(name="page") int page) throws JsonMappingException, JsonProcessingException {
		log.info("getAdditionalList(category={}, page={})", listCategory, page);
		
		MovieListDto listDto = util.getMovieList(listCategory, page);
		log.info("listDto={}", listDto);
		
		
		return ResponseEntity.ok(listDto);		
	}
	
}
