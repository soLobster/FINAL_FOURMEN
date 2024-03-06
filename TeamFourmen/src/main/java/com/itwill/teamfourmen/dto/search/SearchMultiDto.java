package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SearchMultiDto {

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
    @JsonProperty("name")
    private String personName; // 인물의 이름
    @JsonProperty("profile_path")
    private String profilePath; // 인물의 프로필 이미지
    @JsonProperty("known_for")
    private String knownFor; // 인물의 대표작 3개

}
