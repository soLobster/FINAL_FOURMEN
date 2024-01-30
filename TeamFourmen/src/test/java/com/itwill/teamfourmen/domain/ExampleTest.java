package com.itwill.teamfourmen.domain;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.itwill.teamfourmen.dto.movie.MovieCreditDto;
import com.itwill.teamfourmen.dto.movie.MovieCrewDto;
import com.itwill.teamfourmen.dto.movie.MovieDetailsDto;
import com.itwill.teamfourmen.dto.movie.MovieGenreDto;
import com.itwill.teamfourmen.dto.movie.MovieListDto;
import com.itwill.teamfourmen.service.MovieApiUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ExampleTest {

	@Autowired
	private MovieApiUtil movieUtil;
	
	//@Test
	public void test() throws JsonMappingException, JsonProcessingException {
		
		List<MovieGenreDto> genreList = movieUtil.getMovieGenreList();
		
		Assertions.assertNotNull(genreList);
		log.info("genreList={}", genreList);
		
		
	}
	
	// @Test
	public void movieDetailsTest() {
		
		MovieDetailsDto detailsDto = movieUtil.getMovieDetails(609681);
		
		Assertions.assertNotNull(detailsDto);
		log.info("detailsDto={}", detailsDto);
		
	}
	
	@Test
	public void movieCreditTest() {
		
		MovieCreditDto creditDto = movieUtil.getMovieCredit(609681);
		
		Assertions.assertNotNull(creditDto);
		log.info("creditDto={}", creditDto);
		
		log.info("castDto list={}", creditDto.getCast());
		log.info("crewDto list={}", creditDto.getCrew());
		
		List<MovieCrewDto> directorList = creditDto.getCrew().stream().filter((x) -> x.getJob().equals("Director")).toList();
		log.info("director lists = {}", directorList);
		
	}
	
	
}
