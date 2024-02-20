/**
 * person-poster-slide.js
 */
document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('.prev-btn').addEventListener('click', function() {
        document.querySelector('.horizontal-scroll').scrollBy({ left: -500, behavior: 'smooth' });
    });

    document.querySelector('.next-btn').addEventListener('click', function() {
        document.querySelector('.horizontal-scroll').scrollBy({ left: 500, behavior: 'smooth' });
    });
});