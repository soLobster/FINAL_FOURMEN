package com.itwill.teamfourmen.dto.person;



import lombok.Data;

import java.util.List;

@Data
public class MovieCreditsDto {

    private List<MovieCreditsCastDTO> cast;
    private List<MovieCreditsCrewDTO> crew;
    private int id;

}

