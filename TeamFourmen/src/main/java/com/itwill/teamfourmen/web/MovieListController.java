package com.itwill.teamfourmen.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itwill.teamfourmen.dto.MovieListDto;
import com.itwill.teamfourmen.dto.MovieListItemDto;
import com.itwill.teamfourmen.service.MovieApiUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieListController {
	
	private final MovieApiUtil apiUtil;
	
	@GetMapping("/popular")
	public String popularMovieList(Model model) {
		
		log.info("popularMovieList()");
		
		MovieListDto listDto = apiUtil.getMovieList("popular");
		log.info("listDto={}", listDto);
		
		List<MovieListItemDto> movieItemDtoList= listDto.getResults();
		
		model.addAttribute("listDto", listDto);
		model.addAttribute("movieItemDtoList", movieItemDtoList);
		
		return "/movie/movie-list";
	}
	
	
}
