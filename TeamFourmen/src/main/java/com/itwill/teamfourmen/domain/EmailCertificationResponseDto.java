package com.itwill.teamfourmen.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class EmailCertificationResponseDto {
	
	private EmailCertificationResponseDto() {
		super();
	}
	
	public static ResponseEntity<EmailCertificationResponseDto> success(){
		EmailCertificationResponseDto responseBody = new EmailCertificationResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}

}
