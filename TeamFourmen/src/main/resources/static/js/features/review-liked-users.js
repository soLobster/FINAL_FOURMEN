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

        // 유저의 이메일을 통해 팔로우를 체크하고 팔로우 중 이라면 버튼의 색깔과 버튼 이름을 변경함.
        if(await checkFollowUser(userEmail) === true){
            button.classList.replace('notFollowed','isFollowed');
            button.classList.replace('btn-success', 'btn-danger');
            button.innerText = '언팔로우';
        }

        button.removeEventListener('click', await followUser);
        button.removeEventListener('click', await unFollowUser);
        /*
        <button class ="mb-2 btn btn-success notFollowed"
                                th:id = "${ 'user-' + user.getMember().getMemberId() }"
                                th:email = "${user.getMember().getEmail()}">팔로우</button>
        버튼은 이런 형식임.
        그래서 클래스에 notFollowed가 있다면 팔로우 버튼을 누르게 됨
        아닐시에는 언팔로우 버튼을 누르게 됨
        TODO 근데 문제는 버튼에 이벤트 리스너가 한번 등록이 된 상태이기에 이벤트 리스너를 제거해줘야함 어디서 제거할지 모르겠음
        getEventListener를 이용해서 이벤트 리스너가 등록 되고 있는지 확인하고 싶음
         */

        if(button.classList.contains('notFollowed')){
            button.addEventListener('click', async function(){
                await followUser(userEmail , button);
            });
        } else {
            button.addEventListener('click', async function() {
                await unFollowUser(userEmail, button);
            });
        }
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