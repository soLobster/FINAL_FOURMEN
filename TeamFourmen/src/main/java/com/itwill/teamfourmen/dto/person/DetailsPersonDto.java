package com.itwill.teamfourmen.dto.person;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DetailsPersonDto {

    private boolean adult;
    @JsonAlias("also_known_as")
    private List<String> alsoKnownAs;
    private String biography;
    private Date birthday;
    private Date deathday;
    private int gender;
    private String homepage;
    private int id;
    @JsonProperty ("imdb_id") // JSON 키 이름과 정확히 일치시키기 위함
    private String imdbId;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    private String name;
    @JsonProperty("place_of_birth")
    private String placeOfBirth;
    private double popularity;
    @JsonProperty("profile_path")
    private String profilePath;

}
