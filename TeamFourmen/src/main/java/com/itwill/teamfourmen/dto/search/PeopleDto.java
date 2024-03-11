package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itwill.teamfourmen.dto.person.KnownForDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
public class PeopleDto {

    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("gender")
    private int gender; // 인물의 성별

    @JsonProperty("id")
    private int id; // 인물의 아이디

    @JsonProperty("known_for_department")
    private String knownForDepartment; // 인물의 유명 분야

    @JsonProperty("name")
    private String name; // 인물의 이름

    @JsonProperty("original_name")
    private String originalName; // 인물의 오리지널 이름

    @JsonProperty("popularity")
    private double popularity; // 인물의 인기도

    @JsonProperty("profile_path")
    private String profilePath; // 인물의 프로필 이미지

    @JsonProperty("known_for")
    private List<KnownForDto> knownFor; // 인물의 대표작 3개

}
