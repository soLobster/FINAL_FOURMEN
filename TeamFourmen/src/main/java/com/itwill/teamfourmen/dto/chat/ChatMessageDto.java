package com.itwill.teamfourmen.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChatMessageDto {
	
	private String type;	// JOIN, SEND, LEAVE 중 하나
	
	private String sender;
	
	private String content;
	
	private String category;
	
	private int roomId;
	
	
	
}
