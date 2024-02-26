/**
 * board-details.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	const btnPostLike = document.querySelector('.btn-like-post');
	const spanLikeBtn = btnPostLike.querySelector('.span-like-btn');
	const spanNumLikes = document.querySelector('.span-num-likes');
	let numLikes = parseInt(spanNumLikes.textContent);
	
	const user = document.querySelector('.div-profile-image');
	const userEmail = user.getAttribute('email');
	const userNickname = user.getAttribute('nickname');
	
	const postId = document.querySelector('.post-title').getAttribute('postId');
	const authorNickname = document.querySelector('.div-post-author').textContent;	
	
	console.log(`postId=${postId}`);
	console.log(`작가 닉네임=${authorNickname}`);
	console.log(userEmail);
	console.log(userNickname);
	
	if (btnPostLike.classList.contains('liked-already')) {
		spanLikeBtn.textContent = '좋아요 취소';
	}
	
	btnPostLike.addEventListener('click', function() {
		
		if (userNickname === authorNickname) {
			alert('내 게시글엔 좋아요를 누를 수 없습니다.');
			return;
		}
		
		const data = {
			member: {
				email: userEmail
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
			axios.post('/board/delete', data)
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
	
});