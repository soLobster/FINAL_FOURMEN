package com.itwill.teamfourmen.dto.comment;

import lombok.Data;

@Data
public class ReviewCommentDTO {

    private String commentContent;
    private String loggedInUser;
    private Long reviewId;
    private Long commentId;

}
