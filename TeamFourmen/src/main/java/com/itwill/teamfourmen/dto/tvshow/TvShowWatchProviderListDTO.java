package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowWatchProviderListDTO {

    private Map<String, TvShowWatchProviderRegionDTO> results;
    private int id;

}
