package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "media_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MovieDto.class, name = "movie"),
        @JsonSubTypes.Type(value = TvShowsDto.class, name = "tv"),
        @JsonSubTypes.Type(value = PeopleDto.class, name = "person")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MediaItem {

    @JsonProperty("media_type")
    private String mediaType;

}