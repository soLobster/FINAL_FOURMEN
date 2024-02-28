package com.itwill.teamfourmen.dto.comment;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Review;
import lombok.Data;

@Data
public class ReviewLikeDTO {

    private Member member;
    private Review review;

    private Long reviewId;
    private String email;

}
