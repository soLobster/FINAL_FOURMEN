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

@Data
@Table(name = "comment_likes")
@Entity
@SequenceGenerator(name = "comment_likes_id_seq" , sequenceName = "comment_likes_id_seq", allocationSize = 1)
public class CommentLike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_likes_id_seq")
	private Long commentLikeId;
	
	@ManyToOne
	@JoinColumn(name = "comment_id")
	private Comment comment;
	
	@ManyToOne
	@JoinColumn(name = "email")
	private Member member;
	
}
