package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MultiSearchResponse extends MediaItem {

    @JsonProperty("results")
    private List<MediaItem> results;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("total_results")
    private int totalResults;

    @JsonProperty("page")
    private int currentPage;

    @JsonProperty("media_type")
    private String mediaType;

}
