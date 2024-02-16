package com.itwill.teamfourmen.repository;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.itwill.teamfourmen.domain.TmdbLike;

public interface TmdbLikeDao extends JpaRepository<TmdbLike, Long> {
	
	Optional<TmdbLike> findByMemberEmailAndCategoryAndTmdbId(String email, String category, int tmdbId);
	void deleteByMemberEmailAndCategoryAndTmdbId(String email, String category, int tmdbId);
}
