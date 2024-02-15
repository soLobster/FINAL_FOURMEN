package com.itwill.teamfourmen.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.domain.TmdbLike;
import com.itwill.teamfourmen.repository.ReviewDao;
import com.itwill.teamfourmen.repository.TmdbLikeDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FeatureService {
	
	private final ReviewDao reviewDao;
	private final TmdbLikeDao tmdbLikeDao;
	
	public void postReview(Review review) {
		
		log.info("postReview(reviewDto={})", review);
		
		review = reviewDao.save(review);
		
	}
	
	
	public TmdbLike didLikeTmdb(Member member, String category, Long tmdbId) {
		
		log.info("didLikeTmdb(member={})", member);
		
		Optional<TmdbLike> tmdbLike = tmdbLikeDao.findByMemberEmailAndCategoryAndTmdbId(member.getEmail(), category, tmdbId);
		
		
		
		return null;
	}
	
}
