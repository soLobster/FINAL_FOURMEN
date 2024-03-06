/**
 * search.js
 */

// 페이지 로드 시 실행될 함수
document.addEventListener('DOMContentLoaded', function() {
    // URL에서 query 파라미터 값을 추출하는 함수
    function getQueryParam(param) {
        let urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param) || ''; // query 파라미터가 없는 경우 빈 문자열 반환
    }

    // 현재 query 파라미터 값을 가져옴.
    let currentQuery = getQueryParam('query');

    // 검색어 결과 텍스트 업데이트
    let searchResultText;
    searchResultText = `"${currentQuery}"의 검색 결과`;
    document.getElementById('searchQuery').textContent = searchResultText;

    // query 파라미터 값을 검색 입력 필드에 설정
    if (currentQuery) {
        let searchInput = document.querySelector('.navbar-search-input');
        if (searchInput) {
            searchInput.value = currentQuery;
        }
    }

    // 모든 카테고리 링크에 대해 현재 query 파라미터를 추가.
    document.querySelectorAll('a[href^="/search"]').forEach(function(link) {
        let currentUrl = new URL(link.href, window.location.origin);
        if (currentQuery) {
            currentUrl.searchParams.set('query', currentQuery); // 현재 query 값을 추가합니다.
        }
        link.href = currentUrl.href; // 변경된 URL을 링크의 href 속성에 설정합니다.
    });
});

// 카테고리 변경 시 실행될 함수
function addCategory(category) {
    let currentUrl = new URL(window.location);
    let searchParams = new URLSearchParams(currentUrl.search);
    let newSearchParams = new URLSearchParams();

    // query 파라미터가 존재하면 추가 (값이 비어 있거나 공백이어도 포함)
    if (searchParams.has('query')) {
        newSearchParams.set('query', searchParams.get('query'));
    }

    // category 파라미터 추가
    newSearchParams.set('category', category);

    // 새로운 searchParams를 사용하여 URL 업데이트
    currentUrl.search = newSearchParams.toString();
    window.location.href = currentUrl.href;
}