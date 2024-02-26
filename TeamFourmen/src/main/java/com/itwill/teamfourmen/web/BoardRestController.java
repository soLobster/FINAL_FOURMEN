package com.itwill.teamfourmen.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itwill.teamfourmen.domain.PostLike;
import com.itwill.teamfourmen.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardRestController {
	
	private final BoardService boardService;
	
	@PostMapping("/like")
	public ResponseEntity<PostLike> likePost(@RequestBody PostLike postLike) {
		
		log.info("likePost(postLike={})", postLike);
		
		PostLike savedPostLike = boardService.addLike(postLike);
		
		return ResponseEntity.ok(savedPostLike);
	}
	
	@PostMapping("/delete")
	public void deletePost(@RequestBody PostLike postLike) {
		log.info("deletePost(postLike={})", postLike);
		
		boardService.deleteLike(postLike);
		
	}
	
}
