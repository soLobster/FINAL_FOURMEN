document.addEventListener('DOMContentLoaded', function () {
    // 카테고리 항목을 모두 선택
    const categoryItems = document.querySelectorAll('.category-list li');

    // 각 카테고리 항목에 클릭 이벤트 리스너 추가
    categoryItems.forEach(item => {
        item.addEventListener('click', function () {
            // 클릭된 항목 내의 링크를 선택
            const link = this.querySelector('a');
            // 링크가 존재하는 경우에만 이동
            if (link) {
                // 클릭된 항목의 링크로 이동
                window.location.href = link.getAttribute('href');
            }
        });
    });
});