package com.itwill.teamfourmen.dto.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CombinedCreditsDto {

    private List<CombinedCastDTO> cast;
    private List<CombinedCrewDTO> crew;
    private int id;

}

@Data
class CombinedCastDTO {

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
    @JsonProperty("original_name")
    private String originalName;
    private String overview;
    private double popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("first_air_date")
    private String firstAirDate;
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
    @JsonProperty("media_type")
    private String mediaType;

}

@Data
class CombinedCrewDTO {

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
    private String overview;
    private double popularity;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("release_date")
    private String releaseDate;
    private String title;
    @JsonProperty("video")
    private boolean video;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    @JsonProperty("credit_id")
    private String creditId;
    private String department;
    private String job;
    @JsonProperty("media_type")
    private String mediaType;

}