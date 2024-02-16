package com.itwill.teamfourmen.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.itwill.teamfourmen.dto.tvshow.*;
import com.itwill.teamfourmen.service.TvShowApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/tv")
public class TvShowController {
	
	@Value("${api.themoviedb.api-key}")
	private String API_KEY; 

	private final TvShowApiUtil apiUtil;

	@GetMapping("/main")
	public String getTvShowMain(Model model){
		log.info("GET TV SHOW MAIN VIEW");

		// Random 객체 생성 -> 랜덤한 페이지를 보내기 위해
		Random random = new Random();

		// 넷플릭스 tv 리스트를 MAIN으로 보냄
		TvShowListDTO NetflixListDTO = apiUtil.getOttTvShowList("netfilx", random.nextInt(10) + 1);
		List<TvShowDTO> Netfilx = NetflixListDTO.getResults();
		model.addAttribute("Netfilx", Netfilx);

		// 디즈니 tv 리스트를 Main으로 보냄
		TvShowListDTO DisenyPlusListDto = apiUtil.getOttTvShowList("disney_plus", random.nextInt(5) +1);
		List<TvShowDTO> Disney = DisenyPlusListDto.getResults();
		model.addAttribute("Disney", Disney);

		// 애플 tv 리스트를 Main으로 보냄
		TvShowListDTO AppleTvListDto = apiUtil.getOttTvShowList("apple_tv", random.nextInt(5) +1);
		List<TvShowDTO> Apple = AppleTvListDto.getResults();
		model.addAttribute("Apple", Apple);

		// 아마존 tv 리스트를 Main으로 보냄
		TvShowListDTO AmazoneListDto = apiUtil.getOttTvShowList("amazone_prime", random.nextInt(5) +1);
		List<TvShowDTO> Amazone = AmazoneListDto.getResults();
		model.addAttribute("Amazone", Amazone);

		// Watcha 리스트를 Main으로 보냄
		TvShowListDTO WatchaListDto = apiUtil.getOttTvShowList("watcha", random.nextInt(4)+1);
		List<TvShowDTO> Watcha = WatchaListDto.getResults();
		model.addAttribute("Watcha", Watcha);

		// Wavve 리스트를 Main으로 보냄
		TvShowListDTO WavveListDto = apiUtil.getOttTvShowList("wavve" , random.nextInt(5)+1);
		List<TvShowDTO> Wavve = WavveListDto.getResults();
		model.addAttribute("Wavve", Wavve);

		// 이 주의 인기 리스트
		TvShowListDTO PopularThisWeekTvShowList = apiUtil.getTrendTvShowList("week",1);
		List<TvShowDTO> popularThisWeekDto = PopularThisWeekTvShowList.getResults();
		model.addAttribute("popularThisWeek", popularThisWeekDto);

		return "tvshow/tvshow-main";
	}

	@GetMapping("/top_rated")
	public String getTopRatedTvShowList(Model model) throws ParseException {
		log.info("GET Top Rated Tv Show List");

//		TvShowListDTO listDTO = apiUtil.getTvShowList("top_rated", 1);
//		//log.info("listDto = {}", listDTO);
//		model.addAttribute("listDTO", listDTO);
//		List<TvShowDTO> tvShowDto = listDTO.getResults();
//		model.addAttribute("tvShowDto", tvShowDto);
//		TvShowGenreListDTO tvShowGenreListDTO = apiUtil.getTvShowGenreList("ko-KR");
//		List<TvShowGenreDTO> tvShowGenre = tvShowGenreListDTO.getGenres();
//		log.info("tvShow Genre = {}", tvShowGenre);
//		model.addAttribute("tvShowGenreDTO", tvShowGenre);

		getInitialList("top_rated", model);

		return "tvshow/top-rated-list";
	}

//	@GetMapping("/ott/{platform}")
//	public String getOttTvShowList(Model model, @PathVariable String platform){
//		log.info("Get Tv Show From OTT Platform = {}", platform);
//
//		TvShowListDTO listDTO = apiUtil.getOttTvShowList(platform, 1);
//
//		log.info("list={}", listDTO);
//
//		model.addAttribute("listDTO", listDTO);
//
//		List<TvShowDTO> ottTvShowList = listDTO.getResults();
//
//		model.addAttribute("tvShowDto", ottTvShowList);
//
//		return "tvshow/ott-list";
//	}

	@GetMapping("/trending/{timeWindow}")
	public String getPopularTvShowList(Model model, @PathVariable(name = "timeWindow") String timeWindow){
		log.info("GET Trending Tv Show List");

		TvShowListDTO listDTO = apiUtil.getTrendTvShowList(timeWindow, 1);
		log.info("listDto = {}", listDTO);

		model.addAttribute("listDTO", listDTO);

		log.info("TOTALPAGES = {}", listDTO.getTotal_pages());

		List<TvShowDTO> tvShowDto = listDTO.getResults();

		model.addAttribute("tvShowDto", tvShowDto);

		return "tvshow/trend-list";
	}

	/*
	필터링 -> TvShow 리스트 반영
	 */

	@GetMapping("/filter")
	public String getFilterTvShowList(Model model, @ModelAttribute TvShowQueryParamDTO filterDTO) {
		log.info("Get Filter Tv Show List - Filter Dto = {}", filterDTO);

		filterDTO.setListCategory("filter");

		getInitialList(filterDTO, model);

		return "tvshow/top-rated-list";
	}

  @GetMapping("/search")
	public String getSearchTvShowList(Model model, @ModelAttribute TvShowQueryParamDTO searchDTO) {
		log.info("Get Search Tv Show List - Search Dto = {}", searchDTO);
  
		searchDTO.setListCategory("search");

		getInitialList(searchDTO, model);

		return "tvshow/top-rated-list";
	}

//	@GetMapping("/ott/{platform}")
//	public String getOttTvShowList(Model model, @PathVariable (name = "platform") String platform) {
//		log.info("Get Tv Show From OTT Platform = {}", platform);
//
//		TvShowListDTO listDTO = apiUtil.getOttTvShowList(platform, 1);
//
//		log.info("list={}", listDTO);
//
//		model.addAttribute("listDTO", listDTO);
//	}

	// 리스트에서 tvshow를 클릭했을때 상세페이지로 넘어가는 부분
	@GetMapping(value = {"/{id}" })
	public String getTvShowDetails(Model model, @PathVariable(name = "id") int id){
		log.info("Get Tv Show Details = {}", id);
//		log.info("API KEY = {}", API_KEY);

		RestTemplate restTemplate = new RestTemplate();

		int seriesId = id;

		String apiUri = "https://api.themoviedb.org/3/tv";
		// 드라마 정보
		TvShowDTO tvShowDTO = apiUtil.getTvShowDetails(id);

		log.info("tvShowDto = {}", tvShowDTO.toString());

		List<TvShowSeasonDTO> seasonList = tvShowDTO.getSeasons();

		model.addAttribute("tvShowDto", tvShowDTO);

		model.addAttribute("seasonList", seasonList);

		// OTT 정보 (WatchProvider)

		TvShowWatchProviderListDTO tvShowWatchProviderListDTO = apiUtil.getTvShowProvider(id);

		String watchRegion = "KR";

		TvShowWatchProviderRegionDTO tvShowWatchProviderRegionDTO = tvShowWatchProviderListDTO.getResults().get(watchRegion);

		TvShowWatchProviderDTO[] tvShowWatchProviderDTO;

		try {
			tvShowWatchProviderDTO = tvShowWatchProviderRegionDTO.getFlatrate();
			model.addAttribute("watch_provider_list",tvShowWatchProviderDTO);

		} catch (NullPointerException e){
			e.printStackTrace();
		}

//		for(TvShowWatchProviderDTO ott: tvShowWatchProviderDTO){
//			log.info("ott = {}",ott.toString());
//		}


		// 시청 등급
		String contentRatingsUrl = UriComponentsBuilder.fromUriString(apiUri)
				.path("/{seriesId}/content_ratings")
				.queryParam("api_key", API_KEY)
				.buildAndExpand(String.valueOf(seriesId))
				.toUriString();

		TvShowContentRatingsListDTO tvShowContentRatingsList = restTemplate.getForObject(contentRatingsUrl, TvShowContentRatingsListDTO.class);

		List<TvShowContentRatingsDTO> results = tvShowContentRatingsList.getResults();

		TvShowContentRatingsDTO rating = new TvShowContentRatingsDTO();

		for(TvShowContentRatingsDTO r : results ){
				if(r.getIso_3166_1().equals("KR")){
					rating = r;
					break;
				} else if(r.getIso_3166_1().equals("US")) {
					rating = r;
				}
		}

		model.addAttribute("rating",rating);
		//log.info("rating = {}", rating);

		// 방송사? 배급사?
		//log.info("network?? = {}",tvShowDTO.getNetworks().get(0));
		List<TvShowNetworkDTO> networkList = tvShowDTO.getNetworks();

		model.addAttribute("networkList" ,networkList);

		// SNS 불러오기
		String getTvShowSnsUrl = UriComponentsBuilder.fromUriString(apiUri)
				.path("/{seriesId}/external_ids")
				.queryParam("api_key", API_KEY)
				.buildAndExpand(String.valueOf(seriesId))
				.toUriString();

		TvShowSnsDTO tvShowSnsDTO = restTemplate.getForObject(getTvShowSnsUrl, TvShowSnsDTO.class);

		String imdbID = tvShowSnsDTO.getImdb_id();

		log.info("IMDB = {}" ,imdbID);

		List<TvShowGenreDTO> getGenres = tvShowDTO.getGenres();

		TvShowSimklDetailDTO tvShowSimklDetailDTO = null;

		for(TvShowGenreDTO genre : getGenres){
			if(genre.getId() == 16){
				log.info("애니매이션 입니다...!!");
				tvShowSimklDetailDTO = apiUtil.getImdbRating(imdbID, genre.getId());
				break;
			} else {
				log.info("일반 tv 프로그램 입니다...!");
				tvShowSimklDetailDTO = apiUtil.getImdbRating(imdbID, genre.getId());
				break;
			}
		}

		try {
			if(tvShowSimklDetailDTO.getRatings() != null) {
				Map<String, TvShowImdbRatingDTO> imdbRatingDTOMap = tvShowSimklDetailDTO.getRatings();
				if (imdbRatingDTOMap != null) {
					if (imdbRatingDTOMap.get("imdb") != null) {
						TvShowImdbRatingDTO imdbRatingDTO = imdbRatingDTOMap.get("imdb");
						model.addAttribute("imdb", imdbRatingDTO);
					}
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			model.addAttribute("imdb", null);
		}

		model.addAttribute("sns", tvShowSnsDTO);
		//log.info("SNS ??? = {}",tvShowSnsDTO.toString());

		// 배우, 스탭 목록
		String getTvShowCreditUrl = UriComponentsBuilder.fromUriString(apiUri)
				.path("/{seriesId}/credits")
				.queryParam("language", "ko")
				.queryParam("api_key", API_KEY)
				.buildAndExpand(String.valueOf(seriesId))
				.toUriString();

		TvShowCreditListDTO tvShowCreditListDTO = restTemplate.getForObject(getTvShowCreditUrl, TvShowCreditListDTO.class);

		List<TvShowCreditDTO> tvShowCast = tvShowCreditListDTO.getCast();

		List<TvShowCreditDTO> tvShowCrew = tvShowCreditListDTO.getCrew();

		model.addAttribute("tvShowCast", tvShowCast);

		// 장르
		String genresName = tvShowDTO.getGenres().stream()
				.map(TvShowGenreDTO::getName)
				.collect(Collectors.joining(", "));

		model.addAttribute("genres", genresName);


		// 제목 옆 최초 방영 년도 표기
		String dateString = tvShowDTO.getFirst_air_date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date date = dateFormat.parse(dateString);
			SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
			String year = yearFormat.format(date);

			model.addAttribute("releaseYear", year);
		} catch (ParseException e){
			e.printStackTrace();
		}

		// 관련 추천 드라마 목록...
		String getTvShowRecoUrl = UriComponentsBuilder.fromUriString(apiUri)
				.path("/{seriesId}/recommendations")
				.queryParam("language", "ko")
				.queryParam("api_key", API_KEY)
				.buildAndExpand(String.valueOf(seriesId))
				.toUriString();

		TvShowRecoListDTO tvShowRecoListDTO = restTemplate.getForObject(getTvShowRecoUrl, TvShowRecoListDTO.class);

		//log.info("tvShowRecoList = {}",tvShowRecoListDTO.toString());

		List<TvShowRecoDTO> tvShowRecoDTO = tvShowRecoListDTO.getResults();

		log.info("RECO = {}",tvShowRecoDTO.size());

		model.addAttribute("tvShowReco", tvShowRecoDTO);

		// Tv Show 트레일러 가져오기
		TvShowVideoListDTO tvShowVideoDTOList = apiUtil.getTvShowVideo(id);

		List<TvShowVideoDTO> tvShowTrailerList = tvShowVideoDTOList.getResults();

		log.info("TV SHOW TRAILER LIST = {}" , tvShowTrailerList);

		List<TvShowVideoDTO> realTrailer = new ArrayList<>();

		for(TvShowVideoDTO trailer : tvShowTrailerList) {
			if(trailer.getType().equalsIgnoreCase("Trailer")){
				realTrailer.add(trailer);
			} else if (trailer.getName().startsWith("International")) {
				realTrailer.add(trailer);
			}
		}

		log.info("REAL TRAILER = {}",realTrailer);
		model.addAttribute("trailer", realTrailer);

		return "tvshow/tvshow-details";
	}



	@GetMapping("/{id}/season/{season_number}")
	public String getTvShowSeasonDetails(Model model, @PathVariable(name= "id") int id , @PathVariable(name = "season_number") int season_number){

		String apiUri = "https://api.themoviedb.org/3/tv";

		log.info("GET TV SHOW SEASON DETAILS - ID = {} , SEASON_NUM = {}", id, season_number);

		TvShowDTO tvShowDto = apiUtil.getTvShowDetails(id);

		log.info("TVSHOW Name = {}", tvShowDto.getName());

		model.addAttribute("tvShowDto", tvShowDto);

		TvShowSeasonDTO getSeasonDto = apiUtil.getTvShowSeasonDetail(id, season_number);

		log.info("SEASON DETAIL = {}", getSeasonDto);

		model.addAttribute("seasonDto", getSeasonDto);

		// 제목 옆 최초 방영 년도 표기
		if(getSeasonDto.getEpisodes().get(0).getAir_date() != null) {
			String dateString = getSeasonDto.getEpisodes().get(0).getAir_date();

			log.info("==========AIR DATE========== = {}", dateString);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			try {
				Date date = dateFormat.parse(dateString);
				SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
				String year = yearFormat.format(date);

				model.addAttribute("releaseYear", year);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 시즌 1화의 stillpath를 가져오기 위함.
		TvShowEpisodeDTO episodeDTO = apiUtil.getTvShowEpisodeDetail(id, season_number, getSeasonDto.getEpisodes().get(0).getEpisode_number());

		log.info("EPISODE DETAIL = {}", episodeDTO.getStill_path());

		model.addAttribute("episodeDTO", episodeDTO);

		// 시즌 별 배우 목록 가져오기

		String getTvShowCreditUrl = UriComponentsBuilder.fromUriString(apiUri)
				.path("/{seriesId}/season/{season_number}/credits")
				.queryParam("language", "ko-KR")
				.queryParam("api_key", API_KEY)
				.buildAndExpand(String.valueOf(id), String.valueOf(season_number))
				.toUriString();

		RestTemplate restTemplate = new RestTemplate();

		TvShowCreditListDTO tvShowCreditListDTO = restTemplate.getForObject(getTvShowCreditUrl, TvShowCreditListDTO.class);

		List<TvShowCreditDTO> tvShowCast = tvShowCreditListDTO.getCast();

		log.info("Season Tv Show Cast = {}",tvShowCast);

		model.addAttribute("castingList", tvShowCast);

		return "tvshow/season-details";
	}

	private void getInitialList(String pageName, Model model) {

		TvShowQueryParamDTO paramDTO = new TvShowQueryParamDTO();
		paramDTO.setListCategory(pageName);

		TvShowListDTO listDTO = apiUtil.getTvShowList(paramDTO);

		List<TvShowDTO> tvShowDto = listDTO.getResults();

		TvShowGenreListDTO tvShowGenreListDTO = apiUtil.getTvShowGenreList("ko-KR");

		List<TvShowGenreDTO> tvShowGenre = tvShowGenreListDTO.getGenres();

		model.addAttribute("listDTO", listDTO);
		model.addAttribute("tvShowDto", tvShowDto);
		model.addAttribute("tvShowGenreDTO", tvShowGenre);
	}

	private void getInitialList(TvShowQueryParamDTO paramDTO, Model model) {

		TvShowListDTO listDTO = apiUtil.getTvShowList(paramDTO);

		List<TvShowDTO> tvShowDto = listDTO.getResults();

		log.info("PARAMS = {}", paramDTO.toString());

		TvShowGenreListDTO tvShowGenreList = apiUtil.getTvShowGenreList("ko-KR");

		List<TvShowGenreDTO> tvShowGenre = tvShowGenreList.getGenres();

		model.addAttribute("params", paramDTO);
		model.addAttribute("listDTO", listDTO);
		model.addAttribute("tvShowDto", tvShowDto);
		model.addAttribute("tvShowGenreDTO", tvShowGenre);
	}
}
