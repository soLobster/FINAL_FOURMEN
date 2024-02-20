document.addEventListener('DOMContentLoaded', function() {
    const slideWindow = document.querySelector('.slide-window');
    const leftArrow = document.querySelector('.left-arrow');
    const rightArrow = document.querySelector('.right-arrow');

    function updateArrows() {
        const maxScrollLeft = slideWindow.scrollWidth - slideWindow.clientWidth;
        leftArrow.style.visibility = slideWindow.scrollLeft > 0 ? 'visible' : 'hidden';
        rightArrow.style.visibility = slideWindow.scrollLeft < maxScrollLeft ? 'visible' : 'hidden';
    }

    window.moveSlide = function(direction) {
        const scrollAmount = slideWindow.offsetWidth / 5; // 한 번에 하나의 항목을 이동
        const scroll = direction * scrollAmount;
        slideWindow.scrollBy({ left: scroll, behavior: 'smooth' });
        setTimeout(updateArrows, 500); // 스크롤 후 화살표 상태 업데이트
    }

    updateArrows(); // 초기 화살표 상태 설정
    slideWindow.addEventListener('scroll', updateArrows); // 스크롤 시 화살표 상태 업데이트
});