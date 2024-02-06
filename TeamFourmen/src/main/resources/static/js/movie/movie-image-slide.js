/**
 * movie-image-slide.js
 */

 
 document.addEventListener('DOMContentLoaded', function() {
	
	const wholeCardsContainer = document.querySelectorAll('.movie-whole-cards-container');	
	const slideButton = document.querySelectorAll('.btn-slide-image');
	
	
	// true, false를 아규먼트로 받아서 버튼 비활성화 시키는 함수. true일시 비활성화, false일시 활성화
	const btnDeactivate = function(isTrue) {
		
		slideButton.forEach((button) => {
			button.disabled = isTrue;
		});
			
	}
	
	
	// 버튼누를 때 이벤트 리스너
	slideButton.forEach((btn) => {		
		
		btn.addEventListener('click', function(e) {
			const direction = btn.classList.contains('btn-prev') ? -1 : 1;
			
			
			const movieCardContainer = e.target.closest('.movie-card-container');	// 부모 외부컨테이너			
			const movieWholeCardContainer = movieCardContainer.querySelector('.movie-whole-cards-container');	// 카드담고있는 컨테이너
			
			const scrollAmount = movieCardContainer.clientWidth / 2 * direction;	// 보이는거 반만큼만 가도록 함
			movieWholeCardContainer.scrollBy({left: scrollAmount, behavior: "smooth"});
			
			setTimeout(() => {
				btnDeactivate(false);
			}, 500);
			
			btnDeactivate(true);
		})
		
	});
	
	
	wholeCardsContainer.forEach((wholeContainer) => {
		
		const movieCardContainer = wholeContainer.closest('.movie-card-container');
		const btnLeft = movieCardContainer.querySelector('.btn-prev');
		const btnNext = movieCardContainer.querySelector('.btn-next');
		
		const maxScrollWidth = wholeContainer.scrollWidth - wholeContainer.clientWidth;
		
		btnNext.style.display = maxScrollWidth == 0 ? 'none' : 'block';
		
		wholeContainer.addEventListener('scroll', function(e) {
			
			const movieCardContainer = wholeContainer.closest('.movie-card-container');
			const maxScrollWidth = wholeContainer.scrollWidth - wholeContainer.clientWidth;

			
			btnLeft.style.display = wholeContainer.scrollLeft <= 0 ? 'none' : 'block';
			btnNext.style.display = wholeContainer.scrollLeft >= maxScrollWidth ? 'none' : 'block';
						
		});
		
	})
	
	 
 });