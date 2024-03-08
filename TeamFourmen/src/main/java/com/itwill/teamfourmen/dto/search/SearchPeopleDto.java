package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchPeopleDto {

    @JsonProperty("page")
    private int page;

    @JsonProperty("results")
    private List<PeopleDto> peopleResults;

    @JsonProperty("total_pages")
    private String totalPages; // 총 검색 결과 페이지

    @JsonProperty("total_results")
    private String totalResults; // 총 검색 결과 수

}
