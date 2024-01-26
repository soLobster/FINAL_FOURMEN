package com.itwill.teamfourmen.web;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	@Value("${api.themoviedb.api-key}")
	private String API_KEY;
	
	private String url = "https://api.themoviedb.org/3/movie/";
	
	@GetMapping("/")
	public String home() {
		log.info("HOME()");
		
		return "index";
	}
	

	
}