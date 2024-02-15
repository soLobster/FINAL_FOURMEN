/**
 * chat.js
 */

document.addEventListener('DOMContentLoaded', function() {
	
	const chatBody = document.querySelector('.open-chat-body');
	const btnSend = document.querySelector('.btn-chat-send');
	const numOfPeople = document.querySelector('.chat-num-people');
	
	// TODO: 나중에 로그인하면 세션정보 이용해서 닉네임 가져오도록 하기
    const nickname = '유저' +  Math.random();	
	
	const pathName = location.pathname;
	const pathNameList = pathName.split('/');
	
	const category = pathNameList[2];
	const roomId = pathNameList[3];
	
	// SockJS 연결
	const socket = new SockJS('/ws');
	const stompClient = Stomp.over(socket);
	
	stompClient.connect({}, onConnect, onError);	// 채팅을 위한 connect
		
	function onConnect () {
		console.log('연결됨');	// TODO: 나중에 지우기
		
		console.log(`/topic/${category}/${roomId}`);
		
		stompClient.subscribe(`/topic/${category}/${roomId}`, onMessageReceived);
		
		stompClient.send('/app/chat.addUser', {}, JSON.stringify({type: 'JOIN', sender: nickname, category: 'movie', roomId: roomId}));
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
			
			message.content = `${message.sender} has joined the chatroom`;
			
			const eventContainer = document.createElement('div');
			eventContainer.classList.add('each-chat-event-container');
			
			const textElement = document.createTextNode(message.content);
			
			eventContainer.appendChild(textElement);
			chatBody.appendChild(eventContainer);
			
			console.log('조인 들어옴');
		} else if (message.type === 'LEAVE') {
			
			message.content = `${message.sender} has left the chatroom`;
			
			const eventContainer = document.createElement('div');
			eventContainer.classList.add('each-chat-event-container');
			
			const textElement = document.createTextNode(message.content);
			
			eventContainer.appendChild(textElement);
			chatBody.appendChild(eventContainer);
			
			// TODO: 나중에 alert전용 html파일만들어서 거기로 보내고 window.close()시켜버리기
			if (message.sender === nickname) {
				alert('세션이 종료되었습니다.');	
			}
			
			
		} else if (message.type === 'SEND') {
			
			if (message.sender === nickname) {	// 내가 보낸 메세지인 경우			
				chatBody.innerHTML += `
		            <div class="each-chat-container chat-by-me-container">
		                <div class="div-profile-photo">
		                    <img class="profile-img" src="https://memberdata.s3.amazonaws.com/hi/hitsdd/photos/hitsdd_photo_gal__photo_1116899403.jpg">
		                </div>
		                <div class="msg-nickname-by-me-container">
		                    <div class="chat-nickname">${message.sender}</div>
		                    <div class="chat-msg-by-me">${message.content}</div>
		                </div>
		            </div>	
				`;

			} else {	//남이 보낸 메세지인 경우
				
				chatBody.innerHTML += `
		            <div class="each-chat-container">
		                <div class="div-profile-photo">
		                    <img class="profile-img" src="https://dcimg4.dcinside.co.kr/viewimage.php?id=2ba8d227ea&no=24b0d769e1d32ca73fea8ffa11d0283138cbf06620a6c100700a55dbb30dbf98d153c8ce3c0b6e5de2517006b302f1acf9bfe5f1503771dcf0c92d5c996a8d6696f0cfc9a6b32cf3895cafd02e26ec2a9deff8a2bd5867c70b2579d846456795938a39fbdd">
		                </div>
		                <div class="msg-nickname-by-others-container">
		                    <div class="chat-nickname">${message.sender}</div>
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
		event.preventDefault();
		
		const messageInput = document.querySelector('.input-chat-form');
							
		
		stompClient.send(`/app/chat.sendMessage`, {}, JSON.stringify({type: 'SEND', sender: nickname
								, content: messageInput.value, category: category, roomId: roomId}));
		
		messageInput.value = '';
	}
	
	
	
	// 전송 버튼 누르면 메세지 전송하는 이벤트리스너
	btnSend.addEventListener('click', sendMessage);
	
	
});