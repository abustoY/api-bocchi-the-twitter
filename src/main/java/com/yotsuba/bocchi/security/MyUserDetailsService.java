package com.yotsuba.bocchi.security;

import com.yotsuba.bocchi.User;
import com.yotsuba.bocchi.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 外部認証IDはフォームログイン不可
        if (username.startsWith("google:") || username.startsWith("line:")) {
            throw new UsernameNotFoundException("外部認証アカウントはフォームログインできません");
        }
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません: " + username));
        return new MyUserDetails(user.getPassword(), user.getId(), user.getName());
    }
}
