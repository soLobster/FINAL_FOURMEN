package com.itwill.teamfourmen.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itwill.teamfourmen.domain.CertificationNumber;
import com.itwill.teamfourmen.domain.EmailProvider;
import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {
	
	private final EmailProvider emailprovider;
	private final MemberRepository memberdao;
	
	
	public void sendmail() {
		String certificationNumber="";
		
		for(int count =0; count<4; count++) {
			certificationNumber += (int) (Math.random()*10);
			
			
			}
		
		emailprovider.sendCertificationMail("giho5279@naver.com",certificationNumber);
	}
	
    // 아이디로 사용자 찾기
    public Optional<Member> findByemail(String email) {
        return memberdao.findByEmail(email);
    }
	

}
