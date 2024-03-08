/**
 * board-edit.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	
	const editor = document.querySelector('#editor');
	const btnPostEditCancel = document.querySelector('.btn-board-edit-cancel');
	const btnPostEdit = document.querySelector('.btn-board-edit'); 
	const formBoardEdit = document.querySelector('.form-board-edit');
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
		const textareaCkEditor = document.querySelector('#editor');
		const existingContent = textareaCkEditor.getAttribute('value');
		editor.setData(existingContent);
		
		
		// 수정버튼 이벤트리스너
		btnPostEdit.addEventListener('click', function() {
			
			const postTitle = document.querySelector('.post-title').value;
			
			const postContent = editor.getData();
			
			if (!postContent || !postTitle) {
				alert('제목/내용을 입력해주세요');
				return;
			}
			
			if (postContent.length > 1300) {
				alert(`작성 가능한 글자수를 초과하셨습니다. \n${postContent.length} / 1300`);
				return;				
			}
			
			const willProceed = confirm('수정하시겠습니까?');
			
			if(!willProceed) {
				return;
			}
			
			formBoardEdit.submit();
			
		});
		
		
	});
	
	// 취소버튼 이벤트리스너
	btnPostEditCancel.addEventListener('click', function() {
		const willProceed = confirm('정말 하시던 작업을 취소 하시고 이전 페이지로 돌아가시겠습니까?');
		if(willProceed) {
			history.back();
			return;
		}
		
	});
	
	
	
	
});