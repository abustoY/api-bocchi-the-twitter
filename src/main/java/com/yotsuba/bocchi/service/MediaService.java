package com.yotsuba.bocchi.service;

import com.yotsuba.bocchi.entity.Media;
import com.yotsuba.bocchi.repository.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public Optional<Media> findById(Long id) {
        return mediaRepository.findById(id);
    }

    public void saveAll(List<Media> mediaList) {
        mediaRepository.saveAll(mediaList);
    }
}