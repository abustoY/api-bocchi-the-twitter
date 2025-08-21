package com.yotsuba.bocchi.service;

import com.yotsuba.bocchi.entity.User;
import com.yotsuba.bocchi.entity.UserFollower;
import com.yotsuba.bocchi.repository.UserFollowerRepository;
import com.yotsuba.bocchi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserFollowerService {
    private final UserRepository userRepository;
    private final UserFollowerRepository userFollowerRepository;

    public UserFollowerService(UserRepository userRepository, UserFollowerRepository userFollowerRepository) {
        this.userRepository = userRepository;
        this.userFollowerRepository = userFollowerRepository;
    }

    @Transactional
    public void follow(String userId, String followerId) {
        if (userId.equals(followerId)) {
            throw new IllegalArgumentException("自分自身をフォローすることはできません");
        }

        User user = userRepository.findById(userId).orElseThrow();
        User follower = userRepository.findById(followerId).orElseThrow();

        if (!userFollowerRepository.existsByUserAndFollower(user, follower)) {
            UserFollower relation = new UserFollower();
            relation.setUser(user);
            relation.setFollower(follower);
            userFollowerRepository.save(relation);
        }
    }

    @Transactional
    public void unfollow(String userId, String followerId) {
        User user = userRepository.findById(userId).orElseThrow();
        User follower = userRepository.findById(followerId).orElseThrow();

        userFollowerRepository.findAll().stream()
            .filter(f -> f.getUser().equals(user) && f.getFollower().equals(follower))
            .findFirst()
            .ifPresent(userFollowerRepository::delete);
    }

    public List<UserFollower> getFollowers(String userId) {
        return userRepository.findById(userId)
            .map(userFollowerRepository::findByUser)
            .orElse(List.of());
    }

    public List<UserFollower> getFollowing(String userId) {
        return userRepository.findById(userId)
            .map(userFollowerRepository::findByFollower)
            .orElse(List.of());
    }
}