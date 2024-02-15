/**
 * navbar.js
 */
window.addEventListener('DOMContentLoaded', function() {
	
	const detailedMenu = document.querySelector('.navbar-detailed-menu');
	const btnMenu = document.querySelector('.navbar-menu-btn');
	
	const btnClose = document.querySelector('.navbar-detailed-x');
	const body = document.querySelector('body');
	const navbarBackground = document.querySelector('.navbar-background');	// 모달 배경이 되어줄 div		
	
	const navbarDetailedTitleDiv =  document.querySelectorAll('.navbar-detailed-menu-title');	//화면 작아지면 아코디언을 위한 엘리먼트(타이틀 div)
	
	
	if (window.innerWidth > 1024) {
        detailedMenu.style.transition = 'height 0.5s ease-in-out';
	} else {
		detailedMenu.style.transition = 'transform 0.5s ease-in-out';
	}
	
	
	// 메뉴 open 클래스 토글하는 함수
	const toggleOpenClass = function () {
        const detailedMenu = document.querySelector('.navbar-detailed-menu');
        
        const doesContain = detailedMenu.classList.contains('navbar-detailed-menu-open');
        
            if (doesContain) {    // 만약 open class 있을 때               
                detailedMenu.classList.remove('navbar-detailed-menu-open'); 
                navbarBackground.classList.remove('body-detailed-menu-open');                
                body.classList.remove('body-element-navbar');
                               
            } else {    // open class없을 때               
                detailedMenu.classList.add('navbar-detailed-menu-open');
                navbarBackground.classList.add('body-detailed-menu-open');
                body.classList.add('body-element-navbar');
         
            }
        
    }
    
    
    // delay되서 실행되는 transition
    const delayedTransition = (transitionItem) => {
		setTimeout(() => {
			
			detailedMenu.style.transition = transitionItem;
			
		}, 50);		
	} 
    
    
    // Throttle함수
    const throttle = (cb, delay=1000) => {       
        let shouldWait = false;
        
        return (...args) => {
            if (shouldWait) {
                return;
            }
            
            cb(...args);
            shouldWait = true;
            
            setTimeout(() => {
                shouldWait = false;
            }, delay)
        }
        
    };
    
	
	
	// 메뉴버튼 누르면 토글될 수 있도록 함	
	btnMenu.addEventListener('click', throttle(function() {
				
		const menuHeight = detailedMenu.style.height;
		const menuWidth = detailedMenu.style.width;
		console.log(menuHeight);
		console.log(detailedMenu.scrollHeight);
		console.log(window.innerWidth);
		
		const doesContain = detailedMenu.classList.contains('navbar-detailed-menu-open');
		
		if (window.innerWidth > 1024) {	// 폭이 1024px초과할 때
		
			if (menuHeight == '' || menuHeight == '0px') {	// 높이 0일 때 (접혀있을 때)
				console.log('높이 현재 0');
				detailedMenu.style.height = detailedMenu.scrollHeight + 'px';
			} else {	// 메뉴바 펼쳐져 있을 때				
				detailedMenu.style.height = 0;				
														            
			}
			
			toggleOpenClass();
			
		} else {	// 폭이 1024px 이하일 때
							  
			toggleOpenClass();
			
		}

	}, 500));
	
	
	window.addEventListener('resize', function() {		
		
		const menuHeight = detailedMenu.style.height;
		const menuWidth = detailedMenu.style.width;
		
		const doesContain = detailedMenu.classList.contains('navbar-detailed-menu-open');
		
		if(doesContain) {	// open클래스 있을 때
			if (window.innerWidth <= 1024) {

				detailedMenu.style.width = '280px';
				detailedMenu.style.height = '100vh';
												
				delayedTransition('transform 0.5s ease-in-out');
												
				body.classList.add('body-element-navbar');
				body.classList.add('body-element-navbar');
				
			} else {
				
		        detailedMenu.style.width = '85%';
	            detailedMenu.style.height = 'auto';
	            
	            delayedTransition('height 0.5s ease-in-out');
	            
	            body.classList.add('body-element-navbar');
	            
			}	
		} else {	// open클래스 없을 때
			if (window.innerWidth <= 1024) {
				
				detailedMenu.style.height = '100vh';
				detailedMenu.style.width = '280px';
				
				delayedTransition('transform 0.5s ease-in-out');
				
				
				body.classList.remove('body-element-navbar');
			} else {
				
		        detailedMenu.style.width = '85%';
	            detailedMenu.style.height = 0;
	            delayedTransition('height 0.5s ease-in-out');
	            
	            body.classList.remove('body-element-navbar');
			}
		}
		
		
	});
	
	
	btnClose.addEventListener('click', function() {
        toggleOpenClass();
    });
    
    
    
	navbarBackground.addEventListener('click', function() {
		
		if(window.innerWidth <= 1024) {
			toggleOpenClass();
		}
		
	});
	
	
	// 아코디언을 위한 이벤트리스너 (1024px밑엥서만 적용)
	navbarDetailedTitleDiv.forEach((titleDiv) => {			
		
		titleDiv.addEventListener('click', function() {
			const wholeContainer = titleDiv.closest('.navbar-detailed-menu-section-container');
			const itemsDiv = wholeContainer.querySelector('.navbar-detailed-menu-items');
			const divList = wholeContainer.querySelector('.navbar-detailed-menu-list');
			
			
			console.log(itemsDiv);
			console.dir(itemsDiv);
			
			console.log(itemsDiv.scrollHeight);
			
			if (window.innerWidth <= 1024) {
				if (itemsDiv.classList.contains('navbar-detailed-menu-items-open')) {
					
					itemsDiv.style.height = 0;
					itemsDiv.classList.remove('navbar-detailed-menu-items-open');
					divList.classList.remove('.navbar-detailed-menu-list-open');
					
				} else {
					itemsDiv.style.height = itemsDiv.scrollHeight + 'px';
					itemsDiv.classList.add('navbar-detailed-menu-items-open');
					divList.classList.add('.navbar-detailed-menu-list-open');
				}
				
			}
			
		});		
		
	});	

	
	
});