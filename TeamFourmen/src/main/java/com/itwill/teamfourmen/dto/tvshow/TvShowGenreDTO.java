package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowGenreDTO {

    private int id;
    private String name;

}
