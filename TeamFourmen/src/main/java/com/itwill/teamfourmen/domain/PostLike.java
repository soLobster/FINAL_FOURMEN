package com.itwill.teamfourmen.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "post_likes")
@SequenceGenerator(name = "post_likes_id_seq", sequenceName = "post_likes_id_seq", allocationSize = 1)
public class PostLike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_likes_id_seq")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	
	@ManyToOne
	@JoinColumn(name="email")
	private Member member;
	
}
