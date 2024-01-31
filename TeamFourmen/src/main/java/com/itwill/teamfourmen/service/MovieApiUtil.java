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
import com.itwill.teamfourmen.dto.movie.MovieProviderDto;
import com.itwill.teamfourmen.dto.movie.MovieProviderItemDto;
import com.itwill.teamfourmen.dto.movie.MovieVideoDto;

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
	public List<MovieGenreDto> getMovieGenreList() {
		
		WebClient client = WebClient.create(baseUrl);
		
		String json = client.get()
			.uri("/genre/movie/list?language=ko")
			.header("Authorization", token)
			.retrieve()
			.bodyToMono(String.class)
			.block();			
		
		ObjectMapper mapper = new ObjectMapper();		
		
		List<MovieGenreDto> movieGenreList = null;
		
		try {
			JsonNode array = mapper.readValue(json, JsonNode.class);
			JsonNode genres = array.get("genres");			
			MovieGenreDto[] movieGenreArray = mapper.treeToValue(genres, MovieGenreDto[].class);		
			movieGenreList = Arrays.asList(movieGenreArray);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
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
	
	
	/**
	 * 아규먼트로 받은 id에 해당하는 영화의 배우, 크루들의 목록을 MovieCreditDto로 반환
	 * @param id
	 * @return
	 */
	public MovieCreditDto getMovieCredit(int id) {
		
		log.info("MovieCreditDto(id={})", id);
		
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
	
	/**
	 * 아규먼트로 받은 id에 해당하는 영화의 MovieVideoDto리스트를 줌.
	 * 트레일러 리스트만 따로 받고 싶다면 filter를 통해 MovieVideoDto의 type="Trailer"인 요소들만 걸러내면 됨
	 * 
	 * @param id
	 * @return 영화의 비디오 리스트, 없을시 null을 반환
	 */
	public List<MovieVideoDto> getMovieVideoList(int id) {
				
		log.info("getMovieVideoList()");
		
		String queryParam = "?language=ko";
		
		WebClient client = WebClient.create(baseUrl);
		String json = client.get()
			.uri("/movie/" + id + "/videos" + queryParam)
			.header("Authorization", token)
			.retrieve()
			.bodyToMono(String.class)
			.block();
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<MovieVideoDto> movieVideoList = null;
		
		try {
			JsonNode node = mapper.readValue(json, JsonNode.class);
			JsonNode results = node.get("results");
			MovieVideoDto[] movieVideoArray = mapper.treeToValue(results, MovieVideoDto[].class);
			movieVideoList = Arrays.asList(movieVideoArray);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		
		log.info("movie video list = {}", movieVideoList);
		
		return movieVideoList;
	}
	
	
	/**
	 * id에 해당하는 영화의 provider list를 반환. 해당 영화의 provider가 없을 경우 null을 반환함
	 * @param id
	 * @return List<MovieProviderDto> 해당 영화의 provider 리스트, provider가 없을 시 null을 반환
	 */
	public MovieProviderDto getMovieProviderList(int id) {
		
		log.info("getMovieProviderList(id={})", id);
		
		String queryParam = "?language=ko";
		
		WebClient client = WebClient.create(baseUrl);
		String json = client.get()
			.uri("/movie/" + id + "/watch/providers")
			.header("Authorization", token)
			.retrieve()
			.bodyToMono(String.class)
			.block();
		
		ObjectMapper mapper = new ObjectMapper();
		
		List<MovieProviderItemDto> movieProviderList = null;
		
		try {
			JsonNode node = mapper.readValue(json, JsonNode.class);
			JsonNode resultsNode = node.get("results");
			JsonNode countryNode = resultsNode.get("KR");
			MovieProviderDto movieProviderDto = mapper.treeToValue(countryNode, MovieProviderDto.class);
			
			log.info("movieProviderDto={}", movieProviderDto);
			
			return movieProviderDto;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
		
	}
	
	
}
