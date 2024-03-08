package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchMultiDto extends MediaItem {

    @JsonProperty("page")
    private int page;

    @JsonProperty("results")
    private List<MediaItem> multiResults;

    @JsonProperty("total_pages")
    private int totalPages; // 총 검색 결과 페이지

    @JsonProperty("total_results")
    private int totalResults; // 총 검색 결과 수

}
