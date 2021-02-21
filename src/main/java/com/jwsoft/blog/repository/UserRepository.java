package com.jwsoft.blog.repository;

import com.jwsoft.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// DAO
// 자동으로 bean 등록이 된다.
// @Repository 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}


// JPA Naming 쿼리
// select * from user where username = ? and password = ?;
//    User findByUsernameAndPassword(String username, String password);

//    @Query(value = "select * from user where username = ? and password = ?", nativeQuery = true)
//    User login(String username, String password);

