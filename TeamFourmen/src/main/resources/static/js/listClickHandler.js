/*
 * listClickHandler.js
 * search-multi.html 에 포함.
 */
document.addEventListener("DOMContentLoaded", function() {
    const searchInput = document.getElementById('searchInput');

    // URL에서 query 파라미터의 값을 가져옵니다.
    const urlParams = new URLSearchParams(window.location.search);
    const searchQueryFromUrl = urlParams.get('query');

    // URL의 query 파라미터 값이 있으면 검색창에 해당 값을 설정합니다.
    if (searchQueryFromUrl) {
        searchInput.value = searchQueryFromUrl;
    }

    // 페이지 로드 시 이전 검색어 복원
    const lastSearchQuery = sessionStorage.getItem('lastSearchQuery');
    if (lastSearchQuery !== null && searchInput) {
        searchInput.value = lastSearchQuery;
    }

    // 검색창에 입력하는 동안 사용자가 입력한 값을 sessionStorage에 저장합니다.
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            sessionStorage.setItem('lastSearchQuery', this.value);
        });
    }

    // 카테고리 목록에 대한 클릭 이벤트를 설정합니다.
    document.querySelectorAll(".category-list li").forEach(function(item) {
        item.addEventListener("click", function(event) {
            event.preventDefault();

            let action = this.getAttribute("data-action");
            let searchQuery = searchInput ? searchInput.value : '';
            let encodedQuery = encodeURIComponent(searchQuery);

            // 조건에 따라 action 경로 수정
            let baseUrl = "http://localhost:8081";
            if (action === "/movie/search" || action === "/tv/search") {
                // 영화와 TV 항목에 대한 특별 처리
                window.location.href = `${baseUrl}${action}?query=${encodedQuery}`;
            } else {
                // 기타 항목에 대한 일반 처리
                window.location.href = `${baseUrl}${action}?query=${encodedQuery}`;
            }
        });
    });
});
