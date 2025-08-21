package com.yotsuba.bocchi.controller;

import com.yotsuba.bocchi.service.TweetService;
import com.yotsuba.bocchi.dto.TweetDto;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {
    private final TweetService tweetService;

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    // @GetMapping("/default")
    // public List<Tweet> getTweets() {
    //     return tweetService.findAll();
    // }

    @GetMapping
    public List<TweetDto> getAllTweetSummary(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return tweetService.findAllTweetSummary(page, size);
    }

    @GetMapping("/user")
    public List<TweetDto> getUserAllTweetSummary(
            @RequestParam(name = "user_id") String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return tweetService.findUserAllTweetSummary(userId, page, size);
    }

    @GetMapping("/following")
    public List<TweetDto> getFollowingTweetSummary(
            @RequestParam(name = "user_id") List<String> userIds,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return tweetService.findFollowingTweetSummary(userIds, page, size);
    }

    // 旧API: JSON形式でのTweet投稿（メディア無し）
    // @PostMapping
    // public void saveTweet(@RequestBody TweetRequest tweetRequest) {
    //     tweetService.saveTweet(tweetRequest);
    // }

    // 新API: multipart/form-data形式でテキストとファイルの両方を受信
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveTweetWithMedia(
            @RequestParam("userId") String userId,
            @RequestParam("text") String text,
            @RequestPart(value = "mediaFiles", required = false) List<MultipartFile> mediaFiles
    ) {
        System.out.println("ユーザーID: " + userId);
        System.out.println("テキスト: " + text);
        if (mediaFiles != null) {
            System.out.println("受信したファイル数: " + mediaFiles.size());
            for (MultipartFile file : mediaFiles) {
                System.out.println("ファイル名: " + file.getOriginalFilename());
            }
        } else {
            System.out.println("添付ファイルなし");
        }

        // サービス側にファイル付きTweetを渡す処理を呼び出す
        tweetService.saveTweetWithMedia(userId, text, mediaFiles);
    }

    @DeleteMapping
    public void deleteTweet(@RequestParam(name = "tweet_id",required = true) Integer tweetId){
        tweetService.deleteTweet(tweetId);

    }
}
