package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.service.PersonService;
import com.itwill.teamfourmen.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SearchController {

    private final PersonService personService;
    private final SearchService searchService;

//    @GetMapping("/search")
//    public String search(@RequestParam(name = "query", required = false, defaultValue = "") String query,
//                         @RequestParam(name = "category", required = false) String category,
//                         Model model) {
//
//        // model 에 항상 쿼리를 실어 보내서 쿼리 값을 유지하고 사용할 수 있도록 함.
//        model.addAttribute("query", query);
//
//        // 카테고리별로 이동할 주소를 설정.
//        if (category != null) {
//            switch (category) {
//                case "contents":
//                    return "/search/search-contents";
//                case "people":
//                    return "/search/search-people";
//                case "collection":
//                    return "/search/search-collection";
//                case "users":
//                    return "/search/search-users";
//                default:
//                    return "/search/default-search";
//            }
//        } else {
//            return "/search/search-contents"; // 전체 검색 or 비어있는 검색은 무조건 contents 로 이동.
//        }
//
//    } // end 기존 코드.

    @GetMapping("/search")
    public String search(@RequestParam(name = "query", required = false, defaultValue = "") String query,
                         @RequestParam(name = "category", required = false) String category,
                         Model model) {

        // 모델에 검색 쿼리 추가하여 뷰에서 사용할 수 있도록 함.
        model.addAttribute("query", query);

        String viewName = "search/default-search"; // 기본 뷰 설정
        try {
            String searchResults;
            switch (category) {
                case "multi":
                    searchResults = searchService.searchMulti(query).block();
                    model.addAttribute("searchResults", searchResults);
                    viewName = "search/search-multi"; // 전체 검색 결과 뷰
                    break;
                case "contents":
                    searchResults = searchService.searchMovies(query).block();
                    model.addAttribute("searchResults", searchResults);
                    viewName = "search/search-contents"; // 영화 검색 결과 뷰
                    break;
                case "people":
                    searchResults = searchService.searchPeople(query).block();
                    model.addAttribute("searchResults", searchResults);
                    viewName = "search/search-people"; // 인물 검색 결과 뷰
                    break;
                case "tv":
                    searchResults = searchService.searchTvShows(query).block();
                    model.addAttribute("searchResults", searchResults);
                    viewName = "search/search-tv"; // TV 프로그램 검색 결과 뷰
                    break;
                case "collections":
                    searchResults = searchService.searchCollections(query).block();
                    model.addAttribute("searchResults", searchResults);
                    viewName = "search/search-collections"; // 시리즈 검색 결과 뷰 (아마도 시리즈를 다루는 데이터...?)
                default:
                    model.addAttribute("searchResults", "지원되지 않는 카테고리입니다.");
                    // 기본 뷰는 이미 설정되어 있음
                    break;
            }
        } catch (Exception e) {
            log.error("검색 중 오류 발생: ", e);
            model.addAttribute("error", "검색 중 오류가 발생했습니다.");
            viewName = "error"; // 오류 발생 시 오류 페이지 뷰
        }
        return viewName; // 조건에 따라 결정된 뷰 이름 리턴
    }

}
