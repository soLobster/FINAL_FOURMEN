package com.itwill.teamfourmen.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Basic;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE) @Builder
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity @Table(name = "member")
public class Member {

	    @Id // PK
	    @EqualsAndHashCode.Include
	    private String email;
	    
	    @Basic(optional = false)
	    private String password;
	    
	    @Basic(optional = false)
	    private String name;
	    
	    @Basic(optional = false)
	    private String nickname;
	    
	    @Basic(optional = false)
	    private String phone;
	    
	    @Basic(optional = true)
	    private String type;
	    
	    @CreatedDate
	    private LocalDateTime created_time;
	    
	    @Basic(optional = true)
	    private String usersaveprofile;
	    
	    @Basic(optional = true)
	    private String useruploadprofile;
	    
	    @Builder.Default // Builder에서도 null이 아닌 Set<> 객체를 생성하기 위해서.
	    @ToString.Exclude
	    @ElementCollection(fetch = FetchType.LAZY) // 연관된 별도의 테이블을 사용.
	    @Enumerated(EnumType.STRING) // DB 테이블에 저장할 때 상수 이름(문자열)을 사용.
	    private Set<MemberRole> roles = new HashSet<>();

	    public Member addRole(MemberRole role) {
	        roles.add(role);
	        return this;
	    }
	    
	    public Member clearRoles() {
	        roles.clear();
	        return this;
	    }
	   
}
