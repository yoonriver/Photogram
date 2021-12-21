package com.cos.photogramstart.web;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

// 의존 주입 첫번째 방법 : 롬복을 이용해 final이 붙은 필드의 생성자 생성
@RequiredArgsConstructor
@Controller // 1. IoC에 등록, 2. 파일을 리턴하는 컨트롤러
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

//    // 의존 주입 두번째 방법 : 필드 주입
//    @Autowired
//    private AuthService authService;

//    // 의존 주입 세번째 방법 : 생성자 주입
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }

    @GetMapping("/auth/signin")
    public String signinForm() {
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm() {
        return "auth/signup";
    }

    // 포스트 요청을 받는 @PostMapping
    // 회원가입버튼 -> /auth/signup -> /auth/signin
    @PostMapping("/auth/signup")
    public String signup(SignupDto signupDto) { // key=value (x-www-form-urlencoded)
        logger.info(signupDto.toString()); // 데이터를 잘 받았는지 로그로 출력

        // User <- SignupDto
        User user = signupDto.toEntity();
        logger.info(user.toString());

        User userEntity = authService.회원가입(user);
        System.out.println(userEntity);

        return "auth/signin";
    }
}
