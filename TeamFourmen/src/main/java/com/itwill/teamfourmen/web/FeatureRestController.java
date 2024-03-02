package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.dto.comment.ReviewLikeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	/**
	 * 특정 tmdbId와 category가 "person"인 경우의 좋아요 개수를 조회하는 API 엔드포인트.
	 * @param tmdbId 조회하고자 하는 Tmdb ID
	 * @return 좋아요 개수
	 */
	@GetMapping("/like/count")
	public ResponseEntity<Integer> getLikesCountForPerson(@RequestParam int tmdbId) {
		int likesCount = featureService.getLikesCountFOrPersonCategory(tmdbId);
		log.info("getLikesCountForPerson: tmdbId={}, likesCount={}", tmdbId, likesCount);
		return ResponseEntity.ok(likesCount);
	}


}

