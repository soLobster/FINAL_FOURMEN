package com.itwill.teamfourmen.dto.comment;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.ReviewComments;
import lombok.Data;

@Data
public class ReviewCommentLikeDTO {

    private ReviewComments reviewComments;
    private Member member;

    private Long commentId;
    private boolean isLiked;

}
