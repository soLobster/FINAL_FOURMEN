package com.itwill.teamfourmen.dto;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.itwill.teamfourmen.domain.Member;

import lombok.Data;

@Data
@Component
public class MemberCreateNaverDto {
	  private String email;
	    private String password;
	    private String name;
	    private String nickname;
	    private String phone;
	    private String usersaveprofile;
	    private String type;
	    
	    public Member createnaver(PasswordEncoder encoder) {
	        return Member.builder()
	                .email(email)
	                .password(encoder.encode(password))
	                .name(name)
	                .nickname(nickname)
	                .phone(phone)
	                .type(type)
	                .usersaveprofile(usersaveprofile)
	                .build();
	         }
	    
	  
		    
		    
}
