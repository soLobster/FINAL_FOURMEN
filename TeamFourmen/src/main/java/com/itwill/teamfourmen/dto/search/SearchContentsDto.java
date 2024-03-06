package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// 이 dto 도 사용하지 않을 듯? 아마도...

@Data
public class SearchContentsDto {

    @JsonProperty("media_type")
    private String mediaType; // "movie", "tv", "person" 중 하나.
    @JsonProperty("backdrop_path")
    private String backdropPath; // movie, tv의 뒷배경
    @JsonProperty("poster_path")
    private String posterPath; // movie, tv의 포스터 이미지
    @JsonProperty("first_air_date")
    private String firstAirDate; // tv의 첫 번째 방영일
    @JsonProperty("name")
    private String tvName; // tv의 제목
    @JsonProperty("title")
    private String movieTitle; // movie 의 제목
    @JsonProperty("release_date")
    private String releaseDate; // movie 의 개봉일(출시일)
    @JsonProperty("total_pages")
    private String totalPages; // 총 검색 결과 페이지
    @JsonProperty("total_results")
    private String totalResults; // 총 검색 결과 수

}
