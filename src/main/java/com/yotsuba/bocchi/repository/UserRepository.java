package com.yotsuba.bocchi.repository;

import com.yotsuba.bocchi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
