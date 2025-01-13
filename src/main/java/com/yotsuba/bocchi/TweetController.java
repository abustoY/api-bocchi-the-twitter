package com.yotsuba.bocchi;

import com.yotsuba.bocchi.dto.TweetDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/default")
    public List<Tweet> getTweets() {
        return tweetService.findAll();
    }

    @GetMapping
    public List<TweetDto> getAllTweetSummary() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("called getAllTweetSummary");
        System.out.println(authentication);
        System.out.println(authentication.getName());
        System.out.println(authentication.getPrincipal());
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getAuthorities());
        System.out.println("called getAllTweetSummary");
        return tweetService.findAllTweetSummary();
    }

    @GetMapping("/user")
    public List<TweetDto> getUserAllTweetSummary(@RequestParam(name = "user_id", required = true) String userId) {
        return tweetService.findUserAllTweetSummary(userId);
    }

    @PostMapping
    public void saveTweet(@RequestBody TweetRequest tweetRequest) {
        tweetService.saveTweet(tweetRequest);

    }

    @DeleteMapping
    public void deleteTweet(@RequestParam(name = "tweet_id",required = true) Integer tweetId){
        tweetService.deleteTweet(tweetId);

    }
}
