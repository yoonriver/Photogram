package com.cos.photogramstart.web.dto.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data // Getter,Setter
public class SignupDto {

    @Size(min = 2, max = 20) // 길이 20 제한
    @NotBlank
    private String username;
    @NotBlank // 빈 공간(스페이스바 포함) X
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();
    }
}
