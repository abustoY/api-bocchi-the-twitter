package com.yotsuba.bocchi.repository;

import com.yotsuba.bocchi.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Integer>, TweetRepositoryCustom {
}