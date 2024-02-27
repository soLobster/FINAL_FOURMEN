/*
review-details.html

/reviews/{review_id}에 달릴 댓글에 대한 JS

@RequestMapping("/api/comment")

@PostMapping("/reviews")

post -> '/api/comment/reviews'
 */

document.addEventListener('DOMContentLoaded',  function () {

    const pathName = location.pathname;
    const reviewId = pathName.split('/')[2];

    const commentWriterEmail = document.querySelector('.user-info').getAttribute('email');

    console.log('REVIEW ID = ' + reviewId);

    console.log('WRITER = ' + commentWriterEmail);


    const isLikedIcon = document.querySelectorAll('.btn-like-comment');

    console.log(isLikedIcon);


    for(icon of isLikedIcon){
        if(icon.classList.contains('isLiked')) {
            const iTag =  icon.querySelector('i');
            const spanText = icon.querySelector('span');
            iTag.style.color = '#33ff33';
            spanText.textContent = '좋아요 취소';
        }
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

    async function getSingleComment(commentId) {

        const uri = `/api/comment/reviews?commentId=${commentId}`;

        try{
            const response = await axios.get(uri);
            return response.data;
        } catch (error) {
            console.log('댓글 불러오기 실패', error);
        }
    }

   async function getAllComments(page) {

        if(page === undefined){
            page = 0;
        }

        const reviewId = pathName.split('/')[2];

        const uri = `/api/comment/reviews/all/${reviewId}?page=${page}`;

        try {
            const response = await axios.get(uri);
            console.log(response);

            currentPage = response.data.number;
            totalPages = response.data.totalPages;

            makeCommentElements(response.data);
        } catch (error) {
            console.log('댓글 불러오기 실패',error);
        }

    };
   // 댓글 불러오기 함수 끝


    let currentPage = 0;
    let totalPages = 0;

    let commentId;

    const btnUpdateComment = document.querySelectorAll('.btn-show-update-comment-modal');

    for(let btn of btnUpdateComment) {
        btn.addEventListener('click', showUpdateCommentsModal);
    }

    const btnUpdateCommentModalBtn = document.querySelector('#comment-update-btn');

    async function showUpdateCommentsModal(e) {

        e.preventDefault();

        const btn = e.target;

        commentId = btn.closest('.works-comment').getAttribute('commentId');
        console.log(commentId);

        const commentData = await getSingleComment(commentId);
        console.log(commentData);

        const originalContent = commentData.content;

        const originalContentModal = document.querySelector('#update-modal-text-area');

        originalContentModal.value = originalContent;
    };

    btnUpdateCommentModalBtn.addEventListener('click' , updateComment);

    async function updateComment(){

        const commentContent = document.querySelector('#update-modal-text-area').value;

        if(commentContent === ''){
            alert('댓글을 입력하셔야 합니다...!');
            return;
        }

        const data = {commentId, commentContent};

        console.log(data);

        try {
            const response = await axios.patch('/api/comment/reviews', data);

            console.log(response);

            document.querySelector('#comment-content').value = '';
            alert('댓글을 수정했습니다.');

            location.reload(true);
        } catch (error) {
            console.log('댓글 수정에 실패 했습니다.', error);
        }

    };

    // 댓글 수정 함수

    const btnRegComment = document.querySelector('button#comment-reg-btn');

    btnRegComment.addEventListener('click', regComment);

    async function regComment() {
        const commentContent = document.querySelector('#comment-content').value;

        if(commentContent === ''){
            alert('댓글을 입력하셔야 합니다...!');
            return;
        }

        const data = {reviewId, commentWriterEmail, commentContent};

        console.log(data);

        try{
            const response = await axios.post('/api/comment/reviews', data);
            console.log(response);

            document.querySelector('#comment-content').value = '';
            alert('댓글을 등록했습니다.');

            location.reload(true);

        } catch (error) {
            console.log('댓글 등록에 실패 했습니다.',error);
        }

    };
    // 댓글 등록 함수

    const btnDeleteComment = document.querySelectorAll('.btn-delete-comment');

    for(let btn of btnDeleteComment) {
        btn.addEventListener('click', deleteComment);
    }

    async function deleteComment(e){

        e.preventDefault();

        const btn = e.target;

        commentId = btn.closest('.works-comment').getAttribute('commentId');
        console.log(commentId);

        const result = confirm('댓글을 삭제 하시겠습니까???');

        if(!result){
            return;
        }

        try{
        const uri = `/api/comment/reviews?commentId=${commentId}`;

        const response = await axios.delete(uri);
        console.log(response.data);

        alert('댓글을 삭제 했습니다.');

        location.reload(true);

        } catch (error) {
            console.log('댓글을 삭제하지 못했습니다.' , error);
        }
    }

    const btnLikeComment = document.querySelectorAll('.btn-like-comment');

    for(btn of btnLikeComment) {
        btn.addEventListener('click', likeComment);
    }

    async function likeComment(e) {

        const isUserLoggedIn = await checkCurrentUser();

        if(isUserLoggedIn === '') {
            alert('로그인한 유저만 좋아요를 누를 수 있습니다...!');
            return;
        }

        e.preventDefault();

        const btn = e.target;
        console.log(btn);

        const comment_id = btn.getAttribute('id');
        console.log(comment_id);

        const icon = btn.querySelector('i');

        const span = btn.querySelector('span');

        const uri = `/api/comment/reviews/like`

        console.log(isUserLoggedIn);

        data = {
            reviewComments: {
                commentId: comment_id
            },
            member: {
                email: isUserLoggedIn
            }
        }

        console.log(data);

        if(!btn.classList.contains('isLiked')){

            console.log(comment_id);

            try{
                const response = await axios.patch(uri, data);

                console.log(response.data);

                alert('추천 되었습니다.');

                location.reload();
            } catch (error) {
                console.log('에러 발생', error);
            }

        } else {
            btn.classList.remove('isLiked');

            console.log(comment_id);

            try{

                const response = await  axios.patch(uri, data);

                console.log(response.data);

                alert('추천 취소 되었습니다.');

                location.reload();

            } catch (error) {
                console.log('에러 발생', error);
            }
        }

    }

});