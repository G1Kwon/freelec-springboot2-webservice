package com.jojoldu.book.springboot.config.auth;

import com.jojoldu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
//Spring Security 설정들을 활성화
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                //아래 옵션들이 없으면 h2 console등에 접속이 불가능하다.
                .and()
                //URL별 권한 관리를 설정하는 옵션의 시작점.authorizeRequests가 선언되어야 antMatcher 옵션을 사용할 수 있다.
                .authorizeRequests()
                //권한 관리 대상을 URL, HTTP METHOD별로 관리가 가능
                //permitAll()은 모든 권한
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                //위에 설정된 값들 이외 나머지 URL들을 나타낸다.
                //authenticated()를 추가하여 나머지 URL들은 모두 인증된 사용자들에게만 허용.
                .anyRequest().authenticated()
                .and()
                .logout()
                //로그아웃시 "/"로 진입
                .logoutSuccessUrl("/")
                .and()
                //OAuth2 로그인 기능에 대한 여러 설정의 진입점.
                .oauth2Login()
                //OAuth2 로그인 성공 이후 사용자 정보를 가져올 떄 설정을 담
                .userInfoEndpoint()
                //소셜 로그인 성공 시 후속 조치를 진행 할 UserService 인터페이스의 구현체를 등록
                //리소스 서버(소셜 서비스)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.
                .userService(customOAuth2UserService);
    }
}