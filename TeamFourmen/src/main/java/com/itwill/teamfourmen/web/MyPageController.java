package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.MemberRepository;
import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.domain.TmdbLike;
import com.itwill.teamfourmen.dto.movie.MovieDetailsDto;
import com.itwill.teamfourmen.dto.mypage.MypageDTO;
import com.itwill.teamfourmen.dto.person.DetailsPersonDto;
import com.itwill.teamfourmen.dto.review.CombineReviewDTO;
import com.itwill.teamfourmen.dto.tvshow.TvShowDTO;
import com.itwill.teamfourmen.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final MyPageService myPageService;
    private final PersonService personService;

    @GetMapping("/")
    public void mypage() {
    }

    @GetMapping("/details/{memberId}/profile")
    public String getMyPageDetails(Model model, @PathVariable (name = "memberId") Long memberId){
        log.info("get MY PAGE DETAILS MEMBERID = {}", memberId);

        Member profile = myPageService.getMember(memberId);
        
        model.addAttribute("profile", profile);

        return "mypage/details-profile";
    }

    @GetMapping("/details/{memberId}/reviews")
    public String getReviews(Model model, @PathVariable( name = "memberId") Long memberId){


        List<Review> myAllReview =  featureService.getAllMyReview(memberId);

        model.addAttribute("myAllReview", myAllReview);

        List<CombineReviewDTO> combineInfoList = new ArrayList<>();

        Map<Long, Integer> reviewComment = new HashMap<>();
        Map<Long, Long> reviewLiked = new HashMap<>();

        for(Review myReview : myAllReview) {
            int tmdb_id = myReview.getTmdbId();

            Long reviewId = myReview.getReviewId();

            int numOfComment = featureService.getNumOfReviewComment(reviewId);
            Long numOfLiked = featureService.getNumOfReviewLike(reviewId);

            reviewLiked.put(reviewId, numOfLiked);
            reviewComment.put(reviewId, numOfComment);

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

        model.addAttribute("numOfReviewLiked", reviewLiked);
        model.addAttribute("numOfReviewComment", reviewComment);

        log.info("COMBINE LIST = {} ",combineInfoList);

        model.addAttribute("combineInfoList" , combineInfoList);

        return "mypage/details-review-list";
    }

    @GetMapping("/details/{memberId}/{category}")
    public String getLikedList(Model model, @PathVariable(name = "memberId") Long memberId, @PathVariable(name = "category") String category){
        log.info("GET LIKED LIST - MEMBERID = {}, CATEGORY = {}", memberId, category);

        Member member = Member.builder().memberId(memberId).build();

        List<TmdbLike> likedList = featureService.getLikedList(member, category);
        List<MypageDTO> myPageLikedList = new ArrayList<>();

        for (TmdbLike works : likedList) {
            MypageDTO mypageDTO = new MypageDTO(); // 각 반복에서 새로운 객체 생성

            switch (category) {
                case "movie":
                    MovieDetailsDto movieDto = movieApiUtil.getMovieDetails(works.getTmdbId());
                    mypageDTO.setId(movieDto.getId());
                    mypageDTO.setName(movieDto.getTitle());
                    mypageDTO.setCategory(category);
                    mypageDTO.setImagePath(movieDto.getPosterPath());

                    myPageLikedList.add(mypageDTO);

                    continue;

                case "tv":
                    TvShowDTO tvShowDTO = tvShowApiUtil.getTvShowDetails(works.getTmdbId());
                    mypageDTO.setId(tvShowDTO.getId());
                    mypageDTO.setName(tvShowDTO.getName());
                    mypageDTO.setCategory(category);
                    mypageDTO.setImagePath(tvShowDTO.getPoster_path());

                    myPageLikedList.add(mypageDTO);

                    continue;

                case "person":
                    DetailsPersonDto personDto = personService.getPersonDetailsEnUS(works.getTmdbId());
                    mypageDTO.setId(personDto.getId());
                    mypageDTO.setName(personDto.getName());
                    mypageDTO.setCategory(category);
                    mypageDTO.setImagePath(personDto.getProfilePath());

                    myPageLikedList.add(mypageDTO);

                default:
                    log.info("없어요!!!");
                    break;
            }

        }

        log.info("LIKED LIST = {}", myPageLikedList);
        model.addAttribute("likedList", myPageLikedList);

        return "mypage/details-liked-list";
    }
    
    
    
    

}
