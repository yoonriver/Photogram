package com.cos.photogramstart.domain.user;

//JPA - Java Persistence API (자바로 데이터를 DB에 영구적으로 저장할 수 있는 API를 제공)

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 비어있는 생성자
@Data // getter, setter 생성
@Entity // 디비에 테이블을 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    private String name;
    private String website; // 웹 사이트
    private String bio; // 자기 소개
    private String email;
    private String phone;
    private String gender; 

    private String profileImageUrl; // 사진
    private String role; // 권한
    
    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
