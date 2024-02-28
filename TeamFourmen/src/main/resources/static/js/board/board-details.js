/**
 * board-details.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	const btnPostLike = document.querySelector('.btn-like-post');
	const spanLikeBtn = btnPostLike.querySelector('.span-like-btn');
	const spanNumLikes = document.querySelector('.span-num-likes');
	let numLikes = parseInt(spanNumLikes.textContent);
	
	// 게시글 삭제, 수정 버튼
	const btnDeletePost = document.querySelector('.btn-delete-post');
	const btnEditPost = document.querySelector('.btn-edit-post');
	
	const user = document.querySelector('.div-profile-image');
	
	const postId = document.querySelector('.post-title').getAttribute('postId');
	const authorNickname = document.querySelector('.div-post-author').textContent;	
		
	
	// 댓글관련 속성
	const textareaPostComment = document.querySelector('.textarea-post-comment');
	const btnAddPostComment = document.querySelector('.btn-add-post-comment');
	
	const spanNumComments = document.querySelector('.span-num-comments');
	
	const btnCommentRefresh = document.querySelector('.btn-post-comment-refresh');
	const btnCommentDeleteList = document.querySelectorAll('.btn-post-comment-delete');
	
	const commentContainer = document.querySelector('.post-comment-container');
	
	// 댓글 답장 관련 속성
	const btnCommentReplyList = document.querySelectorAll('.btn-post-comment-reply');
	const commentAddReplyContainerList = document.querySelectorAll('.post-comment-add-reply-container');
	
	// 게시글 카테고리(영화, 티비, 인물)
	const category = location.pathname.split('/')[1];
	

	// 게시글 디테일 페이지 띄웠을 때 좋아요 눌렀으면 좋아요버튼 좋아요 취소로 보여주기
	if (btnPostLike.classList.contains('liked-already')) {
		spanLikeBtn.textContent = '좋아요 취소';
	}
	
	
		// 게시글 삭제 버튼
	if (btnDeletePost) {	
		btnDeletePost.addEventListener('click', function() {
			const postId = btnDeletePost.getAttribute('postId');
			
			axios.delete(`/board/delete/${postId}`)
				.then(() => {
					alert('게시글 삭제 성공');
					window.history.back();
				})
				.catch((error) => {
					console.log(`에러 발생!!! ${error}`);
				});
			
		});
	}
	
	// 게시글 수정버튼
	if(btnEditPost) {
		btnEditPost.addEventListener('click', function() {
			const divPostContent = document.querySelector('.div-post-content');
			const divTitle = document.querySelector('.post-title');			
			
			const formToEdit = document.createElement('form');
			formToEdit.method = 'post';
			formToEdit.action = `/${category}/board/edit`;
			formToEdit.classList.add('d-none');
			
			const inputTitle = document.createElement('input');
			inputTitle.type="text";
			inputTitle.name = 'title';
			inputTitle.setAttribute('value', divTitle.textContent);
						
			const inputContent = document.createElement('input');			
			inputContent.type="text";
			inputContent.name = 'content';
			inputContent.setAttribute('value', divPostContent.innerHTML)
			
			const inputPostId = document.createElement('input');
			inputPostId.type="text";
			inputPostId.name="postId";
			inputPostId.setAttribute('value', postId);
			
			console.log(inputPostId);
			
			
			formToEdit.append(inputTitle, inputContent, inputPostId);
			
			
			document.body.appendChild(formToEdit);
			formToEdit.submit();
			
		});
	}
	
	
	// 댓글 좋아요 이벤트리스너 등록하는 함수
	const registerCommentLikeEventListener = function() {
		const commentLikeContainerList = document.querySelectorAll('.post-comment-like-container');
		
		commentLikeContainerList.forEach((btnLike) => {
			
			const commentIdForBtn = btnLike.getAttribute('commentId');
			const commentEachLikeContainer = btnLike.closest('.post-comment-each-like-container');
			const spanNumCommentLikes = commentEachLikeContainer.querySelector('.span-num-comment-likes');
			let commentNumLikes = parseInt(spanNumCommentLikes.textContent);
			
			btnLike.addEventListener('click', function() {
				if(!user) {
					alert('로그인한 유저만 댓글 좋아요를 누를 수 있습니다.');
					return;
				}
				
				
				if (user.getAttribute('nickname') === btnLike.getAttribute('author')) {
					alert('본인글에 좋아요를 누를 수 없습니다.');
					return;
				}
				
				const data = {
					member: {
						email: user.getAttribute('email') 
					},
					comment: {
						commentId: commentIdForBtn
					}				
				};
				
				if (btnLike.classList.contains('post-comment-already-liked')) {	// 이미 좋아요 누른 경우
					axios.post('/board/comment/like/delete', data)
						.then((response) => {										
							btnLike.classList.remove('post-comment-already-liked');
							commentNumLikes--;
							spanNumCommentLikes.textContent = commentNumLikes;
						})
						.catch((error) => {
							console.log(`에러 발생!!! ${error}`);
						})			
				} else {	// 좋아요 안누른 상태인 경우
					axios.post('/board/comment/like/add', data)
						.then((response) => {
							btnLike.classList.add('post-comment-already-liked');
							commentNumLikes++;
							spanNumCommentLikes.textContent = commentNumLikes;
						})
						.catch((error) => {
							console.log(`에러 발생!!! ${error}`);
						})
				}
				
				
				
			});
			
		});
	}
	
	
	
	// 댓글 refresh하는 함수
	const refreshComments = function() {
		
		const params = {
			postId: postId
		};
		
		axios.get('/board/comment/refresh', {params})
			.then((response) => {
				commentContainer.innerHTML = '';
				
				const commentDtoList = response.data;
				
				for (let comment of commentDtoList) {
					
					commentContainer.innerHTML += `					
			            <div class="post-comment-each-container">
			                <div class="post-comment-each-header">
			                    <div class="post-comment-author-create-date-container post-comment-text-vertical-center">
			                        <div class="post-comment-each-profile-container">
			                            <img class="post-comment-each-profile" src="https://photocloud.sbs.co.kr/origin/edit/S01_P468097941/657ba203942b8f2120ce7144-p.jpg" alt="profile-img">
			                        </div>
			                        <div class="post-comment-each-author">${comment.member.nickname}</div>
			                        ${comment.member.nickname === authorNickname ? '<div class="post-comment-by-post-author post-comment-text-vertical-center">작성자</div>' : ''}
			                    </div>                    
			                    <div class="post-comment-each-created-time post-comment-text-vertical-center">${formatTime(comment.createdTime)}</div>
			                </div>
			                <div class="post-comment-body-container">
			                    <div class="post-comment-content">
			                        ${comment.content}
			                    </div>
			                    <div class="post-comment-each-like-container">
                                   <div>
			                            <span>likes</span>
			                            <span class="span-num-comment-likes">${comment.commentLikesList.length}</span>
			                        </div>
			                        <div class="post-comment-each-like-report-icon-container">
			                            <div class="post-comment-like-container ${comment.commentLikesList.some((element) => element.member.email === user.getAttribute('email')) ? 'post-comment-already-liked' : ''}"
			                                commentId="${comment.commentId}" author="${comment.member.nickname}">
			                                <i class="fa-solid fa-thumbs-up"></i>
			                            </div>
			                            <div>
			                                <i class="fa-solid fa-flag"></i>
			                            </div>
			                        </div>                        
			                    </div>
			                </div>
			            </div>						
						`;
					
				}
								
				
				registerCommentLikeEventListener();
				
			})
			.catch((error) => {
				console.log(`에러 발생!!! ${error}`);
			});
		
	}
	
	
	
	// 시간 포매팅하는 함수
	const formatTime = function(timeToFormat) {
		
		  const date = new Date(timeToFormat);
		  
		  return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')} ${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;	
	}
	
	registerCommentLikeEventListener();
	
	
	
	
	
	// 좋아요 버튼 누를 때 이벤트 리스너
	btnPostLike.addEventListener('click', function() {

		if(!user) {
			alert('로그인 한 유저만 좋아요를 누를 수 있습니다.');
			return;
		}
		
		if (user.getAttribute('nickname') === authorNickname) {
			alert('내 게시글엔 좋아요를 누를 수 없습니다.');
			return;
		}
		
		const data = {
			member: {
				email: user.getAttribute('email')
			},
			post: {
				postId: postId
			}			
		}
		
		if(!btnPostLike.classList.contains('liked-already')) {	// 좋아요 누르지 않은 상태인 경우
			axios.post('/board/like', data)
				.then((response) => {				
					numLikes ++;
					spanNumLikes.textContent =  numLikes;
					btnPostLike.classList.add('liked-already');
					spanLikeBtn.textContent = '좋아요 취소';
					
				})
				.catch((error) => {
					console.log(`에러발생!!! ${error}`);	
				});			
		} else {	// 좋아요 이미 누른경우
			axios.post('/board/like/delete', data)
				.then((response) => {
					numLikes--;
					spanNumLikes.textContent = numLikes;
					btnPostLike.classList.remove('liked-already');
					spanLikeBtn.textContent = '좋아요';
					console.log(numLikes);
				})
				.catch((error) => {
					console.log(`에러발생!!! ${error}`);
				})
		}

	});
	
	
	// 댓글다는 버튼 이벤트 리스너
	btnAddPostComment.addEventListener('click', function() {
		
		if(!user) {
			alert('댓글을 달기 위해서는 로그인해주세요.');
			return;
		}
		
		if (!textareaPostComment.value) {
			alert('댓글 내용을 입력해주세요');
			return;
		}
		
		const userProfileImage = user.querySelector('img');
		console.log(userProfileImage);
		
		const data = {
			member: {
				email: user.getAttribute('email')
			},
			post: {
				postId: postId
			},
			content: textareaPostComment.value			
		}
		
		axios.post('/board/comment/add', data)
			.then((response) => {
				alert('댓글 등록 성공');
				textareaPostComment.value = '';
				
				commentContainer.innerHTML += `
					
		            <div class="post-comment-each-container">
		                <div class="post-comment-each-header">
		                    <div class="post-comment-author-create-date-container post-comment-text-vertical-center">
		                        <div class="post-comment-each-profile-container">
		                            <img class="post-comment-each-profile" src="https://photocloud.sbs.co.kr/origin/edit/S01_P468097941/657ba203942b8f2120ce7144-p.jpg" alt="profile-img">
		                        </div>
		                        <div class="post-comment-each-author">${user.getAttribute('nickname')}</div>
		                        ${user.getAttribute('nickname') === authorNickname ? '<div class="post-comment-by-post-author post-comment-text-vertical-center">작성자</div>' : ''}
		                    </div>                    
		                    <div class="post-comment-each-created-time post-comment-text-vertical-center">${formatTime(response.data.createdTime)}</div>
		                </div>
		                <div class="post-comment-body-container">
		                    <div class="post-comment-content">
		                        ${response.data.content}
		                    </div>
		                    <div class="post-comment-each-like-container">
                                <div>
		                            <span>likes</span>
		                            <span class="span-num-comment-likes">${response.data.commentLikesList.length}</span>
		                        </div>
		                        <div class="post-comment-each-like-report-icon-container">
		                            <div class="post-comment-like-container" commentId="${response.data.commentId}" author="${response.data.member.nickname}">
		                                <i class="fa-solid fa-thumbs-up"></i>
		                            </div>
		                            <div>
		                                <i class="fa-solid fa-flag"></i>
		                            </div>
		                        </div>                        
		                    </div>
		                </div>
		            </div>
					
					`;
				
				const numCommments = parseInt(spanNumComments.textContent);
				spanNumComments.textContent = `${numCommments + 1}`;
				
				registerCommentLikeEventListener();
				
			})
			.catch((error) => {
				console.log(`에러 발생!!! ${error}`)
			})
		
	});
	
	
	btnCommentRefresh.addEventListener('click', refreshComments);
	
	
	// 댓글 삭제버튼 
	btnCommentDeleteList.forEach((btnCommentDelete) => {
		
		const commentIdToDelete = btnCommentDelete.getAttribute('commentId');		
		
		btnCommentDelete.addEventListener('click', function() {
			
			const data = {
				comment: {
					commentId: commentIdToDelete
				}				
			}						
			
			axios.delete(`/board/comment/delete/${commentIdToDelete}`, data)
				.then(() => {
					refreshComments();
				})
				.catch((error) => {
					console.log(`에러 발생!!! ${error}`);
				})			
		})
		
	});
	
	btnCommentReplyList.forEach((btnCommentReply) => {
		
		
		const postCommentReplyContainer = btnCommentReply.closest('.post-comment-reply-container');
		const commentAddReplyContainer = postCommentReplyContainer.nextElementSibling;
		
		btnCommentReply.addEventListener('click', function() {
			console.log(btnCommentReply);
			console.log(commentAddReplyContainer);
			if(commentAddReplyContainer.classList.contains('d-none')) {
				commentAddReplyContainer.classList.remove('d-none');
			} else {
				commentAddReplyContainer.classList.add('d-none');
			}
			
		});
		
	})
	
});