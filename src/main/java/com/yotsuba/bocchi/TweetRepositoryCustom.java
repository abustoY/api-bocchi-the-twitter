package com.yotsuba.bocchi;
import java.util.List;

public interface TweetRepositoryCustom {
    List<Tweet> findAllByOrderByCreatedDesc(int offset, int size);
    List<Tweet> findByUser_IdOrderByCreatedDesc(String userId, int offset, int size);
    List<Tweet> findByUser_IdInOrderByCreatedDesc(List<String> userIds, int offset, int size);
}