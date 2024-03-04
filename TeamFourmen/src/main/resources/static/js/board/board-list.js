/**
 * board-list.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	const btnBoardCreate = document.querySelector('.btn-board-create');
	
	const category = location.pathname.split('/')[1];
	
	
	btnBoardCreate.addEventListener('click', function() {
		
		location.href=`/${category}/board/create`
		
	});
});