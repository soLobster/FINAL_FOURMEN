/**
 * movie-detail.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	
	const btnOpenChat = document.querySelector('.btn-open-chat');	// 채팅방 엶
	
	const textareaReviewComment = document.querySelector('.text-area-review-comment');
	const divCountCharacters = document.querySelector('.div-review-count-characters');	// 캐릭터 수 세는 div.
	const spanCountCharacters = document.querySelector('.span-review-count-characthers');	// 캐릭터 수 세는 span
	const contextRoot = location.origin;
	
	
		
	btnOpenChat.addEventListener('click', function() {
		
		window.open(`${contextRoot}/openchat/movie/${movieId}`, "_blank", 'width=450, height=700');
        
		
	});
	
	
	textareaReviewComment.addEventListener('input', function() {
		
		const numCharacters = textareaReviewComment.value.length;
		
		spanCountCharacters.textContent = numCharacters;
		
	});
	
	
});