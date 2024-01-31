package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowSnsDTO {
    private int id;
    private String imdb_id;
    // private int tvdb_id; 보류
    private String facebook_id;
    private String instagram_id;
    private String twitter_id;
}
