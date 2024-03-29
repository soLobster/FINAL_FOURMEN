package com.itwill.teamfourmen.dto.person;

import java.time.LocalDate;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

@Data
public class KnownForDto {
	
    private boolean adult;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    private int id;
    private String name;
    private String title;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_name")
    private String originalName;
    @JsonProperty("original_title")
    private String originalTitle;
    private String overview;
    @JsonProperty("poster_path")
    private String posterPath;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    private double popularity;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("first_air_date")
    private LocalDate firstAirDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("release_date")
    private LocalDate releaseDate;

    @JsonProperty("video")
    private boolean video;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    @JsonProperty("origin_country")
    private List<String> originCountry;
	
}
