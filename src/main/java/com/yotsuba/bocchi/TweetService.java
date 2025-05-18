package com.yotsuba.bocchi;

import com.yotsuba.bocchi.dto.TweetDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.yotsuba.bocchi.Media;
import com.yotsuba.bocchi.MediaRepository;

@Service
public class TweetService {
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;

    public TweetService(TweetRepository tweetRepository, UserRepository userRepository, MediaRepository mediaRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.mediaRepository = mediaRepository;
    }

    public List<Tweet> findAll() {
        return tweetRepository.findAll();
    }

    public List<TweetDto> findAllTweetSummary() {
        List<Tweet> tweets = tweetRepository.findAllByOrderByCreatedAsc();
        return tweets.stream().map(tweet -> {
            List<Long> mediaIds = tweet.getMediaList().stream()
                .map(Media::getId)
                .toList();
            return new TweetDto(
                tweet.getId(),
                tweet.getUser().getName(),
                tweet.getText(),
                tweet.getCreated(),
                mediaIds
            );
        }).toList();
    }

    public List<TweetDto> findUserAllTweetSummary(String userId) {
        List<Tweet> tweets = tweetRepository.findByUser_IdOrderByCreatedAsc(userId);
        return tweets.stream().map(tweet -> {
            List<Long> mediaIds = tweet.getMediaList().stream()
                .map(Media::getId)
                .toList();
            return new TweetDto(
                tweet.getId(),
                tweet.getUser().getName(),
                tweet.getText(),
                tweet.getCreated(),
                mediaIds
            );
        }).toList();
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

    // 新しいメディア対応のTweet保存処理
    // mediaFiles が null/空の場合も考慮
    public void saveTweetWithMedia(String userId, String text, List<MultipartFile> mediaFiles) {
        Tweet tweet = new Tweet();
        tweet.setText(text);
        User user = userRepository.findById(userId).orElseThrow();
        tweet.setUser(user);
        tweetRepository.save(tweet);

        if (mediaFiles != null && !mediaFiles.isEmpty()) {
            List<Media> mediaList = new ArrayList<>();
            for (MultipartFile file : mediaFiles) {
                try {
                    Media media = new Media();
                    media.setFileName(file.getOriginalFilename());
                    media.setContentType(file.getContentType());
                    media.setData(file.getBytes());
                    media.setTweet(tweet);
                    mediaList.add(media);
                } catch (IOException e) {
                    System.err.println("ファイル読み込みエラー: " + e.getMessage());
                }
            }
            mediaRepository.saveAll(mediaList);
        }
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
