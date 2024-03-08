package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TvShowInfo extends MediaItem {

//    @JsonProperty("page")
//    private int page;
//
//    @JsonProperty("results")
//    private List<TvShowInfo> tvResults;
//
//    @JsonProperty("total_pages")
//    private int totalPages;
//
//    @JsonProperty("total_results")
//    private int totalResults;

    //////////////////////
    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("backdrop_path")
    private String tvBackdropPath;

    @JsonProperty("genre_ids")
    private List<Integer> tvGenreIds;

    @JsonProperty("id")
    private int tvId;

    @JsonProperty("origin_country")
    private List<String> tvOriginCountry;

    @JsonProperty("original_language")
    private String tvOriginalLanguage;

    @JsonProperty("original_name")
    private String tvOriginalName;

    @JsonProperty("overview")
    private String tvOverview;

    @JsonProperty("popularity")
    private double tvPopularity;

    @JsonProperty("poster_path")
    private String tvPosterPath;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("first_air_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate tvFirstAirDate;

    @JsonProperty("name")
    private String tvName;

    @JsonProperty("vote_average")
    private double tvVoteAverage;

    @JsonProperty("vote_count")
    private int tvVoteCount;

}
