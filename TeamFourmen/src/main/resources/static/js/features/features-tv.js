/**
 * review.js
 */

document.addEventListener('DOMContentLoaded', function() {

    const signedInUser = document.querySelector('.div-profile-image');		// 닉네임을 포함하고 있는 유저 로그인했을 때 프로필사진 컨테이너
    console.log(signedInUser);
    const btnSendReview = document.querySelector('.btn-send-review');	// 리뷰 보내기 버튼
    const btnAddLike = document.querySelector('.btn-add-like');	// 좋아요 버튼
    const divDidLikeAlready = document.querySelector('.div-like');
    const btnPlaylistdiv = document.querySelector('.div-playlist');
    const divDidReviewedAlready = document.querySelector('.div-review');
    const btnTvReview =  document.querySelector('.btn-tv-review');
	
    const btnReviewAddLike = document.querySelectorAll('.btn-review-add-like');
	
	// 플레이리스트 관련
    const btnPlaylist = document.querySelector('.btn-playlist');	// 플레이리스트 모달켜는 버튼
    const btnCreateNewPlaylist = document.querySelector('.btn-create-new-playlist'); // 플레이리스트 모달 내 새 playlist만들기버튼
    const divPlaylistContainer = document.querySelector('.div-playlist-container');
    const btnAddPlaylist = document.querySelector('.btn-add-playlist');
	
    const pathName = location.pathname;	// 컨텍스트 루트 제외한 주소 가져옴
    const category = pathName.split('/')[1];
    const tmdbId = pathName.split('/')[3];
    console.log(`divDidReviewAlready = ${divDidReviewedAlready}`);
	
	
	// 플레이리스트 refresh함수(axios로 리스트 가져오지는 않고, 리스트를 html로 시현해주는 함수)
    const refreshPlaylist = async function() {
		
		let playlist = null;
		
		// 새 플레이리스트 생성하였으면 영상물 플레이리스트에 추가 모달부분 가기
		await axios.get('/feature/playlist', {params: {email: signedInUser.getAttribute('email')}})
			.then((response) => {
				playlist = response.data
				// console.log(response.data);
				btnToFirstModal.click();
			})
			.catch((error) => {
				
			});
		
		console.log(`playlist`);
		console.log(playlist);
		
		const divPlaylistContainer = document.querySelector('.div-playlist-container');
		
		let playlistContainerHtml = '';
		
		for (let playlistItem of playlist) {
			playlistContainerHtml += `	         
	                    <div class="div-each-playlist-container">
	                        <div class="div-playlist-image-container">
	                            <div>
            `;
            
            if (playlistItem.playlistItemDtoList != null && playlistItem.playlistItemDtoList.length > 0) {
				playlistContainerHtml += `
	                                <img class="img-each-playlist-poster" 
	                                    src=https://image.tmdb.org/t/p/w92/${playlistItem.playlistItemDtoList[0].workDetails.poster_path}">
                `;						
			} else {
				playlistContainerHtml += `
	                                <img class="img-each-playlist-poster"
	                                    src="/image/playlist_default.svg">				
				`;
			}
			
            playlistContainerHtml += `
	                            </div>
	                        </div>
	                        <div class="div-playlist-name-selection-container">
	                            <input class="checkbox-each-playlist d-none" type="radio" name="playlistId" value="${playlistItem.playlistId}" id="playlist${playlistItem.playlistId}">
	                            <label class="label-each-playlist" for="playlist${playlistItem.playlistId}">${playlistItem.playlistName}</label>
	                        </div>
	                    </div>
			`;
		}
		
		divPlaylistContainer.innerHTML = playlistContainerHtml;
	}
	
	// 아규먼트로 받은 id에 해당하는 playlist의 모든 items들을 가져와서 리턴
	const checkIfItemInPlaylist = async function(playlistId) {
		
		let itemsListInPlaylist;
		
		await axios.get('/feature/playlist/get-items', {params: {playlistId: playlistId}})
			.then((response) => {
				console.log(`성공 리스트 = ${response.data}`);
				itemsListInPlaylist = response.data;
			})
			.catch((error) => {
				console.log(`에러 발생!!! ${error}`);
			});
		
		return itemsListInPlaylist;
	}
	
	
	// 플레이리스트 추가 함수
	const addPlaylist = async function() {
		
		let isAlreadyInList = null;
		
		const btnClosePlaylistModal = document.querySelector('.btn-close-playlist-modal');
		const checkedPlaylist = document.querySelector('.div-each-playlist-container .checkbox-each-playlist:checked');
		
		if (!checkedPlaylist) {
			alert('해당 영화/Tv 시리즈를 추가할 플레이리스트를 선택해 주세요.');
			return;
		}
		
		// 유저가 선택한 플레이리스트에 포함된 영상물 리스트를 가져옴
		const itemsListInPlaylist = await checkIfItemInPlaylist(checkedPlaylist.value);
		console.log(`다른함수에서 받아온 리스트 = ${itemsListInPlaylist}`);
		
		// 해당 영화/시리즈가 플레이리스트에 이미 추가돼 있는지 확인
		if (itemsListInPlaylist) {			
			isAlreadyInList = itemsListInPlaylist.some(item => {
				return item.category == category && item.tmdbId == tmdbId;
			});
		}
		
		if(isAlreadyInList) {
			alert('이미 해당 리스트에 추가된 영화/시리즈 입니다.');
			return;
		}
		
		
		data = {
			playlist: {
				playlistId: checkedPlaylist.value
			},
			category: category,
			tmdbId: tmdbId
		}
		
		axios.post('/feature/playlist/add', data)
			.then((response) => {
				alert('플레이리스트에 추가하였습니다.');
				
				checkedPlaylist.checked = false;	// 모달에 플레이리스트 체크 해제	
				refreshPlaylist();			
				btnClosePlaylistModal.click();	// 모달 창 닫기
				
				return;
			})
			.catch((error) => {
				console.log(`에러 발생!! ${error}`)
			})
		
	}
	
	
	
    // 플레이리스트 모달버튼 누를 때
    btnPlaylist.addEventListener('click', function() {
		
		if (!signedInUser) {
			alert('로그인한 유저만 플레이리스트 추가할 수 있습니다.');
			return;
		}
		
	});

    btnPlaylistdiv.addEventListener('click', function () {

        if(!signedInUser){
            alert('로그인한 유저만 플레이리스트 추가할 수 있습니다.');
            return;
        }

    });
	
	
	// 새 플레이리스트 생성 버튼 이벤트리스너
	if (btnCreateNewPlaylist) {
		
		btnCreateNewPlaylist.addEventListener('click', function(){
			
			const inputPlaylistName = document.querySelector('.input-playlist-name');
			const inputIsPrivate = document.querySelector('.input-is-private');
			
			// 두번째 모달에서 첫번째 모달로 이동하는 버튼
			const btnToFirstModal = document.querySelector('.btn-to-first-modal');
			
			const data = {
				playlistName: inputPlaylistName.value,
				isPrivate: `${inputIsPrivate.checked ? 'Y' : 'N'}`,
				member: {
					email: signedInUser.getAttribute('email')
				}
			}
			
			axios.post('/feature/playlist/create', data)
				.then((response) => {
					
					alert('새 플레이리스트를 생성하였습니다.');
					refreshPlaylist();
					btnToFirstModal.click();
				})
				.catch((error) => {
					console.log(`에러 발생!!! ${error}`);	
				})
			
		});
		
	}
    
    
    // 플레이리스트에 추가 버튼 누를 때
    if (btnAddPlaylist) {
		btnAddPlaylist.addEventListener('click', addPlaylist);
	}
	
	
    // 리뷰버튼 로그인 안했으면 못 누르게하는 이벤트 리스너
    btnTvReview.addEventListener('click', function() {

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

    // 리뷰 좋아요 눌렀을 때
    btnReviewAddLike.forEach((btn) => {

        btn.addEventListener('click', function() {

            const authorEmail = btn.getAttribute('author');
            const reviewId = btn.getAttribute('reviewId')

            if (authorEmail === signedInUser.getAttribute('email')) {
                alert('내 리뷰에는 좋아요를 누를 수 없습니다.');
                return;
            }

            data = {
                reviewId:reviewId,
                member: {
                    email: signedInUser.getAttribute('email')
                }
            };

            axios.post('/feature/review/like/add', data)
                .then((response) => {



                })
                .catch((error) => {
                    console.log(`에러 발생!!! ${error}`)
                })

        });

    });



});