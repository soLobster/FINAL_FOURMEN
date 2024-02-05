package com.itwill.teamfourmen.web;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitController {
	
	// open API를 받는 컨트롤러...
	String apiKey = "98284f10b710f15194cd80069283a0aa";
	String apiUrl = String.format("https://api.themoviedb.org/3/person/popular?api_key=%s", apiKey);
	
}
