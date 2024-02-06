package com.itwill.teamfourmen.dto.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PersonImagesDto {

    private int id;
    private List<ImgDetailsDto> profiles;

}

@Data
class ImgDetailsDto {

    @JsonProperty("aspect_ratio")
    private double aspectRatio;
    private int height;
    private String iso_639_1;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;
    private int width;

}
