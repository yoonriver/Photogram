package com.cos.photogramstart.domain.image;

import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String caption; // 글 내용
    private String postImageUrl; // 사진을 전송받아서 그 사진을 서버에 있는 특정 폴더에 저장 - DB에 그 저장된 경로를 Insert

    @JsonIgnoreProperties({"images"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    // 이미지 좋아요

    // 댓글

    private LocalDateTime createDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    // 오브젝트를 콘솔에 출력 할 때 문제가 될 수 있어서 User 부분을 출력되지 않게 함
//    @Override
//    public String toString() {
//        return "Image{" +
//                "id=" + id +
//                ", caption='" + caption + '\'' +
//                ", postImageUrl='" + postImageUrl + '\'' +
//                ", createDate=" + createDate +
//                '}';
//    }
}
