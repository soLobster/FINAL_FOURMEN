package com.itwill.teamfourmen.dto.tvshow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvShowQueryParamDTO {

    // list.html 의 filter의 name: 속성과 매핑되는 부분.
    private int page = 1;

    private String listCategory;

    private String sortBy;

    // tv의 방영 상태 코드
    // 0~5 - > [ 'Returning Series', 'Planned', 'In Production' , 'Ended' , 'Canceled' , 'Pilot' ]
    private String with_status;

    private List<Integer> with_genres;

    // ott 선택.
    private List<Integer> with_watch_provider;

    // 원어
    private String with_original_language;

    // search accordion의 검색어
    private String query;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate first_air_date_gte;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate first_air_date_lte;
}
