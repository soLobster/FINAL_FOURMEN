package com.itwill.teamfourmen.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.itwill.teamfourmen.domain.Member;

import lombok.Data;

@Data
public class loginDto {
	private String email;
	private String password;
	
	 public Member toEntity(PasswordEncoder encoder) {
	        return Member.builder()
	                .email(email)
	                .password(encoder.encode(password))
	                .build();
	         }
}
