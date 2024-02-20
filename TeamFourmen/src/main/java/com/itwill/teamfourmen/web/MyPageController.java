package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.dto.tvshow.TvShowDTO;
import com.itwill.teamfourmen.service.FeatureService;
import com.itwill.teamfourmen.service.TvShowApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final FeatureService featureService;
    private final TvShowApiUtil tvShowApiUtil;

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

        List<TvShowDTO> reviewTvShowList = new ArrayList<>();

        for(Review myReview : myAllReview) {
            int tmdb_id = myReview.getTmdbId();

            TvShowDTO tvShowDTO = tvShowApiUtil.getTvShowDetails(tmdb_id);

            reviewTvShowList.add(tvShowDTO);
        }

        model.addAttribute("reviewTvShowList", reviewTvShowList);

        return "mypage/details-reviews";
    }


}
