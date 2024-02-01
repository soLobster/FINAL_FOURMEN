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
@RequestMapping("/api/tvshow")
public class TvShowRestController {

    private final TvShowApiUtil util;

    @GetMapping("/list")
    public ResponseEntity<TvShowListDTO> getAdditionalList (@RequestParam(name = "listCategory") String listCategory ,@RequestParam(name = "page") int page){
        log.info("Get AdditionalList Category = {} , Page = {}", listCategory, page);

        TvShowListDTO listDTO = util.getTvShowList(listCategory,page);
        log.info("listDTO = {}", listDTO);

        return ResponseEntity.ok(listDTO);
    }

}
