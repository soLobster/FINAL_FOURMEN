package com.itwill.teamfourmen.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

//(1) UserDetails 인터페이스를 직접 구현하거나
//(2) UserDetials 인터페이스를 구현하는 User 클래스를 상속
public class MemberSecurityDto extends User {

	  private MemberSecurityDto(String email, String password,
	            Collection<? extends GrantedAuthority> authorities) {
	        super(email, password, authorities);
	        //-> 상위 클래스 User는 기본 생성자를 갖고 있지 않기 때문에, 명시적으로 생성자를 호출.

	    }
	  
	  
	    public static MemberSecurityDto fromEntity(Member entity) {
//	        List<SimpleGrantedAuthority> authorities =
//	                entity.getRoles().stream()
//	                .map((x) -> new SimpleGrantedAuthority(x.getAuthority()))
//	                .toList();
	        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	        for (MemberRole role : entity.getRoles()) {
	            SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role.getAuthority());
	            authorities.add(auth);
	        }
	        
	        return new MemberSecurityDto(entity.getEmail(), entity.getPassword(), authorities);
	    }
}
