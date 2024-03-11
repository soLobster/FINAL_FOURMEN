package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

// 이 클래스는 추상 클래스로, 실제 타입은 MultiMovieDto, MultiTvDto, MultiPeopleDto 중 하나! (검색 결과에 따라 달라짐)
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "media_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MultiMovieDto.class, name = "movie"),
        @JsonSubTypes.Type(value = MultiTvDto.class, name = "tv"),
        @JsonSubTypes.Type(value = MultiPeopleDto.class, name = "person")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MediaItem {

    @JsonProperty("media_type")
    private String mediaType;

}