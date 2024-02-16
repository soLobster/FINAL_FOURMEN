package com.itwill.teamfourmen.dto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.itwill.teamfourmen.domain.Member;

import lombok.Data;

@Data
@Component
public class MemberCreateDto {
	  private String email;
	    private String password;
	    private String name;
	    private String nickname;
	    private String phone;
	    
	    
	    public Member toEntity(PasswordEncoder encoder) {
	        return Member.builder()
	                .email(email)
	                .password(encoder.encode(password))
	                .name(name)
	                .nickname(nickname)
	                .phone(phone)
	                .type("web")
	                .usersaveprofile("userimage.png")
	                .build();
	         }
	    
	    public Member createkakao (String email, String password, String name, String nickname, String phone) {
	    	return Member.builder().email(email).password(password).name(name).nickname(nickname).phone(phone).build();
	    	
	    }
	    
}
