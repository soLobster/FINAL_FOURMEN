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
import com.itwill.teamfourmen.dto.movie.MovieExternalIdDto;
import com.itwill.teamfourmen.dto.movie.MovieGenreDto;
import com.itwill.teamfourmen.dto.movie.MovieListDto;
import com.itwill.teamfourmen.dto.movie.MovieProviderDto;
import com.itwill.teamfourmen.dto.movie.MovieProviderItemDto;
import com.itwill.teamfourmen.dto.movie.MovieVideoDto;
import com.itwill.teamfourmen.service.MovieApiUtil;
import com.itwill.teamfourmen.service.MovieDetailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ExampleTest {

	@Autowired
	private MovieApiUtil movieUtil;
	@Autowired
	private MovieDetailService movieDetailService;
	
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
	
	// @Test
	public void movieCreditTest() {
		
		MovieCreditDto creditDto = movieUtil.getMovieCredit(609681);
		
		Assertions.assertNotNull(creditDto);
		log.info("creditDto={}", creditDto);
		
		log.info("castDto list={}", creditDto.getCast());
		log.info("crewDto list={}", creditDto.getCrew());
		
		List<MovieCrewDto> directorList = creditDto.getCrew().stream().filter((x) -> x.getJob().equals("Director")).toList();
		log.info("director lists = {}", directorList);
		
	}
	
	
	// @Test
	public void movieVideoTest() throws JsonMappingException, JsonProcessingException {
		
		List<MovieVideoDto> videoList = movieUtil.getMovieVideoList(609681);
		
		Assertions.assertNotNull(videoList);
		log.info("movie video list = {}", videoList);
		
		List<MovieVideoDto> trailerList = videoList.stream().filter((x) -> x.getType().equals("Trailer")).toList();
		
		log.info("movie trailer list = {}", trailerList);
	}
	
	// @Test
	public void movieProviderTest() throws JsonMappingException, JsonProcessingException {
		
		MovieProviderDto providerDto = movieUtil.getMovieProviderList(893723);
		List<MovieProviderItemDto> providerList = movieDetailService.getOrganizedMovieProvider(providerDto);
		
		Assertions.assertNotNull(providerDto);
		log.info("providerDto={}", providerDto);
		
		Assertions.assertNotNull(providerList);
		log.info("provider list = {}", providerList);
		
	}
	
	// @Test
	public void movieCollectionList() {
		
		List<MovieDetailsDto> collectionList = movieUtil.getMovieCollectionList(623911);
		
		Assertions.assertNotNull(collectionList);
		log.info("collectionLIst={}", collectionList);
		
	}
	
	
	// @Test
	public void externalIdTest() {
		MovieExternalIdDto dto = movieUtil.getMovieExternalId(609681);
		
		Assertions.assertNotNull(dto);
		log.info("external ids = {}", dto);
	}
	
	@Test
	public void recommendedMovieTest() {
		List<MovieDetailsDto> list = movieUtil.getRecommendedMovie(609681);
		
		Assertions.assertNotNull(list);
		log.info("list={}", list);
	}
	
	
}
