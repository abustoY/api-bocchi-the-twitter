package com.yotsuba.bocchi.security;

import com.yotsuba.bocchi.User;
import com.yotsuba.bocchi.UserService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> signup(@RequestBody User user) {
        try {
            if (user.getId() == null || user.getId().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("IDは必須です");
            }
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("名前は必須です");
            }
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("パスワードは必須です");
            }

            if (userService.isIdTaken(user.getId())) {
                return ResponseEntity.badRequest().body("このIDは既に使用されています");
            }

            userService.signup(user.getId(), user.getName(), user.getPassword());
            return ResponseEntity.ok("ユーザー登録が完了しました");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("サーバーエラー: " + e.getMessage());
        }
    }
}
