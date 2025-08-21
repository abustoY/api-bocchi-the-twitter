package com.yotsuba.bocchi.repository;

import com.yotsuba.bocchi.entity.Tweet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TweetRepositoryImpl implements TweetRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Tweet> findAllByOrderByCreatedDesc(int offset, int size) {
        TypedQuery<Tweet> q = em.createQuery(
                "SELECT t FROM Tweet t ORDER BY t.created DESC, t.id DESC", Tweet.class);
        q.setFirstResult(offset);
        q.setMaxResults(size);
        return q.getResultList();
    }

    @Override
    public List<Tweet> findByUser_IdOrderByCreatedDesc(String userId, int offset, int size) {
        TypedQuery<Tweet> q = em.createQuery(
                "SELECT t FROM Tweet t WHERE t.user.id = :userId ORDER BY t.created DESC, t.id DESC",
                Tweet.class);
        q.setParameter("userId", userId);
        q.setFirstResult(offset);
        q.setMaxResults(size);
        return q.getResultList();
    }

    @Override
    public List<Tweet> findByUser_IdInOrderByCreatedDesc(List<String> userIds, int offset, int size) {
        if (userIds == null || userIds.isEmpty()) return List.of();
        return em.createQuery(
                        "SELECT t FROM Tweet t WHERE t.user.id IN :userIds ORDER BY t.created DESC",
                        Tweet.class
                )
                .setParameter("userIds", userIds)
                .setFirstResult(offset)
                .setMaxResults(size)
                .getResultList();
    }
}