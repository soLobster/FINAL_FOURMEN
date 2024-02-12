package com.itwill.teamfourmen.dto.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class CombinedCreditsCastIntegratedDateDto {

    private boolean adult;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    private int id;
    @JsonProperty("origin_country")
    private List<String> originCountry;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("original_name")
    private String originalName;
    private String overview;
    private double popularity;
    @JsonProperty("poster_path")
    private String posterPath;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String date; // release_date 와 first_air_date 를 다루는 하나의 변수.

    @JsonProperty("first_air_date")
    private String firstAirDate;
    @JsonProperty("release_date")
    private String releaseDate;
    private String title;
    private String name;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    private String character;
    @JsonProperty("credit_id")
    private String creditId;
    @JsonProperty("episode_count")
    private int episodeCount;
    private int order;
    @JsonProperty("media_type")
    private String mediaType;

}
