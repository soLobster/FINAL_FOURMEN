/**
 * review.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
    const category = location.pathname.split('/')[1];
    const btnSendReview = document.querySelector('.btn-send-review');	// 리뷰 보내기 버튼
    const btnAddLike = document.querySelector('.btn-add-like');
    
    // 리뷰 보내기 버튼 눌렀을 때 event listener
	btnSendReview.addEventListener('click', function() {
		
		const textAreaReview = document.querySelector('.text-area-review-comment');
		const rating = document.querySelector('input[name="rating"]:checked');
		const isSpoiler =  document.querySelector('#checkboxSpoiler:checked');	
		
		if (!textAreaReview.value || !rating) {
			alert('한줄평과 평가를 남겨주세요.');
			return;
		}
		
		data = {
			content: textAreaReview.value,
			rating: rating.value,
			category: category,
			tmdbId: movieId ? movieId : tvShowId,	//  tv show 아이디 가져오기..
			isSpoiler: isSpoiler ? isSpoiler.value : null,
			email: 'cirche1@naver.com'	// 나중에 유저 가져오기			
		}
		
		axios.post('/feature/review/post', data)
			.then((response) => {
				
				const btnClose = document.querySelector('.btn-header-close');
				btnClose.click();
				
			})
			.catch((error) => {
				console.log(`에러 발생!! ${error}`);
			})
		
	});
	
	
	// 좋아요 추가 버튼 누를때 이벤트리스너
	btnAddLike.addEventListener('click', function() {
		
	});
	
	
	
});