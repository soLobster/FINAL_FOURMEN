package com.itwill.teamfourmen.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import com.itwill.teamfourmen.dto.TvShowDTO;
import com.itwill.teamfourmen.dto.TvShowListDTO;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
            log.info("tvShowData - {}", tvShowData.getResults());
            model.addAttribute("tvShowList", tvShowData.getResults());
        }
	}
	
	@GetMapping("/details/{id}")
	public String getTvShow(Model model, @PathVariable (name = "id") int id) {
		log.info("GET TV SHOW () ID = {}", id);
		
		log.info("API KEY - {}",API_KEY);
		
		int seriesId = id;
		
		WebClient webClient = WebClient.create("https://api.themoviedb.org/3/tv");
		
		Mono<TvShowDTO> tvShow = webClient.get()
				.uri("/{seriesId}?language=ko&api_key={api_key}", seriesId, API_KEY)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(TvShowDTO.class);

		TvShowDTO tvShowDto = tvShow.block();
		
		model.addAttribute("tvShowDto", tvShowDto);
		
		return "tvshow/details";
	}
}
