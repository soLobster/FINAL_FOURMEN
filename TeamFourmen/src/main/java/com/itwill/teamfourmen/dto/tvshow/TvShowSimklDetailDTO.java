package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowSimklDetailDTO {

    private String title;
    private TvShowIDsDTO ids;
    private Map<String ,TvShowImdbRatingDTO> ratings;

}
