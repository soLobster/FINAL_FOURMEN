package com.itwill.teamfourmen.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.socket.server.WebSocketService;

import com.itwill.teamfourmen.dto.chat.ChatMessageDto;
import com.itwill.teamfourmen.dto.chat.ChatRoomDto;
import com.itwill.teamfourmen.dto.movie.MovieDetailsDto;
import com.itwill.teamfourmen.dto.tvshow.TvShowDTO;
import com.itwill.teamfourmen.service.MovieApiUtil;
import com.itwill.teamfourmen.service.TvShowApiUtil;
import com.itwill.teamfourmen.service.WebSocketChatService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class WebSocketChatController {
	
	private final MovieApiUtil movieApiUtil;
	private final TvShowApiUtil tvShowApiUtil;
	private final SimpMessageSendingOperations messagingTemplate;
	private final WebSocketChatService chatService;
	
	
	@GetMapping("/openchat/{category}/{roomId}")
	public String getOpenChatWindow(@PathVariable("category") String category, @PathVariable("roomId") int roomId, Model model) {
		
		log.info("getOpenChatWindow(category={}, roomId={})", category, roomId);
		
		// TODO: TV show일 경우도 만들기.. 일단은 영화만
		if (category.equals("movie")) {
			MovieDetailsDto detailsDto = movieApiUtil.getMovieDetails(roomId);
			String title = detailsDto.getTitle();
			model.addAttribute("title", title);
		} else if (category.equals("tv")) {
			TvShowDTO detailsDto = tvShowApiUtil.getTvShowDetails(roomId);
			String title = detailsDto.getName();
			model.addAttribute("title", title);
		}
		
		return "/chat/openchat";
	}
	
	
	/**
	 * 메세지를 보내는 메서드. SendTo는 messagingTemplate를 사용해서 동적인 주소로 보냄
	 * @param messageDto
	 * @return
	 */
	@MessageMapping("/chat.sendMessage")
	public void sendMessage(@Payload ChatMessageDto messageDto) {
		
		log.info("sendMessage(messageDto={})", messageDto);
		
		messagingTemplate.convertAndSend("/topic/" + messageDto.getCategory() + "/" + messageDto.getRoomId(), messageDto);
	}
	
	
	@MessageMapping("/chat.addUser")
	public void addMember(@Payload ChatMessageDto messageDto, SimpMessageHeaderAccessor headerAccessor) {
		
		log.info("addUser(messageDto={})", messageDto);
				
		
        // WebSocket Session에 username추가
        headerAccessor.getSessionAttributes().put("member", messageDto.getMember());
        headerAccessor.getSessionAttributes().put("category", messageDto.getCategory());
        headerAccessor.getSessionAttributes().put("roomId", messageDto.getRoomId());
        
        log.info("headerAccessor={}", headerAccessor.toString());
        
		chatService.addMember(messageDto.getCategory(), messageDto.getRoomId(), messageDto.getMember());
		ChatRoomDto roomDto = chatService.getChatRoom(messageDto.getCategory(), messageDto.getRoomId());
		
		log.info("보낼 subscribe={}", "/topic/" + messageDto.getCategory() + "/" + messageDto.getRoomId());
		
		messagingTemplate.convertAndSend("/topic/" + messageDto.getCategory() + "/" + messageDto.getRoomId(), messageDto);
		messagingTemplate.convertAndSend("/topic/" + messageDto.getCategory() + "/" + messageDto.getRoomId(), roomDto.getMembers().size());
	}
	
}
