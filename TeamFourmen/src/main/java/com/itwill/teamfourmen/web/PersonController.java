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
		log.info("details(language={})", language);

		// 서비스 메서드 호출 (인물의 id 값을 파라미터로 전달)
		DetailsPersonDto detailsPersonDto = personService.getPersonDetails(id, language);
		ExternalIDsDto externalIDsDto = personService.getExternalIDs(id, language);
		MovieCreditsDto movieCreditsDto = personService.getMovieCredits(id, language);
		MovieCreditsCastDto movieCreditsCastDTO = personService.getMovieCreditsCast(id, language);
		TvCreditsDto tvCreditsDto = personService.getTvCredits(id, language);
		TvCreditsCastDto tvCreditsCastDTO = personService.getTvCreditsCast(id, language);
		CombinedCreditsDto combinedCreditsDto = personService.getCombinedCredits(id, language);
		List<CombinedCreditsCastDto> combinedCreditsCastList = personService.getCombinedCreditsCast(id, language);
		List<CombinedCreditsCrewDto> combinedCreditsCrewList = personService.getCombinedCreditsCrew(id, language);

		// CombinedCast를 처리하는 코드.
		List<CombinedCreditsCastDto> castList = combinedCreditsDto.getCast();
		List<CombinedCreditsCastDto> sortedCastList = new ArrayList<>(castList);
		sortedCastList.sort(Comparator.comparingDouble(CombinedCreditsCastDto::getVoteCount).reversed());

		// MovieCast를 처리하는 코드.
		List<MovieCreditsCastDto> movieCastList = movieCreditsDto.getCast();
		List<MovieCreditsCastDto> sortedMovieCastList = new ArrayList<>(movieCastList);
		sortedMovieCastList.sort(Comparator.comparingDouble(MovieCreditsCastDto::getVoteCount).reversed());

		// TvCast를 처리하는 코드.
		List<TvCreditsCastDto> tvCastList = tvCreditsDto.getCast();
		List<TvCreditsCastDto> sortedTvCastList = new ArrayList<>(tvCastList);
		sortedTvCastList.sort(Comparator.comparingDouble(TvCreditsCastDto::getVoteCount).reversed());

		// CombinedCrew를 처리하는 코드.
		List<CombinedCreditsCrewDto> crewList = combinedCreditsDto.getCrew();

		/*
		 * 중복 요소를 허용하지 않는 컬렉션(HashSet)을 사용하여, 중복을 제거한 Cast 리스트 생성.
		 * Cast 포스터 경로를 전달하기 위함.
		 */
		// 중복 요소를 허용하지 않는 컬렉션(HashSet)을 사용하여, 포스터 경로(path)가 고유한지 확인.
		Set<String> uniqueCastPosterPath = new HashSet<>();
		// Cast 필터링. (중복X, 고유한 값을 가지도록 필터링함)
		List<CombinedCreditsCastDto> uniqueCastList = castList.stream()
				.filter(cast -> uniqueCastPosterPath.add(cast.getPosterPath()))
				.collect(Collectors.toList());
		// 필터링한 Cast 를 voteCount 기준 내림차순 정렬.
		uniqueCastList.sort(Comparator.comparingDouble(CombinedCreditsCastDto::getVoteCount).reversed());

		/*
		 * 중복 요소를 허용하지 않는 컬렉션(HashSet)을 사용하여, 중복을 제거한 Crew 리스트 생성.
		 * Crew 포스터 경로를 전달하기 위함.
		 */
		// 중복 요소를 허용하지 않는 컬렉션(HashSet)을 사용하여, 포스터 경로(path)가 고유한지 확인.
		Set<String> uniqueCrewPosterPath = new HashSet<>();
		// Crew 필터링. (중복X, 고유한 값을 가지도록 필터링함)
		List<CombinedCreditsCrewDto> uniqueCrewList = crewList.stream()
				.filter(cast -> uniqueCrewPosterPath.add(cast.getPosterPath()))
				.collect(Collectors.toList());
		// 필터링한 Crew 를 voteCount 기준 내림차순 정렬.
		uniqueCrewList.sort(Comparator.comparingDouble(CombinedCreditsCrewDto::getVoteCount).reversed());

		/*
		 * 인물의 약력 부분에서 참여 작품을 작품 출시 날짜의 내림차순으로 정렬하여 보여주기 위한 코드 작성.
		 * Cast만 해당함.
		 */
		// 방법 1. combinedCreditsCastList를 내림차순으로 정렬:
		// Comparator.nullFirst()를 사용하여 null 값이 있는 요소를 정렬된 목록의 가장 앞에 배치.
		// Comparator.nullLast() -> null 값이 있는 요소를 정렬된 목록의 가장 뒤에 배치.
		combinedCreditsCastList.sort(Comparator.comparing(CombinedCreditsCastDto::getYear, Comparator.nullsFirst(Comparator.reverseOrder())));
		// 방법 2. Map 사용:
		// 연도별로 그룹화하고, 각 그룹을 내림차순으로 정렬
		Map<Year, List<CombinedCreditsCastDto>> groupedByYearCast = combinedCreditsCastList.stream()
				.filter(cast -> cast.getYear() != null) // 연도 정보가 있는 항목만 처리
				.collect(Collectors.groupingBy(CombinedCreditsCastDto::getYear, // 연도별로 그룹화
						Collectors.toList()));
		// 그룹화된 맵을 연도 내림차순으로 정렬
		Map<Year, List<CombinedCreditsCastDto>> sortedByYearDescCast = new TreeMap<>(Comparator.reverseOrder());
		sortedByYearDescCast.putAll(groupedByYearCast);

		/*
		 * 인물의 약력 부분에서 참여 작품을 작품 출시 날짜의 내림차순으로 정렬하여 보여주기 위한 코드 작성.
		 * Crew만 해당함.
		 */
		// 방법 1. combinedCreditsCrewList를 내림차순으로 정렬:
		// Comparator.nullFirst()를 사용하여 null 값이 있는 요소를 정렬된 목록의 가장 앞에 배치.
		// Comparator.nullLast() -> null 값이 있는 요소를 정렬된 목록의 가장 뒤에 배치.
		combinedCreditsCrewList.sort(Comparator.comparing(CombinedCreditsCrewDto::getYear, Comparator.nullsFirst(Comparator.reverseOrder())));
		// 방법 2. Map 사용:
		// 연도별로 그룹화하고, 각 그룹을 내림차순으로 정렬
		Map<Year, List<CombinedCreditsCrewDto>> groupedByYearCrew = combinedCreditsCrewList.stream()
				.filter(crew -> crew.getYear() != null) // 연도 정보가 있는 항목만 처리
				.collect(Collectors.groupingBy(CombinedCreditsCrewDto::getYear, // 연도별로 그룹화
						Collectors.toList()));
		// 그룹화된 맵을 연도 내림차순으로 정렬
		Map<Year, List<CombinedCreditsCrewDto>> sortedByYearDescCrew = new TreeMap<>(Comparator.reverseOrder());
		sortedByYearDescCrew.putAll(groupedByYearCrew);

//		log.info("==============================================");
//		log.info("sortedByYearDescCast={}", sortedByYearDescCast.keySet());
//		log.info("sortedByYearDescCastSize={}", sortedByYearDescCast.size());
//
//		log.info("==============================================");
//		log.info("sortedByYearDescCrew={}", sortedByYearDescCrew.keySet());
//		log.info("sortedByYearDescCrewSize={}", sortedByYearDescCrew.size());

		log.info("==============================================");
		log.info("combinedCreditsCastListSize={}", combinedCreditsCastList.size());
//		log.info("combinedCreditsCastList={}", combinedCreditsCastList);
		log.info("==============================================");
		log.info("combinedCreditsCrewListSize={}", combinedCreditsCrewList.size());
//		log.info("combinedCreditsCrewList={}", combinedCreditsCrewList);

		// combinedCreditsCastList의 사이즈 값 선언.
		Integer combinedCreditsCastListSize = combinedCreditsCastList.size();
		// combinedCreditsCrewList의 사이즈 값 선언.
		Integer combinedCreditsCrewListSize = combinedCreditsCrewList.size();

		/*
		 * KnownCreditsNameOrTitle(참여 작품 수)를 가지는 리스트.
		 * tv면 name, movie면 title을 가짐.
		 * 다른 요소는 갖지 않음.
		 * HashSet을 사용하여 중복을 제거하여 사용.
		 * 중복을 제거한 참여 작품 수를 모델에 전달하기 위함.
		 */
		List<String> knownCreditsNameOrTitle = new ArrayList<>();
		for (int i=0; i<combinedCreditsCastListSize; i++) {
			if (combinedCreditsCastList.get(i).getMediaType() != null && combinedCreditsCastList.get(i).getMediaType().equals("tv")) {
				knownCreditsNameOrTitle.add(combinedCreditsCastList.get(i).getName());
			} else if (combinedCreditsCastList.get(i).getMediaType() != null && combinedCreditsCastList.get(i).getMediaType().equals("movie")) {
				knownCreditsNameOrTitle.add(combinedCreditsCastList.get(i).getTitle());
			} else if (combinedCreditsCastList.get(i) == null) {
				log.info("{}번째 미디어 타입이 null입니다...", i);
			}
		}
		for (int i=0; i<combinedCreditsCrewListSize; i++) {
			if (combinedCreditsCrewList.get(i).getMediaType() != null && combinedCreditsCrewList.get(i).getMediaType().equals("tv")) {
				knownCreditsNameOrTitle.add(combinedCreditsCrewList.get(i).getName());
			} else if (combinedCreditsCrewList.get(i).getMediaType() != null && combinedCreditsCrewList.get(i).getMediaType().equals("movie")) {
				knownCreditsNameOrTitle.add(combinedCreditsCrewList.get(i).getTitle());
			} else if (combinedCreditsCrewList.get(i) == null) {
				log.info("{}번째 미디어 타입이 null입니다...", i);
			}
		}
		// name, title 만을 가지는 것이 아닌 모든 요소를 가지는 knownCreditsAllCast 리스트 생성.
		List<CombinedCreditsCastDto> knownCreditsAllCast = new ArrayList<>(combinedCreditsCastList);
		// name, title 만을 가지는 것이 아닌 모든 요소를 가지는 knownCreditsAllCrew 리스트 생성.
		List<CombinedCreditsCrewDto> knownCreditsAllCrew = new ArrayList<>(combinedCreditsCrewList);

		// *** 이 아래에서 중복을 제거 ***
		// knownCredits 값이 어떻게 저장되어 있는지 확인
		log.info("===============================================");
		log.info("** 중복 제거 전 ** knownCreditsNameOrTitleSize(중복 제거 전 참여 작품 수)={}", knownCreditsNameOrTitle.size());
//		log.info("** 중복 제거 전 ** knownCreditsNameOrTitle(중복 제거 전 참여 작품 리스트) 출력={}", knownCreditsNameOrTitle);
		// HastSet을 사용하여 중복을 제거. (name과 title만을 가지는 리스트를 중복 제거 처리)
		Set<String> uniqueKnownCreditsNameOrTitle = new HashSet<>(knownCreditsNameOrTitle);
		log.info("===============================================");
		log.info("** 중복 제거 후 ** uniqueKnownCreditsNameOrTitleSize(중복 제거 후 참여 작품 수(set))={}", uniqueKnownCreditsNameOrTitle.size());
//		log.info("** 중복 제거 후 ** uniqueKnownCreditsNameOrTitle(중복 제거 후 참여 작품 set)={}", uniqueKnownCreditsNameOrTitle);
		// 증복을 제거한 uniqueKnownCreditsNameOrTitle을 다시 리스트로 변환.
		List<String> uniqueKnownCreditsNameOrTitleList = new ArrayList<>(uniqueKnownCreditsNameOrTitle);
		log.info("===============================================");
		log.info("** 중복 제거 후 ** uniqueKnownCreditsNameOrTitleListSize(중복 제거 후 참여 작품 수(리스트))={}", uniqueKnownCreditsNameOrTitleList.size());
//		log.info("** 중복 제거 후 ** uniqueKnownCreditsNameOrTitleList(중복 제거 후 참여 작품 리스트)={}", uniqueKnownCreditsNameOrTitleList);
		// =============================  구분선  =============================== //
		// HashSet을 사용하여 중복을 제거. (모든 요소를 가지는 리스트를 중복 제거 처리)
		Set<CombinedCreditsCastDto> uniqueKnownCreditsAllCast = new HashSet<>(knownCreditsAllCast);
		Set<CombinedCreditsCrewDto> uniqueKnownCreditsAllCrew = new HashSet<>(knownCreditsAllCrew);
		// 중복을 제거한 모든 요소를 가지는 set을 다시 리스트로 변환.
		List<CombinedCreditsCastDto> uniqueKnownCreditsAllCastList = new ArrayList<>(uniqueKnownCreditsAllCast);
		log.info("===============================================");
		log.info("** 모든 요소를 가지는 Cast 리스트 중복 제거 후 리스트의 크기 ** uniqueKnownCreditsAllCastListSize(중복이 제거된 모든 Cast 요소를 가지는 리스트의 크기)={}", uniqueKnownCreditsAllCastList.size());
//		log.info("** 모든 요소를 가지는 Cast 리스트 중복 제거 후 ** uniqueKnownCreditsAllCastList(중복이 제거된 모든 Cast 요소를 가지는 리스트)={}", uniqueKnownCreditsAllCastList);
		List<CombinedCreditsCrewDto> uniqueKnownCreditsAllCrewList = new ArrayList<>(uniqueKnownCreditsAllCrew);
		log.info("===============================================");
		log.info("** 모든 요소를 가지는 Crew 리스트 중복 제거 후 리스트의 크기 ** uniqueKnownCreditsAllCrewListSize(중복이 제거된 모든 Crew 요소를 가지는 리스트의 크기)={}", uniqueKnownCreditsAllCrewList.size());
//		log.info("** 모든 요소를 가지는 Crew 리스트 중복 제거 후 ** uniqueKnownCreditsAllCrewList(중복이 제거된 모든 Crew 요소를 가지는 리스트)={}", uniqueKnownCreditsAllCrewList);

		// voteCount 를 기준으로 정렬된 castList 를 모델에 추가. (중복 제거 X)
		model.addAttribute("sortedCastList", sortedCastList);
		model.addAttribute("sortedMovieCastList", sortedMovieCastList);
		model.addAttribute("sortedTvCastList", sortedTvCastList);
		// 필터링한 CastList 를 모델에 추가. (중복 제거 O)
		model.addAttribute("uniqueCastList", uniqueCastList);
		// 필터링한 CrewList 를 모델에 추가. (중복 제거 O)
		model.addAttribute("uniqueCrewList", uniqueCrewList);
		// 연도별 내림차순으로 정렬한 Cast 맵을 모델에 추가. (중복 제거 X)
		model.addAttribute("sortedByYearDesc", sortedByYearDescCast);
		// 연도별 내림차순으로 정렬한 Crew 맵을 모델에 추가. (중복 제거 X)
		model.addAttribute("sortedByYearCrew", sortedByYearDescCrew);
		// uniqueKnownCreditsList(중복이 제거된 참여 작품 이름 또는 제목만을 담은 리스트)를 모델에 추가. (중복 제거 O)
		model.addAttribute("uniqueKnownCreditsNameORTitleList", uniqueKnownCreditsNameOrTitleList);


		model.addAttribute("detailsPerson", detailsPersonDto);
		model.addAttribute("externalIDs", externalIDsDto);
		model.addAttribute("movieCredits", movieCreditsDto);
		model.addAttribute("movieCreditsCast", movieCreditsCastDTO);
		model.addAttribute("tvCredits", tvCreditsDto);
		model.addAttribute("tvCreditsCast", tvCreditsCastDTO);
		model.addAttribute("combinedCredits", combinedCreditsDto);
		model.addAttribute("combinedCreditsCastList", combinedCreditsCastList);
		model.addAttribute("combinedCreditsCrewList", combinedCreditsCrewList);

		return "person/details";
	} // end details



}
