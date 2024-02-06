package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowDTO {

    private boolean adult;
    private String backdrop_path;
    private List<TvShowCreatedByDTO> created_by;
    // private List<Integer> episode_run_time;
    private String first_air_date;
    private List<TvShowGenreDTO> genres;
    private String homepage;
    private int id;
    private boolean in_production;
    // private List<String> languages;
    private String last_air_date;
    // private Episode last_episode_to_air;
    private String name;
    // private Episode next_episode_to_air;
    private List<TvShowNetworkDTO> networks;
    private int number_of_episodes;
    private int number_of_seasons;
    // private List<String> origin_country;
    private String original_language;
    private String original_name;
    private String overview;
    private double popularity;
    private String poster_path;
    // private List<ProductionCompany> production_companies;
    // private List<ProductionCountry> production_countries;
    private List<TvShowSeasonDTO> seasons;
    // private List<SpokenLanguage> spoken_languages;
    private String status;
    private String tagline;
    private String type;
    private double vote_average;
    private int vote_count;

}

