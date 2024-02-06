package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowCreditDTO {

    private int id;
    private int gender;
    private String known_for_department;
    private String name;
    private String original_name;
    private String profile_path;
    private String character;
    private String credit_id;
    private int order;

    // crew
    private String department;
    private String job;

}
