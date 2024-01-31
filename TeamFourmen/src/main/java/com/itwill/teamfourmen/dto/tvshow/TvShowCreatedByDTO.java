package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowCreatedByDTO {
    private int id;
    private String credit_id;
    private String name;
    private int gender;
    private String profile_path;
}
