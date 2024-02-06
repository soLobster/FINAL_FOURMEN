package com.itwill.teamfourmen.web;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itwill.teamfourmen.domain.EmailProvider;
import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.PhonemessageProvider;
import com.itwill.teamfourmen.dto.MemberCreateDto;
import com.itwill.teamfourmen.dto.MemberCreateNaverDto;
import com.itwill.teamfourmen.dto.loginDto;
import com.itwill.teamfourmen.service.HomeService;
import com.itwill.teamfourmen.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
	private HomeService homeservice;
	private final EmailProvider emailprovider;
	private final PhonemessageProvider phoneprovider;
	private final MemberService memberservice;
	private final MemberCreateDto membercreateDto;
	private final MemberCreateNaverDto membercreatenaverdto;

	
	@GetMapping("/")
	public String home() {
		log.info("HOME()");

		return "index";
	}

	@GetMapping("/login")
	public void login() {
		log.info("HOME()");

	        

	}

	@GetMapping("/login/email/{email}")
	@ResponseBody
	public ResponseEntity<String> checkNickname(@PathVariable("email") String email) {
		log.info(email);
		String certificationNumber = "";

		for (int count = 0; count < 4; count++) {
			certificationNumber += (int) (Math.random() * 10);

		}

		log.info(certificationNumber);

		boolean result = emailprovider.sendCertificationMail(email, certificationNumber);

		if (result) {
			return ResponseEntity.ok(certificationNumber);
		} else {
			return ResponseEntity.ok(certificationNumber);
		}
	}
	
	@GetMapping("/login/phone/{phone}")
	@ResponseBody
	public ResponseEntity<String> sendSmsToFindEmail(@PathVariable("phone") String phone) {
		
		String certificationNumber = "";

		for (int count = 0; count < 4; count++) {
			certificationNumber += (int) (Math.random() * 10);

		}
		boolean result = phoneprovider.sendCertificationphone(phone, certificationNumber);

		if (result) {
			return ResponseEntity.ok(certificationNumber);
		} else {
			return ResponseEntity.ok(certificationNumber);
		}
    }
	
	@GetMapping("login/kakao/{email}/{name}/{password}/{nickname}/{phone}")
	@ResponseBody
	public ResponseEntity<String> getAllSeats(
	        @PathVariable("email") String email,
	        @PathVariable("name") String name,
	        @PathVariable("password") String password,
	        @PathVariable("nickname") String nickname,
	        @PathVariable("phone") String phone
	        ) {

	   
	    
	    String result= memberservice.createkakao(email, password, name, nickname, phone);
	    log.debug("result={}",result);
	    
	    if (result.equals("Y")) {
	        // 값이 없는 경우 빈 문자열 반환
	        return ResponseEntity.ok("good");
	    } else {
	    	return null;
	    }
	}
	
	
	@PostMapping("login/naver")
	@ResponseBody
	public ResponseEntity<String> postNaverLogin(@RequestBody Map<String, String> naverData) {
		membercreatenaverdto.setEmail(naverData.get("email"));
		membercreatenaverdto.setName(naverData.get("name"));
		membercreatenaverdto.setPassword(naverData.get("password"));
		membercreatenaverdto.setNickname(naverData.get("nickname"));
		membercreatenaverdto.setPhone(naverData.get("phone"));
		membercreatenaverdto.setUsersaveprofile(naverData.get("usersaveprofile"));

	    
	    String result =memberservice.createnaver(membercreatenaverdto);
	    log.debug("result={}",result);
	    
	    if (result.equals("Y")) {
	        // 값이 없는 경우 빈 문자열 반환
	        return ResponseEntity.ok("good");
	    } else {
	    	return null;
	    }
	}
	
	
	
	
		@GetMapping("/login/checknickname/{nickname}")
	    @ResponseBody
	    public ResponseEntity<String> checknamenick(@PathVariable("nickname") String nickname){
	    	boolean result = memberservice.checkNickname(nickname);
	    	if(result) {
	    		return ResponseEntity.ok("Y");
	    	} else {
	    		return ResponseEntity.ok("N");
	    	}
	    }
	
		
		@GetMapping("/login/checkemail/{email}")
	    @ResponseBody
	    public ResponseEntity<String> checkemail(@PathVariable("email") String email){
	    	boolean result = memberservice.checkEmail(email);
	    	if(result) {
	    		return ResponseEntity.ok("Y");
	    	} else {
	    		return ResponseEntity.ok("N");
	    	}
	    }
		
		@GetMapping("/login/checkphone/{phone}")
	    @ResponseBody
	    public ResponseEntity<String> checkphone(@PathVariable("phone") String phone){
	    	boolean result = memberservice.checkPhone(phone);
	    	if(result) {
	    		return ResponseEntity.ok("Y");
	    	} else {
	    		return ResponseEntity.ok("N");
	    	}
	    }
		
		@GetMapping("/login/findemail/{name}/{phone}")
	    @ResponseBody
	    public ResponseEntity<String> findemail(@PathVariable("name") String name, @PathVariable("phone") String phone){
	    	Member result = memberservice.findEmail(name,phone);
		    log.debug("result={}",result);
		    
		    if (result == null) {
		        // 값이 없는 경우 빈 문자열 반환
		        return ResponseEntity.ok("");
		    } else {
		        // 값이 있는 경우 첫 번째 결과의 reserveseat 반환
		        log.debug("result={}", result.getEmail());
		        return ResponseEntity.ok(result.getEmail());
		    }
	    }
		
		@GetMapping("/login/findpassword/{email}/{name}")
	    @ResponseBody
	    public ResponseEntity<String> findpassword(@PathVariable("email") String email, @PathVariable("name") String name){
	    	Member result = memberservice.findPassword(email, name);
		    log.debug("result={}",result);
		    
		    if (result == null) {
		        // 값이 없는 경우 빈 문자열 반환
		        return ResponseEntity.ok("");
		    } else {
		        // 값이 있는 경우 첫 번째 결과의 reserveseat 반환
		        log.debug("result={}", result);
		        return ResponseEntity.ok(result.getEmail());
		    }
	    }
	
	
		@GetMapping("/login/findpassword/{email}/{name}/{password}")
	    @ResponseBody
	    public ResponseEntity<String> changepassword(@PathVariable("email") String email, @PathVariable("name") String name, @PathVariable("password") String password){
	    	Member result = memberservice.changePassword(email, name,password);
		    log.debug("result={}",result);
		    
		    if (result == null) {
		        // 값이 없는 경우 빈 문자열 반환
		        return ResponseEntity.ok("");
		    } else {
		        // 값이 있는 경우 첫 번째 결과의 reserveseat 반환
		        log.debug("result={}", result);
		        return ResponseEntity.ok(result.getPassword());
		    }
	    }
	
		
		
		
    @PostMapping("/signup")
    public String signup(@ModelAttribute MemberCreateDto dto) {
        log.info("POST - signup(dto={})", dto);
        
        memberservice.createMember(dto);
        
        
        return "redirect:/login";
    }
    

}
