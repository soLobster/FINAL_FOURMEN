package com.itwill.teamfourmen.dto.tvshow;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowWatchProviderDTO {

    private int provider_id;
    private String provider_name;
    private String logo_path;

}
