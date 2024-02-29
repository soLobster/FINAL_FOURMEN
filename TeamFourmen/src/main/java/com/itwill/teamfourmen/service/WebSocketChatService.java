package com.itwill.teamfourmen.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.dto.chat.ChatRoomDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebSocketChatService {
	
	// key는 category_roomId의 조합의 String을 사용하기로 약속
	private Map<String, ChatRoomDto> chatRooms = new ConcurrentHashMap<>();
	
	
	/**
	 * 유저가 채팅방에 새로 들어왔을 때 category, roomId, nickname을 아규먼트로 받아 해당 유저를 채팅방정보에 추가한 후,
	 * category, roomId에 해당하는 ChatRoomDto타입의 채팅방을 리턴
	 * @param category
	 * @param roomId
	 * @param nickname
	 * @return ChatRoomDto타입의 유저가 접속한 채팅방 객체
	 */
	public ChatRoomDto addMember(String category, int roomId, Member member) {
		
		log.info("addUser(category={}, roomId={}, nickname={})", category, roomId, member);
		
		log.info("chatRooms={}", chatRooms);
		
		// 해당 채팅방의 key로 사용할 String
		String key = category + "_" + roomId;
		
		ChatRoomDto roomDto = chatRooms.get(key);
		
		if (roomDto == null) {	// 방이 존재하지 않을 때
			Set<Member> members = new HashSet<Member>();
			members.add(member);
			
			roomDto = ChatRoomDto.builder().roomId(roomId).members(members).category(category).build();
			
			chatRooms.put(key, roomDto);
			
		} else {	// 방이 이미 존재할 때
			
			roomDto.getMembers().add(member);
			
		}
		
		log.info("업데이트된 roomDto={}", roomDto);
		
		return roomDto;
	}
	
	
	
	/**
	 * 해당 채팅방의 ChatRoomDto 객체를 리턴.
	 * @param category
	 * @param roomId
	 * @return
	 */
	public ChatRoomDto getChatRoom(String category, int roomId) {
		
		log.info("getChatRoom(category={}, roomId={})", category, roomId);
		String key = category + "_" + roomId;
		
		log.info("key={}", key);
		
		ChatRoomDto chatRoomDto = chatRooms.get(key);
		
		return chatRoomDto;
	}
	
	
	/**
	 * 유저가 채팅방을 나갔을 때 해당 채팅방에서 유저를 빼는 메서드.
	 * 유저가 나간 후 해당 채팅방에 남아있는 유저가 없을 경우 null을, 있을 경우 ChatRoomDto타입의 채팅방 객체를 반환
	 * @param category
	 * @param roomId
	 * @param nickname
	 * @return
	 */
	public ChatRoomDto userLeft(String category, int roomId, Member member) {
		
		log.info("userLeft(category={}, roomId={}, nickname={})", category, roomId, member);

		String key = category + "_" + roomId;
		
		ChatRoomDto roomDto = chatRooms.get(key);
		
		roomDto.getMembers().remove(member);		
		
		int numUsers = roomDto.getMembers().size(); 
		if (numUsers == 0) {	// 만약 유저가 나간 후 해당 채팅방에 남은 유저가 없으면 채팅방 삭제
			chatRooms.remove(key);
			
			log.info("업데이트된 roomDto={}", roomDto);

			
			return null;
		} else {	// 유저가 나갔어도 해당 채팅방에 남아있는 유저가 있으면 그냥 채팅방 객체 리턴			
			log.info("업데이트된 roomDto={}", roomDto);

			return roomDto;
		}
		
	}
	
	
	
}
