package com.yotsuba.bocchi.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Table(name = "user_followers", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "follower_id"})
})
@EntityListeners(AuditingEntityListener.class)
public class UserFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    // getter/setter ...
    public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}

public User getFollower() {
    return follower;
}

public void setFollower(User follower) {
    this.follower = follower;
}
}