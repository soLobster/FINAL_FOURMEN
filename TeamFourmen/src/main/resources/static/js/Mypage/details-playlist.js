/**
 * details-playlist.js
 */

document.addEventListener('DOMContentLoaded', function() {
	const myPageUserMemberId= location.pathname.split('/')[3];	
	const signedInUser = document.querySelector('.div-profile-image');
		
	const btnPlaylistLike = document.querySelectorAll('.btn-playlist-like');
	const btnPlaylistDelete = document.querySelectorAll('.btn-playlist-delete');
		
	
	
	
	if (!signedInUser || signedInUser.getAttribute('memberId') != myPageUserMemberId) {	// 로그인하지 않았거나 로그인한 유저의 mypage가 아닌경우 삭게버튼 숨김
		btnPlaylistDelete.forEach((btnDelete) => btnDelete.classList.add('d-none'));
	} else {	// 로그인한 유저의 마이페이지일 경우 좋아요버튼 숨김
		btnPlaylistLike.forEach((btnLike) => btnLike.classList.add('d-none'));
	}
	
	
	// 플레이리스트 좋아요 axios함수
	const addLikePlaylist = async function(playlistId) {
		
		const data = {
			member: {
				email: signedInUser.getAttribute('email')
			},
			
			playlist: {
				playlistId: playlistId
			}
		}
		
		axios.post('/api/mypage/playlist/add/like', data)
			.then((response) => {
				alert("좋아요 추가 성공");
				location.reload();
			})
			.catch((error) => {
				console.log(`에러 발생!!! ${error}`);
			})
	}
	
	// 플레이리스트 좋아요 취소 axios 함수
	const deleteLikePlaylist = async function(playlistId) {
		
		const data = {
			member: {
				email: signedInUser.getAttribute('email')
			},
			
			playlist: {
				playlistId: playlistId
			}
		}
		
		axios.post('/api/mypage/playlist/delete/like', data)
			.then((response) => {
				alert("좋아요를 취소 하였습니다.");
				location.reload();
			})
			.catch((error) => {
				console.log(`에러 발생!!! ${error}`);
			})
	}
	
	// 플레이리스트 삭제 axios 함수
	const deletePlaylist = async function(playlistId) {

		
		axios.delete(`/api/mypage/playlist/delete/${playlistId}`)
			.then((response) => {
				alert('플레이리스트를 삭제 하였습니다.');
				location.reload();
			})
			.catch((error) => {
				console.log(`에러 발생!!! ${error}`);
			})
	}
	
	// playlist 삭제 버튼 이벤트리스너
	if(btnPlaylistDelete) {
		
		btnPlaylistDelete.forEach((btnDelete) => {
			
			btnDelete.addEventListener('click', async function() {
				
				const playlistId = btnDelete.getAttribute('playlistId');
				const playlistNameContainer = btnDelete.closest('.div-playlist-name-container');
				const playlistName = playlistNameContainer.querySelector('.div-playlist-name').innerText;
				
				if (!signedInUser || signedInUser.getAttribute('memberId') != myPageUserMemberId) {
					alert('본인의 플레이리스트만 삭제할 수 있습니다.');
					return;
				}
				
				const willProceed = confirm(`정말 플레이리스트 ${playlistName}을/를 삭제하겠습니까?`);
				
				if (!willProceed) {
					return;
				}
				
				await deletePlaylist(playlistId);
				
			});
			
		})	
	}
	
	// 플레이리스트 좋아요 버튼 이벤트리스너
	if(btnPlaylistLike) {
		
		btnPlaylistLike.forEach((btnLike) => {
			
			btnLike.addEventListener('click', async function() {
				
				const playlistId = btnLike.getAttribute('playlistId');
				
				if (!signedInUser) {
					alert('로그인 한 유저만 좋아요를 누를 수 있습니다.');
					return;
				}
				
				if (signedInUser && signedInUser.getAttribute('memberId') == myPageUserMemberId) {
					alert('본인의 플레이리스트에는 좋아요를 할 수 없습니다.');
					return;
				}
				
				const ifAlreadyLiked = btnLike.classList.contains('playlist-already-liked');
				
				if (ifAlreadyLiked) {
					await deleteLikePlaylist(playlistId);
				} else {					
					await addLikePlaylist(playlistId);					
				}

				
			});
			
		})
		
	}
	
	
});