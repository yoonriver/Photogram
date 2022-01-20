package com.cos.photogramstart.config;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // IoC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final OAuth2DetailsService oAuth2DetailsService;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨.

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**", "/api/**")
                .authenticated()
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/auth/signin") // antMatchers() 괄호 안에 페이지를 요청할 때 인증이 안되어있으면 GET 방식으로 요청
                .loginProcessingUrl("/auth/signin") // POST 방식으로 요청(login 시도) 되면 스프링 시큐리티가 로그인 프로세스 진행
                .defaultSuccessUrl("/");
//                .and()
//                .oauth2Login() // form 로그인도 하는데, oauth2 로그인도 허용
//                .userInfoEndpoint() // oauth2 로그인을 하면 최종 응답으로 회원 정보를 바로 받을 수 있다.
//                .userService(oAuth2DetailsService); // 그 정보를 oAuth2DetailsService에서 처리하겠다.
    }
}
