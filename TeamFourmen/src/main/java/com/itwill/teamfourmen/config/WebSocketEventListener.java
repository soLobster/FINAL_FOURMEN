package com.itwill.teamfourmen.config;

import java.util.HashMap;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.dto.chat.ChatMessageDto;
import com.itwill.teamfourmen.dto.chat.ChatRoomDto;
import com.itwill.teamfourmen.service.WebSocketChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
	
	private final SimpMessageSendingOperations messagingTemplate;
	private final WebSocketChatService chatService;
	
	@EventListener
	public void handleWebSocketConnectListener(SessionConnectEvent event) {
		
		log.info("새 유저 연결");
		
	}
	
	
	/**
	 * 일단은 영화에 대해서만 만들어놨는데 나중에 리팩토링하기
	 * @param event
	 */
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		
		log.info("유저 연결 끊김");
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		Member member = (Member) headerAccessor.getSessionAttributes().get("member");
		String category = (String) headerAccessor.getSessionAttributes().get("category");
		Integer roomId = (Integer) headerAccessor.getSessionAttributes().get("roomId");
		
		ChatRoomDto roomDto = chatService.getChatRoom(category, roomId);
		// HashMap<Member, Integer> memberMapInRoom = roomDto.getMembers();
		int numOfWindows = roomDto.getMembers().get(member);
		numOfWindows--;
		roomDto.getMembers().put(member, numOfWindows); // 유저가 띄운 창 개수 하나 빼고 채팅방의 Members 맵 업데이트함
		
		if (member != null && numOfWindows <= 0) {
			
			ChatMessageDto chatMessage = ChatMessageDto.builder().member(member).type("LEAVE").build();
						
			ChatRoomDto roomDtoAfterLeave = chatService.userLeft(category, roomId, member);
			
			messagingTemplate.convertAndSend("/topic/" + category + "/" + roomId, chatMessage);
			
			if (roomDtoAfterLeave != null) {
				messagingTemplate.convertAndSend("/topic/" + category + "/" + roomId, roomDtoAfterLeave.getMembers().size());	
			}
			
			
			
		}
		
	}
	
	
}
