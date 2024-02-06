package com.itwill.teamfourmen.dto.person;



import lombok.Data;

import java.util.List;

@Data
public class MovieCreditsDto {

    private List<MovieCreditsCastDto> cast;
    private List<MovieCreditsCrewDto> crew;
    private int id;

}

