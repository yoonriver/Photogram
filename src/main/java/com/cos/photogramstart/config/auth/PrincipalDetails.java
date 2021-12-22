package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails{

    private static final long serialVersionUID=1L;

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> {return user.getRole();});

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    // 계정이 만료되지 않았는가?
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    // 계정이 잠기지 않았는가?
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    // 계정의 패스워드가 만료되지 않았는가?
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    // 계정이 사용 가능한가?
    public boolean isEnabled() {
        return true;
    }
}
