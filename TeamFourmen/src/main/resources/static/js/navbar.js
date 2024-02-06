/**
 * navbar.js
 */
window.addEventListener('DOMContentLoaded', function() {
	
	
	const btnMenu = document.querySelector('.navbar-menu-btn');
	const detailedMenu = document.querySelector('#navbar-detailed-menu');
	
	// 메뉴버튼 누르면 토글될 수 있도록 함	
	btnMenu.addEventListener('click', function() {
		
		const menuHeight = detailedMenu.style.height;
		console.log(menuHeight);
		console.log(detailedMenu.scrollHeight);
		console.log(window.innerWidth);
		if (menuHeight == '' || menuHeight == '0px') {
			console.log('높이 현재 0')
			detailedMenu.style.width = '85%';
			detailedMenu.style.height = detailedMenu.scrollHeight + 'px';
			detailedMenu.style.opacity = 1;
		} else {
			detailedMenu.style.height = 0;
			detailedMenu.style.opacity = 0;
		}

			
	});
	
	
	
	
});