/**
 * person-lists.js
 * /person/list.html 에 포함.
 * 스크롤 이벤트를 사용한 페이징 처리.
 */

document.addEventListener('DOMContentLoaded', () => {

    let pageNum= 1; // 시작 페이지 번호.
    const maxPage = 500; // 최대 페이지 번호, 나중에 필요하면 서버에서 총 페이지 수 가져와서 사용해도 됨.
    const container = document.querySelector('.result'); // 결과를 추가할 컨테이너.

    // 스크롤 이벤트 리스너.
    window.addEventListener('scroll', () => {
        // 페이지 끝에 도달했는지 확인.
        if (window.innerHeight + window.scrollY >= document.body.offsetHeight) {
            loadMoreData();
        }
    });

    // 데이터 로드 함수.
    function loadMoreData() {
        if (pageNum >= maxPage) return; // 마지막 페이지를 넘어서면 더 이상 데이터를 로드하지 않음.

        pageNum++; // 페이지 번호 증가.
        const url = `/person/list?page=${pageNum}&language=${param.language}`; // 데이터를 요청할 url.

        // 데이터 요청.
        fetch(url)
            .then(response => response.json())
            .then(data => {

            })
    }
})