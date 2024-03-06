package com.itwill.teamfourmen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itwill.teamfourmen.dto.search.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class SearchService {

    @Value("${tmdb.api.key}")
    private String apiKey;
    private static final String SEARCH = "search";
    private static final String apiUrl = "https://api.themoviedb.org/3";
    private final WebClient webClient;

    @Autowired
    public SearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
    }

    // 영화 검색
    public Mono<String> searchMovies(String query) {
        return search("/search/movie", query);
    }

    // TV 프로그램 검색
    public Mono<String> searchTvShows(String query) {
        return search("/search/tv", query);
    }

    // 멀티 카테고리 검색 (영화, TV 프로그램, 인물)
    public Mono<String> searchMulti(String query) {
        return search("/search/multi", query);
    }

    // 컬렉션 검색
    public Mono<String> searchCollections(String query) {
        return search("/search/collections", query);
    }

    // 인물 검색
    public Mono<String> searchPeople(String query) {
        return search("/search/person", query);
    }

    // 범용 검색 메소드
    private Mono<String> search(String path, String query) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(path)
                        .queryParam("api_key", apiKey)
                        .queryParam("query", query)
                        .queryParam("include_adult", "false")
                        .queryParam("language", "ko-KR")
                        .queryParam("page", "1")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> log.error("Error fetching data from TMDB: ", error))
                .doOnSuccess(response -> log.info("Successfully fetched data for path " + path + " and query " + query));
    }

    // 결국 DTO 사용... ㅠㅠ
    /**
     * JSON 데이터를 List<SearchMultiDto> 객체로 변환.
     * 검색 기능 중, "movie", "tv", "person"을 검색하기 위함(일종의 전체 검색 기능)
     * 검색 결과 데이터를 리스트 형태로 받아서 사용하기 위한 메서드.
     * @param query, page
     * 파라미터는 검색어 String query -> 유저가 검색창에 입력한 값 / int page
     * @return JSON 데이터를 매핑한 searchMultiList 리스트 객체.
     */
    public List<SearchMultiDto> getSearchMultiList(String query, int page) {

        // API 요청 주소 생성
        String uri = String.format(apiUrl + "/search/multi" + "?api_key=%s&query=%s&include_adult=false&language=ko-KR&page=%d", query, page);

        SearchMultiDto searchMultiDto;
        JsonNode node = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        JsonNode castNode = node.get("cast");

        ObjectMapper mapper = new ObjectMapper();

        try {
            SearchMultiDto[] castArray = mapper.treeToValue(castNode, SearchMultiDto[].class);
            List<SearchMultiDto> searchMultiList = Arrays.asList(castArray);
            return searchMultiList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * JSON 데이터를 List<SearchPeopleDto> 객체로 변환.
     * 검색 기능 중, "person"을 검색하기 위함(인물 검색 기능)
     * 검색 결과 데이터를 리스트 형태로 받아서 사용하기 위한 메서드.
     * @param query, page
     * 파라미터는 검색어 String query -> 유저가 검색창에 입력한 값 / int page
     * @return JSON 데이터를 매핑한 searchPeopleList 리스트 객체.
     */
    public List<SearchPeopleDto> getSearchPeopleList(String query, int page) {

        // API 요청 주소 생성
        String uri = String.format(apiUrl + "/search/person" + "?api_key=%s&query=%s&include_adult=false&language=ko-KR&page=%d", query, page);

        SearchPeopleDto searchPeopleDto;
        JsonNode node = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        JsonNode castNode = node.get("cast");

        ObjectMapper mapper = new ObjectMapper();

        try {
            SearchPeopleDto[] castArray = mapper.treeToValue(castNode, SearchPeopleDto[].class);
            List<SearchPeopleDto> searchPeopleList = Arrays.asList(castArray);
            return searchPeopleList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * JSON 데이터를 List<SearchMoviesDto> 객체로 변환.
     * 검색 기능 중, "movie"를 검색하기 위함(영화 검색 기능)
     * 검색 결과 데이터를 리스트 형태로 받아서 사용하기 위한 메서드.
     * @param query, page
     * 파라미터는 검색어 String query -> 유저가 검색창에 입력한 값 / int page
     * @return JSON 데이터를 매핑한 searchMoviesList 리스트 객체.
     */
    public List<SearchMoviesDto> getSearchMoviesList(String query, int page) {

        // API 요청 주소 생성
        String uri = String.format(apiUrl + "/search/movie" + "?api_key=%s&query=%s&include_adult=false&language=ko-KR&page=%d", query, page);

        SearchMoviesDto searchMoviesDto;
        JsonNode node = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        JsonNode castNode = node.get("cast");

        ObjectMapper mapper = new ObjectMapper();

        try {
            SearchMoviesDto[] castArray = mapper.treeToValue(castNode, SearchMoviesDto[].class);
            List<SearchMoviesDto> searchMoviesList = Arrays.asList(castArray);
            return searchMoviesList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * JSON 데이터를 List<SearchTvShowsDto> 객체로 변환.
     * 검색 기능 중, "tv"를 검색하기 위함(tv 검색 기능)
     * 검색 결과 데이터를 리스트 형태로 받아서 사용하기 위한 메서드.
     * @param query, page
     * 파라미터는 검색어 String query -> 유저가 검색창에 입력한 값 / int page
     * @return JSON 데이터를 매핑한 searchTvShowsList 리스트 객체.
     */
    public List<SearchTvShowsDto> getSearchTvsList(String query, int page) {

        // API 요청 주소 생성
        String uri = String.format(apiUrl + "/search/tv" + "?api_key=%s&query=%s&include_adult=false&language=ko-KR&page=%d", query, page);

        SearchTvShowsDto searchTvShowsDto;
        JsonNode node = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        JsonNode castNode = node.get("cast");

        ObjectMapper mapper = new ObjectMapper();

        try {
            SearchTvShowsDto[] castArray = mapper.treeToValue(castNode, SearchTvShowsDto[].class);
            List<SearchTvShowsDto> searchTvShowsList = Arrays.asList(castArray);
            return searchTvShowsList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

    }

}