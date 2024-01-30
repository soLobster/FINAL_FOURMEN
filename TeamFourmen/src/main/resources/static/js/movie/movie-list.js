/**
 * movie-list.js
 * 
 * 스크롤 가능한 총 높이
 * -> document.body.scrollHeight
 * 
 * 컴퓨터 스크린 상의 웹 페이지 높이
 * -> document.documentElement.clientHeight
 * 
 * 현재 스크롤 위치
 * -> window.scrollY
 */

window.addEventListener('DOMContentLoaded', function() {
    
    let page = 1;   // default로 1 page에서 시작, load할수록 page 증가.
    let pathname = location.pathname;   // 현재 있는 주소(query string제외)
    const listCategory = pathname.substring(7); // 현재 리스트 페이지의 카테고리(now_playing, popular, top_rated, ...)  
    
    
    
    const listContainer = document.querySelector('.list-container');
    const btnMorePost = document.querySelector('#btn-more-post');
    
    // Throttle함수
    const throttle = (cb, delay=1000) => {       
        let shouldWait = false;
        
        return (...args) => {
            if (shouldWait) {
                return;
            }
            
            cb(...args);
            shouldWait = true;
            
            setTimeout(() => {
                shouldWait = false;
            }, delay)
        }
        
    }
    
    
    // 영화리스트 가져오는 async함수;
    const getAdditionalList = async function() {
        
        const url = '../api/movie/list';
        let queryString = `?listCategory=${listCategory}&page=${page+1}`;
        
        let innerHtml = '';
        
        if (page < totalPages) {
			
			await axios.get(url + queryString)
            .then((response) => {
                
                page = response.data.page;
                
                for (let movie of response.data.results) {
                    innerHtml += `
                        <div class="movie-item-container">
                            <div class="movie-item-image-container">
                                <img src='https://image.tmdb.org/t/p/original/${movie.poster_path}'>
                            </div>
                            <div class="movie-description-container">
                                <div>${movie.title}</div>
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
    
    
    document.addEventListener('scroll', throttle(function() {
        
        const totalHeight = document.body.scrollHeight - document.documentElement.clientHeight;
        const currentHeight = window.scrollY;
        
        if ((totalHeight - currentHeight) < 800) {
            getAdditionalList();
        }
        
    }, 300));
    
    
    btnMorePost.addEventListener('click', getAdditionalList);
    
    
   
    
    // const throttleGetAdditionalList = throttle(getAdditionalList, 3000);
    
     
});