package com.itwill.teamfourmen.domain;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailProvider {

	private final JavaMailSender javaMailsender;
	
	private final String SUBJECT ="[영화사이트 인증 메일 입니다.]";
	
	public boolean sendCertificationMail(String email, String certificationNumber) {
		
		try {
			MimeMessage message = javaMailsender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
			
			String htmlContent = getCertificationMessage(certificationNumber);
			
			messageHelper.setTo(email);
			messageHelper.setSubject(SUBJECT);
			messageHelper.setText(htmlContent, true);
			
			javaMailsender.send(message);
			
		}catch(Exception exception) {
			exception.printStackTrace();
			return false;
		}
		return true;

	}
	
	private String getCertificationMessage(String certificationNumber) {
		String imageUrl = "https://assets.nflxext.com/ffe/siteui/vlv3/4da5d2b1-1b22-498d-90c0-4d86701dffcc/36fcd3f1-f8ca-4eb5-95a8-4e156245cd54/KR-ko-20240129-popsignuptwoweeks-perspective_alpha_website_large.jpg";

		  String certificationMessage = "";
		    certificationMessage += "<div style='background-image: url(" + imageUrl + "); background-size: cover; background-position: center; background-repeat: no-repeat; height: 500px; margin-bottom:100px; filter: grayscale(40%);'>";
		    certificationMessage += "<h1 style='text-align: center; color: lightgray; margin-top: 30px;'>[FOURMEN]</h1>";
		    certificationMessage += "<h1 style='text-align: center; color: lightgray; margin-top: 50px;'>Authentication mail</h1>";
		    certificationMessage += "<h3 style='text-align: center; color: lightgray; margin-top: 20px;'>Please enter the authentication code below when you sign up for membership or find your password!</h3>";
		    certificationMessage += "<h3 style='text-align: center; color: lightgray; margin-top: 20px;'>Authentication code: <strong style='font-size: 32px; letter-spacing: 8px; color:yellow;'>" + certificationNumber +"</strong></h3>";
		    certificationMessage += "<h3 style='text-align: center; color: lightgray; margin-top: 70px; margin-right:100px; margin-left:100px;'>If you register as a member or find a password, you may be subject to criminal punishment if you hack or acquire an authentication code illegally. Please use the authentication code only when registering as a member or finding a password. You may be subject to criminal punishment if you sign up for membership or find a password or acquire an authentication code illegally. Please use the authentication code only when signing up for membership or finding a password. The authentication time is 3 minutes, and if the time exceeds, you must send the authentication code by e-mail again.</h3>";
		    certificationMessage += "</div>";
		    return certificationMessage;
	}
	}
