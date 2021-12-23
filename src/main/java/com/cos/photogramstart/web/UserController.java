package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @GetMapping("/user/{id}")
    public String profile(@PathVariable String id) {
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) { //

        // 1. 추천 @AuthenticationPrincipal : Authentification 객체에 바로 접근 가능하게 해주는 어노테이션
        System.out.println("세션 정보 : " + principalDetails.getUser());

        // 2. 복잡한 방법(추천 x)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails mPrincipalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("직접 찾은 세션 정보 " + mPrincipalDetails.getUser());

        return "user/update";
    }

}
