package com.itwill.teamfourmen.dto.search;

import lombok.Data;

import java.util.List;

@Data
public class SearchMultiResultsDto {

    private List<MultiMovieDto> movies;
    private List<MultiTvDto> tvShows;
    private List<MultiPeopleDto> people;
    private int currentPage;
    private int totalPages;

}
