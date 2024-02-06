/**
 * navbar.js
 */
window.addEventListener('DOMContentLoaded', function() {
	
	
	const btnMenu = document.querySelector('.navbar-menu-btn');
	
	
	// 메뉴버튼 누르면 토글될 수 있도록 함	
	btnMenu.addEventListener('click', function() {
		
		const detailedMenu = document.querySelector('.navbar-detailed-menu');
		
		const menuHeight = detailedMenu.style.height;
		const menuWidth = detailedMenu.style.width;
		console.log(menuHeight);
		console.log(detailedMenu.scrollHeight);
		console.log(window.innerWidth);
		if (window.innerWidth > 1024) {	// 폭이 1024px초과할 때
			if (menuHeight == '' || menuHeight == '0px') {	// 높이 0일 때 (접혀있을 때)
				console.log('높이 현재 0');
				detailedMenu.style.height = detailedMenu.scrollHeight + 'px';
			} else {	// 메뉴바 펼쳐져 있을 때
				
				detailedMenu.style.height = 0;															
	
			}
		} else {	// 폭이 1024px 이하일 때
			
			
			
		}

	});
	
	
	window.addEventListener('resize', function() {		
		
		const detailedMenu = document.querySelector('.navbar-detailed-menu');

		
		const menuHeight = detailedMenu.style.height;
		const menuWidth = detailedMenu.style.width;
		
		if (window.innerWidth < 1024) {
			detailedMenu.style.width = '280px';
			detailedMenu.style.height = '100vh';
		} else {
			detailedMenu.style.width = '85%';
			detailedMenu.style.height = 'auto';
		}
		
	})
	
	
	
});