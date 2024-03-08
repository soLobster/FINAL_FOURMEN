package com.itwill.teamfourmen.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
	
	@GetMapping("/popular/board")
	public String getBoardList(@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		
		
		
		return "board/list";
	}
	
}
