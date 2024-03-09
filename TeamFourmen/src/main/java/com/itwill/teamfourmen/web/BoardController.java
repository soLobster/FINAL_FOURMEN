package com.itwill.teamfourmen.web;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.teamfourmen.dto.board.PostDto;
import com.itwill.teamfourmen.dto.person.PageAndListDto;
import com.itwill.teamfourmen.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BoardController {
	
	private final BoardService boardService;
	
	/**
	 * 인기 게시판 컨트롤러 메서드
	 * @param page
	 * @param model
	 * @return
	 */
	@GetMapping("/popular/board")
	public String getBoardList(@RequestParam(name = "page", required = false, defaultValue = "0") int page, Model model) {
		
		Page<PostDto> postDtoList = boardService.getPostList("popular", page);
		
		postDtoList.forEach((post) -> {
			Long likes = boardService.countLikes(post.getPostId());
			post.setLikes(likes);
		});
		
		// TODO: total element 타입 Long으로변경하는거 논의
		PageAndListDto pagingDto = PageAndListDto.getPagingDto(page, (int) postDtoList.getTotalElements(), postDtoList.getTotalPages(), 5, 20);		
		log.info("pagingDto={}", pagingDto);
		
		model.addAttribute("category", "popular");
		model.addAttribute("postDtoList", postDtoList);
		model.addAttribute("pagingDto", pagingDto);
		
		return "board/list";
	}
	
	
	/**
	 * 검색 카테고리와 검색어를 기반으로 검색결과를 가져다주는 컨트롤러 메서드
	 * @return
	 */
	@GetMapping("/popular/board/search")
	public String searchPopularBoard(Model model, @RequestParam(name = "searchCategory") String searchCategory
			, @RequestParam(name = "searchContent") String searchContent, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		log.info("searchMovieBoard(searchCategory={}, searchContent={})", searchCategory, searchContent);
		
		List<PostDto> searchedPostDtoList = boardService.searchPopularPost(searchCategory, searchContent, page);
		
		searchedPostDtoList.forEach((post) -> {
			Long likes = boardService.countLikes(post.getPostId());
			post.setLikes(likes);
		});
		
		log.info("인기게시판 검색 searchedPostDtoList = {}", searchedPostDtoList);
		
		// 페이징 처리하지 않은 전체 검색결과 수와 페이지당 포스트 수
		Map<String, Integer> totElementsAndPostsPerPage = boardService.getPopularSearchTotalElementAndPostPerPage(searchCategory, searchContent);
		
		// 총 페이지 수 구하기
		int postsPerPage = totElementsAndPostsPerPage.get("postsPerPage");
		int totElements = totElementsAndPostsPerPage.get("totElements");
		int totPages =  (int) Math.ceil((double) totElements / postsPerPage);
		
		log.info("전체 게시물 수 = {}, 페이지당 게시물 수 = {}, 총 페이지 수 = {}", totElements, postsPerPage, totPages);
		
		PageAndListDto pagingDto = PageAndListDto.getPagingDto(page, totElements, totPages, 5, 20);
		
		model.addAttribute("totElements", totElements);
		model.addAttribute("category", "popular");
		model.addAttribute("isSearch", "검색 결과");
		model.addAttribute("postDtoList", searchedPostDtoList);
		model.addAttribute("pagingDto", pagingDto);
		model.addAttribute("keyword", searchContent);
		model.addAttribute("searchCategory", searchCategory);
		
		
		return "board/list";
	}
	
}
