package com.yotsuba.bocchi;

import com.yotsuba.bocchi.dto.TweetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {
    @Query(value = "SELECT new com.yotsuba.bocchi.dto.TweetDto(t.id,t.user.name,t.text,t.created)" + "FROM Tweet AS t ORDER BY t.created ASC")
    List<TweetDto> findAllTweetSummary();

    @Query(value = "SELECT new com.yotsuba.bocchi.dto.TweetDto(t.id,t.user.name, t.text, t.created) " +
            "FROM Tweet AS t " +
            "WHERE t.user.id = :user_id " +
            "ORDER BY t.created ASC")
    List<TweetDto> findUserAllTweetSummary(@Param("user_id")String userId);


}
