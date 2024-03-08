package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowCreditListDTO {

    private List<TvShowCreditDTO> cast;
    private List<TvShowCreditDTO> crew;
    private int id;

}
