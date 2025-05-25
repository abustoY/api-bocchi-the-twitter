package com.yotsuba.bocchi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {
    boolean existsByUserAndFollower(User user, User follower);
    List<UserFollower> findByUser(User user);       // フォロワー一覧
    List<UserFollower> findByFollower(User follower); // フォローしている一覧
    long countByUser(User user);                    // フォロワー数
    long countByFollower(User follower);            // フォロー数
}