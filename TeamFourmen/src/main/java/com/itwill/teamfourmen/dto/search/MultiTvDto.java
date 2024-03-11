package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
public class MultiTvDto extends MediaItem {

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;

    @JsonProperty("id")
    private int id;

    @JsonProperty("origin_country")
    private List<String> originCountry;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_name")
    private String originalName;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("popularity")
    private double popularity;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("first_air_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate firstAirDate;

    @JsonProperty("name")
    private String name;

    @JsonProperty("vote_average")
    private double voteAverage;

    @JsonProperty("vote_count")
    private int voteCount;

    @JsonProperty("media_type")
    private String mediaType;


}
