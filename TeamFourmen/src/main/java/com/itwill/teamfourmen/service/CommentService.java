package com.itwill.teamfourmen.service;

import com.itwill.teamfourmen.domain.*;
import com.itwill.teamfourmen.dto.comment.ReviewCommentDTO;
import com.itwill.teamfourmen.dto.comment.ReviewCommentLikeDTO;
import com.itwill.teamfourmen.repository.ReviewCommentLikeRepository;
import com.itwill.teamfourmen.repository.ReviewCommentsRepository;
import com.itwill.teamfourmen.repository.ReviewDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final ReviewCommentLikeRepository reviewCommentLikeRepository;
    private final ReviewCommentsRepository reviewCommentsDao;
    private final ReviewDao reviewDao;
    private final MemberRepository memberDao;

    public Page<ReviewComments> getAllComments(Long reviewId, int page){
        log.info("GET ALL COMMENTS OF REVIEW = {} , PAGE  ={} ", reviewId, page);

        // 특정 리뷰를 가져온다.
        Review targetReview = reviewDao.findByReviewId(reviewId);

        // 페이징
        Pageable pageable = PageRequest.of(page, 10, Sort.by("likes").descending());

        // 리뷰에 대한 댓글들을 가져온다.
        Page<ReviewComments> targetReviewCommentList = reviewCommentsDao.findByReview(targetReview, pageable);

        log.info("targetReviewCommentList number = {}, TotalPage = {}", targetReviewCommentList.getNumber(), targetReviewCommentList.getTotalPages());

        return targetReviewCommentList;
    }

    public List<ReviewComments> getAllComments(Long reviewId){
        log.info("GET ALL COMMENT OF REVIEW = {}", reviewId);

         Review targetReview = reviewDao.findByReviewId(reviewId);

         List<ReviewComments> targetReviewCommentList = reviewCommentsDao.findByReview(targetReview);

         return  targetReviewCommentList;
    }

    public ReviewComments getSingleComment(Long commentId){
        log.info("GET SINGLE COMMENT = {}",commentId);

       ReviewComments getComment =  reviewCommentsDao.findById(commentId).orElseThrow();

       return getComment;
    }

    public void regComment(ReviewCommentDTO dto){
        log.info("REGISTER COMMENT = {}",dto);

        Review review = reviewDao.findByReviewId(dto.getReviewId());

        Member member = memberDao.findByEmail(dto.getCommentWriterEmail()).orElseThrow();

        ReviewComments entity = ReviewComments.builder()
                .member(member)
                .content(dto.getCommentContent())
                .review(review)
                .likes(0L)
                .build();

        reviewCommentsDao.save(entity);
    }

    public void deleteComment(Long commentId){
        log.info("DELETE COMMENT = {}" , commentId);

        reviewCommentsDao.deleteById(commentId);
    }

    @Transactional
    public void likeComment(ReviewCommentLikeDTO dto){
        log.info("LIKE COMMENT = {}", dto);

        ReviewComments reviewComments = reviewCommentsDao.findById(dto.getReviewComments().getCommentId()).orElseThrow();

        log.info("comment = {}", reviewComments);

        reviewComments.plusCommentLike(reviewComments.getLikes());

        Member member = memberDao.findByEmail(dto.getMember().getEmail()).orElseThrow();

        log.info("Find - member= {}", member);

        ReviewCommentsLike entity = ReviewCommentsLike.builder()
                .member(member)
                .reviewComments(reviewComments)
                .build();

        reviewCommentLikeRepository.save(entity);
    }

    @Transactional
    public void cancleLikeComment(ReviewCommentLikeDTO dto){

    }

    public ReviewCommentsLike didReviewCommentLike(ReviewComments comments, Member member){
        log.info("DID USER LIKE COMMENTS?? COMMENTS = {}, MEMBER = {}", comments, member);

        ReviewComments reviewComments = reviewCommentsDao.findById(comments.getCommentId()).orElseThrow();

        log.info("target Review Comment = {}",reviewComments);

        ReviewCommentsLike reviewCommentsLike = reviewCommentLikeRepository.findByMemberAndReviewComments_CommentId(member, reviewComments.getCommentId());

        log.info("target Review Comment Like ? = {}",reviewCommentsLike);

        return reviewCommentsLike;
    }


    @Transactional
    public void updateComment(ReviewCommentDTO dto) {
        log.info("UPDATE COMMENT = {}", dto);

        ReviewComments reviewComments = reviewCommentsDao.findById(dto.getCommentId()).orElseThrow();

        log.info("BEFORE COMMENT = {}", reviewComments.getContent());

        reviewComments.updateComment(dto.getCommentContent());
    }

}
