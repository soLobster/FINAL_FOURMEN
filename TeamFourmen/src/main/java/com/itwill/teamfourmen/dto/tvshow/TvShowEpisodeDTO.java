package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowEpisodeDTO {

    private String air_date;
    private int episode_number;
    private String episode_type;
    private long id;
    private String name ;
    private String overview;
    //private String production_code;
    private int runtime;
    private int show_id;
    private String still_path;
    private double vote_average;
    private int vote_count;
    // private List<TvShowCrewDTO> crew
    // private List<TVShowGuestStarsDTO> guest_stars;
}
