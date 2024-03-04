package com.itwill.teamfourmen.dto.tvshow;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowWatchProviderDTO {

    private int provider_id;
    private String provider_name;
    private String logo_path;

}
