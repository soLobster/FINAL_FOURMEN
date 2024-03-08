package com.itwill.teamfourmen.dto.review;

import com.itwill.teamfourmen.domain.ImdbRatings;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CombineReviewDTO {

    private String posterPath;
    private int id;
    private String name;
    private String category;
    private ImdbRatings imdbRatings;
    private String imdbId;

    private LocalDate openingDate;

    private int year;

}
