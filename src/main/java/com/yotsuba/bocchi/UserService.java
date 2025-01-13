package com.yotsuba.bocchi;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void signup(String id,String name,String password){
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }

    public boolean isIdTaken(String id){
        System.out.println(id+"でした");
        System.out.println(userRepository.findById(id)+"だった");
        return userRepository.findById(id).orElse(null) != null;
    }
}
