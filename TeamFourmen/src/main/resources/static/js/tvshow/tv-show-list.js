/**
 * -> document.body.scrollHeight
 *
 * -> document.documentElement.clientHeght
 *
 * -> window.scrollY
 */

window.addEventListener('DOMContentLoaded', function (){

    let page = 1; // default 1 페이지
    let originPath = location.origin;
    let pathName = location.pathname; // 현재 주소

    const listCategory = pathName.substring(8); // 카테고리 뽑아오기 ... popular, top_rated

    const flexContainer  = document.querySelector('.flex-container');
    const btnLoadTvShow = document.querySelector('#btn-load-tvShow');

    console.log(listCategory);
    console.log('LOCAL PATH = ' + pathName);

    // Throttle 함수 : 마지막 함수가 호출된 후 일정 시간이 지나기 전에 다시 호출되지 않도록 하는 것
    /*
     이벤트가 과도한 횟수로 발생하여 이벤트 핸들러가 무거운 연산을 수 없이 많이 수행하는 경우에
     제약을 걸어 제어할 수 있는 수준으로 이벤트를 발생시키는 것을 목표로하는 기술
     */

    const throttle = (cb, delay=1000) => {
        let shouldWait = false;

        return (...args) => {
            if(shouldWait) {
                return;
            }

            cb(...args)
            shouldWait = true;

            setTimeout(() => {
                shouldWait = false;
            }, delay)
        }
    }

    const getAdditionalTvShowList = async function() {

        const url = '../api/tvshow/list';
        let queryString = `?listCategory=${listCategory}&page=${page+1}`;

        let innerHtml = '';

        if(page < totalPages) {
            await
                axios.get(url + queryString)
                    .then((response) => {
                        console.log('불러온 PAGE'  + response.data.page);

                        page = response.data.page;

                        let tvShowListDTO = response.data;

                        for (let tvShowDto of tvShowListDTO.results) {
                            innerHtml += `
                                <div class="flex-container">
                                    <div class="rounded border-0 card flex_box">
                                         <a href="/tvshow/details/${tvShowDto.id}" class="text-black text-decoration-none">
                                             <img class="rounded-top show_poster" src="https://image.tmdb.org/t/p/original${tvShowDto.poster_path}" width="180" height="273" />
                                                 <div class="card-body">
                                                     <p class="mb-0 card-title">${tvShowDto.name}</p>
                                                     <p class="fst-italic card-text">${tvShowDto.first_air_date}</p>
                                                 </div>
                                        </a>
                                    </div>
                                </div>       
                            `;
                        }
                        flexContainer.innerHTML += innerHtml;
                    })
                    .catch((error) => {
                        console.log(`ERROR ...! ${error}`)
                    })
        } else {
            btnLoadTvShow.classList.add('d-none');
        }

    };

    document.addEventListener('scroll', throttle(function () {

        const totalHeight = document.body.scrollHeight - document.documentElement.clientHeight;
        const currentHeight = window.scrollY;

        if ((totalHeight - currentHeight) < 800) {
            getAdditionalTvShowList();
        }

    }, 300));

    btnLoadTvShow.addEventListener('click', getAdditionalTvShowList);

});