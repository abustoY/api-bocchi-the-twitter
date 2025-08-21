package com.yotsuba.bocchi.repository;
import com.yotsuba.bocchi.entity.Tweet;

import java.util.List;

public interface TweetRepositoryCustom {
    List<Tweet> findAllByOrderByCreatedDesc(int offset, int size);
    List<Tweet> findByUser_IdOrderByCreatedDesc(String userId, int offset, int size);
    List<Tweet> findByUser_IdInOrderByCreatedDesc(List<String> userIds, int offset, int size);
}