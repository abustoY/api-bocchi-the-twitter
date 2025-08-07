package com.yotsuba.bocchi;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@DBRider
@SpringBootTest
@DataSet("datasets/tweets.yml")
public class TweetRepositoryTest {

    @Autowired
    TweetRepository tweetRepository;

    @Test
    void testFindAllByOrderByCreatedDesc() {
        List<Tweet> result = tweetRepository.findAllByOrderByCreatedDesc();

        assertThat(result)
            .extracting(
                Tweet::getId,
                tweet -> tweet.getUser().getId(),
                Tweet::getText,
                Tweet::getCreated
            )
            .containsExactly(
                tuple(5, "user1", "5番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 14:00:00")),
                tuple(4, "user1", "4番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 13:00:00")),
                tuple(3, "user1", "3番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 12:00:00")),
                tuple(2, "user1", "2番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 11:00:00")),
                tuple(1, "user1", "最初のツイート", java.sql.Timestamp.valueOf("2024-01-01 10:00:00"))
            );
    }
}