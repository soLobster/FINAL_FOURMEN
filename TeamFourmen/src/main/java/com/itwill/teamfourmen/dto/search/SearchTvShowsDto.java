package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchTvShowsDto {

    @JsonProperty("page")
    private int page;

    @JsonProperty("results")
    private List<TvShowsDto> tvResults;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private int totalResults;

}
