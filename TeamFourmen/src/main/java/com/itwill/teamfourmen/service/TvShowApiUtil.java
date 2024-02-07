package com.itwill.teamfourmen.service;

import com.itwill.teamfourmen.dto.tvshow.*;
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
import java.util.List;

@Slf4j
@Service
public class TvShowApiUtil {

    @Value("${api.themoviedb.api-key}")
    private String API_KEY;

    private TvShowDTO tvShowDTO;

    private final String BASE_URL = "https://api.themoviedb.org/3";

    /**
     * Tv Show List를 TvShowListDTO 객체로 돌려주는 메서드
     * 파라미터 =  {"popular" , "top_rated}
     * queryParam language = ko
     * int page
     */

    public TvShowListDTO getTvShowList (String listCategory, int page) {
        log.info("GET TV SHOW LIST Category = {}, Page = {}", listCategory, page);

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl =  BASE_URL + "/tv";

        //"https://api.themoviedb.org/3/tv";

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

    /*
    OTT 별 Tv Show List 불러오기

    Ex URL ) https://api.themoviedb.org/3/discover/tv?language=ko-KR&page=4&sort_by=vote_count.desc&watch_region=KR&with_watch_providers=8&api_key=390e779304bcd53af3b649f4e27c6452

    language = ko-KR
    page
    sort_by = vote_count
    watch_region=KR
    with_watch_provider = {parameter} 넷플릭스, 애플티비, 디즈니, 아마존, 왓챠, 웨이브 등등을 이름과 아이디를 매핑을 해놔야함.
    api_key =
     */

    public TvShowListDTO getOttTvShowList (String platform, int page){
        log.info("Get Ott Tv Show List platform = {}, page = {}" , platform, page);

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://api.themoviedb.org/3/discover/tv";

        String targetUrl = "";

        switch (platform){
            case "netfilx":
                targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                        .queryParam("language", "ko-KR")
                        .queryParam("page", page)
                        .queryParam("sort_by", "vote_count.desc")
                        .queryParam("watch_region", "KR")
                        .queryParam("with_watch_providers", 8)
                        .queryParam("api_key", API_KEY)
                        .toUriString();
                log.info("targetURL = {}", targetUrl);
                break;
            case "disney_plus":
                targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                        .queryParam("language", "ko-KR")
                        .queryParam("page", page)
                        .queryParam("sort_by", "vote_count.desc")
                        .queryParam("watch_region", "KR")
                        .queryParam("with_watch_providers", 337)
                        .queryParam("api_key", API_KEY)
                        .toUriString();
                log.info("targetURL = {}", targetUrl);
                break;
            case "apple_tv":
                targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                        .queryParam("language", "ko-KR")
                        .queryParam("page", page)
                        .queryParam("sort_by", "vote_count.desc")
                        .queryParam("watch_region", "KR")
                        .queryParam("with_watch_providers", 350)
                        .queryParam("api_key", API_KEY)
                        .toUriString();
                log.info("targetURL = {}", targetUrl);
                break;
            case "amazone_prime":
                targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                        .queryParam("language", "ko-KR")
                        .queryParam("page", page)
                        .queryParam("sort_by", "vote_count.desc")
                        .queryParam("watch_region", "KR")
                        .queryParam("with_watch_providers", 119)
                        .queryParam("api_key", API_KEY)
                        .toUriString();
                log.info("targetURL = {}", targetUrl);
                break;
            case "watcha":
                targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                        .queryParam("language", "ko-KR")
                        .queryParam("page", page)
                        .queryParam("sort_by", "vote_count.desc")
                        .queryParam("watch_region", "KR")
                        .queryParam("with_watch_providers", 97)
                        .queryParam("api_key", API_KEY)
                        .toUriString();
                log.info("targetURL = {}", targetUrl);
                break;
            case "wavve":
                targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                        .queryParam("language", "ko-KR")
                        .queryParam("page", page)
                        .queryParam("sort_by", "vote_count.desc")
                        .queryParam("watch_region", "KR")
                        .queryParam("with_watch_providers", 356)
                        .queryParam("api_key", API_KEY)
                        .toUriString();
                log.info("targetURL = {}", targetUrl);
                break;
            default:
                log.info("WRONG PARAM - getOttTvShowList");
                break;
        }

        TvShowListDTO tvShowListDTO = restTemplate.getForObject(targetUrl, TvShowListDTO.class);

        return tvShowListDTO;
    }

    // 장르 가져오기
    public TvShowGenreListDTO getTvShowGenreList (String language) {
        log.info("get TvShowGenreList - Language = {}", language);

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = BASE_URL + "/genre/tv/list";

        String targetUrl = "";

        targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("language", language)
                .queryParam("api_key", API_KEY)
                .toUriString();
        log.info("TARGET URL = {}", targetUrl);

        TvShowGenreListDTO tvShowGenreListDTO = restTemplate.getForObject(targetUrl, TvShowGenreListDTO.class);

        return tvShowGenreListDTO;
    }

    // 장르별 TvShowList 출력
    public TvShowListDTO getGenreTvShowList (String genre, int page) {
        log.info("get GenreTvShowList - Genre = {}, page = {}", genre, page);

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://api.themoviedb.org/3/discover/tv";

        String targetUrl = "";

        targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("language", "ko-KR")
                .queryParam("page", page)
                .queryParam("sort_by", "vote_count.desc")
                .queryParam("watch_region", "KR")
                .queryParam("with_genres", genre)
                .queryParam("api_key", API_KEY)
                .toUriString();
        log.info("TARGET URL = {}", targetUrl);

        TvShowListDTO tvShowListDTO = restTemplate.getForObject(targetUrl,TvShowListDTO.class);
        return  tvShowListDTO;
    }


    public TvShowDTO getTvShowDetails (int tvshow_id) {
        log.info("get TvShow Season Detail - TVSHOW ID = {}", tvshow_id);

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://api.themoviedb.org/3/tv";

        String targetUrl = "";

        targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/{tvshow_id}")
                .queryParam("language", "ko")
                .queryParam("api_key", API_KEY)
                .buildAndExpand(String.valueOf(tvshow_id))
                .toUriString();
        log.info("TARGET URL = {}",targetUrl);

        TvShowDTO tvShowDTO = restTemplate.getForObject(targetUrl, TvShowDTO.class);

        return  tvShowDTO;
    }


    public TvShowSeasonDTO getTvShowSeasonDetail (int tvshow_id ,int season_number){
        log.info("get TvShow Season Detail - TVSHOW ID = {} , SEASON_NUM = {}", tvshow_id, season_number);

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://api.themoviedb.org/3/tv";

        String targetUrl = "";

        targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/{tvshow_id}")
                .path("/season")
                .path("/{season_number}")
                .queryParam("language", "ko-KR")
                .queryParam("api_key", API_KEY)
                .buildAndExpand(String.valueOf(tvshow_id), String.valueOf(season_number))
                .toUriString();
        log.info("TARGET URL = {}",targetUrl);

        TvShowSeasonDTO seasonDTO = restTemplate.getForObject(targetUrl, TvShowSeasonDTO.class);

        return  seasonDTO;
    }

    public TvShowEpisodeDTO getTvShowEpisodeDetail(int tvshow_id, int season_number, int episode_number){
        log.info("get TvShow Episode Detail - TVSHOW ID = {} , SEASON_NUMBER = {}, EPISODE_NUMBER = {}", tvshow_id, season_number, episode_number);

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://api.themoviedb.org/3/tv";

        String targetUrl = "";

        targetUrl = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/{tvshow_id}")
                .path("/season")
                .path("/{season_number}")
                .path("/episode")
                .path("/{episode_number}")
                .queryParam("language", "ko-KR")
                .queryParam("api_key", API_KEY)
                .buildAndExpand(String.valueOf(tvshow_id), String.valueOf(season_number), String.valueOf(episode_number))
                .toUriString();
        log.info("TARGET URL = {}", targetUrl);

        TvShowEpisodeDTO episodeDTO = restTemplate.getForObject(targetUrl, TvShowEpisodeDTO.class);

        return  episodeDTO;
    }


}
