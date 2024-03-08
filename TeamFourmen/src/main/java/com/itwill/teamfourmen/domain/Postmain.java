package com.itwill.teamfourmen.domain;

import lombok.Data;

@Data
public class Postmain {
	
	private long postid;
    private String content;
    private String category;
    private int size;
}