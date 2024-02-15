package com.itwill.teamfourmen.dto.person;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonPagingDto {

    private int startPage; // 페이지 시작 번호.
    private int endPage; // 페이지 끝 번호.
    private int totalPage; // 페이지 총 개수.
    private int pagesShowInBar; // 페이징 단위(페이징 바에 얼마큼씩/몇 개씩 보여줄 건지 설정)

}