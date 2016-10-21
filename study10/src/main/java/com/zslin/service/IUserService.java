package com.zslin.service;

import com.zslin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/21 17:02.
 */
public interface IUserService extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
}
