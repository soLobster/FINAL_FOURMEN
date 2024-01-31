package com.itwill.teamfourmen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itwill.teamfourmen.dto.movie.MovieProviderDto;
import com.itwill.teamfourmen.dto.movie.MovieProviderItemDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovieDetailService {
	
	public List<MovieProviderItemDto> getOrganizedMovieProvider(MovieProviderDto providerDto) {
		
		log.info("getOrganizedMovieProvider(providerDto={})", providerDto);
		
		List<MovieProviderItemDto> organizedProviderList = new ArrayList<>();
		
		organizedProviderList.addAll(providerDto.getRent());
		organizedProviderList.stream().map((x) -> x.getOptions().add("렌트")).toList();
		
		if (providerDto.getBuy() != null) {
			for (MovieProviderItemDto provider : providerDto.getBuy()) {			
				addOrganizedProviderList(organizedProviderList, provider, "구매");			
			}			
		}

		if (providerDto.getFlatrate() != null) {
			for (MovieProviderItemDto provider : providerDto.getFlatrate()) {
				addOrganizedProviderList(organizedProviderList, provider, "스트리밍");
			}			
		}

		if (providerDto.getFree() != null) {
			for (MovieProviderItemDto provider : providerDto.getFree()) {
				addOrganizedProviderList(organizedProviderList, provider, "무료");
			}			
		}

		if (providerDto.getAds() != null) {
			for (MovieProviderItemDto provider : providerDto.getAds()) {
				addOrganizedProviderList(organizedProviderList, provider, "무료(광고 포함)");
			}			
		}		
		
		log.info("organized provider list = {}", organizedProviderList);
		
		return organizedProviderList;
	}
	
	
	public List<MovieProviderItemDto> addOrganizedProviderList(List<MovieProviderItemDto> organizedProviderList
			, MovieProviderItemDto provider, String option) {
		
		boolean isInList = false;
		int index = 0;
		
		for(int i = 0; i < organizedProviderList.size(); i++) {				
			if (organizedProviderList.get(i).getProviderName().equals(provider.getProviderName())) {	// 만약 이미 organized list에 포함돼 있는 경우, isInList = true
				isInList = true;
				index = i;	// organized에 이미 있었던 항목 index
			}
		}
		
		if (isInList) { // 이미 리스트에 있는 경우 그냥 option만 추가해줌
			organizedProviderList.get(index).getOptions().add(option);				
		} else { // 리스트에 없는 경우 추가해줌
			provider.getOptions().add(option);
			organizedProviderList.add(provider);
		}
		
		
		return organizedProviderList;
	}
	
	
}
