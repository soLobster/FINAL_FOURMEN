package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchPeopleDto {

    @JsonProperty("page")
    private int page;
    @JsonProperty("results")
    private List<ResultItemDto> peopleResults;
//    @JsonProperty("total_pages")
//    @JsonProperty("total_results")


    @JsonProperty("media_type")
    private String mediaType; // "movie", "tv", "person" 중 하나.
    @JsonProperty("name")
    private String personName; // 인물의 이름
    @JsonProperty("id")
    private String personId;
    @JsonProperty("profile_path")
    private String profilePath; // 인물의 프로필 이미지
    @JsonProperty("known_for")
    private String knownFor; // 인물의 대표작 3개
    @JsonProperty("total_pages")
    private String totalPages; // 총 검색 결과 페이지
    @JsonProperty("total_results")
    private String totalResults; // 총 검색 결과 수

}
