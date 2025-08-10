package com.yotsuba.bocchi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Integer>, TweetRepositoryCustom {
}