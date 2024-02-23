package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.dto.movie.MovieDetailsDto;
import com.itwill.teamfourmen.dto.review.CombineReviewDTO;
import com.itwill.teamfourmen.dto.tvshow.TvShowDTO;
import com.itwill.teamfourmen.service.FeatureService;
import com.itwill.teamfourmen.service.MovieApiUtil;
import com.itwill.teamfourmen.service.TvShowApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final FeatureService featureService;
    private final TvShowApiUtil tvShowApiUtil;
    private final MovieApiUtil movieApiUtil;

    @GetMapping("/")
    public void mypage() {
    }

    @GetMapping("/details/{id}")
    public String getMyPageDetails(Model model, @PathVariable (name = "id") String email){

        return "mypage/details-profile";
    }

    @GetMapping("/details/{id}/reviews")
    public String getReviews(Model model, @PathVariable( name = "id") String email){

        List<Review> myAllReview =  featureService.getAllMyReview(email);

        model.addAttribute("myAllReview", myAllReview);

        List<CombineReviewDTO> combineInfoList = new ArrayList<>();

        for(Review myReview : myAllReview) {
            int tmdb_id = myReview.getTmdbId();

            String category = myReview.getCategory();

            CombineReviewDTO combineReviewDTO = new CombineReviewDTO();
            switch (category) {
                case "tv":
                    TvShowDTO tvShowDTO = tvShowApiUtil.getTvShowDetails(tmdb_id);

                    combineReviewDTO.setId(tvShowDTO.getId());
                    combineReviewDTO.setName(tvShowDTO.getName());
                    combineReviewDTO.setCategory(category);
                    combineReviewDTO.setPosterPath(tvShowDTO.getPoster_path());

                    combineInfoList.add(combineReviewDTO);

                    continue;
                case "movie":
                    MovieDetailsDto movieDTO = movieApiUtil.getMovieDetails(tmdb_id);

                    combineReviewDTO.setId(movieDTO.getId());
                    combineReviewDTO.setName(movieDTO.getTitle());
                    combineReviewDTO.setCategory(category);
                    combineReviewDTO.setPosterPath(movieDTO.getPosterPath());

                    combineInfoList.add(combineReviewDTO);

                    continue;
                default:
                    log.info("NOPE");
            }
        }

        log.info("COMBINE LIST = {} ",combineInfoList);

        model.addAttribute("combineInfoList" , combineInfoList);

        return "mypage/details-review-list";
    }

}
