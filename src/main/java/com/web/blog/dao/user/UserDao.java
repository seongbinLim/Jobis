package com.web.blog.dao.user;

import java.util.Optional;

import com.web.blog.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, String> {
    
    //중복확인
    Optional<User> findByEmailOrUid(String email,String uid);
    Optional<User> findByEmail(String email);
    Optional<User> findByUid(String uid);

    // 조회
    User findUserByEmail(String email);

    // uid로 유저 정보 조회
    User findUserByUid(String uid);

    // 로그인
    Optional<User> findUserByUidAndPassword(String uid, String password);

    //c, u
    User save(User user);

    //d
    Optional<User> deleteByUid(String userid);
}