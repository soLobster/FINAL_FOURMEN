package com.itwill.teamfourmen;

import com.itwill.teamfourmen.dto.person.CombinedCreditsCastDto;
import com.itwill.teamfourmen.dto.person.CombinedCreditsDto;
import com.itwill.teamfourmen.service.PersonService;
import com.itwill.teamfourmen.web.PersonController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Slf4j
class TeamFourmenApplicationTests {

	@Autowired
	PersonService personService;
	PersonController personController;

	@Test
	void contextLoads() {
//		CombinedCreditsDto allList = personService.getCombinedCredits(2416);
		List<CombinedCreditsCastDto> list = personService.getCombinedCreditsCast(115440, "en");

		// 오류 데이터 출력 테스트.
//		log.info("============================================");
//		log.info("오류 데이터 출력={}", list.get(41));
//		log.info("============================================");
//		log.info("오류 데이터 출력={}", list.get(124));

		// 데이터 출력 테스트.
		for (int i = 0; i < list.size(); i++) {
			Year year = list.get(i).getYear(); // 먼저 날짜 객체를 가져옵니다.
			if (year != null) {
				String result = year.toString(); // null이 아닐 때만 toString 호출
				log.info("============================================");
				log.info("{}번째 데이터 출력 결과={}", i, result);
			} else {
				log.info("============================================");
				log.info("{}번째 데이터 출력값이 null 입니다...", i);
			}
		}

	} // end contextLoads()

}
