/**
 * chat.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	const signedInUser = document.querySelector('.open-chat-header');		// 닉네임을 포함하고 있는 유저 로그인했을 때 프로필사진 컨테이너
	
	const chatBody = document.querySelector('.open-chat-body');
	const btnSend = document.querySelector('.btn-chat-send');
	const numOfPeople = document.querySelector('.chat-num-people');
	
	// TODO: 나중에 로그인하면 세션정보 이용해서 닉네임 가져오도록 하기
    const nickname = signedInUser.getAttribute('nickname');	
	const email = signedInUser.getAttribute('email');
	
	const usersaveprofile = signedInUser.getAttribute('usersaveprofile');
	let profileImageUrl = '';	
	
	const pathName = location.pathname;
	const pathNameList = pathName.split('/');
	
	const category = pathNameList[2];
	const roomId = pathNameList[3];
	
	// profile image url구하기 위한 조건문
	if (usersaveprofile.toLowerCase().startsWith('http')){
        profileImageUrl = usersaveprofile;
    } else if (!usersaveprofile.toLowerCase().startsWith('http') && usersaveprofile == 'userimage.png') {
        profileImageUrl = '/image/userimage.png';
    } else {
        profileImageUrl = `/image?photo=${usersaveprofile}`;
    }
    
	// SockJS 연결
	const socket = new SockJS('/ws');
	const stompClient = Stomp.over(socket);
	
	stompClient.connect({}, onConnect, onError);	// 채팅을 위한 connect
		
	function onConnect () {
		console.log('연결됨');	// TODO: 나중에 지우기
		
		console.log(`/topic/${category}/${roomId}`);
		
		stompClient.subscribe(`/topic/${category}/${roomId}`, onMessageReceived);
		
		stompClient.send('/app/chat.addUser', {}, JSON.stringify({type: 'JOIN', member: {email: email, nickname: nickname, usersaveprofile: profileImageUrl}
		                                                          , category: category, roomId: roomId}));
	}
	
	function onError() {
        chatBody.textContent = '에러발생.. 새로고침 하세요.';
        chatBody.style.color = 'red';       
	}
	
	function onMessageReceived(payload) {
		
		console.log(payload);
		console.log(payload.body);
		
		message = JSON.parse(payload.body);
		console.log(message.type);
		if (message.type === 'JOIN') {
			
			message.content = `${message.member.nickname} has joined the chatroom`;
			
			const eventContainer = document.createElement('div');
			eventContainer.classList.add('each-chat-event-container');
			
			const textElement = document.createTextNode(message.content);
			
			eventContainer.appendChild(textElement);
			chatBody.appendChild(eventContainer);
			
			console.log('조인 들어옴');
			
		} else if (message.type === 'LEAVE') {
			
			message.content = `${message.member.nickname} has left the chatroom`;
			
			const eventContainer = document.createElement('div');
			eventContainer.classList.add('each-chat-event-container');
			
			const textElement = document.createTextNode(message.content);
			
			eventContainer.appendChild(textElement);
			chatBody.appendChild(eventContainer);
			
			// TODO: 나중에 alert전용 html파일만들어서 거기로 보내고 window.close()시켜버리기
			if (message.sender === nickname) {
				alert('세션이 종료되었습니다.');
				window.close();	
			}
			
			
		} else if (message.type === 'SEND') {
			
			if (message.member.nickname === nickname) {	// 내가 보낸 메세지인 경우			
				console.log(message);
				
				chatBody.innerHTML += `
		            <div class="each-chat-container chat-by-me-container">
		                <div class="div-profile-photo">
		                    <img class="profile-img" src="${message.member.usersaveprofile}">
		                </div>
		                <div class="msg-nickname-by-me-container">
		                    <div class="chat-nickname">${message.member.nickname}</div>
		                    <div class="chat-msg-by-me">${message.content}</div>
		                </div>
		            </div>	
				`;

			} else {	//남이 보낸 메세지인 경우
				console.log(message);
				chatBody.innerHTML += `
		            <div class="each-chat-container">
		                <div class="div-profile-photo">
		                    <img class="profile-img" src="${message.member.usersaveprofile}">
		                </div>
		                <div class="msg-nickname-by-others-container">
		                    <div class="chat-nickname">${message.member.nickname}</div>
		                    <div class="chat-msg-by-others">${message.content}</div>
		                </div>
		            </div>
				`;
				
			}
			
		} else {	// 채팅방 유저 업데이트 메세지인 경우
			numOfPeople.innerText = message;
		}
		
		chatBody.scrollTop = chatBody.scrollHeight;
		
	}
	
	
	function sendMessage(event) {
		
		console.log(`signedInUser=${signedInUser}`)
		if (!signedInUser) {
			alert('오픈채팅에 참여하고 싶으면 로그인해주세요.');
			return;
		}
		
		event.preventDefault();
		
		const messageInput = document.querySelector('.input-chat-form');
							
		
		stompClient.send(`/app/chat.sendMessage`, {}, JSON.stringify({type: 'SEND', member: {email: email, nickname: nickname, usersaveprofile: profileImageUrl}
								, content: messageInput.value, category: category, roomId: roomId}));
		
		messageInput.value = '';
	}
	
	
	
	// 전송 버튼 누르면 메세지 전송하는 이벤트리스너
	btnSend.addEventListener('click', sendMessage);
	
	
});