package com.itwill.teamfourmen.repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.itwill.teamfourmen.domain.TmdbLike;

public interface TmdbLikeDao extends JpaRepository<TmdbLike, Long> {

	// 특정 이메일, 카테고리, 그리고 TmdbId를 가진 TmdbLike 엔티티를 찾음. 결과가 없는 경우 Optional.empty()를 반환.
	Optional<TmdbLike> findByMemberEmailAndCategoryAndTmdbId(String email, String category, int tmdbId);
	// 주어진 이메일, 카테고리, 그리고 TmdbId에 해당하는 TmdbLike 엔티티를 삭제
	void deleteByMemberEmailAndCategoryAndTmdbId(String email, String category, int tmdbId);
	// 주어진 이메일과 카테고리에 해당하는 모든 TmdbLike 엔티티들의 리스트를 반환
	List<TmdbLike> findByMemberEmailAndCategory(String email, String category);
	// 주어진 tmdbId와 category가 "person"인 TmdbLike 엔티티들의 개수를 반환
	int countByTmdbIdAndCategory(int tmdbId, String category);
}
