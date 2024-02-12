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
import reactor.core.publisher.Mono;

import java.time.Year;
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
			@RequestParam(name = "language", required = false, defaultValue = "ko") String language,
			Model model) {

		// 클라이언트가 page 파라미터를 전달하지 않았을 때 page 값을 1로 설정.
		if (page == null) {
			page = 1; // 기본 페이지를 1로 설정.
		}

		// 서비스 메서드 호출 (항상 "popular"를 파라미터로 전달)
		PageAndListDto pageAndListDto = personService.getPersonList(page, language);

		// 결과가 비어있는지 확인
		if (pageAndListDto.getResults() == null || pageAndListDto.getResults().isEmpty()) {
			// 결과가 비어있다면 영어("en")로 다시 요청
			pageAndListDto = personService.getPersonList(page, "en");
		}

		model.addAttribute("pageInfo", pageAndListDto.getPage());
		model.addAttribute("personList", pageAndListDto.getResults());

		return "person/lists";

	} // end list

	@GetMapping("/details/{id}")
	public String details(
			@PathVariable("id") int id,
			@RequestParam(name = "language", required = false, defaultValue = "ko") String language,
			Model model
	) {

		log.info("details(id={})", id);
		log.info("details(language={}", language);

		// 서비스 메서드 호출 (인물의 id 값을 파라미터로 전달)
		DetailsPersonDto detailsPersonDto = personService.getPersonDetails(id, language);
		ExternalIDsDto externalIDsDto = personService.getExternalIDs(id, language);
		MovieCreditsDto movieCreditsDto = personService.getMovieCredits(id, language);
		MovieCreditsCastDto movieCreditsCastDTO = personService.getMovieCreditsCast(id, language);
		TvCreditsDto tvCreditsDto = personService.getTvCredits(id, language);
		TvCreditsCastDto tvCreditsCastDTO = personService.getTvCreditsCast(id, language);
		CombinedCreditsDto combinedCreditsDto = personService.getCombinedCredits(id, language);
		List<CombinedCreditsCastDto> combinedCreditsCastList = personService.getCombinedCreditsCast(id, language);

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

		// combinedCreditsCastList를 내림차순으로 정렬하여 리턴.
		combinedCreditsCastList.sort(Comparator.comparing(CombinedCreditsCastDto::getYear, Comparator.nullsLast(Comparator.reverseOrder())));

//		// Map 사용:
//		// 연도별로 그룹화하고, 각 그룹을 내림차순으로 정렬
//		Map<Year, List<CombinedCreditsCastDto>> groupedByYear = combinedCreditsCastList.stream()
//				.filter(cast -> cast.getYear() != null) // 연도 정보가 있는 항목만 처리
//				.collect(Collectors.groupingBy(CombinedCreditsCastDto::getYear, // 연도별로 그룹화
//						Collectors.toList()));
//		// 그룹화된 맵을 연도 내림차순으로 정렬
//		Map<Year, List<CombinedCreditsCastDto>> sortedByYearDesc = new TreeMap<>(Comparator.reverseOrder());
//		sortedByYearDesc.putAll(groupedByYear);

//		log.info("==============================================");
//		log.info("sortedByYearDesc={}", sortedByYearDesc.keySet());

		log.info("==============================================");
		log.info("combinedCreditsCastList={}", combinedCreditsCastList);

		// combinedCreditsCastList의 사이즈 값 선언.
		Integer combinedCreditsCastListSize = combinedCreditsCastList.size();

		// 인기순으로 정렬된 castList를 모델에 추가.
		model.addAttribute("sortedCastList", sortedCastList);
		model.addAttribute("sortedMovieCastList", sortedMovieCastList);
		model.addAttribute("sortedTvCastList", sortedTvCastList);
		// 필터링한 CastList를 모델에 추가.
		model.addAttribute("uniqueCastList", uniqueCastList);
		// 연도별 내림차순으로 정렬한 맵을 모델에 추가.
//		model.addAttribute("sortedByYearDesc", sortedByYearDesc);
		// combinedCreditsCastList의 사이즈 값을 모델에 추가.
		model.addAttribute("combinedCreditsCastListSize", combinedCreditsCastListSize);

		model.addAttribute("detailsPerson", detailsPersonDto);
		model.addAttribute("externalIDs", externalIDsDto);
		model.addAttribute("movieCredits", movieCreditsDto);
		model.addAttribute("movieCreditsCast", movieCreditsCastDTO);
		model.addAttribute("tvCredits", tvCreditsDto);
		model.addAttribute("tvCreditsCast", tvCreditsCastDTO);
		model.addAttribute("combinedCredits", combinedCreditsDto);
		model.addAttribute("combinedCreditsCastList", combinedCreditsCastList);

		return "person/details";
	} // end details



}
