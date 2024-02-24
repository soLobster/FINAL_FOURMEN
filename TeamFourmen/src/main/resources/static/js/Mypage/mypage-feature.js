/*

개인 대시보드에서 리뷰의 숫자를 표기해야함.

/api/mypage

/get-num-of-reviews

 */

document.addEventListener('DOMContentLoaded', function () {

    const pathName = location.pathname;
    const userEamil = pathName.split('/')[3];

    console.log(userEamil);

    // 유저의 정보를 가져옴
    axios.get('/api/mypage/user-info', {
        params: {
            email: userEamil
        }
    })
        .then((response) => {
            const {
                email,
                name,
                nickname,
                usersaveprofile
            } = response.data;
            console.log('불러온 유저 정보');
            console.log('EAMIL = ' + email + ',이름 = ' + name + ',닉네임 = ' + nickname + ',PROFILE IMG = ' + usersaveprofile);

            // 유저의 닉네임을 표기
            const userNickname = nickname;
            const nicknameElement = document.querySelector('#user-nick-name');
            nicknameElement.textContent = userNickname;

            // 유저 대시보드 공통 네비게이션에 링크를 걸어준다.
            const profileLink = document.querySelector('.nav-item:nth-child(1) .nav-link');
            profileLink.href = `/mypage/details/${userEamil}`;

            const reviewsLink = document.querySelector('.nav-item:nth-child(2) .nav-link');
            reviewsLink.href = `/mypage/details/${userEamil}/reviews`;


        })

    // 리뷰 카운트를 구해서 유저 정보 우측에 추가함
    axios.get('/api/mypage/get-num-of-reviews', {
        params: {
            email: userEamil
        }
    })
        .then((response) => {
            const reviewCount = response.data;
            console.log('불러온 리뷰의 수 = ' + reviewCount);
            const reviewLinkElement = document.querySelector('.review-num a');
            reviewLinkElement.href = `/mypage/details/${userEamil}/reviews`

            const reviewNumElement = document.querySelector('.review-num .value');
            reviewNumElement.textContent = reviewCount;
        })
        .catch((error) => {
            console.log('리뷰의 수를 불러오는데 실패 하였습니다', error);
        });

    // 유저 팔로워 팔로우 추가 해야함

});