package com.zslin.service;

import com.zslin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/19 11:41.
 */
public interface IUserService extends JpaRepository<User, Integer> {

    User findById(Integer id);

    User findByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);

    List<User> findByUserNameLike(String username);

    List<User> findByUserNameNotLike(String username);

    List<User> findByUserNameNot(String username);

    @Query("FROM User u WHERE u.userName=?1 AND u.password IS NOT NULL")
    List<User> findAll(String userName);

    @Query("UPDATE User u SET u.password=?2 WHERE u.userName=?1")
    @Modifying
    @Transactional
    void updatePwd(String userName, String pwd);

    @Query("DELETE FROM User u WHERE u.userName=?1")
    @Modifying
    @Transactional
    void deleteByUserName(String userName);

    @Query("UPDATE User u SET u.email= :email WHERE u.userName = :user")
    @Modifying
    @Transactional
    void updateEmail(@Param("user") String userName, @Param("email") String email);
}
