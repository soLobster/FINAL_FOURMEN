package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// DTO 는 만들었지만.. collection 은 미사용할 예정
// 추후에 컬렉션 페이지를 추가하게 되면 그 때 사용하시길..

@Data
public class SearchCollectionsDto {

    @JsonProperty("name")
    private String collectionsName;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("id")
    private int collectionsId;
    @JsonProperty("overview")
    private String overview;

}
