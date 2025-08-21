package com.yotsuba.bocchi.security;

import com.yotsuba.bocchi.entity.User;
import com.yotsuba.bocchi.service.UserService;
import com.yotsuba.bocchi.dto.AuthenticatedUserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {
    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/authentication/status")
    public ResponseEntity<?> getStatus() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Object principal = authentication.getPrincipal();

        // フォームログインの場合
        if (principal instanceof MyUserDetails userDetails) {
            return ResponseEntity.ok(
                    new AuthenticatedUserResponse(userDetails.getUsername(), userDetails.getName())
            );
        }

        // OIDC ログイン（Google / LINE）の場合
        if (authentication instanceof OAuth2AuthenticationToken oauthToken &&
                principal instanceof OAuth2User oAuth2User) {

            String registrationId = oauthToken.getAuthorizedClientRegistrationId(); // google / line
            Object subAttr = oAuth2User.getAttribute("sub");
            String sub = subAttr != null ? subAttr.toString() : "";
            Object nameAttr = oAuth2User.getAttributes().getOrDefault("name",
                    oAuth2User.getAttributes().getOrDefault("displayName", "User"));
            String name = nameAttr != null ? nameAttr.toString() : "User";

            // ユーザーIDは "google:xxxx" / "line:xxxx" の形式に
            String appUserId = registrationId + ":" + sub;

            return ResponseEntity.ok(
                    new AuthenticatedUserResponse(appUserId, name)
            );
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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