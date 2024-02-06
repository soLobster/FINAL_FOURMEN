package com.itwill.teamfourmen.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itwill.teamfourmen.domain.CertificationNumber;
import com.itwill.teamfourmen.domain.EmailProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeService {
	
	private final EmailProvider emailprovider;
	
	public void sendmail() {
		String certificationNumber="";
		
		for(int count =0; count<4; count++) {
			certificationNumber += (int) (Math.random()*10);
			
			
			}
		
		emailprovider.sendCertificationMail("giho5279@naver.com",certificationNumber);
	}
	
	
	
}
