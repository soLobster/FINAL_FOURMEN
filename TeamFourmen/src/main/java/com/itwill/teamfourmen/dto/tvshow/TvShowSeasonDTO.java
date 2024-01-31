package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowSeasonDTO {

    private String air_date;
    private int episode_count;
    private int id;
    private String name;
    private String overview;
    private String poster_path;
    private int season_number;
    private double vote_average;

}
