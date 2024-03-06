package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SearchPeopleDto {

    @JsonProperty("media_type")
    private String mediaType; // "movie", "tv", "person" 중 하나.
    @JsonProperty("name")
    private String personName; // 인물의 이름
    @JsonProperty("profile_path")
    private String profilePath; // 인물의 프로필 이미지
    @JsonProperty("known_for")
    private String knownFor; // 인물의 대표작 3개

}
