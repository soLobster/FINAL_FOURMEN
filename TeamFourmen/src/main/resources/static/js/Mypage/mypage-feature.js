/*

개인 대시보드에서 리뷰의 숫자 / 팔로워 / 팔로잉 수 를 표기해야함.

/api/mypage

/get-num-of-reviews

 */

document.addEventListener('DOMContentLoaded', async function () {

    const pathName = location.pathname;
    //const userEmail = pathName.split('/')[3];
    const memberId = pathName.split('/')[3];

	let userEmail = '';

    let loggedInUser = '';
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

    if(await checkCurrentUser() !== 'anonymousUser') {
        loggedInUser = document.querySelector('.div-profile-image').getAttribute('email');
    } else {
        loggedInUser = 'NON_MEMBER';
    }

    console.log('로그인 유저 = '+loggedInUser);

    const browsersTitle = document.querySelector('title');
    const editProfile = document.querySelector('.edit-profile');
    const followButton = document.querySelector('.follow-button');

    let userProfileImg = document.querySelector('.mypage-details-profile-img img');

    const likedListTitle = document.querySelector('.category-like-list');

    console.log('마이페이지 유저 = ' + memberId);

    // 유저의 정보를 가져옴

   await axios.get(`/api/mypage/user-info?memberId=${memberId}`)
        .then((response) => {
            const {
                memberId,
                email,
                name,
                nickname,
                usersaveprofile,
                type
            } = response.data;
            console.log('불러온 유저 정보');
            console.log('MEMBER ID = ' + memberId +'EMAIL = ' + email + ',이름 = ' + name + ',닉네임 = ' + nickname + ',PROFILE IMG = ' + usersaveprofile , 'TYPE = ' + type);

            // 유저의 닉네임을 표기
            const userNickname = nickname;
            const nicknameElement = document.querySelector('#user-nick-name');
            nicknameElement.textContent = userNickname;

            console.log('불러온 이미지 = ' + usersaveprofile);

            if(type !== 'web'){
                 userProfileImg.setAttribute('src', usersaveprofile);
            } else {
                userProfileImg.setAttribute('src',  '/image/userimage.png')
            }

//            browsersTitle.textContent = userNickname + ' ' + 'DashBoard';
            
            userEmail = email;
            
            console.log('쑤셔넣은 이메일임 = ' +userEmail);

            // 유저 대시보드 공통 네비게이션에 링크를 걸어준다.
            const profileLink = document.querySelector('.nav-item:nth-child(1) .nav-link');
            profileLink.href = `/mypage/details/${memberId}/profile`;

            const reviewsLink = document.querySelector('.nav-item:nth-child(2) .nav-link');
            reviewsLink.href = `/mypage/details/${memberId}/reviews`;

            const likedMovieLink = document.querySelector('.nav-item:nth-child(4) .nav-link');
            likedMovieLink.href = `/mypage/details/${memberId}/movie`

            const likedTvShowLink = document.querySelector('.nav-item:nth-child(5) .nav-link');
            likedTvShowLink.href = `/mypage/details/${memberId}/tv`

            const likedPersonLink = document.querySelector('.nav-item:nth-child(6) .nav-link');
            likedPersonLink.href = `/mypage/details/${memberId}/person`
            
            const myeditLink =document.querySelector('a#myedit');
            myeditLink.href = `/mypage/details/${memberId}/edit`;

        });

    // 팔로우 체크
    await axios.get(`/api/follow/${userEmail}`)
        .then((reponse) => {
            console.log('현재 페이지 유저 팔로우 체크 TRUE OR FALSE = ' + reponse.data);
            const isAlreadyFollow = reponse.data;

            if(isAlreadyFollow === true){
                followButton.querySelector('button').classList.replace('follow', 'following');
                followButton.querySelector('button').classList.replace('btn-primary', 'btn-danger');
                followButton.querySelector('button').innerHTML = '팔로우 취소';
            }

        }).catch((error) =>{
            console.log('에러 발생 = ' + error)
        });

    // 리뷰 카운트를 구해서 유저 정보 우측에 추가함
    await axios.get(`/api/mypage/get-num-of-reviews?memberId=${memberId}`)
        .then((response) => {
            const reviewCount = response.data;
            console.log('불러온 리뷰의 수 = ' + reviewCount);
            const reviewLinkElement = document.querySelector('.review-num a');
            reviewLinkElement.href = `/mypage/details/${memberId}/reviews`

            const reviewNumElement = document.querySelector('.review-num .value');
            reviewNumElement.textContent = reviewCount;
        })
        .catch((error) => {
            console.log('리뷰의 수를 불러오는데 실패 하였습니다', error);
        });

    // 팔로워 / 팔로잉 수 가져오기
    await axios.get(`/api/follow/${userEmail}/follower`)
        .then((response) => {
            const targetUserSocialCount = response.data;

            const followersCount = targetUserSocialCount.followers;
            const followingsCount = targetUserSocialCount.followings;

            console.log(`불러온 팔로워 수 =  ${followersCount}`);
            console.log(`불러온 팔로잉 수 =  ${followingsCount}`);

            const followersElement = document.querySelector('.followers .value');
            followersElement.textContent = followersCount;

            const followingsElement = document.querySelector('.followings .value');
            followingsElement.textContent = followingsCount;
        })
        .catch((error) => {
           console.log('팔로워 수를 불러오는데 실패 하였습니다.', error);
        });

    // 유저 팔로워 팔로우 추가 해야함
    if(await checkCurrentUser() !== 'anonymousUser') {
        followButton.addEventListener('click', followUser);
    }
    async function followUser() {
        console.log('팔로우 할 유저의 이메일 = ' + userEmail);

        const friendEmail = userEmail;

        const userNickName = document.querySelector('.overview-nickname-and-button #user-nick-name').innerText;

        console.log('유저의 닉네임 = ' + userNickName);

        const uri = `/api/follow/${friendEmail}`;

        const followStatus = followButton.querySelector('button').classList;

        if(followStatus.contains('follow')){

            try {
                const response = await axios.post(uri);

                console.log(response.data);

                alert(userNickName + ' 님을 팔로우 했습니다.');

                location.reload();

                return;

            } catch (error) {
                console.log('에러 발생 = ' + error);
            }

        } else {

            try {

                const response = await axios.delete(uri);

                console.log(response.data);

                alert(userNickName + ' 님을 언팔로우 했습니다.');

                location.reload();

                return;

            } catch (error) {
                console.log('에러 발생 = ' + error);
            }
        }
    }  // 팔로우 추가/삭제

    const category = pathName.split('/')[4].toUpperCase();

    console.log(category);


 if(location.pathname.split('/')[4] != 'profile' && location.pathname.split('/')[4] != 'reviews' && location.pathname.split('/')[4] != 'management' 
 && location.pathname.split('/')[4] != 'admindetail' && location.pathname.split('/')[4] != 'search' && location.pathname.split('/')[4] != 'edit'){
        likedListTitle.textContent = category + ' Liked List';
    }

    // 로그인 유저와 마이페이지의 유저가 다르면 프로필 편집 불가
    if(userEmail !== await checkCurrentUser()) {
        editProfile.classList.add('d-none');
    }

    // 로그인 유저와 마이페이지 유저가 같다면 팔로우 불가
    if(userEmail === await checkCurrentUser()){
		console.log(`userEmail=${userEmail}`);
        followButton.classList.add('d-none');
    }




});