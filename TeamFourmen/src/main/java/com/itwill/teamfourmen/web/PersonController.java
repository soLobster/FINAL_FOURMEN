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

import java.util.*;
import java.util.stream.Collectors;

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
		PageAndListDto pageAndListDto = personService.getPersonList(page);

		model.addAttribute("pageInfo", pageAndListDto.getPage());
		model.addAttribute("personList", pageAndListDto.getResults());

		return "person/lists";

	} // end list

	@GetMapping("/details/{id}")
	public String details(
			@PathVariable("id") int id,
			@RequestParam(name = "originalName", required = false) String originalName,
			@RequestParam(name = "language", required = false) String language,
			Model model
	) {
		log.info("details(id={})", id);
		if (originalName != null) {
			log.info("details(originalName={})", originalName);
		}

		// 서비스 메서드 호출 (인물의 id 값을 파라미터로 전달)
		DetailsPersonDto detailsPersonDto = personService.getPersonDetails(language, id);
		ExternalIDsDto externalIDsDto = personService.getExternalIDs(id);
		MovieCreditsDto movieCreditsDto = personService.getMovieCredits(id);
		MovieCreditsCastDto movieCreditsCastDTO = personService.getMovieCreditsCast(id);
		TvCreditsDto tvCreditsDto = personService.getTvCredits(id);
		TvCreditsCastDto tvCreditsCastDTO = personService.getTvCreditsCast(id);
		CombinedCreditsDto combinedCreditsDto = personService.getCombinedCredits(id);
		CombinedCreditsCastDto combinedCreditsCastDto = personService.getCombinedCreditsCast(id);

		// CombinedCast를 처리하는 코드.
		List<CombinedCreditsCastDto> castList = combinedCreditsDto.getCast();
		List<CombinedCreditsCastDto> sortedCastList = new ArrayList<>(castList);
		sortedCastList.sort(Comparator.comparingDouble(CombinedCreditsCastDto::getPopularity).reversed());

		// MovieCast를 처리하는 코드.
		List<MovieCreditsCastDto> movieCastList = movieCreditsDto.getCast();
		List<MovieCreditsCastDto> sortedMovieCastList = new ArrayList<>(movieCastList);
		sortedMovieCastList.sort(Comparator.comparingDouble(MovieCreditsCastDto::getPopularity).reversed());

		// TvCast를 처리하는 코드.
		List<TvCreditsCastDto> tvCastList = tvCreditsDto.getCast();
		List<TvCreditsCastDto> sortedTvCastList = new ArrayList<>(tvCastList);
		sortedTvCastList.sort(Comparator.comparingDouble(TvCreditsCastDto::getPopularity).reversed());

		// 중복 요소를 허용하지 않는 컬렉션(HastSet)을 사용하여, 포스터 경로(path)가 고유한지 확인.
		Set<String> uniquePosterPath = new HashSet<>();

		// Cast 필터링. (중복X, 고유한 값을 가지도록 필터링함)
		List<CombinedCreditsCastDto> uniqueCastList = castList.stream()
				.filter(cast -> uniquePosterPath.add(cast.getPosterPath()))
				.collect(Collectors.toList());

		// 필터링한 Cast를 popularity 기준 내림차순 정렬.
		uniqueCastList.sort(Comparator.comparingDouble(CombinedCreditsCastDto::getPopularity).reversed());

		// 인기순으로 정렬된 castList를 모델에 추가.
		model.addAttribute("sortedCastList", sortedCastList);
		model.addAttribute("sortedMovieCastList", sortedMovieCastList);
		model.addAttribute("sortedTvCastList", sortedTvCastList);
		// 필터링한 CastList를 모델에 추가.
		model.addAttribute("uniqueCastList", uniqueCastList);

		model.addAttribute("detailsPerson", detailsPersonDto);
		model.addAttribute("externalIDs", externalIDsDto);
		model.addAttribute("movieCredits", movieCreditsDto);
		model.addAttribute("movieCreditsCast", movieCreditsCastDTO);
		model.addAttribute("tvCredits", tvCreditsDto);
		model.addAttribute("tvCreditsCast", tvCreditsCastDTO);
		model.addAttribute("combinedCredits", combinedCreditsDto);
		model.addAttribute("combinedCreditsCast", combinedCreditsCastDto);

		return "person/details";
	} // end details



}
