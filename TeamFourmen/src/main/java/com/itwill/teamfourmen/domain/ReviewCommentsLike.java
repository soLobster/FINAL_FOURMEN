package com.itwill.teamfourmen.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "review_comments_likes")
@SequenceGenerator(name = "review_comment_like_seq", sequenceName =  "review_comment_like_seq", allocationSize = 1)
public class ReviewCommentsLike {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "review_comment_like_seq")
    private Long reviewCommentLikeId;

    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "review_comment_id")
    private ReviewComments reviewComments;

    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "email")
    private Member member;

}
