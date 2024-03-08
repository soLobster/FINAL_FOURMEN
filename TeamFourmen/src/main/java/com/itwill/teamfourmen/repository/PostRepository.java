package com.itwill.teamfourmen.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.itwill.teamfourmen.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	
	Page<Post> findAllByCategoryOrderByCreatedTimeDesc(@Param("cateogry")String category, Pageable pageable);
	
	
	
	@Query("select p from Post p where upper(p.title) like upper('%' || :keyword || '%') and category = :boardCategory")
	Page<Post> getSearchResultByTitle(@Param("keyword") String keyword, @Param("boardCategory") String boardCategory, Pageable pageable);
	
	@Query("select p from Post p where upper(p.textContent) like upper('%' || :keyword || '%') and category = :boardCategory")
	Page<Post> getSearchResultByContent(@Param("keyword") String keyword, @Param("boardCategory")String boardCategory, Pageable pageable);
	
	@Query("select p from Post p where (upper(p.title) like upper('%' || :keyword || '%') or upper(p.textContent) like upper('%' || :keyword ||'%')) "
			+ "and category = :boardCategory")
	Page<Post> getSearchResultByTitleAndContent(@Param("keyword")String keyword, @Param("boardCategory") String boardCategory, Pageable pageable);
	
	@Query("select p from Post p where upper(p.member.nickname) like upper('%' || :keyword || '%') and category = :boardCategory")	
	Page<Post> getSearchResultByAuthor(@Param("keyword") String searchContent, @Param("boardCategory") String boardCategory, Pageable pagealbe);
}
