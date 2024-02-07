package com.itwill.teamfourmen.domain;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootTest
public class ExampleTest {
	
	@Test
	public void test() {
        Integer[] array = {1, 2, 3};
        List<Object> list = Arrays.asList(array);
		
        log.info(list.toString());
        
		
	}
	
}