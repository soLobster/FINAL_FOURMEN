/**
 * person-poster-slide.js
 */
let currentIndex = 0; // 현재 슬라이드 인덱스
const postersToShow = 5; // 한 번에 보여줄 포스터 수
const moveAmount = 2; // 한 번에 이동할 포스터 수

// TMDB API에서 데이터를 가져와서 슬라이드에 포스터 이미지를 동적으로 추가하는 함수
// function fetchPosters() {
//     const personId = '12345'; // 특정 인물의 ID
//     const apiKey = 'YOUR_API_KEY'; // TMDB API 키
//     const url = `https://api.themoviedb.org/3/person/${personId}/movie_credits?api_key=${apiKey}&language=ko`;
//
//     fetch(url)
//         .then(response => response.json())
//         .then(data => {
//             const movies = data.cast.slice(0, 10); // 첫 10개의 출연작만 가져옴
//             const slide = document.getElementById('slide');
//             movies.forEach(movie => {
//                 const imgElement = document.createElement('img');
//                 imgElement.src = `https://image.tmdb.org/t/p/w500${movie.poster_path}`;
//                 imgElement.classList.add('poster');
//                 slide.appendChild(imgElement);
//             });
//             updateArrowVisibility();
//         });
// }

// 슬라이드 이동 함수
function moveSlide(direction) {
    currentIndex += direction * moveAmount;
    currentIndex = Math.max(0, Math.min(currentIndex, postersToShow - 1)); // 인덱스 범위 유지
    const slide = document.getElementById('slide');
    slide.style.transform = `translateX(-${currentIndex * 200}px)`; // 200은 포스터 너비
    updateArrowVisibility();
}

// 화살표 표시/숨김 업데이트 함수
function updateArrowVisibility() {
    document.querySelector('.left-arrow').style.display = currentIndex > 0 ? 'block' : 'none';
    document.querySelector('.right-arrow').style.display = currentIndex < postersToShow - moveAmount ? 'block' : 'none';
}

// 페이지 로드 시 포스터 데이터 가져오기
// document.addEventListener('DOMContentLoaded', fetchPosters);