package com.itwill.teamfourmen.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.MemberRepository;
import com.itwill.teamfourmen.domain.MemberRole;
import com.itwill.teamfourmen.domain.MemberSecurityDto;
import com.itwill.teamfourmen.dto.MemberCreateDto;
import com.itwill.teamfourmen.dto.MemberCreateNaverDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberDao;
    private final MemberCreateDto MemberCreateDto;
    private final MemberCreateNaverDto MemberCreatenaverDto;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // MemberRepository의 메서드를 호출해서 username이 일치하는 사용자 정보가 있는 지를
        // 리턴. 만약 사용자 정보가 없으면 exception을 던짐.
        log.info("loadUserByUsername(username={})", email);
        
        Optional<Member> opt = memberDao.findByEmail(email);
        if (opt.isPresent()) {
            return MemberSecurityDto.fromEntity(opt.get());
        } else {
            throw new UsernameNotFoundException(email + " 찾을 수 없음!");
        }
        
    }

    public void createMember(MemberCreateDto dto) {
        log.info("crateMember(dto={})", dto);
        
        Member entity = dto.toEntity(passwordEncoder);
        entity.addRole(MemberRole.USER);
        
        memberDao.save(entity); //-> insert into members/member_roles
    }
    
    
    public String createkakao(String email, String password, String name, String nickname, String phone) {
        
        Member entity = MemberCreateDto.createkakao(email, password, name, nickname, phone);
        
        
        Member member = memberDao.save(entity); //-> insert into members/member_roles
        log.info("memberDao(memeber={})",member);
        if(member != null) {
        	return "Y"; 
        } else {
        	return "N";
        }
        
        
    }
    
    public String createnaver(MemberCreateNaverDto dto) {
        
        Member entity = dto.createnaver(passwordEncoder);
        entity.addRole(MemberRole.USER);
        
        Member member = memberDao.save(entity); //-> insert into members/member_roles
        log.info("memberDao(memeber={})",member);
        if(member != null) {
        	return "Y"; 
        } else {
        	return "N";
        }
    }
    
    public boolean checkNickname(String Nickname) {
		Member member= memberDao.findByNickname(Nickname);
		if(member == null) {
			return true;
		} else {
			return false;
		}
	}
    
    public boolean checkPhone(String phone) {
		Member member= memberDao.findByPhone(phone);
		log.info("member={}",member);
		if(member == null) {
			return true;
		} else {
			return false;
		}
	}
    
    public boolean checkEmail(String email) {
    	Optional<Member> opt = memberDao.findByEmail(email);
    	
		if(opt.isPresent()) {
			return false;
		} else {
			return true;
		}
	}
    
    public Member findEmail(String name, String phone) {
    	Member result = memberDao.findByNameAndPhone(name, phone);
    	log.info("findemailil(memeber={})",result);
    	return result;
    }
    
    public Member findPassword(String email, String name) {
    	Member result = memberDao.findByEmailAndName(email, name);
    	return result;
    }
    
    public Member changePassword(String email, String name, String password) {
    	Member result = memberDao.findByEmailAndName(email, name);
    	
      	if (result != null) {
            // 패스워드 인코딩
             
             result.setPassword(passwordEncoder.encode(password));
             memberDao.save(result);
             return result ;
        }else {

        return null;
    }
     	
   }

    	
    
    	

}