package com.yotsuba.bocchi.security;

import com.yotsuba.bocchi.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Map;
import java.util.UUID;

@Service
public class CustomOidcUserService extends OidcUserService {
    private static final Logger log = LoggerFactory.getLogger(CustomOidcUserService.class);

    private final UserService userService;

    public CustomOidcUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "google" or "line"
        String sub = oidcUser.getSubject();
        Map<String, Object> claims = oidcUser.getClaims();
        String name = String.valueOf(claims.getOrDefault("name", claims.getOrDefault("displayName", "User")));

        String appUserId = registrationId + ":" + sub;

        try {
            boolean exists = userService.isIdTaken(appUserId);
            log.info("[OIDC] user exists? {} (appUserId={})", exists, appUserId);
            if (!exists) {
                userService.signup(appUserId, name, UUID.randomUUID().toString());
                log.info("[OIDC] signup done: {}", appUserId);
            }
        } catch (Exception e) {
            log.error("[OIDC] signup flow error for {}: {}", appUserId, e.getMessage(), e);
        }

        return oidcUser;
    }
}