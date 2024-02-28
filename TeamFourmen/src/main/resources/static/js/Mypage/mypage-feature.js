/*

개인 대시보드에서 리뷰의 숫자를 표기해야함.

/api/mypage

/get-num-of-reviews

 */

document.addEventListener('DOMContentLoaded', function () {

    const pathName = location.pathname;
    const userEmail = pathName.split('/')[3];




    const loggedInUser = document.querySelector('.div-profile-image').getAttribute('email');

    console.log('로그인 유저 = '+loggedInUser);

    const browsersTitle = document.querySelector('title');
    const editProfile = document.querySelector('.edit-profile');
    const followButton = document.querySelector('.follow-button');

    const likedListTitle = document.querySelector('.category-like-list');



    console.log('마이페이지 유저 = ' + userEmail);

    // 유저의 정보를 가져옴
    axios.get('/api/mypage/user-info', {
        params: {
            email: userEmail
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

            browsersTitle.textContent = userNickname + ' ' + 'DashBoard';

            // 유저 대시보드 공통 네비게이션에 링크를 걸어준다.
            const profileLink = document.querySelector('.nav-item:nth-child(1) .nav-link');
            profileLink.href = `/mypage/details/${userEmail}/profile`;

            const reviewsLink = document.querySelector('.nav-item:nth-child(2) .nav-link');
            reviewsLink.href = `/mypage/details/${userEmail}/reviews`;

            const likedMovieLink = document.querySelector('.nav-item:nth-child(4) .nav-link');
            likedMovieLink.href = `/mypage/details/${userEmail}/movie`

            const likedTvShowLink = document.querySelector('.nav-item:nth-child(5) .nav-link');
            likedTvShowLink.href = `/mypage/details/${userEmail}/tv`

            // const likedPersonLink = document.querySelector('.nav-item:nth-child(6) .nav-link');
            // likedPersonLink.href = `/mypage/details/${userEmail}/person`

        })

    // 리뷰 카운트를 구해서 유저 정보 우측에 추가함
    axios.get('/api/mypage/get-num-of-reviews', {
        params: {
            email: userEmail
        }
    })
        .then((response) => {
            const reviewCount = response.data;
            console.log('불러온 리뷰의 수 = ' + reviewCount);
            const reviewLinkElement = document.querySelector('.review-num a');
            reviewLinkElement.href = `/mypage/details/${userEmail}/reviews`

            const reviewNumElement = document.querySelector('.review-num .value');
            reviewNumElement.textContent = reviewCount;
        })
        .catch((error) => {
            console.log('리뷰의 수를 불러오는데 실패 하였습니다', error);
        });

    // 유저 팔로워 팔로우 추가 해야함

    // 로그인 유저와 마이페이지의 유저가 다르면 프로필 편집 불가
    if(userEmail !== loggedInUser) {
        editProfile.classList.add('d-none');
    }

    // 로그인 유저와 마이페이지 유저가 같다면 팔로우 불가
    if(userEmail === loggedInUser){
        followButton.classList.add('d-none');
    }

    const category = pathName.split('/')[4].toUpperCase();

    console.log(category);

    likedListTitle.textContent = category + ' Liked List';

});