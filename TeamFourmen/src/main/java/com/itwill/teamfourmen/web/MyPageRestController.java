package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.MemberRepository;
import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.dto.review.CombineReviewDTO;
import com.itwill.teamfourmen.service.FeatureService;
import com.itwill.teamfourmen.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage")
public class MyPageRestController {

    private final FeatureService featureService;
    private final MyPageService myPageService;

    @GetMapping("/user-info")
    public ResponseEntity<Member> getMemberInfo(@RequestParam(name = "memberId") Long memberId){
        log.info("GET MEMBER INFO = {} ", memberId);

        Member userInfo = myPageService.getMember(memberId);

        return ResponseEntity.ok(userInfo);
    }


    @GetMapping("/get-num-of-reviews")
    public ResponseEntity<Integer> getNumReviews(@RequestParam(name = "email") String email){
        log.info("GET REVIEWS NUM WHO  = {} ", email);

        List<Review> allReview =  featureService.getAllMyReview(email);

        int numOfReview = 0;

        if(!allReview.isEmpty()){
            numOfReview = allReview.size();
        } else {
            numOfReview = 0;
        }

        return ResponseEntity.ok(numOfReview);
    }
}
