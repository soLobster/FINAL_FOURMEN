package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowRecoDTO {

    private int id;
    private String backdrop_path;
    private String name;
    private String original_name;
    private String media_type;
    private String first_air_date;


}
