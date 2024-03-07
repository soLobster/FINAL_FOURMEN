package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.dto.search.MediaItem;
import com.itwill.teamfourmen.dto.search.SearchMultiDto;
import com.itwill.teamfourmen.dto.search.SearchResult;
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
                    model.addAttribute("searchResults", searchService.getSearchMultiList(query, page));
                    viewName = "search/search-multi";
                    break;
                case "people":
                    model.addAttribute("searchResults", searchService.getSearchPeopleList(query, page));
                    viewName = "search/search-people";
                    break;
                case "tv":
                    model.addAttribute("searchResults", searchService.getSearchTvsList(query, page));
                    viewName = "search/search-tv";
                    break;
                case "movie":
                    model.addAttribute("searchResults", searchService.getSearchMoviesList(query, page));
                    viewName = "search/search-movie";
                    break;
                case "contents":
                    model.addAttribute("movieSearchResults", searchService.getSearchMoviesList(query, page));
                    model.addAttribute("tvSearchResults", searchService.getSearchTvsList(query, page));
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

//    @GetMapping("/search")
//    public String searchMulti(
//            @RequestParam(name = "query", required = false, defaultValue = "") String query,
//            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
//            Model model) {
//
//        // 서치 서비스 메서드 호출
//        // 멀티 검색 결과(인물, 영화, tv)
//        SearchResult<MediaItem> searchMultiResult = searchService.getSearchMultiList(query, page);
//        model.addAttribute("searchMultiResult", searchMultiResult);
//
//        log.info("////////////////////////////////////////////////////////////");
//        log.info("searchMultiList 출력 = {}", searchMultiResult);
//        log.info("////////////////////////////////////////////////////////////");
//
//        return "search/search-multi";

    }


//    @GetMapping("/search-contents")
//    @GetMapping("/search-people")
//    @GetMapping("/search-users")



//}
