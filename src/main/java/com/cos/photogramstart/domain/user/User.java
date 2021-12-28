package com.cos.photogramstart.domain.user;

//JPA - Java Persistence API (자바로 데이터를 DB에 영구적으로 저장할 수 있는 API를 제공)

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.subscribe.Subscribe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 비어있는 생성자
@Data // getter, setter 생성
@Entity // 디비에 테이블을 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;

    @Column(length = 20, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;
    private String website; // 웹 사이트
    private String bio; // 자기 소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender; 

    private String profileImageUrl; // 사진
    private String role; // 권한


    //FetchType.Lazy = User를 Select 할 때 해당 userId로 등록 된 image들을 가져오지마 - 대신 getImages() 함수가 호출 될 때 가져와
    //FetchType.Eager = User를 Select 할 때 해당 userId로 등록 된 image들을 전부 Join해서 가져와
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"user"}) // 필드변수 user를 제외하고 JSON으로 파싱
    private List<Image> images;


    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

}
