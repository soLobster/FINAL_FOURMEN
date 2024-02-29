package com.itwill.teamfourmen.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.teamfourmen.domain.Comment;
import com.itwill.teamfourmen.domain.CommentLike;
import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.MemberRepository;
import com.itwill.teamfourmen.domain.Post;
import com.itwill.teamfourmen.domain.PostLike;
import com.itwill.teamfourmen.dto.board.CommentDto;
import com.itwill.teamfourmen.dto.board.PostDto;
import com.itwill.teamfourmen.dto.person.PageAndListDto;
import com.itwill.teamfourmen.dto.post.PostCreateDto;
import com.itwill.teamfourmen.repository.CommentLikeRepository;
import com.itwill.teamfourmen.repository.CommentRepository;
import com.itwill.teamfourmen.repository.PostLikeRepository;
import com.itwill.teamfourmen.repository.PostRepository;
import com.okta.spring.boot.oauth.env.OktaEnvironmentPostProcessorApplicationListener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
	
	private final PostRepository postDao;
	private final PostLikeRepository postLikeDao;
	private final CommentRepository commentDao;
	private final CommentLikeRepository commentLikeDao;
	private final MemberRepository memberDao;
	
	private int postsPerPage = 5;
	
	/**
	 * postDto를 아규먼트로 받아 게시글 작성하는 메서드
	 * @param postDto
	 */
	public Post post(PostCreateDto postDto) {
		log.info("post(postDto={})", postDto);
		
		Post post = postDto.toEntity();
		Post savedPost = postDao.save(post);
		
		return savedPost;
	}
	
	/**
	 * postId를 아규먼트로 받아 게시글 삭제하는 메서드
	 * @param postId
	 */
	public void deletePost(Long postId) {
		log.info("deletePost(postId={})", postId);
		
		postDao.deleteById(postId);
	}
	
	@Transactional
	public void updatePost(Post post) {
		log.info("updatePost(post={})", post);
		
		Optional<Post> postOptional = postDao.findById(post.getPostId());
		Post postToUpdate = postOptional.orElse(null);
		
		postToUpdate.setTitle(post.getTitle());
		postToUpdate.setContent(post.getContent());
		postToUpdate.setModifiedTime(LocalDateTime.now());
		
	}
	
	/**
	 * 아규먼트로 받은 카테고리의 게시물 리스트를 반환
	 * @param category
	 * @return
	 */
	public Page<PostDto> getPostList(String category, int page) {
		
		log.info("getPostList(category={}, page={}", category, page);
		
		Pageable pageable = PageRequest.of(page, postsPerPage, Sort.by("postId").descending());
		
		Page<Post> postList = postDao.findAllByCategoryOrderByCreatedTimeDesc(category, pageable);
		Page<PostDto> postDtoList = postList.map((post) -> PostDto.fromEntity(post));
		
		postDtoList.forEach((postDto) -> {
			postDto.setTimeDifferenceInMinute(getMinuteDifferenceIfDateSame(postDto.getCreatedTime()));
			log.info("time difference={}", postDto.getTimeDifferenceInMinute());
		});
		
		log.info("postDtoList={}", postDtoList);		
		
		return postDtoList;
	}
	
	/**
	 * id를 아규먼트로 받아, 해당 postId의 게시물을 Post 타입으로 반환함
	 * 
	 * @param id
	 * @return Post타입의 해당 postId의 게시물 객체
	 */
	public PostDto getPostDetail(Long id) {
		log.info("getPostDetail(id={})", id);
		
		Optional<Post> postDetailsOptional = postDao.findById(id);
		
		Post postDetails = postDetailsOptional.orElse(null);
		PostDto postDetailsDto = PostDto.fromEntity(postDetails);
		postDetailsDto.setTimeDifferenceInMinute(getMinuteDifferenceIfDateSame(postDetailsDto.getCreatedTime()));
		
		return postDetailsDto;
	}
	
	public PostLike haveLiked(Member signedInUser, Long postId) {
		log.info("haveLiked(signedInUser={}, postId={})", signedInUser, postId);
		
		Optional<PostLike> haveLikedOptional = postLikeDao.findByMemberEmailAndPostPostId(signedInUser.getEmail(), postId);
		PostLike haveLiked = haveLikedOptional.orElse(null);
		
		return haveLiked;
	}
	
	
	/**
	 * postId에 해당하는 게시물의 조회수를 1 늘려줌
	 * @param postId
	 */
	@Transactional
	public void addView(Long postId) {
		log.info("addView(postId={})", postId);
		
		Optional<Post> thePostOptional = postDao.findById(postId);
		
		Post thePost = thePostOptional.orElse(null);
		
		Long views = thePost.getViews();
		thePost.setViews(views + 1);
	}
	
	/**
	 * postId에 해당하는 게시글이 받은 좋아요 개수 표시하기 위한 서비스 메서드
	 * @param postId
	 * @return
	 */
	public Long countLikes(Long postId) {
		log.info("countLikes(postId={})", postId);
		
		return postLikeDao.countByPostPostId(postId);			
	}
	
	/**
	 * 좋아요 추가하는 메서드
	 * @param postLike
	 * @return
	 */
	@Transactional
	public PostLike addLike(PostLike postLike) {
		log.info("addLike(postLike={})", postLike);
		
		PostLike savedPostLike = postLikeDao.save(postLike);
		
		return savedPostLike;
	}
	
	
	/**
	 * 좋아요 취소하는 메서드
	 * @param postLike
	 */
	@Transactional
	public void deleteLike(PostLike postLike) {
		log.info("deleteLike(postLike={})", postLike);
		
		postLikeDao.deleteByMemberEmailAndPostPostId(postLike.getMember().getEmail(), postLike.getPost().getPostId());
	}
	
	/**
	 * postId에 달린 모든 댓글들의 리스트를 반환(리스트는 가장 상위 댓글을 포함하며, 상위댓글 dto의 필드로 답글들이 리스트로 있음)
	 * @param postId
	 * @return postId에 달린 모든 댓글들의 리스트를 반환(리스트는 가장 상위 댓글을 포함하며, 상위댓글 dto의 필드로 답글들이 리스트로 있음)
	 */
	public List<CommentDto> getCommentList(Long postId) {
		log.info("getCommentList(postId={})", postId);
		
		List<Comment> commentList = commentDao.findByPostPostIdAndReplyToOrderByCommentIdAsc(postId, null);
		
		List<CommentDto> commentDtoList = new ArrayList<>();
		List<CommentDto> repliesList = new ArrayList<>();
		
		commentList.forEach((comment) -> {			
			CommentDto commentDto = CommentDto.fromEntity(comment);
			
			List<CommentLike> commentLikeList = commentLikeDao.findAllByCommentCommentId(comment.getCommentId());
			commentDto.setCommentLikesList(commentLikeList);
			
			commentDto.setTimeDifferenceInMinute(getMinuteDifferenceIfDateSame(commentDto.getCreatedTime()));
			
			// 해당 부모댓글로부터 이어진 모든 대댓글들을 가져옴
			List<Comment> initialRepliesList = commentDao.findAllByReplyTo(comment.getCommentId());
			List<CommentDto> initialRepliesDtoList = initialRepliesList.stream().map((reply) -> CommentDto.fromEntity(reply)).toList();
			commentDto.getRepliesList().addAll(initialRepliesDtoList);
			for (CommentDto initialReplyDto : initialRepliesDtoList) {
				initialReplyDto.setTimeDifferenceInMinute(getMinuteDifferenceIfDateSame(initialReplyDto.getCreatedTime()));
				addAllRepliesToComments(commentDto, initialReplyDto);	
			}
			
			commentDtoList.add(commentDto);			
		});
		
		// 가져온 대댓글들을 commentId순으로 정렬
		commentDtoList.forEach((commentDto) -> Collections.sort(commentDto.getRepliesList(), CommentDto.ORDER_BY_COMMENT_ID_ASC));
		
		log.info("commentDtoList={}", commentDtoList);
		
		return commentDtoList;
	}
	
	public int getNumOfComments(List<CommentDto> commentDtoList) {
		log.info("getNumOfComments(commentDtoList={})", commentDtoList);
		
		int numOfComment = 0;
		numOfComment += commentDtoList.size();
		
		for (CommentDto commentDto : commentDtoList) {
			numOfComment += commentDto.getRepliesList().size();
		}
		
		return numOfComment;
	}
	
	/**
	 * comment를 아규먼트로 받아 DB에 댓글을 등록하는 메서드. 등록한 댓글을 리턴함
	 * @param comment
	 * @return
	 */
	@Transactional
	public CommentDto addComment(Comment comment) {
		log.info("addCommnet(comment={})", comment);
		
		Comment savedComment = commentDao.save(comment);		
		log.info("saved comment = {}", savedComment);
		
		CommentDto savedCommentDto = CommentDto.fromEntity(savedComment);
		
		savedCommentDto.setCommentLikesList(commentLikeDao.findAllByCommentCommentId(savedCommentDto.getCommentId()));
		
		Member member = memberDao.findByEmail(savedComment.getMember().getEmail()).orElse(null);
		savedCommentDto.setMember(member);
		
		return savedCommentDto;
	}
	
	/**
	 * 댓글 삭제하는 메서드
	 * @param commentId
	 */
	@Transactional
	public void deleteComment(Long commentId) {
		log.info("deleteComment(commentId={})", commentId);
		
		commentDao.deleteById(commentId);
		
	}
	
	/**
	 * 댓글 좋아요 추가하는 메서드
	 * @param commentLike
	 * @return
	 */
	@Transactional
	public CommentLike addCommentLike(CommentLike commentLike) {
		log.info("addCommentLike(commentLike={})", commentLike);
		
		CommentLike savedCommentLike = commentLikeDao.save(commentLike);
		
		return savedCommentLike;
	}
	
	/**
	 * 댓글 좋아요 취소 메서드
	 * @param commentLike
	 */
	@Transactional
	public void deleteCommentLike(CommentLike commentLike) {
		log.info("deleteCommentLike(commentLike={})", commentLike);
		
		commentLikeDao.deleteByCommentCommentIdAndMemberEmail(commentLike.getComment().getCommentId(), commentLike.getMember().getEmail());
	}
	
	
	// 보조 메서드
	/**
	 * 가장 상위 댓글 Dto에 모든 답변들과 관련한 모든 List를 가져옴
	 * @param commentDto 가장 상위 댓글 Dto
	 * @param replyDto 상위댓글에 대한 직접적인 답글 CommentDto
	 */
	public void addAllRepliesToComments(CommentDto commentDto, CommentDto replyDto) {
		
		List<Comment> repliesList = commentDao.findAllByReplyTo(replyDto.getCommentId());
		List<CommentDto> repliesDtoList = repliesList.stream().map((reply) -> CommentDto.fromEntity(reply)).toList();
		
		repliesDtoList.forEach((replyComment) -> replyComment.setTimeDifferenceInMinute(getMinuteDifferenceIfDateSame(replyComment.getCreatedTime())));
		
		commentDto.getRepliesList().addAll(repliesDtoList);
		
		
		
		for(CommentDto repliesReplyDto : repliesDtoList) {
			addAllRepliesToComments(commentDto, repliesReplyDto);
		}
	}
	
	
	/**
	 * LocalDateTime 타입의 아규먼트와 현재 시간의 차이가 24시간 이내일 경우, 시간차이를 '분'(Long 타입)으로 반환
	 * @param timeVariable
	 * @return 시간차 24시간 이내일 경우 시간차를 '분'으로 반환, 24시간 이상일 경우 null
	 */
	public Long getMinuteDifferenceIfDateSame(LocalDateTime timeVariable) {
		
		log.info("timeVariable");
		
		LocalDateTime currentTime = LocalDateTime.now();
		
		Duration duration = Duration.between(timeVariable, currentTime);
		
		Long timeDifferenceInMinute = null;
		if (duration.toMinutes() < 60 * 24) {
			timeDifferenceInMinute = duration.toMinutes();
		}
		
		return timeDifferenceInMinute;
	}
	
}	// BoardService class끝
