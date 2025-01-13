package com.yotsuba.bocchi.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserDetails implements UserDetails {
    private String password;
    private String username;

    public MyUserDetails(String password, String username) {
        this.password = password;
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
//        bcrypt kandou
//        return "$2a$08$BdyRw1yKdpg/P3u8ptx7jOnKEID8lUFnW1/j0awhqiv6FyP9xi142";
        return this.password;
    }

    @Override
    public String getUsername() {
//        return "kita194";
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
