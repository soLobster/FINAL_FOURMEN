
document.addEventListener('DOMContentLoaded', function (){

    const loggedInUser = document.querySelector('.div-profile-image').getAttribute('email');

    const btnLikeReview = document.querySelector('.btn-like-review');

    const btnDeleteReview = document.querySelector('.btn-delete-review');

    console.log(btnDeleteReview.getAttribute('email'))

    if(loggedInUser != btnDeleteReview.getAttribute('email')){
        btnDeleteReview.classList.add('d-none');
    }

    console.log('LOGIN USER = ' + loggedInUser);

    const reviewId = btnLikeReview.getAttribute('reviewId');

    const reviewerEmail = btnLikeReview.getAttribute('email');

    console.log('REVIER EMAIL = ' + reviewerEmail);

    if(btnLikeReview.classList.contains('isLikedReview')){
        const iTag = btnLikeReview.querySelector('.fa-thumbs-up');
        iTag.style.color = '#33ff33';
    }

    async function checkCurrentUser() {
        try {
            const response = await axios.get('/api/user/current-user');

            console.log(response.data);

            return response.data;

        } catch (error) {
            console.log('사용자 상태 확인 오류', error);
            return false;
        }
    }

    btnLikeReview.addEventListener('click',  addLikeReview );

    async function addLikeReview() {

        console.log(reviewId);

        const signedInUser =   checkCurrentUser();

        if(!signedInUser) {
            alert('로그인 한 유저만 좋아요를 누를 수 있습니다...!');
            return;
        }

        if(loggedInUser == reviewerEmail) {
            alert('본인 리뷰는 추천할 수 없습니다.');
            return;
        }

        console.log(signedInUser);

        const uri = '/feature/review/like/add'

       data = {
            email: loggedInUser,
            reviewId: reviewId
       }

        if(!btnLikeReview.classList.contains('isLikedReview')) {

            try{

                console.log(data);

                const response = await axios.post(uri, data);

                console.log(response.data);

                alert('추천 되었습니다.');

                location.reload();
            } catch (error) {
                console.log('에러 발생', error);
            }
        } else {

            try{
                const response = await axios.post(uri, data);

                console.log(response.data);

                alert('추천 취소 되었습니다.');

                location.reload();
            } catch (error) {
                console.log('에러 발생', error);
            }
        }

    } // 추천 누르기 함수 끝

    btnDeleteReview.addEventListener('click', deleteReview);

    async function deleteReview() {

        const result = confirm('정말 리뷰를 삭제 하시겠습니까??');

        if(!result) {
            return;
        }

        const email = btnDeleteReview.getAttribute('email');

        const reviewId = btnDeleteReview.getAttribute('reviewid');

        console.log('삭제될 리뷰의 작성자 이메일 = ', email);

        console.log('삭제될 리뷰의 리뷰 ID = ', reviewId);

        const uri = `/feature/review?reviewId=${reviewId}&email=${email}`;

        try {
            const response = await axios.delete(uri);

            console.log(response.data);

            alert('리뷰가 삭제 되었습니다.');

            // window.location.href = `/mypage/details/${email}/reviews`;

            history.go(-1);

        } catch (error) {
            console.log('에러 발생',error);
        }

    } // 리뷰 삭제 함수 끝...


});