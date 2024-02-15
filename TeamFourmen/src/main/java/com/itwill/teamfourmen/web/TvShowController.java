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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

//	@GetMapping("/list")
//	public void getTvShowList(Model model) {
//		log.info("GET TV SHOW LIST ()");
//
//		WebClient webClient = WebClient.create("https://api.themoviedb.org/3/tv/top_rated?language=ko&api_key=390e779304bcd53af3b649f4e27c6452");
//		Flux<TvShowListDTO> tvShowList = webClient.get().accept(MediaType.APPLICATION_JSON)
//				.retrieve().bodyToFlux(TvShowListDTO.class);
//
//		List<TvShowListDTO> list = tvShowList.collectList().block();
//
//		log.info(list.toString());
//
//	}
	
//	@GetMapping("/details/{id}")
//	public String getTvShow(Model model, @PathVariable (name = "id") int id) {
//		log.info("GET TV SHOW () ID = {}", id);
//
//		log.info("API KEY - {}",API_KEY);
//
//		int seriesId = id;
//
//		WebClient webClient = WebClient.create("https://api.themoviedb.org/3/tv");
//
//		Mono<TvShowDTO> tvShow = webClient.get()
//				.uri("/{seriesId}?language=ko&api_key={api_key}", seriesId, API_KEY)
//				.accept(MediaType.APPLICATION_JSON)
//				.retrieve()
//				.bodyToMono(TvShowDTO.class);
//
//		TvShowDTO tvShowDto = tvShow.block();
//
//		model.addAttribute("tvShowDto", tvShowDto);
//
//		return "tvshow/details";
//	}

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

	@GetMapping("/top_rated")
	public String getTopRatedTvShowList(Model model) throws ParseException {
		log.info("GET Top Rated Tv Show List");

		TvShowListDTO listDTO = apiUtil.getTvShowList("top_rated", 1);
		//log.info("listDto = {}", listDTO);

		model.addAttribute("listDTO", listDTO);

		List<TvShowDTO> tvShowDto = listDTO.getResults();

		model.addAttribute("tvShowDto", tvShowDto);

		return "tvshow/top-rated-list";
	}

	@GetMapping("/ott/{platform}")
	public String getOttTvShowList(Model model, @PathVariable (name = "platform") String platform){
		log.info("Get Tv Show From OTT Platform = {}", platform);

		TvShowListDTO listDTO = apiUtil.getOttTvShowList(platform, 1);

		log.info("list={}", listDTO);

		model.addAttribute("listDTO", listDTO);

		List<TvShowDTO> ottTvShowList = listDTO.getResults();

		model.addAttribute("tvShowDto", ottTvShowList);

		return "tvshow/ott-list";
	}

	// 리스트에서 tvshow를 클릭했을때 상세페이지로 넘어가는 부분
	@GetMapping(value = {"/{id}" })
	public String getTvShowDetails(Model model, @PathVariable(name = "id") int id){
		log.info("Get Tv Show Details = {}", id);
		log.info("API KET = {}", API_KEY);

		RestTemplate restTemplate = new RestTemplate();

		int seriesId = id;

		String apiUri = "https://api.themoviedb.org/3/tv";
		// 드라마 정보
//		String targetUrl = UriComponentsBuilder.fromUriString(apiUri)
//				.path("/{seriesId}")
//				.queryParam("language", "ko")
//				.queryParam("api_key", API_KEY)
//				.buildAndExpand(String.valueOf(seriesId))
//				.toUriString();
//
//		log.info(targetUrl);
//
//		TvShowDTO tvShowDTO = restTemplate.getForObject(targetUrl, TvShowDTO.class);
		TvShowDTO tvShowDTO = apiUtil.getTvShowDetails(id);

		log.info("tvShowDto = {}", tvShowDTO.toString());

		List<TvShowSeasonDTO> seasonList = tvShowDTO.getSeasons();

		model.addAttribute("tvShowDto", tvShowDTO);

		model.addAttribute("seasonList", seasonList);

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

		return "tvshow/tvshow-details";
	}



	@GetMapping("/{id}/season/{season_number}")
	public String getTvShowSeasonDetails(Model model, @PathVariable(name= "id") int id , @PathVariable(name = "season_number") int season_number){


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

		return "tvshow/season-details";
	}


	private void getInitialList(String pageName, Model model) {

		TvShowListDTO listDTO = apiUtil.getTvShowList(pageName, 1);
		log.info("list={}", listDTO);

		List<TvShowDTO> tvShowDTOList = listDTO.getResults();

		model.addAttribute("tvShowDTOList", tvShowDTOList);
	}


}
