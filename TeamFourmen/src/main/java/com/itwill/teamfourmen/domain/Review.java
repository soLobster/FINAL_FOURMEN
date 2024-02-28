package com.itwill.teamfourmen.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
@Table(name = "reviews")
@SequenceGenerator(name = "reviews_seq", sequenceName = "reviews_seq", allocationSize = 1)
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_seq")
	private Long reviewId;
	
	@Basic(optional = false)
	private Integer tmdbId;
	
	@Basic(optional = false)
	private String category;
	
	@Basic(optional = false)
	@ManyToOne
	@JoinColumn(name = "email")
	private Member member;
	
	@Basic(optional = false)
	private String content;
	
	private String isSpoiler;
	
	private Double rating;
	
	@CreatedDate
	private LocalDateTime createdDate;

	public LocalDate changeDateType(LocalDateTime time){
		return createdDate.toLocalDate();
	}
}
