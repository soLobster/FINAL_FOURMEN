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
	}).then((editor) => {
		
		btnBoardCreateCancel.addEventListener('click', function() {
		
			window.history.back();		
		});
		
		btnPostBoard.addEventListener('click', function() {
			
			const willProceed = confirm('게시하겠습니까?');
			
			if(!willProceed) {
				return;
			}
			
			const textContentInput = document.createElement('input');

			
	        console.log( viewToPlainText( editor.editing.view.getRoot() ) );

						
			textContentInput.value = editor.getData();
			
			console.log(`plain text =${plainText}`);
			
			alert('하하하하');
			
			formBoard.appendChild(textContentInput);
			
			formBoard.submit();
			
			
			alert('성공적으로 게시하였습니다.');
			
		});
		
	});
	
	
	
});