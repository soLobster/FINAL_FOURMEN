/**
 * details-playlist-items.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	const myPageUserMemberId = location.pathname.split('/')[3];
	const signedInUser = document.querySelector('.div-profile-image');
	
	const btnSetPrivate = document.querySelector('.btn-set-playlist-private');
	const btnSetPublic = document.querySelector('.btn-set-playlist-public');
	
	const playlistId = location.pathname.split('/')[5];	
	
	const ulPlaylistItem  = document.querySelector('.ul-playlist-items');
	const liPlaylistItem = document.querySelectorAll('.li-playlist-item');
	
	const btnDeletePlaylistItemList = document.querySelectorAll('.btn-delete-playlist-item');
	
	const updateListOrder = async function(liPlaylist) {
		
		let index = 0;				
		
		let dataList = [];
		
		liPlaylist.forEach((li) => {
			const category = li.getAttribute('category');
			const tmdbId = li.getAttribute('tmdbId');
			const playlistItemId = li.getAttribute('playlistItemId');
			
			for (let i = 0; i < ulPlaylistItem.children.length; i++) {
				
				
				if (ulPlaylistItem.children[i] == li) {					
					index = i;
				
					break;
				}
				
			}
			

			// 순번 업데이트 데이터
			const data = {
				playlistItemId: playlistItemId,
				playlist: {
					playlistId: playlistId
				},
				category: category,
				tmdbId: tmdbId,
				nthInPlaylist: index + 1
			};
			
			dataList.push(data);
			
		});
		
		
		axios.post('/api/mypage/playlist/reorder', dataList)
			.then(() => {
				console.log('성공');
			})
			.catch((error) => {
				console.log(`에러 발생!!! ${error}`);
			});
		
	};
	
	
	liPlaylistItem.forEach((li) => {
		
		// 드래그 시작할 때 이벤트 리스너
		li.addEventListener('dragstart', () => {
			setTimeout(() => {
				li.classList.add('dragging');
			}, 10);
		});
		
		// 드래그 끝나면 dragging클래스 지움
		li.addEventListener('dragend', () => {
			li.classList.remove('dragging');
			
			if (signedInUser && signedInUser.getAttribute('memberId') == myPageUserMemberId) {
				updateListOrder(liPlaylistItem);
			}
		});
		
	});
	
	
	const initSortableList = (e) => {
		// 현재 드래그 중인 요소
		const draggingItem = ulPlaylistItem.querySelector('.dragging');
		// 현재 드래그 중인 요소만 빼고 선택
		const siblings = [...ulPlaylistItem.querySelectorAll('.li-playlist-item:not(.dragging)')];
				
		// 드래깅 되는 요소가 가야할 형제요소 찾음
		let nextSibling = siblings.find(sibling => {			
			return (e.clientY - ulPlaylistItem.getBoundingClientRect().top) <= (sibling.offsetTop - ulPlaylistItem.offsetTop + sibling.offsetHeight / 2);
		});
		
		// 형제요소 이전에 드래깅요소를 집어넣어줌
		ulPlaylistItem.insertBefore(draggingItem, nextSibling);
		
	}
	
	
	ulPlaylistItem.addEventListener("dragover", function(e) {

		if (signedInUser && signedInUser.getAttribute('memberId') == myPageUserMemberId) {
			initSortableList(e);			
		}
	});
	
	
	if(btnDeletePlaylistItemList) {
		
		btnDeletePlaylistItemList.forEach((btnDeletePlaylistItem) => {
			
			btnDeletePlaylistItem.addEventListener('click', function() {
				
				const liPlaylistItem = btnDeletePlaylistItem.closest('.li-playlist-item');
				const playlistItemIdToDelete = liPlaylistItem.getAttribute('playlistItemId');
				
				axios.delete(`/api/mypage/playlist/delete-item/${playlistItemIdToDelete}`)
					.then((response) => {
						location.reload();
					})
					.catch((error) => {
						console.log(`에러 발생!!! ${error}`);
					})
								
			});
			
		});
			
	}
	
	
	// 플레이리스트 private설정 버튼 이벤트리스너
	if (btnSetPrivate) {
		
		btnSetPrivate.addEventListener('click', function() {
			
			const data = {
				playlistId: playlistId,
				isPrivate: 'Y'
			};
			
			axios.post('/api/mypage/playlist/set/privacy', data)
				.then(() => {
					alert('플레이리스트를 전체 공개로 설정하였습니다.');
					location.reload();
				})
				.catch((error) => {
					console.log(`에러 발생!!! ${error}`);
				})
		})
		
	}
	
	// 플레이리스트 public설정 버튼 이벤트리스너
	if (btnSetPublic) {
		
		btnSetPublic.addEventListener('click', function() {

			const data = {
				playlistId: playlistId,
				isPrivate: 'N'
			};
			
			axios.post('/api/mypage/playlist/set/privacy', data)
				.then(() => {
					alert('플레이리스트를 나만보기로 설정하였습니다.');
					location.reload();
				})
				.catch((error) => {
					console.log(`에러 발생!!! ${error}`);
				})
				
			
		});
		
		
	}
	
	
	
	
});