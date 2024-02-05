package com.itwill.teamfourmen.dto.person;

import lombok.Data;

import java.util.List;

@Data
public class TvCreditsDto {

    private List<TvCreditsCastDTO> cast;
    private List<TvCreditsCrewDTO> crew;
    private int id;

}

