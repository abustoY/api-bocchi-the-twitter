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
        List<Tweet> result = tweetRepository.findAllByOrderByCreatedDesc(0, 100);

        assertThat(result)
            .extracting(
                Tweet::getId,
                tweet -> tweet.getUser().getId(),
                Tweet::getText,
                Tweet::getCreated
            )
            .containsExactly(
                tuple(7, "user2", "user2の2番目のツイート", java.sql.Timestamp.valueOf("2024-02-01 09:30:00")),
                tuple(6, "user2", "user2の最初のツイート", java.sql.Timestamp.valueOf("2024-02-01 09:00:00")),
                tuple(5, "user1", "5番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 14:00:00")),
                tuple(4, "user1", "4番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 13:00:00")),
                tuple(3, "user1", "3番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 12:00:00")),
                tuple(2, "user1", "2番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 11:00:00")),
                tuple(1, "user1", "最初のツイート", java.sql.Timestamp.valueOf("2024-01-01 10:00:00"))
            );
    }

    @Test
    void testFindByUser_IdOrderByCreatedDesc_Paging() {
        // page 0, size 2  -> offset 0
        List<Tweet> page0 = tweetRepository.findByUser_IdOrderByCreatedDesc("user1", 0, 2);
        assertThat(page0)
            .extracting(
                Tweet::getId,
                t -> t.getUser().getId(),
                Tweet::getText,
                Tweet::getCreated
            )
            .containsExactly(
                tuple(5, "user1", "5番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 14:00:00")),
                tuple(4, "user1", "4番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 13:00:00"))
            );

        // page 1, size 2 -> offset 2
        List<Tweet> page1 = tweetRepository.findByUser_IdOrderByCreatedDesc("user1", 2, 2);
        assertThat(page1)
            .extracting(
                Tweet::getId,
                t -> t.getUser().getId(),
                Tweet::getText,
                Tweet::getCreated
            )
            .containsExactly(
                tuple(3, "user1", "3番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 12:00:00")),
                tuple(2, "user1", "2番目のツイート", java.sql.Timestamp.valueOf("2024-01-01 11:00:00"))
            );

        // page 2, size 2 -> offset 4 (1件のみ)
        List<Tweet> page2 = tweetRepository.findByUser_IdOrderByCreatedDesc("user1", 4, 2);
        assertThat(page2)
            .extracting(
                Tweet::getId,
                t -> t.getUser().getId(),
                Tweet::getText,
                Tweet::getCreated
            )
            .containsExactly(
                tuple(1, "user1", "最初のツイート", java.sql.Timestamp.valueOf("2024-01-01 10:00:00"))
            );
    }
}