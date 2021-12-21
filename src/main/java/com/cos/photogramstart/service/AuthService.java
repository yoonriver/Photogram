package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor // Lombok을 이용한 생성자 의존주입
@Service // 1.IoC 2. 트랜잭션 관리
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // BCrypt 엔코더 사용

    @Transactional // Write(Insert, Update, Delete) 할 때마다 트랜잭션으로 관리
    public User 회원가입(User user) {
        // 회원가입 진행
        String rawPassword = user.getPassword(); // 암호화되지 않은 원본 비밀번호를 변수에 저장
        String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 비밀번호를 '해시'로 암호화
        user.setPassword(encPassword); // user에 암호화 된 비밀번호 저장
        user.setRole("ROLE_USER"); // 관리자 : ROLE_ADMIN
        User userEntity = userRepository.save(user);

        return userEntity;
    }
}
