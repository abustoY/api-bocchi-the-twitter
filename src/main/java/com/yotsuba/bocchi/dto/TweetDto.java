package com.yotsuba.bocchi.dto;

import java.util.Date;
import java.util.List;

public class TweetDto {
    private Integer id;
    private String name;
    private String text;
    private Date created;
    private List<Long> mediaIds;

    public TweetDto(Integer id, String name, String text, Date created) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.created = created;
        this.mediaIds = null;
    }

    public TweetDto(Integer id, String name, String text, Date created, List<Long> mediaIds) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.created = created;
        this.mediaIds = mediaIds;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Date getCreated() {
        return created;
    }

    public List<Long> getMediaIds() {
        return mediaIds;
    }

}
