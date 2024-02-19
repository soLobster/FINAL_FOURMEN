package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    @GetMapping("/")
    public void mypage() {
    }

    @GetMapping("/details/{id}")
    public String getMyPageDetails(Model model, @PathVariable (name = "id") String email){



        return "mypage/details";
    }

}
