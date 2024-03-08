package com.itwill.teamfourmen.dto.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CombinedCreditsCrewDto {

    private boolean adult;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("genre_ids")
    private List<Integer> genreIds;
    private int id;
    @JsonProperty("origin_country")
    private List<String> originCountry;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("original_name")
    private String originalName;
    private String overview;
    private double popularity;
    @JsonProperty("poster_path")
    private String posterPath;

    private Year year; // 통합된 날짜 필드

    @JsonProperty("first_air_date")
    private String firstAirDate;
    @JsonProperty("release_date")
    private String releaseDate;

    private String title;
    private String name;
    @JsonProperty("video")
    private boolean video;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    @JsonProperty("credit_id")
    private String creditId;
    @JsonProperty("episode_count")
    private int episodeCount;
    private int order;
    private String department;
    private String job;
    @JsonProperty("media_type")
    private String mediaType;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // "release_date"와 "first_air_date"에 대한 커스텀 세터 메서드 수정
    @JsonSetter("release_date")
    public void setReleaseDate(String releaseDateStr) {
        this.year = parseYear(releaseDateStr);
    }

    @JsonSetter("first_air_date")
    public void setFirstAirDate(String firstAirDateStr) {
        this.year = parseYear(firstAirDateStr);
    }

    // 문자열 날짜를 Year 객체로 변환하는 헬퍼 메서드
    private Year parseYear(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            // 날짜 문자열이 null이거나 비어있는 경우, null을 반환
            return null;
        }
        try {
            LocalDate date = LocalDate.parse(dateStr, formatter);
            return Year.of(date.getYear());
        } catch (DateTimeParseException e) {
            // 로깅 또는 사용자 정의 예외 처리
            System.err.println("Failed to parse date: " + dateStr + ", using default year instead.");
            return null;
        }
    }

}
