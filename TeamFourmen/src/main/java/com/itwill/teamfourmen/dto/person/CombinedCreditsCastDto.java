package com.itwill.teamfourmen.dto.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Comparator;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CombinedCreditsCastDto {

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

    public static final Comparator<CombinedCreditsCastDto> SORT_RELEASE_DATE_ASC = new Comparator<CombinedCreditsCastDto>() {

        @Override
        public int compare(CombinedCreditsCastDto o1, CombinedCreditsCastDto o2) {
            return o1.getReleaseDate().compareTo(o2.releaseDate);
        }
    };

}
