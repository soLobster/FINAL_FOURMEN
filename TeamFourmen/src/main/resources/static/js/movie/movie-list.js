/**
 * movie-list.js
 */

document.addEventListener('DOMContentLoaded', function() {
      
    let page = 1;   // default로 1 page에서 시작, load할수록 page 증가.
    let originPath = location.origin;
    let pathname = location.pathname;   // 현재 있는 주소(query string제외)
    const listCategory = pathname.substring(7); // 현재 리스트 페이지의 카테고리(now_playing, popular, top_rated, ...)  
    
    
    
    const listContainer = document.querySelector('.list-container');
    const btnMorePost = document.querySelector('#btn-more-post');
    
    const observer = new IntersectionObserver(async (entries) => {
        
        const fourthLastElement = entries[0];
        
        if (!fourthLastElement.isIntersecting) {
            return;
        }
            observer.unobserve(document.querySelector('.movie-item-container:nth-last-child(4)'))
            await getAdditionalList();                
            //observer.unobserve(fourthLastElement.target);
            observer.observe(document.querySelector('.movie-item-container:nth-last-child(4)'));

    });
    
    if (totalPages > 1) {
        observer.observe(document.querySelector('.movie-item-container:nth-last-child(4)'));    
    }
    
    
    
    // 영화리스트 가져오는 async함수;
    const getAdditionalList = async function() {
        
        const url = '../api/movie/list';        
        let queryString = 
            location.search ? location.search + `&listCategory=${listCategory}&page=${page+1}` : `?listCategory=${listCategory}&page=${page+1}`;
                // `?listCategory=${listCategory}&page=${page+1}`;
        
        let innerHtml = '';
        
        if (page < totalPages) {
            
            await axios.get(url + queryString)
            .then((response) => {
                page = response.data.page;
                console.log(`page=${page}`);
                                
                for (let movie of response.data.results) {
                    innerHtml += `
                        <div class="movie-item-container">
                            <div class="movie-item-image-container">
                                <a href='${originPath}/movie/details/${movie.id}'>
                                    <img src='https://image.tmdb.org/t/p/w220_and_h330_face/${movie.poster_path}'>
                                </a>
                            </div>
                            <div class="movie-description-container">
                                <a href='${originPath}/movie/details/${movie.id}'>
                                    <div class="movie-title">${movie.title}</div>
                                </a>
                                <div>${movie.release_date}</div>
                            </div>                            
                        </div>
                    `;
                }
                
                listContainer.innerHTML += innerHtml;

            })
            .catch((error) => {
                console.log(`ERROR 발생!! ${error}`)
            })              
        } else {
            btnMorePost.classList.add('d-none');
        }
        
    };
    
    
    if (btnMorePost) {
        btnMorePost.addEventListener('click', async() => {
        
        
            observer.unobserve(document.querySelector('.movie-item-container:nth-last-child(4)'));
            await getAdditionalList();
            observer.observe(document.querySelector('.movie-item-container:nth-last-child(4)'));        
        });        
    }

   
    
});