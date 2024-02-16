/**
 * movie-detail.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	const signedInUser = document.querySelector('.div-profile-image');		// 닉네임을 포함하고 있는 유저 로그인했을 때 프로필사진 컨테이너

	const btnOpenChat = document.querySelector('.btn-open-chat');	// 채팅방 엶
	
	const textareaReviewComment = document.querySelector('.text-area-review-comment');
	const divCountCharacters = document.querySelector('.div-review-count-characters');	// 캐릭터 수 세는 div.
	const spanCountCharacters = document.querySelector('.span-review-count-characthers');	// 캐릭터 수 세는 span
	const contextRoot = location.origin;
	const pathName = location.pathname;
	
	const movieId = pathName.split('/')[3];
	
	console.log(movieId);	
		
	btnOpenChat.addEventListener('click', function() {
		
				
		if (!signedInUser) {
			alert('오픈채팅에 참여하려면 로그인을 해주세요.');
			return;
		}
		
		
		window.open(`${contextRoot}/openchat/movie/${movieId}`, "_blank", 'width=450, height=700');
        
		
	});
	
	if (textareaReviewComment) {
		textareaReviewComment.addEventListener('input', function() {
			
			const numCharacters = textareaReviewComment.value.length;
			
			spanCountCharacters.textContent = numCharacters;
			
		});	
	}
	
	
	
});