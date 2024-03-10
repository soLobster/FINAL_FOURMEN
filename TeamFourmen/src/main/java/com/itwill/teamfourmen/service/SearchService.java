package com.itwill.teamfourmen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itwill.teamfourmen.dto.search.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;


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
     * JSON 데이터를 List<MedaiItem> 객체로 변환.
     * 검색 기능 중, "movie", "tv", "person"을 검색하기 위함(일종의 전체 검색 기능)
     * 검색 결과 데이터를 리스트 형태로 받아서 사용하기 위한 메서드.
     * @param query, page
     * 파라미터는 검색어 String query -> 유저가 검색창에 입력한 값 / int page
     * @return JSON 데이터를 매핑한 searchMultiResultsList 리스트 객체.
     */
    public List<MultiSearchResponse> getSearchMultiListAndPageInfo(String query, int page) {
        String uri = String.format(apiUrl + "/search/multi?api_key=%s&query=%s&language=ko-KR&include_adult=false&page=%d", apiKey, query, page);
        List<MultiSearchResponse> searchMultiResponseList = new ArrayList<>();

        try {
            JsonNode node = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            if (node != null) {
                ObjectMapper mapper = new ObjectMapper()
                        .registerModule(new JavaTimeModule())
                        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                // 결과 데이터 추출
                List<MediaItem> results = Arrays.asList(mapper.treeToValue(node.get("results"), MediaItem[].class));

                // 페이징 정보 추출
                int totalPages = node.get("total_pages").asInt();
                int totalResults = node.get("total_results").asInt();

                // MultiSearchResponse 객체 생성 및 리스트에 추가
                MultiSearchResponse multiSearchResponse = new MultiSearchResponse();
                multiSearchResponse.setQuery(query); // 쿼리 추가하여 영화, tv 검색 페이지로 이동할 때 사용 가능하게 함. (기존의 잘 만들어진 검색 페이지 사용 목적..)
                multiSearchResponse.setResults(results);
                multiSearchResponse.setTotalPages(totalPages);
                multiSearchResponse.setTotalResults(totalResults);
                multiSearchResponse.setCurrentPage(page);

                searchMultiResponseList.add(multiSearchResponse);
            }
        } catch (JsonProcessingException e) {
            log.error("JSON processing exception: ", e);
        } catch (Exception e) {
            log.error("Error retrieving search results: ", e);
        }

        log.info("*************** 서치 서비스 클래스에서 데이터 출력 테스트 ***************");
        log.info("쿼리가 잘 전달되었는지 확인: {}", query);

        return searchMultiResponseList; // 검색 결과와 페이징 정보 및 쿼리를 포함하는 MultiSearchResponse 객체를 담은 리스트 반환
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
        String uri = String.format("%s/search/person?api_key=%s&query=%s&include_adult=false&language=ko-KR&page=%d", apiUrl, apiKey, query, page);
        try {
            JsonNode node = webClient.get().uri(uri).retrieve().bodyToMono(JsonNode.class).block();
            if (node != null && node.has("results")) {
                ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return Arrays.asList(mapper.treeToValue(node.get("results"), SearchPeopleDto[].class));
            }
        } catch (JsonProcessingException e) {
            log.error("JSON processing exception for URI {}: ", uri, e);
        } catch (Exception e) {
            log.error("Error retrieving search results for URI {}: ", uri, e);
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
            JsonNode resultsNode = node.get("results"); // API 응답에서 "results" 노드에 접근

            if (resultsNode != null) {
                ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule()) // Java 8 날짜/시간 모듈 등록
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 알려지지 않은 속성이 있어도 실패하지 않도록 설정

                // resultsNode를 SearchMoviesDto 배열로 변환하고 리스트로 변환하여 반환
                List<SearchMoviesDto> searchMoviesList = Arrays.asList(mapper.treeToValue(resultsNode, SearchMoviesDto[].class));
                return searchMoviesList;
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
    public List<SearchTvShowsDto> getSearchTvsList(String query, int page) {

        // API 요청 주소 생성
        String uri = String.format(apiUrl + "/search/tv?api_key=%s&query=%s&include_adult=false&language=ko-KR&page=%d",
                apiKey, query, page);

        try {
            JsonNode node = webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            JsonNode resultsNode = node.get("results");

            if (resultsNode != null) {
                ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule()) // Java 8 날짜/시간 모듈 등록
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 알려지지 않은 속성이 있어도 실패하지 않도록 설정

                // resultsNode를 SearchTvShowsDto 배열로 변환하고 리스트로 변환하여 반환
                List<SearchTvShowsDto> searchTvShowsList = Arrays.asList(mapper.treeToValue(resultsNode, SearchTvShowsDto[].class));
                return searchTvShowsList;
                }
        } catch (JsonProcessingException e) {
            log.error("JSON processing exception: ", e);
        } catch (Exception e) {
            log.error("Error retrieving search results: ", e);
        }
        return Collections.emptyList(); // 예외 발생 시 빈 리스트 반환
    }

}