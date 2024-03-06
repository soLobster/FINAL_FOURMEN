package com.itwill.teamfourmen.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itwill.teamfourmen.dto.person.KnownForDto;
import lombok.Data;

import java.util.List;

@Data
public class ResultItemDto {

    @JsonProperty("adult")
    private boolean adult;
    @JsonProperty("gender")
    private int gender;
    @JsonProperty("id")
    private int personId;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    @JsonProperty("name")
    private String personName;
    @JsonProperty("original_name")
    private String personOriginalName;
    @JsonProperty("popularity")
    private double personPopularity;
    @JsonProperty("profile_path")
    private String personProfilePath;
    @JsonProperty("known_for")
    private List<KnownForDto> personKnownFor;

}
