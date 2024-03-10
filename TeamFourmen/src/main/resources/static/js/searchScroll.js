// /**
//  * searchScroll.js
//  * searchMulti.html 에 포함.
//  */
//
// // document.addEventListener('DOMContentLoaded', function() {
// //     const row = document.querySelector('.row');
// //     const prevButton = document.querySelector('.prev-button');
// //     const nextButton = document.querySelector('.next-button');
// //
// //     prevButton.addEventListener('click', function() {
// //         // 왼쪽으로 스크롤
// //         row.scrollBy({ left: -300, behavior: 'smooth' });
// //     });
// //
// //     nextButton.addEventListener('click', function() {
// //         // 오른쪽으로 스크롤
// //         row.scrollBy({ left: 300, behavior: 'smooth' });
// //     });
// // });
//
// document.addEventListener('DOMContentLoaded', function() {
//     const row = document.querySelector('.row');
//     const items = row.querySelectorAll('li'); // 모든 항목을 선택
//     const prevButton = document.querySelector('.prev-button');
//     const nextButton = document.querySelector('.next-button');
//
//     let itemWidth = items[0].offsetWidth; // 첫 번째 항목의 너비를 기준으로 사용
//     let scrollAmount = itemWidth * 2; // 한 번에 스크롤할 양 (여기서는 2개 항목 너비)
//
//     // 스크롤 가능한지 확인하여 버튼 활성화/비활성화
//     function updateButtonState() {
//         prevButton.disabled = row.scrollLeft <= 0;
//         nextButton.disabled = row.scrollLeft + row.offsetWidth >= row.scrollWidth;
//     }
//
//     prevButton.addEventListener('click', function() {
//         // 왼쪽으로 스크롤
//         row.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
//         updateButtonState();
//     });
//
//     nextButton.addEventListener('click', function() {
//         // 오른쪽으로 스크롤
//         row.scrollBy({ left: scrollAmount, behavior: 'smooth' });
//         updateButtonState();
//     });
//
//     // 초기 버튼 상태 업데이트
//     updateButtonState();
// });