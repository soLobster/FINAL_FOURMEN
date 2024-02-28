package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.dto.comment.ReviewLikeDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.teamfourmen.domain.NicknameInterceptor;
import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.domain.ReviewLike;
import com.itwill.teamfourmen.domain.TmdbLike;
import com.itwill.teamfourmen.service.FeatureService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feature")
public class FeatureRestController {

	private final NicknameInterceptor nicknameInterceptor;
	private final FeatureService featureService;

	@PostMapping("/review/post")
	public void postReview(@RequestBody Review review) {

		log.info("postReview(reviewdto={})", review);
		featureService.postReview(review);

	}


	@Transactional
	@PostMapping("/like/add")
	public void addLike(@RequestBody TmdbLike tmdbLike) {

		log.info("addLike(tmdbLike = {})", tmdbLike);

		nicknameInterceptor.getMember().addRole(null);

		featureService.addLike(tmdbLike);

	}

	@Transactional
	@PostMapping("/like/delete")
	public void deleteLike(@RequestBody TmdbLike tmdbLike) {

		log.info("deleteLike(tmdbLike={})", tmdbLike);

		featureService.deleteLike(tmdbLike);

	}

//	@Transactional
	@PostMapping("/review/like/add")
	public void addReviewLike(@RequestBody ReviewLikeDTO reviewLikeDto) {
		log.info("addReviewLike(reviewLike={})", reviewLikeDto);
		featureService.controllReviewLike(reviewLikeDto);
	}


}

