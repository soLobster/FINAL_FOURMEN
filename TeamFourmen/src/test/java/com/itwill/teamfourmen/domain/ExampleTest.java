package com.itwill.teamfourmen.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.teamfourmen.dto.MovieListDto;
import com.itwill.teamfourmen.service.MovieApiUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ExampleTest {

	@Autowired
	private MovieApiUtil movieUtil;
	
	@Test
	public void test() {
		
		MovieListDto listDto = movieUtil.getMovieList("popular");
		Assertions.assertNotNull(listDto);
		log.info("listDto={}", listDto);
		listDto.getResults().get(0);		
		
	}
	
	
}
