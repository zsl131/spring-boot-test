package com.zslin.service;

import com.zslin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/21 10:56.
 */
public interface IUserService extends JpaRepository<User, Integer> {
}
