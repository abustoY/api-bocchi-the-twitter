package com.yotsuba.bocchi;

import com.yotsuba.bocchi.dto.TweetDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
    }

    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    public List<TweetDto> findAllTweetSummary() {
        return tweetRepository.findAllTweetSummary();
    }

    public List<TweetDto> findUserAllTweetSummary(String userId) {
        return tweetRepository.findUserAllTweetSummary(userId);
    }

    public void saveTweet(TweetRequest tweetRequest) {
        Tweet tweet = new Tweet();
        tweet.setText(tweetRequest.getText());
        User user = userRepository.findById(tweetRequest.getUserId()).orElseThrow();
        tweet.setUser(user);
        System.out.println("tweet.getId():" + tweet.getId());
        System.out.println("tweet.getCreated():" + tweet.getCreated());
        System.out.println("tweet.getUser().getName():" + tweet.getUser().getName());
        tweetRepository.save(tweet);

    }

    public void deleteTweet(Integer tweetId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (isTweetOwnedByUser(tweetId, ((UserDetails) authentication.getPrincipal()).getUsername())) {
            tweetRepository.deleteById(tweetId);
        }

    }

    public boolean isTweetOwnedByUser(Integer tweetId, String userId) {
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow();
        return tweet.getUser().getId().equals(userId);
    }
}
