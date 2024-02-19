package com.itwill.teamfourmen.web;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import com.itwill.teamfourmen.domain.NicknameInterceptor;
import com.itwill.teamfourmen.domain.PhonemessageProvider;
import com.itwill.teamfourmen.dto.MemberCreateDto;
import com.itwill.teamfourmen.dto.MemberCreateNaverDto;
import com.itwill.teamfourmen.dto.MemberModifyDto;
import com.itwill.teamfourmen.dto.loginDto;
import com.itwill.teamfourmen.service.HomeService;
import com.itwill.teamfourmen.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
	private final HomeService homeservice;
	private final EmailProvider emailprovider;
	private final PhonemessageProvider phoneprovider;
	private final MemberService memberservice;
	private final MemberCreateDto membercreateDto;
	private final MemberCreateNaverDto membercreatenaverdto;
	private final PasswordEncoder passwordEncoder;
	private final NicknameInterceptor interceptor;
	
	@GetMapping("/")
	public String home() {
		log.info("HOME()");

		return "index";
	}
	
	@GetMapping("/mypage")
	public void mypage() {
		

		
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
	

	
	
	@PostMapping("login/naver")
	@ResponseBody
	public ResponseEntity<String> postNaverLogin(@RequestBody Map<String, String> naverData) {
		membercreatenaverdto.setEmail(naverData.get("email"));
		membercreatenaverdto.setName(naverData.get("name"));
		membercreatenaverdto.setPassword(naverData.get("password"));
		membercreatenaverdto.setNickname(naverData.get("nickname"));
		membercreatenaverdto.setPhone(naverData.get("phone"));
		membercreatenaverdto.setUsersaveprofile(naverData.get("usersaveprofile"));
		membercreatenaverdto.setType(naverData.get("type"));
	    
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
	
		 	
		 	 @PostMapping("/mypage/update")
		     public String updateUser(@ModelAttribute MemberModifyDto dto) throws IllegalStateException, IOException {
		         // 여기서 비밀번호를 비교하고 처리하면 됩니다.
		 		String sDirectory = "C:/image";
		 		memberservice.update(dto, sDirectory);
		 		
		 		return "redirect:/mypage";
				
	 		
		 	
		 		 
		 	
		     }
		    
		 	@GetMapping("/image")
			public ResponseEntity<Resource> getImage(@RequestParam(name= "photo") String photo) {
				log.info("photo={}",photo);
			    try {
			        // 이미지 파일의 경로를 지정하여 Resource 객체 생성
			        File file = new File("C:/image/" + photo);
			        Resource resource = new FileSystemResource(file);

			        // Resource 객체를 반환하여 이미지 파일을 클라이언트에게 전송
			        return ResponseEntity.ok()
			                .contentType(MediaType.IMAGE_JPEG) // 이미지 형식으로 전달함을 명시
			                .body(resource);
			    } catch (Exception e) {
			        // 이미지 파일이 존재하지 않는 경우 404 에러 반환
			        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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
	
		@GetMapping("/checkpassword/{password}")
	    @ResponseBody
	    public ResponseEntity<String> checkpassword(@PathVariable("password") String password){
			 String orginpassword= interceptor.getMember().getPassword();
			 
		    boolean result = passwordEncoder.matches(password, orginpassword);
		    
		    if (result == true) {
		        // 값이 없는 경우 빈 문자열 반환
		        return ResponseEntity.ok("Y");
		    } else {
		    	return ResponseEntity.ok("N");
		    }
	    }
		
    @PostMapping("/signup")
    public String signup(@ModelAttribute MemberCreateDto dto) {
        log.info("POST - signup(dto={})", dto);
        
        memberservice.createMember(dto);
        
        
        return "redirect:/login";
    }
    
//    @GetMapping("/delete")
//    public String delete(@RequestParam(name = "email") String email) {
//        log.info("delete(id={})", id);
//
//        memberservice.deleteById(id);
//
//        return "redirect:/post/list";
//    }
}
