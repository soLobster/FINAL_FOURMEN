package com.itwill.teamfourmen.domain;

import org.springframework.http.ResponseEntity;

public interface EmailRepository {

	ResponseEntity<EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto);
}