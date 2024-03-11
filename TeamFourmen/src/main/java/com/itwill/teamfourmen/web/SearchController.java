package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.dto.search.*;
import com.itwill.teamfourmen.service.PersonService;
import com.itwill.teamfourmen.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/multi")
    public String searchMulti(@RequestParam(name = "query", required = false, defaultValue = "") String query,
                              @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                              Model model) {

        try {
            List<MultiSearchResponse> searchMultiList = searchService.getSearchMultiListAndPageInfo(query, page);
            SearchMultiResultsDto searchResultsDto = new SearchMultiResultsDto();

            // query를 searchResultDto 객체에 설정.
            searchResultsDto.setQuery(query);

            // 새로운 리스트로 초기화하여 null을 방지
            searchResultsDto.setMovies(new ArrayList<>());
            searchResultsDto.setTvShows(new ArrayList<>());
            searchResultsDto.setPeople(new ArrayList<>());

            if (!searchMultiList.isEmpty()) {
                MultiSearchResponse response = searchMultiList.get(0);
                searchResultsDto.setTotalPages(response.getTotalPages());
                searchResultsDto.setCurrentPage(page);

                for (MediaItem item : searchMultiList.get(0).getResults()) {
                    // item의 실제 타입에 따라 적절한 리스트에 추가
                    if (item instanceof MultiMovieDto) {
                        searchResultsDto.getMovies().add((MultiMovieDto) item);
                    } else if (item instanceof MultiTvDto) {
                        searchResultsDto.getTvShows().add((MultiTvDto) item);
                    } else if (item instanceof MultiPeopleDto) {
                        searchResultsDto.getPeople().add((MultiPeopleDto) item);
                    }
                }

                log.info("************************ 로그 출력 테스트 ************************");
//                log.info("서비스 컨트롤러에서 출력합니다...");
//                log.info("영화 데이터 출력: {}", searchResultsDto.getMovies().get(0).getReleaseDate());
//                log.info("영화 데이터 출력: {}", searchResultsDto.getMovies().get(0).getPosterPath());
//                log.info("영화 데이터 출력: {}", searchResultsDto.getMovies().get(0).getTitle());
//                log.info("searchResults 출력: {}", searchResultsDto);
                log.info("************************ 로그 출력 테스트 ************************");

                model.addAttribute("searchResults", searchResultsDto);
            }
        } catch (Exception e) {
            log.error("검색 중 오류 발생: ", e);
            model.addAttribute("error", "검색 중 오류가 발생했습니다.");
            return null;
        }

//        SearchPeopleDto searchPeopleList = searchService.searchPeople(query, page);
//        model.addAttribute("searchPeopleList", searchPeopleList);
//
//        SearchMoviesDto searchMoviesList = searchService.searchMovies(query, page);
//        model.addAttribute("searchMoviesList", searchMoviesList);
//
//        SearchTvShowsDto searchTvShowsList = searchService.searchTvShows(query, page);
//        model.addAttribute("searchTvShowsList", searchTvShowsList);

        return "search/search-multi";
    }

}
