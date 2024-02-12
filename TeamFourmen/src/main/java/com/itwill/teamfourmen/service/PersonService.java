package com.itwill.teamfourmen.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itwill.teamfourmen.dto.person.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonService {

	@Value("${tmdb.api.key}")
	private String apiKey;
	private static final String POPULAR = "popular";
    private static final String apiUrl = "https://api.themoviedb.org/3";
	private final WebClient webClient;

	@Autowired
	public PersonService(WebClient webClient) {
		this.webClient = webClient;
	}

    /**
     * JSON 데이터를 PageAndListDto 객체로 변환.
     * 파라미터는 "popular"와 페이지네이션을 위한 "page".
     * @param "popular" and "page"(for paging)
     * @return API 요청으로 받아온 JSON 데이터를 매핑한 pageAndListDto 객체.
     */
    public PageAndListDto getPersonList(int page, String language) {

    	// API 요청 주소 생성. (인물의 리스트를 받아옴)
    	String uri = String.format(apiUrl + "/person/" + POPULAR + "?api_key=%s&language=%s&page=%d", apiKey, language, page);

    	PageAndListDto pageAndListDto;
        pageAndListDto = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(PageAndListDto.class)
                .block();

		return pageAndListDto;

	}

	/**
	 * JSON 데이터를 DetailsPersonDto 객체로 변환.
	 * 파라미터는 언어와 인물의 id 값.
	 *
	 * @param "language" and "id"
	 * @return API 요청으로 받아온 JSON 데이터를 매핑한 detailsPersonDto 객체.
	 */
	public DetailsPersonDto getPersonDetails(int id, String language) {

		// API 요청 주소 생성. (각 인물의 상세 정보를 받아옴)
		String uri = String.format(apiUrl + "/person/" + id + "?api_key=%s&language=%s", apiKey, language);

		DetailsPersonDto detailsPersonDto;
		detailsPersonDto = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(DetailsPersonDto.class)
				.block();

		return detailsPersonDto;

	}

	/**
	 * JSON 데이터를 받아 ExternalIDsDto 객체로 변환.
	 * 파라미터는 인물의 id 값.
	 *
	 * @param "id"
	 * @return API 요청으로 받아온 JSON 데이터를 매핑한 externalIDsDto 객체.
	 */
	public ExternalIDsDto getExternalIDs(int id, String language) {

		// API 요청 주소 생성. (각 인물의 SNS, 유튜브, 홈페이지 등의 외부 아이디 정보를 받아옴)
		String uri = String.format(apiUrl + "/person/" + id + "/external_ids" + "?api_key=%s&language=%s", apiKey, language);

		ExternalIDsDto externalIDsDto;
		externalIDsDto = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(ExternalIDsDto.class)
				.block();

		return externalIDsDto;

	}

	/**
	 * JSON 데이터를 받아 MovieCreditsDto 객체로 변환.
	 * 파라미터는 인물의 id와 language 값.
	 *
	 * @param id, language
	 * @return API 요청으로 받아온 JSON 데이터를 매핑한 externalIDsDto 객체.
	 */
	public MovieCreditsDto getMovieCredits(int id, String language) {

		// API 요청 주소 생성. (각 인물의 SNS, 유튜브, 홈페이지 등의 외부 아이디 정보를 받아옴)
		String uri = String.format(apiUrl + "/person/" + id + "/movie_credits" + "?api_key=%s&language=%s", apiKey, language);

		MovieCreditsDto movieCreditsDto;
		movieCreditsDto = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(MovieCreditsDto.class)
				.block();

		return movieCreditsDto;

	}

	public MovieCreditsCastDto getMovieCreditsCast(int id, String language) {

		// API 요청 주소 생성. (각 인물의 SNS, 유튜브, 홈페이지 등의 외부 아이디 정보를 받아옴)
		String uri = String.format(apiUrl + "/person/" + id + "/movie_credits" + "?api_key=%s&language=%s", apiKey, language);

		MovieCreditsCastDto movieCreditsCastDTO;
		movieCreditsCastDTO = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(MovieCreditsCastDto.class)
				.block();

		return movieCreditsCastDTO;

	}

	public TvCreditsDto getTvCredits(int id, String language) {

		// API 요청 주소 생성. (각 인물의 SNS, 유튜브, 홈페이지 등의 외부 아이디 정보를 받아옴)
		String uri = String.format(apiUrl + "/person/" + id + "/tv_credits" + "?api_key=%s&language=%s", apiKey, language);

		TvCreditsDto tvCreditsDto;
		tvCreditsDto = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(TvCreditsDto.class)
				.block();

		return tvCreditsDto;

	}

	public TvCreditsCastDto getTvCreditsCast(int id, String language) {

		// API 요청 주소 생성. (각 인물의 SNS, 유튜브, 홈페이지 등의 외부 아이디 정보를 받아옴)
		String uri = String.format(apiUrl + "/person/" + id + "/tv_credits" + "?api_key=%s&language=%s", apiKey, language);

		TvCreditsCastDto tvCreditsCastDTO;
		tvCreditsCastDTO = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(TvCreditsCastDto.class)
				.block();

		return tvCreditsCastDTO;

	}

	public CombinedCreditsDto getCombinedCredits(int id, String language) {

		// API 요청 주소 생성. (각 인물의 SNS, 유튜브, 홈페이지 등의 외부 아이디 정보를 받아옴)
		String uri = String.format(apiUrl + "/person/" + id + "/combined_credits" + "?api_key=%s&language=%s", apiKey, language);

		CombinedCreditsDto combinedCreditsDto;
		combinedCreditsDto = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(CombinedCreditsDto.class)
				.block();

		return combinedCreditsDto;

	}

	public List<CombinedCreditsCastDto> getCombinedCreditsCast(int id, String language) {

		// API 요청 주소 생성. (각 인물의 SNS, 유튜브, 홈페이지 등의 외부 아이디 정보를 받아옴)
		String uri = String.format(apiUrl + "/person/" + id + "/combined_credits" + "?api_key=%s&language=%s", apiKey, language);

		CombinedCreditsCastDto combinedCreditsCastDto;
		JsonNode node = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(JsonNode.class)
				.block();

		JsonNode castNode = node.get("cast");

		ObjectMapper mapper = new ObjectMapper();

        try {
            CombinedCreditsCastDto[] castArray = mapper.treeToValue(castNode, CombinedCreditsCastDto[].class);
			List<CombinedCreditsCastDto> castList = Arrays.asList(castArray);
			return castList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
			return null;
        }

	}

	public CombinedCreditsCrewDto getCombinedCreditsCrew(int id, String language) {

		// API 요청 주소 생성. (각 인물의 SNS, 유튜브, 홈페이지 등의 외부 아이디 정보를 받아옴)
		String uri = String.format(apiUrl + "/person/" + id + "/combined_credits" + "?api_key=%s&language=%s", apiKey, language);

		CombinedCreditsCrewDto combinedCreditsCrewDto;
		combinedCreditsCrewDto = webClient.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(CombinedCreditsCrewDto.class)
				.block();

		return combinedCreditsCrewDto;

	}

}