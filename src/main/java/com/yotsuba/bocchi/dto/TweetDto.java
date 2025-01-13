package com.yotsuba.bocchi.dto;

import java.util.Date;

public class TweetDto {
    private Integer id;
    private String name;
    private String text;
    private Date created;

    public TweetDto(Integer id, String name, String text, Date created) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.created = created;
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

}
