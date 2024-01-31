/**
 * img-dominant-color.js
 */

 
document.addEventListener('DOMContentLoaded', function() {
	
	console.log(backdropPath)
	const posterImage = document.querySelector('.detail_poster');
	const detailHeader = document.querySelector('#movie-details-header');
	const backgroundColorDiv = document.querySelector('#movie-background-color-div');
	const descriptionContainer  = document.querySelector('.movie-details-description-container');
	const tagline = document.querySelector('.movie-tagline');
	
	const imageRootPath = 'https://media.themoviedb.org/t/p/w1920_and_h800_multi_faces';
	
	const encodedUrl = encodeURI(imageRootPath+backdropPath);
	
	
	const backgroundImageUrl = location.origin + "/api/image/proxy?url=" + encodedUrl;
	
	
	
	console.log(backgroundImageUrl);
	
	//backgroundImageDiv.style.backgroundImage = url(backgroundImageUrl);
	
	posterImage.addEventListener('load', function() {
	    const colorThief = new ColorThief();
	    const dominantColor = colorThief.getColor(posterImage);
		const brightness = dominantColor[0] * 0.299 + dominantColor[1] * 0.587 + dominantColor[2] * 0.114;
	    console.log(`주 색 = ${dominantColor}`);
	    
	    
	    detailHeader.style.backgroundImage = `url(${encodedUrl})`;
	    
	    backgroundColorDiv.style.backgroundImage = `linear-gradient(to right, rgba(${dominantColor}, 1) calc((50vw - 170px) - 340px), rgba(${dominantColor}, 0.84) 50%, rgba(${dominantColor}, 0.84) 100%)`;
	    
	    console.log(`밝기 공식 값 = ${brightness}`);
	    console.dir(descriptionContainer);
		
		if (brightness > 140) {
			console.log('밝기 조건문 안에 왓음');
			descriptionContainer.style.color = '#000000';
			tagline.style.color = '#495057';
			
		} else {
			descriptionContainer.style.color = '#fff';
		}
	    
	});

	
});