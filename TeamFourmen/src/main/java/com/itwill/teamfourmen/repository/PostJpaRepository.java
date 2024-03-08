package com.itwill.teamfourmen.repository;

import java.util.List;

import com.itwill.teamfourmen.domain.Post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class PostJpaRepository {
	
	private final EntityManager entityManager;
	
	
	
}
