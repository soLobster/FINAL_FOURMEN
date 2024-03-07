package com.itwill.teamfourmen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itwill.teamfourmen.dto.search.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.itwill.teamfourmen.dto.search.SearchResult;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SearchService {

    @Value("${tmdb.api.key}")
    private String apiKey;
    private static final String apiUrl = "https://api.themoviedb.org/3";
    private final WebClient webClient;

    @Autowired
    public SearchService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
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
    public SearchResult<MediaItem> getSearchMultiList(String query, int page) {
        String uri = String.format(apiUrl + "/search/multi?api_key=%s&query=%s&language=ko-KR&include_adult=false&page=%d",
                apiKey, query, page);

        try {
            JsonNode node = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            JsonNode resultsNode = node.get("results"); // "results" 키에 접근

            if (resultsNode != null) {
                ObjectMapper mapper = new ObjectMapper()
                        .registerModule(new JavaTimeModule()) // Java 8 날짜/시간 모듈 등록
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 알려지지 않은 속성이 있어도 실패하지 않도록 설정

                // 역직렬화 대상 타입을 MediaItem[].class로 변경
                MediaItem[] mediaItems = mapper.treeToValue(resultsNode, MediaItem[].class);
                SearchResult<MediaItem> searchMultiResult = new SearchResult<>();
                searchMultiResult.setResults(Arrays.asList(mediaItems));
                return searchMultiResult;

            }
        } catch (JsonProcessingException e) {
            log.error("JSON processing exception: ", e);
        } catch (Exception e) {
            log.error("Error retrieving search results: ", e);
        }
        return null; // 예외 발생시 null 반환
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
        // API 요청 URL 생성
        String uri = String.format(apiUrl + "/search/person?api_key=%s&query=%s&include_adult=false&language=ko-KR&page=%d",
                apiKey, query, page);

        try {
            JsonNode node = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (node != null) {
                JsonNode resultsNode = node.get("results"); // API 응답에서 "results" 노드에 접근
                if (resultsNode != null) {
                    ObjectMapper mapper = new ObjectMapper()
                            .registerModule(new JavaTimeModule()) // Java 8 날짜/시간 모듈 등록
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 알려지지 않은 속성이 있어도 실패하지 않도록 설정

                    // resultsNode를 SearchPeopleDto 배열로 변환하고 리스트로 변환하여 반환
                    List<SearchPeopleDto> searchPeopleList = Arrays.asList(mapper.treeToValue(resultsNode, SearchPeopleDto[].class));
                    return searchPeopleList;
                }
            }
        } catch (JsonProcessingException e) {
            log.error("JSON processing exception: ", e);
        } catch (Exception e) {
            log.error("Error retrieving search results: ", e);
        }

        return Collections.emptyList(); // 예외 발생 시 빈 리스트 반환
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
        String uri = String.format(apiUrl + "/search/movie?api_key=%s&query=%s&include_adult=false&language=ko-KR&page=%d",
                apiKey, query, page);

        try {
            JsonNode node = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (node != null) {
                JsonNode resultsNode = node.get("results"); // API 응답에서 "results" 노드에 접근
                if (resultsNode != null) {
                    ObjectMapper mapper = new ObjectMapper()
                            .registerModule(new JavaTimeModule()) // Java 8 날짜/시간 모듈 등록
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 알려지지 않은 속성이 있어도 실패하지 않도록 설정

                    // resultsNode를 SearchMoviesDto 배열로 변환하고 리스트로 변환하여 반환
                    List<SearchMoviesDto> searchMoviesList = Arrays.asList(mapper.treeToValue(resultsNode, SearchMoviesDto[].class));
                    return searchMoviesList;
                }
            }
        } catch (JsonProcessingException e) {
            log.error("JSON processing exception: ", e);
        } catch (Exception e) {
            log.error("Error retrieving search results: ", e);
        }

        return Collections.emptyList(); // 예외 발생 시 빈 리스트 반환
    }

    /**
     * JSON 데이터를 List<SearchTvShowsDto> 객체로 변환.
     * 검색 기능 중, "tv"를 검색하기 위함(tv 검색 기능)
     * 검색 결과 데이터를 리스트 형태로 받아서 사용하기 위한 메서드.
     * @param query, page
     * 파라미터는 검색어 String query -> 유저가 검색창에 입력한 값 / int page
     * @return JSON 데이터를 매핑한 searchTvShowsList 리스트 객체.
     */
    @SneakyThrows
    public SearchResult<SearchTvShowsDto> getSearchTvsList(String query, int page) {

        // API 요청 주소 생성
        String uri = String.format(apiUrl + "/search/tv?api_key=%s&query=%s&include_adult=false&language=ko-KR&page=%d",
                apiKey, query, page);

        SearchResult<SearchTvShowsDto> searchResult = new SearchResult<>();

        try {
            JsonNode node = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (node != null) {
//                JsonNode resultsNode = node.get("results"); // API 응답에서 "results" 노드에 접근
//                if (resultsNode != null) {
                    ObjectMapper mapper = new ObjectMapper()
                            .registerModule(new JavaTimeModule()) // Java 8 날짜/시간 모듈 등록
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 알려지지 않은 속성이 있어도 실패하지 않도록 설정

//                    // resultsNode를 SearchTvShowsDto 배열로 변환하고 리스트로 변환하여 반환
//                    List<SearchTvShowsDto> searchTvShowsList = Arrays.asList(mapper.treeToValue(resultsNode, SearchTvShowsDto[].class));
//                    return searchTvShowsList;

                // 'results' 배열을 SearchTvShowsDto 리스트로 변환
                List<SearchTvShowsDto> results = Arrays.asList(mapper.treeToValue(node.get("results"), SearchTvShowsDto[].class));

                // 'page', 'total_results', 'total_pages' 값을 포함하는 SearchResult 객체 생성
                searchResult.setPage(node.get("page").asInt());
                searchResult.setTotalResults(node.get("total_results").asInt());
                searchResult.setTotalPages(node.get("total_pages").asInt());
                searchResult.setResults(results);

                }
        } catch (JsonProcessingException e) {
            log.error("JSON processing exception: ", e);
        } catch (Exception e) {
            log.error("Error retrieving search results: ", e);
        }

        return null;
    }

}