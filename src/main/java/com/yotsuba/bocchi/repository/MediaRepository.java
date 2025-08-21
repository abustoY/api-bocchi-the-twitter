package com.yotsuba.bocchi.repository;

import com.yotsuba.bocchi.entity.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}