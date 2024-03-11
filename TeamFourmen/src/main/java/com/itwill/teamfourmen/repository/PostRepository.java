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

	
	// 인기글 가져오는 메서드
	@Query("SELECT p FROM Post p ORDER BY (p.views + p.likes * 5) DESC")
	Page<Post> findAllPopularPosts(Pageable pageable);
	
	// 인기게시판 검색 관련(페이징)
	/**
	 * 페이징처리한 인기게시판 제목 검색
	 * @param keyword
	 * @param startingPost
	 * @param postsPerPage
	 * @return
	 */
	@Query(value = "select sp.* " +
		    "from (select * from post p order by (p.views + p.likes * 5) FETCH FIRST 100 ROWS ONLY) sp " +
			"where upper(sp.title) like upper('%' || :keyword || '%') " +
		    "order by (sp.views + sp.likes * 5) " +
			"offset :startingPost rows fetch next :postsPerPage rows only", nativeQuery = true)	
	List<Post> findPopularBoardSearchByTitle(@Param("keyword") String keyword, @Param("startingPost") Long startingPost, @Param("postsPerPage") int postsPerPage);
	
	/**
	 * 페이징 처리한 인기게시판 내용 검색
	 * @param keyword
	 * @param startingPost
	 * @param postsPerPage
	 * @return
	 */
	@Query(value="select sp.* " +
				 "from (select * from post p order by (p.views + p.likes * 5) FETCH FIRST 100 ROWS ONLY) sp " +
				 "where upper(sp.text_content) like upper('%' || :keyword || '%') " +
				 "order by (sp.views + sp.likes * 5) " +
				 "offset :startingPost rows fetch next :postsPerPage rows only", nativeQuery = true)
	List<Post> findPopularBoardSearchByContent(@Param("keyword") String keyword, @Param("startingPost") Long startingPost, @Param("postsPerPage") int postsPerPage);
	
	/**
	 * 페이징 처리 한 인기게시판 제목 + 내용 검색
	 * @param keyword
	 * @param startingPost
	 * @param postsPerPage
	 * @return
	 */
	@Query(value="select sp.* " +
				 "from (select * from post p order by (p.views + p.likes * 5) FETCH FIRST 100 ROWS ONLY) sp " +
				 "where upper(sp.title) like upper('%' || :keyword || '%') OR upper(sp.text_content) like upper('%' || :keyword || '%')" +
				 "order by (sp.views + sp.likes * 5) " +
				 "offset :startingPost rows fetch next :postsPerPage rows only", nativeQuery = true)
	List<Post> findPopularBoardSearchByTitleContent(@Param("keyword") String keyword, @Param("startingPost") Long startingPost, @Param("postsPerPage") int postsPerPage);
	
	/**
	 * 페이징 처리 한 인기게시판 작성자
	 * @param keyword
	 * @param startingPost
	 * @param postsPerPage
	 * @return
	 */
	@Query(value="select sp.* " +
				 "from (select * from post p order by (p.views + p.likes * 5) FETCH FIRST 100 ROWS ONLY) sp " +
				 "	join member m on sp.email = m.email " +
				 "where upper(m.nickname) like upper('%' || :keyword || '%') " +
				 "order by (sp.views + sp.likes * 5) " +
				 "offset :startingPost rows fetch next :postsPerPage rows only", nativeQuery = true)
	List<Post> findPopularBoardSearchByAuthor(@Param("keyword") String keyword, @Param("startingPost") Long startingPost, @Param("postsPerPage") int postsPerPage);
	
	// 검색결과 총 개수를 구하기 위한 메서드
	/**
	 * 페이징처리하지 않은 인기게시판 제목 검색 총 결과
	 * @return
	 */
	@Query(value = "select sp.* " +
		    "from (select * from post p order by (p.views + p.likes * 5) FETCH FIRST 100 ROWS ONLY) sp " +
			"where upper(sp.title) like upper('%' || :keyword || '%') " +
		    "order by (sp.views + sp.likes * 5)", nativeQuery = true)
	List<Post> findAllPopularBoardSearchByTitle(@Param("keyword") String keyword);
	
	/**
	 * 페이징 처리하지 않은 인기 게시판 내용 검색 총 결과
	 * @param keyword
	 * @return
	 */
	@Query(value = "select sp.* " +
		    "from (select * from post p order by (p.views + p.likes * 5) FETCH FIRST 100 ROWS ONLY) sp " +
			"where upper(sp.text_content) like upper('%' || :keyword || '%') " +
		    "order by (sp.views + sp.likes * 5)", nativeQuery = true)
	List<Post> findAllPopularBoardSearchByContent(@Param("keyword") String keyword);
	
	/**
	 * 페이징 처리하지 않은 인기 게시판 제목 + 내용 검색 총 결과
	 * @param keyword
	 * @return
	 */
	@Query(value="select sp.* " +
			 "from (select * from post p order by (p.views + p.likes * 5) FETCH FIRST 100 ROWS ONLY) sp " +
			 "where upper(sp.title) like upper('%' || :keyword || '%') OR upper(sp.text_content) like upper('%' || :keyword || '%')" +
			 "order by (sp.views + sp.likes * 5)", nativeQuery = true)
	List<Post> findAllPopularBoardSearchByTitleContent(@Param("keyword") String keyword);
	
	
	@Query(value="select sp.* " +
			 "from (select * from post p order by (p.views + p.likes * 5) FETCH FIRST 100 ROWS ONLY) sp " +
			 "	join member m on sp.email = m.email " +
			 "where upper(m.nickname) like upper('%' || :keyword || '%') " +
			 "order by (sp.views + sp.likes * 5) ", nativeQuery = true)
	List<Post> findAllPopularBoardSearchByAuthor(@Param("keyword") String keyword);

}
