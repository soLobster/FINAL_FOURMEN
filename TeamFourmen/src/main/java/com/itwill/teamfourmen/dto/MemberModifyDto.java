package com.itwill.teamfourmen.dto;

import org.springframework.stereotype.Component;
import java.util.List;
import com.itwill.teamfourmen.domain.Member;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
@Component
public class MemberModifyDto {
	  private String email;

	    private String name;
	    private String nickname;
	    private String phone;
	    private String usersaveprofile;
	    private List<MultipartFile> upload_photo;
	    
	    public Member toEntity() {
	        return Member.builder()
	                .email(email)
	                .name(name)
	                .nickname(nickname)
	                .phone(phone)
	                .usersaveprofile(usersaveprofile)
	                .build();
	         }
}
