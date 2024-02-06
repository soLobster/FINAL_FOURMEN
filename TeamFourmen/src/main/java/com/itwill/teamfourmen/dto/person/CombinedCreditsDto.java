package com.itwill.teamfourmen.dto.person;

import lombok.Data;

import java.util.List;

@Data
public class CombinedCreditsDto {

    private List<CombinedCreditsCastDto> cast;
    private List<CombinedCreditsCrewDto> crew;
    private int id;

}

