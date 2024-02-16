package com.itwill.teamfourmen.web;

import java.util.List;


import com.itwill.teamfourmen.domain.ImdbRatings;
import com.itwill.teamfourmen.service.ImdbRatingUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.domain.TmdbLike;
import com.itwill.teamfourmen.dto.movie.MovieCastDto;
import com.itwill.teamfourmen.dto.movie.MovieCreditDto;
import com.itwill.teamfourmen.dto.movie.MovieCrewDto;
import com.itwill.teamfourmen.dto.movie.MovieDetailsDto;
import com.itwill.teamfourmen.dto.movie.MovieExternalIdDto;
import com.itwill.teamfourmen.dto.movie.MovieQueryParamDto;
import com.itwill.teamfourmen.dto.movie.MovieReleaseDateItemDto;
import com.itwill.teamfourmen.dto.movie.MovieGenreDto;
import com.itwill.teamfourmen.dto.movie.MovieListDto;
import com.itwill.teamfourmen.dto.movie.MovieListItemDto;
import com.itwill.teamfourmen.dto.movie.MovieProviderDto;
import com.itwill.teamfourmen.dto.movie.MovieProviderItemDto;
import com.itwill.teamfourmen.dto.movie.MovieVideoDto;
import com.itwill.teamfourmen.service.FeatureService;
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
	private final FeatureService featureService;
	
	// IMDB RATING을 가져오기 위함.
	private String category = "movie";
	private final ImdbRatingUtil imdbRatingUtil;

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
	
	
	/**
	 * 유저가 선택한 필터내용을 반영한 영화 리스트를 반환함
	 * @param filterDto
	 * @param model
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@GetMapping("/filter")
	public String filterMovieList(@ModelAttribute MovieQueryParamDto filterDto, Model model) throws JsonMappingException, JsonProcessingException {
		
		log.info("filterMovieList(filterDto={})", filterDto);
		filterDto.setListCategory("filter");
		
		getInitialList(filterDto, model);		
		
		return "/movie/movie-list";
	}
	
	
	@GetMapping("/search")
	public String searchMovieList(@ModelAttribute MovieQueryParamDto searchDto, Model model) throws JsonMappingException, JsonProcessingException {
		
		log.info("searchMovieList(searchDto=${})", searchDto);
		searchDto.setListCategory("search");
		
		getInitialList(searchDto, model);
		
		return "/movie/movie-list";
	}
	
	
	/**
	 * id에 해당하는 영화의 상세 페이지
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/details/{id}")
	public String movieDetails(@PathVariable(name = "id") int id, Model model) {
		
		log.info("movieDetails(id={})", id);
		
		// 영화 디테일 정보 가져오기
		MovieDetailsDto movieDetailsDto = apiUtil.getMovieDetails(id);
		//log.info("movieDetailsDto={}", movieDetailsDto);
		
		// TODO: 만약 credit dto가 없을 리가 있을까 생각해보자..
		MovieCreditDto movieCreditDto = apiUtil.getMovieCredit(id);
		//log.info("movieCreditDto={}", movieCreditDto);
		List<MovieCrewDto> directorList = movieCreditDto.getCrew().stream().filter((x) -> x.getJob().equals("Director")).toList();	// 감독만 꺼내온 리스트
		List<MovieCastDto> castList = movieCreditDto.getCast();	// 출연진만 꺼내온 리스트
		
		// 영화의 비디오 가져오기
		List<MovieVideoDto> movieVideoList = apiUtil.getMovieVideoList(id);
		List<MovieVideoDto> movieTrailerList = null;	// 여기에 트레일러 리스트만 가져올거임
		
		if (movieVideoList != null) {	// 영화의 비디오 리스트가 있는 경우에.
			movieTrailerList = movieVideoList.stream().filter((x) -> x.getType().equals("Trailer")).toList();
		}
		
		//log.info("movieVideoList = {}", movieVideoList);
		
		
		// 영화 provider 리스트 가져오기
		// 무비 provider, 각각 플랫폼마다 어떤 서비스가 있는지 확인하기 위해 MovieService에서 메서드 사용
		MovieProviderDto movieProviderDto = apiUtil.getMovieProviderList(id);
		log.info("movieProviderDto={}", movieProviderDto);
		
		
		// releasedate관련 정보 가져옴 (작품 연령제한 포함된 정보)
		List<MovieReleaseDateItemDto> releaseDateItemList = apiUtil.getMovieReleaseDateInfo(id, "KR");
		MovieReleaseDateItemDto releaseItemDto = detailService.getType3MovieReleaseDateItem(releaseDateItemList);
		if (releaseItemDto == null || releaseItemDto.getCertification() != null) {	// 한국 release date정보 없으면 미국꺼 가져오도록 함
			releaseDateItemList = apiUtil.getMovieReleaseDateInfo(id, "US");
			releaseItemDto = detailService.getType3MovieReleaseDateItem(releaseDateItemList);	// 미국꺼 가져옴. 미국정보마져 없으면 null
		}
		
		
		List<MovieProviderItemDto> movieProviderList = null;
		if (movieProviderDto != null) {
			movieProviderList = detailService.getOrganizedMovieProvider(movieProviderDto);
			log.info("movieProviderList={}", movieProviderList);
		}
		
		
		// 해당 영화의 Collection이 있으면 Collection리스트 가져오기
		if(movieDetailsDto.getBelongsToCollection() != null) {
			List<MovieDetailsDto> movieCollectionList = apiUtil.getMovieCollectionList(movieDetailsDto.getBelongsToCollection().getId());
			model.addAttribute("movieCollectionList", movieCollectionList);
		}
		
		// 해당영화의 social media id 가져옴
		MovieExternalIdDto movieExternalIdDto = apiUtil.getMovieExternalId(id);
		
		// 해당 영화와 관련된 추천영화 목록 가져옴
		List<MovieDetailsDto> recommendedList = apiUtil.getRecommendedMovie(id);
		
		// 좋아요 눌렀는지 여부 가져옴
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Member signedInUser = Member.builder().email(email).build();
		TmdbLike tmdbLike = featureService.didLikeTmdb(signedInUser, "movie", id);	// 만약 좋아요 이미 눌렀으면 TmdbLike객체 리턴됨
		
		
		// 관련 리뷰 가져옴
		List<Review> movieReviewList = featureService.getReviews("movie", id);
		Review myReview = featureService.getMyReviewInTmdbWork(email, "movie", id);
		
		model.addAttribute("movieDetailsDto", movieDetailsDto);
		model.addAttribute("movieCreditDto", movieCreditDto);
		model.addAttribute("directorList", directorList);
		model.addAttribute("castList", castList);
		model.addAttribute("movieTrailerList", movieTrailerList);
		model.addAttribute("movieProviderList", movieProviderList);
		model.addAttribute("movieExternalIdDto", movieExternalIdDto);
		model.addAttribute("recommendedList", recommendedList);
		model.addAttribute("releaseItemDto", releaseItemDto);

		// imdb rating을 가져오기 위함...
		// id = TMDB의 movie id , category = 제일 상단에 이미 선언 "movie"
		String imdbId = imdbRatingUtil.getImdbId(id, category);
		ImdbRatings imdbRatings = imdbRatingUtil.getImdbRating(imdbId);

		log.info("IMDB RATINGS = {}", imdbRatings.toString());

		// 객체로 넘어감. 원하는 값은 imdbRatings -> getter를 통해서
		// IMDB 아이콘은 static/icons/imdb-icon.svg 파일...!
		model.addAttribute("imdbRatings", imdbRatings);

		model.addAttribute("tmdbLike", tmdbLike);	// 좋아요 눌렀는지 확인하기 위해
    
		model.addAttribute("movieReviewList", movieReviewList);
		model.addAttribute("myReview", myReview);
		
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
		
		MovieQueryParamDto paramDto = new MovieQueryParamDto();
		paramDto.setListCategory(pageName);
		
		MovieListDto listDto = apiUtil.getMovieList(paramDto);
		//log.info("listDto={}", listDto);		
				
		List<MovieGenreDto> movieGenreList = apiUtil.getMovieGenreList();
		
		model.addAttribute("listDto", listDto);
		model.addAttribute("movieGenreList", movieGenreList);
	}
	
	
	/**
	 * 페이지 이름을 String으로 받아 List<MovieAdditionalListDto>를 반환해주는 메서드
	 * pageName은 반드시 "filter" ~ 중 하나여아 함! (만드는 중,,,)
	 * @param pageName "filter", ~ 중 하나
	 * @param model
	 * @param filterDto
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	private void getInitialList(MovieQueryParamDto paramDto, Model model) throws JsonMappingException, JsonProcessingException {
		
		
		MovieListDto listDto = apiUtil.getMovieList(paramDto);
		// log.info("listDto={}", listDto);		
				
		List<MovieGenreDto> movieGenreList = apiUtil.getMovieGenreList();
				
		model.addAttribute("listDto", listDto);
		model.addAttribute("movieGenreList", movieGenreList);
	}
	
}
