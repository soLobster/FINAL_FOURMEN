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
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public String search(@RequestParam(name = "query", required = false, defaultValue = "") String query,
                         @RequestParam(name = "category", required = false) String category,
                         @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                         Model model) {

        // 모델에 검색 쿼리 추가하여 뷰에서 사용할 수 있도록 함.
        model.addAttribute("query", query);

        // 기본 뷰 설정
        String viewName = "search/default-search";

        try {
            switch (category) {
                case "multi":
                    List<MultiSearchResponse> searchMultiList = searchService.getSearchMultiListAndPageInfo(query, page);
                    SearchMultiResultsDto searchResultsDto = new SearchMultiResultsDto();

                    if (!searchMultiList.isEmpty()) {
                        MultiSearchResponse response = searchMultiList.get(0);
                        searchResultsDto.setTotalPages(response.getTotalPages());
                        searchResultsDto.setCurrentPage(page);

                        for (MediaItem item : searchMultiList.get(0).getResults()) {
                            // item의 실제 타입에 따라 적절한 리스트에 추가
                            if (item instanceof MultiMovieDto) {
                                if (searchResultsDto.getMovies() == null) {
                                    searchResultsDto.setMovies(new ArrayList<>());
                                }
                                searchResultsDto.getMovies().add((MultiMovieDto) item);
                            } else if (item instanceof MultiTvDto) {
                                if (searchResultsDto.getTvShows() == null) {
                                    searchResultsDto.setTvShows(new ArrayList<>());
                                }
                                searchResultsDto.getTvShows().add((MultiTvDto) item);
                            } else if (item instanceof MultiPeopleDto) {
                                if (searchResultsDto.getPeople() == null) {
                                    searchResultsDto.setPeople(new ArrayList<>());
                                }
                                searchResultsDto.getPeople().add((MultiPeopleDto) item);
                            }
                        }

                        log.info("************************ 로그 출력 테스트 ************************");
                        log.info("확인해보자...={}", searchResultsDto.getTvShows());
                        log.info("확인해보자...={}", searchResultsDto.getTvShows().get(0));

                        log.info("확인해보자...={}", searchResultsDto.getPeople());
                        log.info("logloglog={}", searchResultsDto.getPeople().get(0));
                        log.info("mutliPeoplesize={}", searchResultsDto.getPeople().size());
                        log.info("multiPeopleElement={}", searchResultsDto.getPeople().get(0).getKnownFor().get(0).getTitle());
                        log.info("************************ 로그 출력 테스트 ************************");

                        model.addAttribute("searchResults", searchResultsDto);
                    }
                    viewName = "search/search-multi";
                    break;
                case "people":
                    List<SearchPeopleDto> searchPeopleList = searchService.getSearchPeopleList(query, page);
                    model.addAttribute("searchPeopleResults", searchPeopleList);
                    viewName = "search/search-people";
                    break;
                case "contents":
                    List<SearchMoviesDto> searchMovieList = searchService.getSearchMoviesList(query, page);
                    List<SearchTvShowsDto> searchTvList = searchService.getSearchTvsList(query, page);
                    model.addAttribute("searchMovieResults", searchMovieList);
                    model.addAttribute("searchTvResults", searchTvList);
                    viewName = "search/search-contents";
                    break;
                default:
                    model.addAttribute("searchResults", "지원되지 않는 카테고리입니다.");
                    break;
            }
        } catch (Exception e) {
            log.error("검색 중 오류 발생: ", e);
            model.addAttribute("error", "검색 중 오류가 발생했습니다.");
            viewName = "error";
        }

        return viewName;
    }

}
