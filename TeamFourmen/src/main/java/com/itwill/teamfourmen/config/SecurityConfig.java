package com.itwill.teamfourmen.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

import org.springframework.security.web.SecurityFilterChain;



import lombok.RequiredArgsConstructor;


// Spring 컨테이너에서 Bean(객체)을 생성, 관리 -> 필요한 곳에 DI (의존성 주입)
// Component의 자식임
@Configuration
@EnableMethodSecurity // 컨트롤러 메서드 애너테이션을 사용한 권한 부여, 인증 활성화
@RequiredArgsConstructor
@Configurable
public class SecurityConfig {
    


	
    // Spring security 5 버전부터 비밀번호는 반드시 암호화를 해야 함.
    // 비밀번호를 암호화하지 않으면 HTTP 403(access denied, 접근 거부)
    // HTTP 500 (내부 서버 오류, internal server error) 에러가 발생함.
    // 비밀번호 암호화를 할 수 있는 객체를 스프링 컨테이너가 bean으로 관리해야 함.
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 국룰 리턴
    }
    
    // 사용자 관리(로그인, 로그아웃, 회원 가입 등)를 위한 서비스 인터페이스.
    // 스프링 부트 애플리케이션에서 스프링 시큐리티를 이용한 로그인/로그아웃을 하려면
    // UserDetailsService 인터페이스를 구현하는 서비스 클래스 와
    // UserDetails 인터페이스를 구현하는 엔터티 클래스가 있어야 함.
    /*
    @Bean
    public UserDetailsService inMemoryUserDetailsService() {
        // 메모리에 임시 저장하는 사용자 객체를 생성.
        
        UserDetails user1 = User.withUsername("user1") // 아이디
                .password(passwordEncoder().encode("1111"))  // 패스워드 암호화
                .roles("USER") // 사용자 권한 (ADMIN, USER)
                .build();
        
        UserDetails user2 = User.withUsername("user2")
                .password(passwordEncoder().encode("2222"))
                .roles("ADMIN", "USER")
                .build();
        
        UserDetails user3 = User.withUsername("user3")
                .password(passwordEncoder().encode("3333"))
                .roles("ADMIN")
                .build();
        
        
        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
    */
    // 스프링 시큐리티 필터 체인 객체 (bean)
    // 로그인/ 로그아웃 관련 설정.
    // 로그인 페이지 (뷰), 로그 아웃 페이지
    // 페이지 접근 권한, 인증 설정. (로그인 없이 접근 가능한 페이지/로그인해야만 접근 가능한 페이지)
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 기능을 활성화한 경우 
        // Ajax Post/Put/Delete 요청에서 csrf 토큰을 서버로 전송하지 않으면 HTTP 403 에러가 발생.
        // -> CSRF(Cross Site Request Forgery) 비활성화
        http.csrf((csrf) -> csrf.disable());
        
        // 로그인 페이지(뷰) 설정 - 스프링 시큐리티에서 제공하는 기본 페이지를 이용.
        // http.formLogin(Customizer.withDefaults());
        
        // 로그인 (폼) 페이지를 Custom 페이지(우리가 작성하는 페이지)로 설정.
        http.formLogin((x) -> 
        		x.loginPage("/login")
        		  .loginProcessingUrl("/login")); // 로그인 할 페이지를 설정
        
           
        // 로그아웃 이후에 이동할 페이지 설정 - 홈 페이지(/)
//        http.logout((logout) -> logout.logoutSuccessUrl("/"));
//        http
//        .authorizeHttpRequests(request -> request
//            .requestMatchers("/", "/index","/login", "/oauth2/**").permitAll()
//            .anyRequest().authenticated())
//        .oauth2Login(oauth2 -> oauth2
//        		.loginPage("/signin")
//        	.authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/oauth2"))
//            .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
//            .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
//            .successHandler(oAuth2successhandler)
//        		);
        // 페이지 접근 권한, 인증 설정
        // 1. authorizeHttpRequests() 메서드에서 직접 설정하는 방법.
        // -> 단점: 새로운 요청 경로가 생길때 마다 설정 코드(requestMatchers())를 변경.
        
        // 2. 애너테이션을 사용해서 인증 권한, 인증 설정
        //  1) SecurityConfig 빈에서 @EnableMethodSecurity 애너테이션을 사용.
        //  2) 각각의 컨트롤러 메서드에서 @PreAuthorize, @PostAuthorize 애너테이션을 사용.
        
        /*
        http.authorizeHttpRequests((auth) -> 
            //auth.anyRequest().authenticated() 모든 요청주소에 대해서 (role에 상관없이) 아이디/비밀번호
            //auth.anyRequest().hasRole("USER") // 모든 요청 주소에 대해서 USER 권한을 가진 아이디/비밀번호 로그인.
            
            // 페이지마다 로그인 필요한 페이지와 그렇지 않은 페이지 구분해서 설정: 
            auth
                .requestMatchers("/post/create", "/post/details/**", "/post/update/**", "/post/delete/**", "/api/comment/**") // 매칭되는 페이지 PathVariable, QueryString 주의...ㅎ
                .hasRole("USER") // 가능한 역할
                .anyRequest() // 위에서 매칭한 페이지를 제외한 리퀘스트는
                .permitAll() // 전부 승인 
                // 설정 순서가 중요하다.
        
        );
        */
        
        return http.build();
    }
}
