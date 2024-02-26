package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.domain.*;
import com.itwill.teamfourmen.dto.movie.MovieDetailsDto;
import com.itwill.teamfourmen.dto.movie.MovieListItemDto;
import com.itwill.teamfourmen.dto.review.CombineReviewDTO;
import com.itwill.teamfourmen.dto.tvshow.TvShowDTO;
import com.itwill.teamfourmen.repository.ReviewCommentLikeRepository;
import com.itwill.teamfourmen.repository.ReviewDao;
import com.itwill.teamfourmen.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewDao reviewDao;
    private final MovieApiUtil movieApiUtil;
    private final TvShowApiUtil tvShowApiUtil;
    private final ImdbRatingUtil imdbRatingUtil;
    private final CommentService commentService;
    private final FeatureService featureService;
    private final ReviewCommentLikeRepository reviewCommentLikeRepository;

    // 리뷰 ID를 바탕으로 한가지 리뷰를 보여주는 페이지
    @GetMapping("/{review_id}")
    public String getReviewDetails(Model model, @PathVariable(name = "review_id") long reviewId) {
        log.info("GET REVIEW DETAILS = {}", reviewId);

        // review ID를 통해서 특정 리뷰 객체를 가져와서 view로 전달
        Review singleReview =  reviewDao.findByReviewId(reviewId);
        model.addAttribute("review", singleReview);

        // 가져온 리뷰 객체의 TMDB ID와 Category를 통해서 작품의 정보를 가져옴
        int tmdbId = singleReview.getTmdbId();
        String category = singleReview.getCategory();

        CombineReviewDTO combineReviewDTO = new CombineReviewDTO();

        String imdbId = imdbRatingUtil.getImdbId(tmdbId, category);
        combineReviewDTO.setImdbId(imdbId);

        ImdbRatings imdbRatings = imdbRatingUtil.getImdbRating(imdbId);
        combineReviewDTO.setImdbRatings(imdbRatings);

        // 가져온 카테고리를 바탕으로 movie 또는 tv의 한가지 works를 가져옴.
        switch (category) {
            case "movie" :
                MovieDetailsDto moviesInfo = movieApiUtil.getMovieDetails(tmdbId);
                combineReviewDTO.setId(moviesInfo.getId());
                combineReviewDTO.setCategory(category);
                combineReviewDTO.setName(moviesInfo.getTitle());
                combineReviewDTO.setPosterPath(moviesInfo.getPosterPath());
                break;
            case "tv" :
                TvShowDTO tvInfo = tvShowApiUtil.getTvShowDetails(tmdbId);
                combineReviewDTO.setId(tvInfo.getId());
                combineReviewDTO.setCategory(category);
                combineReviewDTO.setName(tvInfo.getName());
                combineReviewDTO.setPosterPath(tvInfo.getPoster_path());
                break;
            default:
                log.info("WRONG CATEGORY");
                break;
        }

        model.addAttribute("works", combineReviewDTO);

        Page<ReviewComments> reviewComments = commentService.getAllComments(reviewId,0);

        model.addAttribute("reviewComments", reviewComments);

        List<ReviewComments> allComment = commentService.getAllComments(reviewId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Member signedInUser = Member.builder().email(email).build();

        


        return "review/review-details";
    }

}
