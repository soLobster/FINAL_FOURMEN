/**
 * movie-detail.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	
	const btnOpenChat = document.querySelector('.btn-open-chat');
	
	const contextRoot = location.origin;
		
		
	btnOpenChat.addEventListener('click', function() {
		
		window.open(`${contextRoot}/openchat/movie/${movieId}`, "_blank", 'width=450, height=700');
        
		
	});
	
});