/**
 * 리뷰에 좋아요를 누른 사람들을 표기하고
 * 팔로우 체크를 통해 팔로우/팔로우 취소 등을 동적으로 표기
 *
 */


document.addEventListener('DOMContentLoaded', async function () {

    // 좋아요 누른 유저의 div들을 찾음
    const likedUsers = document.querySelectorAll('.liked-user');
    //console.log(likedUsers);

    // 각각 유저의 이메일을 뽑아냄
    for (let user of likedUsers) {

        // 각각의 유저 div에 속해있는 버튼과 버튼의 속성인 email, data-status를 가져옴
        let button = user.querySelector('button');
        let userEmail = button.getAttribute('email');

        console.log('좋아요를 누른 유저의 이메일 = ' + userEmail);

        console.log('USER EMAIL = ' + userEmail);

        // 현재 로그인 유저와 좋아요를 누른 유저가 같다면 팔로우 버튼을 숨김
        if(userEmail === await checkCurrentUser()) {
            button.classList.add('d-none');
        }

        // 팔로우 여부를 가려서 팔로우를 한 유저라면 버튼을 언팔로우 버튼으로 바꾸고 'data-status'를 'following' 중으로 바꿈
        // 버튼 역시 기본의 'btn-success' 에서 -> 'btn-danger'로 바꾸고 이름까지 언팔로우로 바꿈.
        //
        if(await checkFollowUser(userEmail) === true){
            button.classList.replace('notFollowed','isFollowed');
            button.classList.replace('btn-success', 'btn-danger');
            button.innerText = '언팔로우';
        }

        //
        if(button.querySelector('button').classList.contains('notFollowed')){
            button.addEventListener('click', async function(){
                await followUser(userEmail , button);
            });
            button.removeEventListener('click', followUser);
        } else {
            button.addEventListener('click', async function() {
                await unFollowUser(userEmail, button);
            });
            button.removeEventListener('click', unFollowUser);
        }
        // status가 'following' 중이라면 팔로우 취소 버튼을 실행.
        // if(status === 'following') {
        //     button.addEventListener('click', async function(){
        //         await unFollowUser(userEmail , button);
        //     });
        // }

    }

    async function checkFollowUser(userEmail) {

        try{
            const response = await axios.get(`/api/follow/${userEmail}`);
            console.log( `${userEmail} 팔로우 여부  = ` + response.data);

            return response.data;

        } catch (error) {
            console.log('팔로우 체크 오류 ', error);
            return undefined;
        }

    }

    async function followUser(userEmail, button) {

        try {
            const response = await axios.post(`/api/follow/${userEmail}`);
            console.log(response.data);


            button.classList.replace('btn-success', 'btn-danger');
            button.classList.replace('notFollowed', "isFollowed");
            button.innerText = '언팔로우';

            alert('팔로우 되었습니다.');


        } catch (error) {
            console.log('팔로우 추가 오류', error);
        }
    }
    async function unFollowUser(userEmail , button) {

        const result = confirm('정말 언팔로우 하시겠습니까??');

        if(!result) {
            return;
        }

        try {
            const response = await axios.delete(`/api/follow/${userEmail}`);
            console.log(response.data);

            button.classList.replace('btn-danger' , 'btn-success');
            button.classList.replace('isFollowed', 'notFollowed');
            button.innerText = '팔로우';
            alert('언팔로우 되었습니다.');


        } catch (error) {
            console.log('팔로우 삭제 오류', error);
        }


    }

    async function checkCurrentUser() {
        try {
            const response = await axios.get('/api/user/current-user');

            console.log('현재 로그인 유저 = ' + response.data);

            return response.data;

        } catch (error) {
            console.log('사용자 상태 확인 오류', error);
            return false;
        }
    }

});