package com.itwill.teamfourmen.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.teamfourmen.domain.Comment;
import com.itwill.teamfourmen.domain.CommentLike;
import com.itwill.teamfourmen.domain.Post;
import com.itwill.teamfourmen.domain.PostLike;
import com.itwill.teamfourmen.dto.board.CommentDto;
import com.itwill.teamfourmen.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardRestController {
	
	private final BoardService boardService;
	
	/**
	 * 게시글에 좋아요 추가하는 컨트롤러 메서드
	 * @param postLike
	 */
	@PostMapping("/like")
	public ResponseEntity<PostLike> likePost(@RequestBody PostLike postLike) {
		
		log.info("likePost(postLike={})", postLike);
		
		PostLike savedPostLike = boardService.addLike(postLike);
		
		return ResponseEntity.ok(savedPostLike);
	}
	
	
	/**
	 * 게시글 좋아요 취소하는 컨트롤러 메서드
	 * @param postLike
	 */
	@PostMapping("/delete")
	public void deletePost(@RequestBody PostLike postLike) {
		log.info("deletePost(postLike={})", postLike);
		
		boardService.deleteLike(postLike);
		
	}
	
	@GetMapping("/comment/refresh")
	public ResponseEntity<List<CommentDto>> refreshComment(@RequestParam(name = "postId") Long postId) {
		
		log.info("refreshComment(postId={})", postId);
		
		List<CommentDto> commentDtoList = boardService.getCommentList(postId);
		
		return ResponseEntity.ok(commentDtoList);
	}
	
	/**
	 * 게시글 댓글 추가하는 컨트롤러 메서드
	 */
	@PostMapping("/comment/add")
	public ResponseEntity<CommentDto> addComment(@RequestBody Comment comment) {
		log.info("addComment(comment={}", comment);
		
		CommentDto savedCommentDto = boardService.addComment(comment);
		
		return ResponseEntity.ok(savedCommentDto);
	}
	
	
	@PostMapping("/comment/like/add")
	public ResponseEntity<CommentLike> addCommentLike(@RequestBody CommentLike commentLike) {
		log.info("addCommentLike(commentLike={})", commentLike);
		
		CommentLike savedCommentLike = boardService.addCommentLike(commentLike);
		
		return ResponseEntity.ok(savedCommentLike);
	}
	
	
	@PostMapping("/comment/like/delete")
	public void deleteCommentLike(@RequestBody CommentLike commentLike) {
		log.info("deleteCommentLike(commentLike={})", commentLike);
		
		boardService.deleteCommentLike(commentLike);
		
	}
	
}
