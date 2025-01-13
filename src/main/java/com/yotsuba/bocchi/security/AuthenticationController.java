package com.yotsuba.bocchi.security;

import com.yotsuba.bocchi.User;
import com.yotsuba.bocchi.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/authentication/status")
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        System.out.println(authentication.getPrincipal());

        if (authentication.getPrincipal() != "anonymousUser") {
            System.out.println((UserDetails) authentication.getPrincipal());
            System.out.println(((UserDetails) authentication.getPrincipal()).getUsername());
            return true;
        }

        return false;
    }

    @GetMapping("/api/authentication/user-id")
    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    @PostMapping("/api/authentication/signup")
    public void signup(@RequestBody User user){
        if (!userService.isIdTaken(user.getId())){
            userService.signup(user.getId(),user.getName(),user.getPassword());
        }
        else {
            throw new RuntimeException("id already taken");
        }
    }
}
