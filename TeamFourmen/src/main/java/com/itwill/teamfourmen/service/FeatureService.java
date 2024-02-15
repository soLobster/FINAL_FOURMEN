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
	
}
