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
    const commentWriterEmail = document.querySelector('#comment-writer-email').getAttribute('email');

    console.log('REVIEW ID = ' + reviewId);

    console.log('WRITER = ' + commentWriterEmail);

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

    for(let btn of btnLikeComment) {
        btn.addEventListener('click', likeComment);
    }

    async function likeComment(e) {
        e.preventDefault();

        const btn = e.target;

        commentId = btn.closest('.works-comment').getAttribute('commentId');
        console.log(commentId);


    }

});