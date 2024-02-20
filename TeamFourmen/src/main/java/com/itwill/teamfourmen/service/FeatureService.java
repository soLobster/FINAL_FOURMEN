package com.itwill.teamfourmen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.domain.ReviewLike;
import com.itwill.teamfourmen.domain.TmdbLike;
import com.itwill.teamfourmen.repository.ReviewDao;
import com.itwill.teamfourmen.repository.ReviewLikeRepository;
import com.itwill.teamfourmen.repository.TmdbLikeDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeatureService {

	private final ReviewDao reviewDao;
	private final TmdbLikeDao tmdbLikeDao;
	private final ReviewLikeRepository reviewLikeDao;

	public void postReview(Review review) {

		log.info("postReview(reviewDto={})", review);

		review = reviewDao.save(review);

	}

	public List<Review> getReviews(String category, int tmdbId) {

		log.info("getReviews(category={}, id={})", category, tmdbId);

		List<Review> tmdbReviewList = reviewDao.findByCategoryAndTmdbId(category, tmdbId);

		return tmdbReviewList;
	}

	public Review getMyReviewInTmdbWork(String email, String category, int tmdbId) {

		log.info("getMyReviewInTmdbWork(email={}, category={}, tmdbId={}", email, category, tmdbId);

		Optional<Review> myTmdbReviewOptional = reviewDao.findByMemberEmailAndCategoryAndTmdbId(email, category, tmdbId);
		Review myTmdbReview = myTmdbReviewOptional.orElse(null);

		return myTmdbReview;
	}


	public TmdbLike didLikeTmdb(Member member, String category, int tmdbId) {

		log.info("didLikeTmdb(member={})", member);

		Optional<TmdbLike> tmdbLikeOptional = tmdbLikeDao.findByMemberEmailAndCategoryAndTmdbId(member.getEmail(), category, tmdbId);

		TmdbLike tmdbLike = tmdbLikeOptional.orElse(null);
		log.info("tmdbLike={}", tmdbLike);


		return tmdbLike;
	}


	public void addLike(TmdbLike tmdbLike) {

		log.info("addLike(tmdbLike={})", tmdbLike);

		tmdbLike = tmdbLikeDao.save(tmdbLike);

		log.info("저장된 tmdbLike={}", tmdbLike);

	}

	public void deleteLike(TmdbLike tmdbLike) {

		log.info("deleteLike(tmdbLike={})", tmdbLike);
		tmdbLikeDao.deleteByMemberEmailAndCategoryAndTmdbId(tmdbLike.getMember().getEmail(), tmdbLike.getCategory(), tmdbLike.getTmdbId());

	}


	public void addReviewLike(ReviewLike reviewLike) {

		log.info("addReviewLike(reviewLike={})", reviewLike);

		reviewLike = reviewLikeDao.save(reviewLike);

		log.info("저장 후 reviewLike={}", reviewLike);
	}

	/**
	 * MyPage에서 내가 작성한 모든 리뷰를 가져오기 위함
	 * - 작성자 권오중
	 * @param email
	 * @return Review 리스트
	 */
	public List<Review> getAllMyReview (String email) {
		log.info("GET ALL MY REVIEW E-MAIL = {}", email);

		List<Review> myAllReivew = reviewDao.findByMemberEmail(email);

		for(Review review : myAllReivew) {
			log.info("My review = {}", review);
		}

		return myAllReivew;
	}

}