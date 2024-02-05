package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.dto.person.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itwill.teamfourmen.service.PersonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

	private final PersonService personService;

	@GetMapping("/list")
	public String list(
			@RequestParam(name = "page", required = false) Integer page,
			Model model) {

		// 클라이언트가 page 파라미터를 전달하지 않았을 때 page 값을 1로 설정.
		if (page == null) {
			page = 1; // 기본 페이지를 1로 설정.
		}

		// 서비스 메서드 호출 (항상 "popular"를 파라미터로 전달)
		PageAndListDto pageAndListDto = personService.getPersonList("popular", page);

		model.addAttribute("pageInfo", pageAndListDto.getPage());
		model.addAttribute("personList", pageAndListDto.getResults());

		return "person/list";

	} // end list

	@GetMapping("/details/{id}")
	public String details (
			@PathVariable("id") int id,
			@RequestParam(name = "orginalName", required = false) String originalName,
			Model model
	) {
		log.info("details(id={})", id);
		if (originalName != null) {
			log.info("details(originalName={})", originalName);
		}

		// 서비스 메서드 호출 (인물의 id 값을 파라미터로 전달)
		DetailsPersonDto detailsPersonDto = personService.getPersonDetails(id);
		ExternalIDsDto externalIDsDto = personService.getExternalIDs(id);
		MovieCreditsDto movieCreditsDto = personService.getMovieCredits(id);
		MovieCreditsCastDTO movieCreditsCastDTO = personService.getMovieCreditsCast(id);
		TvCreditsDto tvCreditsDto = personService.getTvCredits(id);
		TvCreditsCastDTO tvCreditsCastDTO = personService.getTvCreditsCast(id);
		CombinedCreditsDto combinedCreditsDto = personService.getCombinedCredits(id);

		// 값 출력 테스트.
		String dto = movieCreditsCastDTO.getTitle();
		log.info("dto 출력 테스트: {}", dto);


		model.addAttribute("detailsPerson", detailsPersonDto);
		model.addAttribute("externalIDs", externalIDsDto);
		model.addAttribute("movieCredits", movieCreditsDto);
		model.addAttribute("movieCreditsCast",movieCreditsCastDTO);
		model.addAttribute("tvCredits", tvCreditsDto);
		model.addAttribute("tvCreditsCast", tvCreditsCastDTO);
		model.addAttribute("combinedCredits", combinedCreditsDto);

		return "person/details";
	} // end details

}
