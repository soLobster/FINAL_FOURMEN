package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.ReviewComments;
import com.itwill.teamfourmen.domain.ReviewCommentsLike;
import com.itwill.teamfourmen.dto.comment.ReviewCommentDTO;
import com.itwill.teamfourmen.dto.comment.ReviewCommentLikeDTO;
import com.itwill.teamfourmen.service.CommentService;
import com.itwill.teamfourmen.service.FeatureService;
import com.itwill.teamfourmen.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comment")
public class CommentRestController {

    // 게시판의 댓글과 리뷰의 댓글을 하나의 RestController로 관리할 수 있지 않을까???

    private final FeatureService featureService;
    private final MyPageService myPageService;
    private final CommentService commentService;

    @GetMapping("/reviews/all")
    public ResponseEntity<Page<ReviewComments>> getAllReviewComments(@RequestParam (name = "reviewId") Long reviewId, @RequestParam(name = "page") int page){
        log.info("GET ALL COMMENT OF REVIEW = {} , PAGE = {}", reviewId, page);

        Page<ReviewComments> getReviewComments = commentService.getAllComments(reviewId, page);

        log.info("review COMMENT = {}", getReviewComments);

        return ResponseEntity.ok(getReviewComments);
    }

    @GetMapping("/reviews")
    public ResponseEntity<ReviewComments> getSingleReviewComment(@RequestParam(name = "commentId") Long commentId){
        log.info("Get Single Review Comment = {}", commentId);

        ReviewComments getSinlgeComment = commentService.getSingleComment(commentId);

        return ResponseEntity.ok(getSinlgeComment);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reviews")
    public void regComment(@RequestBody ReviewCommentDTO dto){
        log.info("Register Comment ReviewComment = {}",dto);

        commentService.regComment(dto);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/reviews")
    public void updateComment(@RequestBody ReviewCommentDTO dto){
        log.info("UPDATE COMMENT = {}", dto);

        commentService.updateComment(dto);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/reviews")
    public void deleteComment(@RequestParam (name = "commentId") Long commentId){
        log.info("DELETE REVIEWS COMMENT commentId= {}" , commentId);

        commentService.deleteComment(commentId);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/reviews/like")
    public void likeComment(@RequestBody ReviewCommentLikeDTO dto){
        log.info("LIKE REVIEW COMMENTS comment = {}",  dto);

        commentService.likeComment(dto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/reviews/check-like")
    public ResponseEntity<Boolean> checkIfUserLikedComment(@RequestParam (name = "commentId") Long commentId, @RequestParam(name = "email") String email){
        log.info("USER LIKED COMMENT? commentId = {}, email = {}", commentId,email);

        ReviewComments comments = new ReviewComments();
        comments.setCommentId(commentId);

        Member member = new Member();
        member.setEmail(email);

        boolean didUserLikedComment = commentService.didReviewCommentLike(comments, member);

        return ResponseEntity.ok(didUserLikedComment);
    }

}
