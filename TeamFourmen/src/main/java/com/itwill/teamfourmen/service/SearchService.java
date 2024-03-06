package com.itwill.teamfourmen.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
}