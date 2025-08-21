package com.yotsuba.bocchi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    private String id;
    private String name;
    private String password;
    @CreatedDate
    private Date created;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avatar;
    private String avatarContentType;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordWithHash(String password) {
        this.password = passwordEncoder.encode(password);
    }

    public boolean matchesPassword(String rawPassword) {
        return passwordEncoder.matches(rawPassword, this.password);
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarContentType() {
        return avatarContentType;
    }

    public void setAvatarContentType(String avatarContentType) {
        this.avatarContentType = avatarContentType;
    }
}
