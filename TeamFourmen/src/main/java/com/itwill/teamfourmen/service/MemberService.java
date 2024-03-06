package com.itwill.teamfourmen.service;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.MemberRepository;
import com.itwill.teamfourmen.domain.MemberRole;
import com.itwill.teamfourmen.domain.MemberSecurityDto;
import com.itwill.teamfourmen.dto.MemberCreateDto;
import com.itwill.teamfourmen.dto.MemberCreateNaverDto;
import com.itwill.teamfourmen.dto.MemberModifyDto;
import com.itwill.teamfourmen.dto.MemberSearchDto;

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
    
    public Page<Member> getmemberlist(int page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by("email").descending());
        Page<Member> data = memberDao.findAll(pageable);
        
        return data;
    };
    
    public Member getMemberByMemberId(Long memberId) {
    	return memberDao.findByMemberId(memberId).orElse(null);
    }
    
    public Member getmemberdetail(String email) {
    	Optional<Member> opt = memberDao.findByEmail(email);
    	Member member = opt.orElse(null);
    	
    	return member;
    };
    
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
    
    @Transactional
    public void deleteByEmail(String email) {
    	
    	  memberDao.findByEmail(email).ifPresent(member -> {
              member.clearRoles(); // 멤버의 모든 역할을 제거합니다.
              memberDao.deleteByEmail(email);
          });
        }

    @Transactional
    //-> 검색한 엔터티의 변화가 생기면 트랜잭션이 종료될 때 update 쿼리가 자동으로 실행.
    public void update(MemberModifyDto dto, String sDirectory) throws IllegalStateException, IOException {
        log.info("update(dto={})", dto);
        List<MultipartFile> files = dto.getUpload_photo();
		log.debug("files={}", files);
		for(MultipartFile file : files) {
		if(!file.isEmpty()) {
		String originalFileName = file.getOriginalFilename();
		
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		String savedFileName = UUID.randomUUID().toString() + extension;
		
		String absolutePath = sDirectory + File.separator + savedFileName;
		log.info("absolutPath={}",absolutePath);
		file.transferTo(new File(absolutePath));
		
		dto.setUsersaveprofile(savedFileName);
		
		
		
		}
		}
		 Member entity = memberDao.findByEmail(dto.getEmail()).orElseThrow();
	        //-> DB에서 저장되어 있는 업데이트 전의 엔터티
		  if (dto.getUsersaveprofile() != null) {
		        entity.update(dto.getEmail(), dto.getName(), dto.getNickname(), dto.getPhone(), dto.getUsersaveprofile());
		    }
		  if (dto.getUsersaveprofile() == null) {
			  entity.updatewithout(dto.getEmail(), dto.getName() ,dto.getNickname(), dto.getPhone());
		  }
	       
	        //-> DB에서 검색한 엔터티의 속성(필드)들의 값을 변경.
	        //-> PostRepository.save 메서드를 호출하지 않음.
	        //-> @Transactional 애너테이션이 있기 때문에 변경 내용이 자동으로 저장됨!
       
    }
    
    public Page<Member> search(MemberSearchDto dto) {
        log.info("search(dto={})", dto);

        Pageable pageable = PageRequest.of(dto.getP(), 10, Sort.by("email").descending());
        
        Page<Member> result = null;
        switch (dto.getCategory()) {
        case "e":
            result = memberDao.findByEmailContainingIgnoreCase(dto.getKeyword(), pageable);
            break;
        case "n":
            result = memberDao.findByNameContainingIgnoreCase(dto.getKeyword(), pageable);
            break;
        case "ni":
            result = memberDao.findByNicknameContainingIgnoreCase(dto.getKeyword(), pageable);
            break;
        case "p":
            result = memberDao.findByPhoneContainingIgnoreCase(dto.getKeyword(), pageable);
            break;
        case "t":
            result = memberDao.findByTypeContainingIgnoreCase(dto.getKeyword(), pageable);
            break;
  
        }
        
        return result;
    }
	
    	

}