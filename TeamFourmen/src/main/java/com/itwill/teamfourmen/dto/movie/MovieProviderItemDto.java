package com.itwill.teamfourmen.dto.movie;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieProviderItemDto {
	
	private String logoPath;
	private int providerId;
	private String providerName;
	private int displayPriority;
	
	// 나중에 어떤 옵션(buy, rent 등) 이 있었는지 표시하기 위해서..
	private List<String> options = new ArrayList<>();
	
}
