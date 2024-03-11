package com.itwill.teamfourmen.dto.board;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Post;
import com.itwill.teamfourmen.dto.post.PostCreateDto;

import jakarta.persistence.Basic;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	

	private Long postId;

	private Member member;
	
	private String title;
	
	private String content;
	
	private String category;
	
	private LocalDateTime createdTime;
	
	// 현재시간과 createdTime이 24시간 이내로 차이날 경우, 시간차를 분으로 나타내는 변수
	private Long timeDifferenceInMinute;
	
	private LocalDateTime modifiedTime;
	
	private Long views;
	
	private Long likes;
	
	private int numOfComments;
	
	public static PostDto fromEntity(Post post) {
		
		
		return PostDto.builder()
					.postId(post.getPostId())
					.member(post.getMember())
					.title(post.getTitle())
					.content(post.getContent())
					.category(post.getCategory())
					.createdTime(post.getCreatedTime())
					.modifiedTime(post.getModifiedTime())
					.views(post.getViews())
					.likes(post.getLikes())
					.build();
	}
	
}
