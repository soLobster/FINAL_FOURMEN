/**
 * person-thumbsUp.js
 * person-details.html 에 포함.
 */
document.addEventListener('DOMContentLoaded', function() {

    const signedInUser = document.querySelector('.div-profile-image'); // 로그인한 유저의 프로필 사진 컨테이너
    console.log(signedInUser);

    const thumbsUpBtn = document.querySelector('.thumbs_up_btn'); // 좋아요 버튼
    const thumbsUpSpan = document.querySelector('.thumbs_up_span'); // 좋아요 텍스트
    const thumbsUpImg = document.querySelector('.thumbs_up_img'); // 좋아요 이미지
    const divDidLikeAlready = document.querySelector('.div-like'); // 좋아요 상태 div

    const pathName = location.pathname; // 컨텍스트 루트 제외한 주소 가져옴
    const category = pathName.split('/')[1];
    const tmdbId = pathName.split('/')[3];

    // 로그인 안 했으면 좋아요 버튼을 못 누르게 하는 이벤트 리스너
    thumbsUpBtn.addEventListener('click', function() {
        if (!signedInUser) {
            alert('로그인한 유저만 좋아요를 추가할 수 있습니다.');
            // 로그인 안 했으면 로그인 페이지로 리다이렉트하는 로직을 추가할 수 있음
            return;
        }

        const isLiked = divDidLikeAlready.classList.contains('user-liked-already');

        data = {
            category: category,
            tmdbId: tmdbId,
            member: {
                email: signedInUser.getAttribute('email')
            }
        }

        if (!isLiked) { // 좋아요를 누르지 않았을 경우

            axios.post('/feature/like/add', data)
                .then((response) => {
                    console.log("좋아요 눌리지 않았을 경우 좋아요누름")
                    divDidLikeAlready.classList.toggle('user-liked-already');

                })
                .catch((error) => {
                    console.log(`에러 발생!! ${error}`)
                })

            return;

        } else { // 만약 이미 좋아요를 누른 상태라면.

            axios.post('/feature/like/delete', data)
                .then((response) => {
                    console.log("좋아요 이미 누름.. 좋아요 취소");
                    divDidLikeAlready.classList.toggle('user-liked-already');

                })
                .catch((error) => {
                    console.log(`에러 발생!! ${error}`);
                })

            return;

        }

    });

    // 좋아요 눌렀을 때
    thumbsUpBtn.forEach((btn) => {

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