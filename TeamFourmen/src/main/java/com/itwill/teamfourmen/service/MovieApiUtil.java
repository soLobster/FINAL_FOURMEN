package com.itwill.teamfourmen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.itwill.teamfourmen.dto.MovieListDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieApiUtil {
	
	@Value("${tmdb.hd.token}")
	private String token;
	
	/**
	 * 영화 리스트를 MovieListDto객체로 돌려주는 메서드.
	 * 파라미터는 무조건 "popular", "now playing", "top rated", "upcoming" 중 하나여야 함!
	 * @param listCategory 무조건 "popular", "now playing", "top rated", "upcoming" 중 하나
	 * @return 받아온 json데이터를 매핑한 MovieListDto객체
	 */
	public MovieListDto getMovieList(String listCategory) {		
		String uri = "";
		String queryParam = "?language=ko";
				
		
		switch(listCategory) {
		case "now playing":
			uri = "/movie/now_playing" + queryParam;
			break;
			
		case "popular":
			uri = "/movie/popular" + queryParam;
			break;
			
		case "top rated":
			uri = "/movie/top_rated" + queryParam;
			break;
			
		case "upcoming":
			uri = "/movie/upcoming" + queryParam;
			break;
			
		default:
			log.info("getMovieList()에 잘못된 파라미터 입력함");
			break;			
		}
		
		WebClient client = WebClient.create("https://api.themoviedb.org/3");
		
		MovieListDto movieListDto = client.get()
				.uri(uri)
				//.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZDI4ZDdiZDdlNTUwZWE4NmRmMmI2NGRmYjJhYmUzNiIsInN1YiI6IjY1YjA5NzdkZGQ5MjZhMDE3MzRjZDQxNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VpevgFlBqIQZ7hzHFm5gib-kLSaWg8uFl9814rvllnU")
				.header("Authorization", token)
				.retrieve()
				.bodyToMono(MovieListDto.class)
				.block();
		
		return movieListDto;
	}
	
	
}
