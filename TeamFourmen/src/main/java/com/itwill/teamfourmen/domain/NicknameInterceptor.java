package com.itwill.teamfourmen.domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.itwill.teamfourmen.service.HomeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.Principal;
import java.util.Optional;

@Component
public class NicknameInterceptor implements HandlerInterceptor {

    @Autowired
    private HomeService homeservice;
    
    private Member memberInfo;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	if (modelAndView != null) {
            Principal principal = request.getUserPrincipal();
            String nickname = "Guest"; // 기본값 설정

            if (principal != null) {
                String username = principal.getName();
                Optional<Member> optionalMember = homeservice.findByemail(username);
                if (optionalMember.isPresent()) {
                    memberInfo = optionalMember.get(); // memberInfo에 값을 할당
                    nickname = memberInfo.getNickname();
                }
            }

            modelAndView.addObject("nickname", nickname);
            modelAndView.addObject("member", memberInfo);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
    
    public Member getMember() {
        return memberInfo;
    }
}