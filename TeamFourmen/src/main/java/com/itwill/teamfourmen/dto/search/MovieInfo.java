package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieInfo extends MediaItem {

//    @JsonProperty("page")
//    private int page;
//
//    @JsonProperty("results")
//    private List<MovieInfo> movieResults;
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
    private String movieBackdropPath;

    @JsonProperty("genre_ids")
    private List<Integer> movieGenreIds;

    @JsonProperty("id")
    private int movieId;

    @JsonProperty("original_language")
    private String movieOriginalLanguage;

    @JsonProperty("original_title")
    private String movieOriginalTitle;

    @JsonProperty("overview")
    private String movieOverview;

    @JsonProperty("popularity")
    private double moviePopularity;

    @JsonProperty("poster_path")
    private String moviePosterPath;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("release_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate movieReleaseDate;

    @JsonProperty("title")
    private String movieTitle;

    @JsonProperty("video")
    private boolean movieVideo;

    @JsonProperty("vote_average")
    private double movieVoteAverage;

    @JsonProperty("vote_count")
    private int movieVoteCount;

}
