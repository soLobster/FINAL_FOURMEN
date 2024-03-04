//package com.itwill.teamfourmen.web;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@Slf4j
//@RequiredArgsConstructor
//public class SearchController {
//
//    @Autowired
//    private SearchService searchService;
//
//    @GetMapping("/search")
//    public String search(@RequestParam("query") String query, Model model) {
//        SearchResults results = searchService.search(query);
//        model.addAttribute("results", results);
//        return "searchResults"; // 검색 결과를 보여줄 뷰의 이름
//    }
//
//
//}
