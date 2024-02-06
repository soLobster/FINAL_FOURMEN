package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.dto.tvshow.TvShowListDTO;
import com.itwill.teamfourmen.service.TvShowApiUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tv")
public class TvShowRestController {

    private final TvShowApiUtil util;


    @GetMapping("/trend-list")
    public ResponseEntity<TvShowListDTO> getAdditonalTrendList (@RequestParam(name = "timeWindow") String timeWindow, @RequestParam(name = "page") int page){
        log.info("Get AdditionalTrendList - timeWindow = {}, Page = {}", timeWindow, page);

        TvShowListDTO trendListDto = util.getTrendTvShowList(timeWindow,page);

        return ResponseEntity.ok(trendListDto);
    }

    @GetMapping("/ott-list")
    public ResponseEntity<TvShowListDTO> getAddtionalOttTvShowList (@RequestParam(name = "platform") String platform, @RequestParam(name = "page") int page){
        log.info("Get AdditionalOttTvShowList - platform = {}, page = {}", platform, page);

        TvShowListDTO ottTvShowList = util.getOttTvShowList(platform, page);

        return ResponseEntity.ok(ottTvShowList);
    }


    @GetMapping("/list")
    public ResponseEntity<TvShowListDTO> getAdditionalList (@RequestParam(name = "listCategory") String listCategory ,@RequestParam(name = "page") int page){
        log.info("Get AdditionalList Category = {} , Page = {}", listCategory, page);

        TvShowListDTO listDTO = util.getTvShowList(listCategory,page);
        //log.info("listDTO = {}", listDTO);

        return ResponseEntity.ok(listDTO);
    }


}
