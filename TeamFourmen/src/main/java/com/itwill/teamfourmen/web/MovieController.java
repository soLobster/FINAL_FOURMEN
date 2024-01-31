package com.itwill.teamfourmen.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.itwill.teamfourmen.dto.movie.MovieCreditDto;
import com.itwill.teamfourmen.dto.movie.MovieCrewDto;
import com.itwill.teamfourmen.dto.movie.MovieDetailsDto;
import com.itwill.teamfourmen.dto.movie.MovieGenreDto;
import com.itwill.teamfourmen.dto.movie.MovieListDto;
import com.itwill.teamfourmen.dto.movie.MovieListItemDto;
import com.itwill.teamfourmen.dto.movie.MovieProviderDto;
import com.itwill.teamfourmen.dto.movie.MovieProviderItemDto;
import com.itwill.teamfourmen.dto.movie.MovieVideoDto;
import com.itwill.teamfourmen.service.MovieApiUtil;
import com.itwill.teamfourmen.service.MovieDetailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {
	
	private final MovieApiUtil apiUtil;
	private final MovieDetailService detailService;
	
	/**
	 * 인기영화 리스트 컨트롤러
	 * @param model
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@GetMapping("/popular")
	public String popularMovieList(Model model) throws JsonMappingException, JsonProcessingException {
		
		log.info("popularMovieList()");
		
		getInitialList("popular", model);
		
		return "/movie/movie-list";
	}
	
	
	/**
	 * 현재 상영 영화 리스트 컨트롤러
	 * @param model
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@GetMapping("/now_playing")
	public String nowPlayingMovieList(Model model) throws JsonMappingException, JsonProcessingException {
		
		log.info("nowPlayingMovieList()");
		
		getInitialList("now_playing", model);
		
		
		return "/movie/movie-list";
	}
	
	
	/**
	 * 평점 높은 영화 리스트 컨트롤러
	 * @param model
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@GetMapping("/top_rated")
	public String topRatedMovieList(Model model) throws JsonMappingException, JsonProcessingException {
		
		log.info("topRatedMovieList()");
		
		getInitialList("top_rated", model);
		
		return "/movie/movie-list";
	}
	
	
	/**
	 * 상영 예정 영화 리스트 컨트롤러
	 * @param model
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@GetMapping("/upcoming")
	public String upcomingMovieList(Model model) throws JsonMappingException, JsonProcessingException {
		
		log.info("upcomingMovieList()");
		
		getInitialList("upcoming", model);
		
		return "/movie/movie-list";
	}
	
	
	
	@GetMapping("/details")
	public String movieDetails(@RequestParam(name = "id") int id, Model model) {
		
		log.info("movieDetails(id={})", id);
		
		// 영화 디테일 정보 가져오기
		MovieDetailsDto movieDetailsDto = apiUtil.getMovieDetails(id);
		log.info("movieDetailsDto={}", movieDetailsDto);
		
		// TODO: 만약 credit dto가 없을 리가 있을까 생각해보자..
		MovieCreditDto movieCreditDto = apiUtil.getMovieCredit(id);
		log.info("movieCreditDto={}", movieCreditDto);
		List<MovieCrewDto> directorList = movieCreditDto.getCrew().stream().filter((x) -> x.getJob().equals("Director")).toList();
		
		// 영화의 비디오 가져오기
		List<MovieVideoDto> movieVideoList = apiUtil.getMovieVideoList(id);
		List<MovieVideoDto> movieTrailerList = null;	// 여기에 트레일러 리스트만 가져올거임
		
		if (movieVideoList != null) {	// 영화의 비디오 리스트가 있는 경우에.
			movieTrailerList = movieVideoList.stream().filter((x) -> x.getType().equals("Trailer")).toList();
		}
		
		log.info("movieVideoList = {}", movieVideoList);
		
		
		// 영화 provider 리스트 가져오기
		// 무비 provider, 각각 플랫폼마다 어떤 서비스가 있는지 확인하기 위해 MovieService에서 메서드 사용
		MovieProviderDto movieProviderDto = apiUtil.getMovieProviderList(id);
		log.info("movieProviderDto={}", movieProviderDto);
		
		List<MovieProviderItemDto> movieProviderList = null;
		if (movieProviderDto != null) {
			movieProviderList = detailService.getOrganizedMovieProvider(movieProviderDto);
			log.info("movieProviderList={}", movieProviderList);
		}

		
		
		model.addAttribute("movieDetailsDto", movieDetailsDto);
		model.addAttribute("movieCreditDto", movieCreditDto);
		model.addAttribute("directorList", directorList);
		model.addAttribute("movieTrailerList", movieTrailerList);
		model.addAttribute("movieProviderList", movieProviderList);
		
		return "/movie/movie-details";
	}
	
	
	
	// 이 아래로는 일반 메서드 모음
	
	/**
	 * 페이지 이름을 String으로 받아 List<MovieAdditionalListDto>를 반환해주는 메서드
	 * PageName은 반드시 "popular", "now_playing", "top_rated", "upcoming" 중 하나여야 함!
	 * @param pageName 반드시 "popular", "now_playing", "top_rated", "upcoming" 중 하나
	 * @param model
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	private void getInitialList(String pageName, Model model) throws JsonMappingException, JsonProcessingException {
		
		MovieListDto listDto = apiUtil.getMovieList(pageName, 1);
		log.info("listDto={}", listDto);		
		
		List<MovieListItemDto> movieItemDtoList = listDto.getResults();
		
		List<MovieGenreDto> movieGenreList = apiUtil.getMovieGenreList();
		
		model.addAttribute("listDto", listDto);
		model.addAttribute("movieGenreList", movieGenreList);
	}
	
}
