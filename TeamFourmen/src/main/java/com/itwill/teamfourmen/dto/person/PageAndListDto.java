package com.itwill.teamfourmen.dto.person;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@Data
public class PageAndListDto {
	
	private int page;
	private List<PopularPersonDto> results;
	private int total_pages;
	private int total_results;
	private int startPage;
	private int endPage;
	private int pagesShownInBar;
	
	
	public static PageAndListDto getPagingDto(int page, int totElements, int totPages, int pagesShownInBar, int postsPerPage) {
		log.info("getPagingDto(page={})", page);
		
		log.debug("paging(page={})", page);
		
		if (totPages == 0) {
			totPages = 1;
		}
		
		page++;	// 0페이지부터 시작하기 때문에 1더해줌..
		
		int startPage = (int) Math.ceil( ((double) page / pagesShownInBar) - 1 ) * pagesShownInBar + 1 ;
			
		int endPage = 0;
		
		if ((startPage + pagesShownInBar - 1) >= totPages) {
			endPage = totPages;
		} else {
			endPage = startPage + pagesShownInBar - 1;
		};
		
		PageAndListDto pagingDto = PageAndListDto.builder().page(page).startPage(startPage).endPage(endPage).total_pages(totPages)
			.pagesShownInBar(pagesShownInBar).total_results(totElements).build();						
		
		return pagingDto;
	}
	
}
