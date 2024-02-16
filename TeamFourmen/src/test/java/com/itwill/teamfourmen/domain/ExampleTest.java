package com.itwill.teamfourmen.domain;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.itwill.teamfourmen.dto.chat.ChatRoomDto;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
public class ExampleTest {
	
	// @Test
	public void test() {
        Integer[] array = {1, 2, 3};
        List<Object> list = Arrays.asList(array);
		
        log.info(list.toString());
        
		
	}
	
	
//	@Test
	public void mapTest() {
		
		Map<String, ChatRoomDto> chatRooms = new ConcurrentHashMap<>();
		
		ChatRoomDto roomDto = ChatRoomDto.builder().category("movie").roomId(111).build();
		
		chatRooms.put("movie_111", roomDto);
		
		log.info(chatRooms.get("movie_111").toString());
		
		log.info("이거 null? = {}", chatRooms.get("12313"));
		
		
		Set<String> users = new HashSet<>();
		
		users.add("111");
		
		users.add("222");
		
		log.info("유저 삭제 전 = {}", users);
		
		users.remove("111");
		
		log.info("users 삭제후 = {}", users);
		
	}

	@Test
	public void fileTest() {
		String rootDr = File.listRoots()[1].getAbsolutePath();
		log.info("rootDr = {}", rootDr);

		log.info("root = {}", File.listRoots());

	}
	
}