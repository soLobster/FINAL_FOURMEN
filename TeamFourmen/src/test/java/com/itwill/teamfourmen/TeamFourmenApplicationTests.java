package com.itwill.teamfourmen;

import com.itwill.teamfourmen.dto.person.CombinedCreditsCastDto;
import com.itwill.teamfourmen.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@Slf4j
class TeamFourmenApplicationTests {

	@Autowired
	PersonService personService;

	@Test
	void contextLoads() {
		List<CombinedCreditsCastDto> list = personService.getCombinedCreditsCast(115440);
		log.info("====================================================================");
		log.info("리스트 출력={}", list);

		for (int i=0; i<list.size(); i++) {
			if (((list.get(i).getMediaType().equals("tv")) && list.get(i).getFirstAirDate() == null) ||
			((list.get(i).getMediaType().equals("movie") && list.get(i).getReleaseDate() == null))) {
				log.info("====================================================================");
				System.out.println("테스트 성공");
			} else {
				log.info("====================================================================");
				System.out.println("테스트 실패");
			}
		}
		log.info("listSize={}", list.size());

		// tv면 첫 방송 날짜, movie면 출시일을 저장하는 리스트 생성.
		List<CombinedCreditsCastDto> firstAirOrReleaseDate = new ArrayList<>();

		// releaseDate가 null인 것만 필터링하여 출력.
//		List<CombinedCreditsCastDto> nullList = list.stream().filter((x) -> x.getReleaseDate() == null).toList();
//		log.info("*** NULL 값들만 필터링하여 출력 *** nullList={}", nullList);
//		log.info("====================================================================");
//
        // notNull만 필터링하여 출력.
//		List<CombinedCreditsCastDto> notNullList = new ArrayList<>(list.stream().filter((x) -> x.getReleaseDate() != null).toList());
//		log.info("notNullList before Sorting = {}", notNullList);
//		log.info("====================================================================");

		// firstAirDate가 null인 것만 리스트 생성.
//		List<CombinedCreditsCastDto> releaseDateNotNullList =

		//
//		Collections.sort(notNullList, CombinedCreditsCastDto.SORT_RELEASE_DATE_ASC);
//		log.info("notNullList after Sorting = {}", notNullList);
//		log.info("====================================================================");


	}

}
