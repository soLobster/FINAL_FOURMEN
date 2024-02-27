
document.addEventListener('DOMContentLoaded', function (){

    const loggedInUser = document.querySelector('.div-profile-image').getAttribute('email');

    const btnLikeReview = document.querySelector('.btn-like-review');

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

    }

});