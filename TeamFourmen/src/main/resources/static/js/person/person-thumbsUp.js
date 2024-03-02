/**
 * person-thumbsUp.js
 * person-details.html 에 포함.
 */
document.addEventListener('DOMContentLoaded', function() {

    // 로그인을 했으면, nav 바에 로그인한 유저의 프로필 사진이 있을 것이므로 이것으로 로그인, 비로그인 상태를 구분함.
    const signedInUser = document.querySelector('.div-profile-image'); // 로그인한 유저의 프로필 사진 컨테이너
    console.log(signedInUser);

    const thumbsUpBtn = document.querySelector('.thumbs_up_btn'); // 좋아요 버튼
    const thumbsUpSpan = document.querySelector('.thumbs_up_span'); // 좋아요 텍스트
    const thumbsUpImg = document.querySelector('.thumbs_up_img'); // 좋아요 이미지
    const divDidLikeAlready = document.querySelector('.div-like'); // 좋아요 상태 div

    const pathName = location.pathname; // 컨텍스트 루트 제외한 주소 가져옴
    const category = pathName.split('/')[1]; // URL에서 category 추출
    const tmdbId = pathName.split('/')[3]; // URL에서 tmdbId 추출
    console.log(`divDidLikeAlready = ${divDidLikeAlready}`);

    // 좋아요 수를 조회하고 업데이트하는 함수.
    function fetchLikesCount() {
        axios.get(`/feature/like/count?tmdbId=${tmdbId}`)
            .then((response) => {
                // 서버로부터 받은 좋아요 수 표시
                const likesCount = response.data;
                thumbsUpSpan.textContent = `${likesCount}명이 이 인물을 좋아합니다.`;
            })
            .catch((error) => console.error(`좋아요 수 조회 에러: ${error}`));
    }

    // localStorage에서 좋아요 상태를 읽어와서 적용
    const likedStatus = localStorage.getItem(`liked-${tmdbId}`);
    if (likedStatus === 'true') {
        divDidLikeAlready.classList.add('user-liked-already');
        thumbsUpBtn.classList.add('liked'); // 좋아요 상태 클래스 추가
        // 좋아요를 누른 상태의 이미지로 변경
        thumbsUpImg.src = '/icons/free-icon-thumbs-up-cancel-10692400.png';
    } else {
        divDidLikeAlready.classList.remove('user-liked-already');
        thumbsUpBtn.classList.remove('liked'); // 좋아요 상태 클래스 제거
        // 좋아요를 누르지 않은 상태의 이미지로 변경
        thumbsUpImg.src = '/icons/free-icon-thumbs-up-firstLike-10691706.png';
    }

    // 페이지 로드 시 좋아요 수 조회.
    fetchLikesCount();

    // 좋아요 버튼 이벤트 리스너.
    thumbsUpBtn.addEventListener('click', function() {

        // 유저가 로그인을 하지 않았을 경우.
        if (!signedInUser) {
            alert('로그인한 유저만 좋아요를 추가할 수 있습니다.');
            // ** 로그인 안 했으면 로그인 페이지로 리다이렉트하는 로직을 추가할 수 있음 **
            return;
        }

        // 웹 페이지에서 특정 요소의 클래스 목록(여기서는 divDidLikeAlready 로 가정된 DOM 요소)에서
        // 'user-liked-already' 라는 클래스가 존재하는지 여부를 확인.
        // -> 사용자가 이미 '좋아요'를 눌렀는지를 판단하는 데 사용됨.
        const isLiked = divDidLikeAlready.classList.contains('user-liked-already');

        let data = {
            category: category,
            tmdbId: tmdbId,
            member: {
                email: signedInUser.getAttribute('email')
            }
        }

        if (!isLiked) { // 좋아요를 누르지 않았을 경우

            axios.post('/feature/like/add', data)
                .then((response) => {
                    console.log("좋아요 누르지 않은 경우: 좋아요 누름")
                    // divDidLikeAlready 요소의 클래스 목록에 'user-liked-already' 클래스를 토글.
                    // 이 토글 작업으로 UI 상에서 사용자가 해당 항목에 좋아요를 눌렀다는 것을 시각적으로 표시.
                    divDidLikeAlready.classList.toggle('user-liked-already');
                    thumbsUpBtn.classList.add('liked'); // 좋아요 상태 클래스 추가(css 적용)
                    // 좋아요 이미지를 '좋아요 상태' 이미지로 변경.
                    thumbsUpImg.src = '/icons/free-icon-thumbs-up-cancel-10692400.png'
                    localStorage.setItem(`liked-${tmdbId}`, 'true'); // localStorage에 좋아요 상태 저장
                    fetchLikesCount(); // 좋아요 수 업데이트
                })
                .catch((error) => {
                    console.log(`에러 발생! ${error}`)
                })

            return;

        } else { // 만약 이미 좋아요를 누른 상태라면.

            axios.post('/feature/like/delete', data)
                .then((response) => {
                    console.log("좋아요 이미 누름: 좋아요 취소");
                    // alert('좋아요를 취소합니다.');
                    // divDidLikeAlready 요소의 클래스 목록에 'user-liked-already' 클래스를 토글.
                    // 이 토글 작업으로 UI 상에서 사용자가 해당 항목에 좋아요를 눌렀다는 것을 시각적으로 표시.
                    divDidLikeAlready.classList.toggle('user-liked-already');
                    thumbsUpBtn.classList.remove('liked'); // 좋아요 상태 클래스 제거(css 제거)
                    // 좋아요 이미지를 '좋아요 되돌리기(취소) 상태' 이미지로 변경.
                    thumbsUpImg.src = '/icons/free-icon-thumbs-up-firstLike-10691706.png';
                    localStorage.removeItem(`liked-${tmdbId}`); // localStorage에서 좋아요 상태 제거
                    fetchLikesCount(); // 좋아요 수 업데이트
                })
                .catch((error) => {
                    console.log(`에러 발생! ${error}`);
                })

            return;

        }

    });

});