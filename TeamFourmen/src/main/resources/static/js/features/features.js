/**
 * review.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
    const btnSendReview = document.querySelector('.btn-send-review');	// 리뷰 보내기 버튼
    const btnAddLike = document.querySelector('.btn-add-like');	// 좋아요 버튼
    let didLikeAlready = document.querySelector('.div-like').classList.contains('user-liked-already');
    
    
    const pathName = location.pathname;	// 컨텍스트 루트 제외한 주소 가져옴
    const category = pathName.split('/')[1];
    const tmdbId = pathName.split('/')[3];
    
    
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
			tmdbId: tmdbId,
			isSpoiler: isSpoiler ? isSpoiler.value : null,
			email: 'cirche1@naver.com'	// 나중에 유저 가져오기			
		}
		
		axios.post('/feature/review/post', data)
			.then((response) => {
				
				const btnClose = document.querySelector('.btn-header-close');
				
				alert('리뷰작성 성공');
				
				btnClose.click();
				
			})
			.catch((error) => {
				console.log(`에러 발생!! ${error}`);
			})
		
	});
	
	// TODO: 아이디 세션만료에 따른 오차가 있을거같은데..
	// 좋아요 추가 버튼 누를때 이벤트리스너
	btnAddLike.addEventListener('click', function() {
		
		data = {
			category: category,
			tmdbId: tmdbId,
			member: {
				email: 'cirche1@naver.com'
			}
		}
		
		if (!didLikeAlready) {	// 좋아요를 누르지 않았을 경우
			
			axios.post('/feature/like/add', data)
			.then((response) => {
				
				location.reload();
				
			})
			.catch((error) => {
				console.log(`에러 발생!! ${error}`)
			})
			
			return;
			
		} else {	// 만약 이미 좋아요를 누른 상태라면.
			
			
			axios.post('/feature/like/delete', data)
				.then((response) => {
					
					location.reload();
					
				})
				.catch((error) => {
					console.log(`에러 발생!! ${error}`);
				})
			
			return;
		}
			
		
	});
	
	
	
});