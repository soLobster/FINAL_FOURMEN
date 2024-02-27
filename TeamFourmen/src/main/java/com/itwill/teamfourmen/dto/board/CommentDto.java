package com.itwill.teamfourmen.dto.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.itwill.teamfourmen.domain.Comment;
import com.itwill.teamfourmen.domain.CommentLike;
import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Post;

import jakarta.persistence.Basic;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
	
	private Long commentId;
	
	private Post post;
	
	private Member member;

	private String content;
	
	private LocalDateTime createdTime;
	
	private Long likes;
	
	private List<CommentLike> commentLikesList = new ArrayList<>();
	
	public static CommentDto fromEntity(Comment comment) {
		
		return CommentDto.builder()
					.commentId(comment.getCommentId())
					.post(comment.getPost())
					.member(comment.getMember())
					.content(comment.getContent())
					.createdTime(comment.getCreatedTime())
					.likes(comment.getLikes())
					.build();		
	}
	
}
