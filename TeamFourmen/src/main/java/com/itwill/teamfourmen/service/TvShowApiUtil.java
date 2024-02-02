package com.itwill.teamfourmen.service;

import com.itwill.teamfourmen.dto.tvshow.TvShowDTO;
import com.itwill.teamfourmen.dto.tvshow.TvShowListDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class TvShowApiUtil {

    @Value("${api.themoviedb.api-key}")
    private String API_KEY;

    private TvShowDTO tvShowDTO;

    /**
     * Tv Show List를 TvShowListDTO 객체로 돌려주는 메서드
     * 파라미터 =  {"popular" , "top_rated}
     * queryParam language = ko
     * int page
     */

    public TvShowListDTO getTvShowList (String listCategory, int page) {
        log.info("GET TV SHOW LIST Category = {}, Page = {}", listCategory, page);

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://api.themoviedb.org/3/tv";

        String targetUrl = "";

        switch (listCategory) {
            case "popular", "top_rated":
                targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                        .path("/{listCategory}")
                        .queryParam("language", "ko-KR")
                        .queryParam("page", page)
                        .queryParam("api_key",API_KEY)
                        .buildAndExpand(String.valueOf(listCategory))
                        .toUriString();
                log.info("targetURL = {}", targetUrl);
                break;
            default:
                log.info("Wrong Pram - getTvShowList()");
                break;
        }

        TvShowListDTO tvShowListDTO = restTemplate.getForObject(targetUrl, TvShowListDTO.class);

        return tvShowListDTO;
    }

    public TvShowListDTO getTrendTvShowList (String timeWindow, int page) {
        log.info("Get Trend Tv Show List - TimeWindow = {} , Page = {}", timeWindow, page);

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://api.themoviedb.org/3/trending/tv";

        String targetUrl = "";

        switch (timeWindow){
            case "day" :
            case "week":
            targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                    .path("/{timeWindow}")
                    .queryParam("language", "ko-KR")
                    .queryParam("page", page)
                    .queryParam("api_key", API_KEY)
                    .buildAndExpand(String.valueOf(timeWindow))
                    .toUriString();
            log.info("targetURL = {}", targetUrl);
            break;
            default:
                log.info("WRONG PARAM - getTrendTvShowList");
                break;
        }

        TvShowListDTO tvShowListDTO = restTemplate.getForObject(targetUrl, TvShowListDTO.class);

        return tvShowListDTO;
    }


}
