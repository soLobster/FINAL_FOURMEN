/**
 * review.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	const signedInUser = document.querySelector('.div-profile-image');		// 닉네임을 포함하고 있는 유저 로그인했을 때 프로필사진 컨테이너
	console.log(signedInUser);
    const btnSendReview = document.querySelector('.btn-send-review');	// 리뷰 보내기 버튼
    const btnAddLike = document.querySelector('.btn-add-like');	// 좋아요 버튼
    const divDidLikeAlready = document.querySelector('.div-like');
    const divDidReviewedAlready = document.querySelector('.div-review');
    const btnMovieReview =  document.querySelector('.btn-movie-review');
    
    const pathName = location.pathname;	// 컨텍스트 루트 제외한 주소 가져옴
    const category = pathName.split('/')[1];
    const tmdbId = pathName.split('/')[3];
    console.log(`divDidReviewAlready = ${divDidReviewedAlready}`);
    
    // 리뷰버튼 로그인 안했으면 못 누르게하는 이벤트 리스너
    btnMovieReview.addEventListener('click', function() {
		
		if (!signedInUser) {
			alert('로그인한 유저만 리뷰작성이 가능합니다.');
			return;
		}
		
		if (divDidReviewedAlready.classList.contains('user-reviewed-already')) {
			alert('리뷰는 한번만 남길 수 있습니다.');
			return;
		}
		
	});
    
    // 리뷰 보내기 버튼 눌렀을 때 event listener
    if (btnSendReview) {
		btnSendReview.addEventListener('click', function() {
			const btnClose = document.querySelector('.btn-header-close');
			
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
				member: {
					email: signedInUser.getAttribute('email')
				}			
			}
			
			
			axios.post('/feature/review/post', data)
				.then((response) => {
									
					alert('리뷰작성 성공');
					divDidReviewedAlready.classList.toggle('user-reviewed-already');
					btnClose.click();
					location.reload();
					
				})
				.catch((error) => {
					console.log(`에러 발생!! ${error}`);
				})
			
		});		
	}

	
	
	// TODO: 아이디 세션만료에 따른 오차가 있을거같은데..
	// 좋아요 추가 버튼 누를때 이벤트리스너
	btnAddLike.addEventListener('click', function() {
		
		if (!signedInUser) {
			alert('로그인한 유저만 좋아요 추가할 수 있습니다.');
			return;
		}
		
		const didLikeAlready = divDidLikeAlready.classList.contains('user-liked-already');
		
		data = {
			category: category,
			tmdbId: tmdbId,
			member: {
				email: signedInUser.getAttribute('email')
			}
		}
		
		if (!didLikeAlready) {	// 좋아요를 누르지 않았을 경우
			
			axios.post('/feature/like/add', data)
			.then((response) => {
				console.log("좋아요 눌리지 않았을 경우 좋아요누름")
				divDidLikeAlready.classList.toggle('user-liked-already');
				
			})
			.catch((error) => {
				console.log(`에러 발생!! ${error}`)
			})
			
			return;
			
		} else {	// 만약 이미 좋아요를 누른 상태라면.
			
			
			axios.post('/feature/like/delete', data)
				.then((response) => {
					console.log("좋아요 이미 눌름.. 좋아요 취소");
					divDidLikeAlready.classList.toggle('user-liked-already');
					
				})
				.catch((error) => {
					console.log(`에러 발생!! ${error}`);
				})
			
			return;
		}
			
		
	});
	
	
	
});