package com.itwill.teamfourmen.dto.Comment;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.Review;
import lombok.Data;

@Data
public class ReviewCommentDTO {

    private String commentContent;
    private String commentWriterEmail;
    private Long reviewId;
    private Long commentId;

}
