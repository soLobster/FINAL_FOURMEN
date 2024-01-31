package com.itwill.teamfourmen.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.itwill.teamfourmen.dto.tvshow.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/tvshow")
public class TvShowController {
	
	@Value("${api.themoviedb.api-key}")
	private String API_KEY; 

	@GetMapping("/list")
	public void getTvShowList(Model model) {
		log.info("GET TV SHOW LIST ()");
		
		WebClient webClient = WebClient.create("https://api.themoviedb.org/3/tv/top_rated?language=ko&api_key=390e779304bcd53af3b649f4e27c6452");
		Flux<TvShowListDTO> tvShowList = webClient.get().accept(MediaType.APPLICATION_JSON)
				.retrieve().bodyToFlux(TvShowListDTO.class);
		
		List<TvShowListDTO> list = tvShowList.collectList().block();
		
		log.info(list.toString());
		
		for(TvShowListDTO tvShowData : list){
            //log.info("tvShowData - {}", tvShowData.getResults());
            model.addAttribute("tvShowList", tvShowData.getResults());
        }
	}
	
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

	@GetMapping("/details/{id}")
	public String getTvShowDetails(Model model, @PathVariable(name = "id") int id){
		log.info("Get Tv Show Details = {}", id);
		log.info("API KET = {}", API_KEY);
		RestTemplate restTemplate = new RestTemplate();

		int seriesId = id;

		// 드라마 정보
		String apiUri = "https://api.themoviedb.org/3/tv";

		String targetUrl = UriComponentsBuilder.fromUriString(apiUri)
				.path("/{seriesId}")
				.queryParam("language", "ko")
				.queryParam("api_key", API_KEY)
				.buildAndExpand(String.valueOf(seriesId))
				.toUriString();

		log.info(targetUrl);

		TvShowDTO tvShowDTO = restTemplate.getForObject(targetUrl, TvShowDTO.class);

		//log.info("tvShowDto = {}", tvShowDTO.toString());

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
		// TODO : 방송사가 하나가 아닌곳이 있음... LIST로 해서 타임리프로 하나씩 출력하는것으로 수정해야함.
		TvShowNetworkDTO network = tvShowDTO.getNetworks().get(0);

		model.addAttribute("network", network);

		// SNS 불러오기
		String getTvShowSnsUrl = UriComponentsBuilder.fromUriString(apiUri)
				.path("/{seriesId}/external_ids")
				.queryParam("api_key", API_KEY)
				.buildAndExpand(String.valueOf(seriesId))
				.toUriString();
		// TODO: 페북, 인스타, 트위터가 각각 있음 있는것도 있고 없는것도 있고 이거 판별 해야 함.
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
		// TODO 여기서 부터 해야됨.


		return "tvshow/details";
	}

}
