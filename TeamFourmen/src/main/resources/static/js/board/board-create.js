/**
 * board-create.js
 */

document.addEventListener('DOMContentLoaded', function() {
	const editor = document.querySelector('#editor');
	
	const formBoard = document.querySelector('.form-board-create');
	
	const btnBoardCreateCancel = document.querySelector('.btn-board-craete-cancel');
	const btnPostBoard = document.querySelector('.btn-board-create');
	
	const category = location.pathname.split('/')[1];
	
	ClassicEditor.create(editor, {
		language: "ko",
		
        ckfinder: {
            uploadUrl: "/ckeditor/image/upload",
            withCredentials: true
        },
        
        image: {
            resizeUnit: "%",
            resizeOptions: [ {
                name: 'resizeImage:original',
                value: null,
                icon: 'original'
            },
            {
                name: 'resizeImage:25',
                value: '25',
                icon: 'small'
            },
            {
                name: 'resizeImage:50',
                value: '50',
                icon: 'medium'
            },
            {
                name: 'resizeImage:75',
                value: '75',
                icon: 'large'
            } ],
            toolbar: ['toggleImageCaption', 'imageTextAlternative', 'resizeImage:25', 'resizeImage:50', 'resizeImage:75', 'resizeImage:original']
        }
	});
	
	
	btnBoardCreateCancel.addEventListener('click', function() {
		
		window.history.back();		
	});
	
	btnPostBoard.addEventListener('click', function() {
		
		const willProceed = confirm('게시하겠습니까?');
		
		if(!willProceed) {
			return;
		}
		
		
		formBoard.submit();
		
		
		alert('성공적으로 게시하였습니다.');
		
	});
	
});