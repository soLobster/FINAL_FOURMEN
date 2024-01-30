package com.itwill.teamfourmen.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itwill.teamfourmen.dto.movie.MovieCreditDto;
import com.itwill.teamfourmen.dto.movie.MovieDetailsDto;
import com.itwill.teamfourmen.dto.movie.MovieGenreDto;
import com.itwill.teamfourmen.dto.movie.MovieListDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieApiUtil {
	
	@Value("${tmdb.hd.token}")
	private String token;
	
	@Value("${tmdb.api.baseurl}")
	private String baseUrl;
	
	/**
	 * 영화 리스트를 MovieListDto객체로 돌려주는 메서드.
	 * 파라미터는 무조건 "popular", "now_playing", "top_rated", "upcoming" 중 하나여야 함!
	 * @param listCategory 무조건 "popular", "now_playing", "top_rated", "upcoming" 중 하나
	 * @return 받아온 json데이터를 매핑한 MovieListDto객체
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	public MovieListDto getMovieList(String listCategory, int page) throws JsonMappingException, JsonProcessingException {
		log.info("getMovieList(listCategory={}, page={})", listCategory, page);
		
		String uri = "";
		String queryParam = "?language=ko&page=" + page;
				
		
		switch(listCategory) {
		case "now_playing":
			uri = "/movie/now_playing" + queryParam;
			break;
			
		case "popular":
			uri = "/movie/popular" + queryParam;
			break;
			
		case "top_rated":
			uri = "/movie/top_rated" + queryParam;
			break;
			
		case "upcoming":
			uri = "/movie/upcoming" + queryParam;
			break;
			
		default:
			log.info("getMovieList()에 잘못된 파라미터 입력함");
			break;			
		}
		
		WebClient client = WebClient.create(baseUrl);
		
		MovieListDto movieListDto = client.get()
				.uri(uri)				
				.header("Authorization", token)
				.retrieve()
				.bodyToMono(MovieListDto.class)
				.block();
		
//		ObjectMapper mapper = new ObjectMapper();
//		MovieListDto movieListDto = mapper.readValue(json, MovieListDto.class);
		
		return movieListDto;
	}
	
	
	/**
	 * 영화 장르 모음들을 가져오는 메서드
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public List<MovieGenreDto> getMovieGenreList() throws JsonMappingException, JsonProcessingException {
		
		WebClient client = WebClient.create(baseUrl);
		
		String json = client.get()
			.uri("/genre/movie/list?language=ko")
			.header("Authorization", token)
			.retrieve()
			.bodyToMono(String.class)
			.block();			
		
		ObjectMapper mapper = new ObjectMapper();		
		
		JsonNode array = mapper.readValue(json, JsonNode.class);
		JsonNode genres = array.get("genres");			
		
		MovieGenreDto[] movieGenreArray = mapper.treeToValue(genres, MovieGenreDto[].class);		
		
		List<MovieGenreDto> movieGenreList = Arrays.asList(movieGenreArray);
		
		return movieGenreList;
	}
	
	
	/**
	 * Movie Details Dto를 반환함.
	 * @param id details을 보고싶은 영화의 id
	 * @return id에 해당하는 영화의 detail정보를 포함한 MovieDetailsDto 반환 
	 */
	public MovieDetailsDto getMovieDetails(int id) {
		
		String queryParam = "?language=ko";
		
		WebClient client = WebClient.create(baseUrl);
		
		MovieDetailsDto movieDetailsDto = client.get()
			.uri("/movie/" + id + queryParam)
			.header("Authorization", token)
			.retrieve()
			.bodyToMono(MovieDetailsDto.class)
			.block();
		
		log.info("movieDetailsDto={}", movieDetailsDto);
		
		return movieDetailsDto;
	}
	
	
	public MovieCreditDto getMovieCredit(int id) {
		
		String queryParam = "?language=ko";
		
		WebClient client = WebClient.create(baseUrl);
		
		MovieCreditDto movieCreditDto = client.get()
				.uri("/movie/"+ id + "/credits")
				.header("Authorization", token)
				.retrieve()
				.bodyToMono(MovieCreditDto.class)
				.block();
		
		log.info("movieCreditDto={}", movieCreditDto);
		
		return movieCreditDto;
	}
	
}
