

package com.yotsuba.bocchi.controller;

import com.yotsuba.bocchi.service.UserFollowerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/follow")
public class UserFollowerController {

    private final UserFollowerService userFollowerService;

    public UserFollowerController(UserFollowerService userFollowerService) {
        this.userFollowerService = userFollowerService;
    }

    @PostMapping("/follow")
    public void follow(@RequestParam String userId, @RequestParam String followerId) {
        userFollowerService.follow(userId, followerId);
    }

    @PostMapping("/unfollow")
    public void unfollow(@RequestParam String userId, @RequestParam String followerId) {
        userFollowerService.unfollow(userId, followerId);
    }

    @GetMapping("/following")
    public List<String> getFollowing(@RequestParam String userId) {
        return userFollowerService.getFollowing(userId).stream()
                .map(uf -> uf.getUser().getId())
                .collect(Collectors.toList());
    }

    @GetMapping("/followers")
    public List<String> getFollowers(@RequestParam String userId) {
        return userFollowerService.getFollowers(userId).stream()
                .map(uf -> uf.getFollower().getId())
                .collect(Collectors.toList());
    }
}
