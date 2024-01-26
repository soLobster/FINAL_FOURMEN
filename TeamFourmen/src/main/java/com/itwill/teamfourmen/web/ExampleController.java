package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.dto.ExampleDto;
import com.itwill.teamfourmen.dto.ExampleListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/movie")
public class ExampleController {

    private final String API_KEY = "390e779304bcd53af3b649f4e27c6452";
    private final String MovieEndPoint = "https://api.themoviedb.org/3/movie/";

    @GetMapping("ExampleLayout")
    public void movie(Model model){
        log.info("EXAMPLE CONTROLLER - GET MOVIE()");

        WebClient webClient = WebClient.create();

        Flux<ExampleListDto> responseBodyFlux = webClient.get()
                .uri(MovieEndPoint + "top_rated?api_key=" + API_KEY)
                .header("accept", "application/json")
                .retrieve()
                .bodyToFlux(ExampleListDto.class);

        // 리스트를 Flux에서 바로 추출하여 모델에 추가하기.
        log.info("movieData = {}",
                responseBodyFlux.flatMap(dto -> Flux.fromIterable(dto.getResults())).collectList().block());
        model.addAttribute("movieDataList",
                responseBodyFlux
                        .flatMap(dto -> Flux
                                        .fromIterable(dto.getResults()))
                        .collectList()
                        .block());

//        List<ExampleListDto> list =  responseBodyFlux.collectList().block();
//
//        for(ExampleListDto movieData : list){
//            log.info("movieData - {}", movieData.getResults());
//            model.addAttribute("movieDataList", movieData.getResults());
//        }
    }

}
