package com.itwill.teamfourmen.dto.person;

import lombok.Data;

import java.util.List;

@Data
public class TvCreditsDto {

    private List<TvCreditsCastDto> cast;
    private List<TvCreditsCrewDto> crew;
    private int id;

}

