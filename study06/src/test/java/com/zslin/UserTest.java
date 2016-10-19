package com.zslin;

import com.zslin.model.User;
import com.zslin.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/19 12:00.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class UserTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testAdd() {
        User user = new User();
        user.setEmail("393156105@qq.com");
        user.setNickName("知识林");
        user.setPassword("123456");
        user.setUserName("zslin");

        userService.save(user);
    }

    @Test
    public void testFind() {
        User user = userService.findOne(1);
        System.out.println("nickName : "+user.getNickName()+", email : "+user.getEmail());
    }

    @Test
    public void testUpdate() {
        User user = userService.findOne(1);
        user.setNickName("钟述林");
        userService.save(user);
        System.out.println("nickName : "+user.getNickName()+", email : "+user.getEmail());
    }

    @Test
    public void testDelete() {
        userService.delete(1);
    }

    @Test
    public void testAddBatch() {
        for(Integer i=0; i < 5; i++) {
            User user = new User();
            user.setNickName("昵称"+i);
            user.setUserName("user"+i);
            user.setPassword("pwd"+i);
            user.setEmail("email"+i+"@domain.com");
            userService.save(user);
        }
    }

    @Test
    public void testFindAll() {
        List<User> list = userService.findAll();
        for(User u : list) {
            System.out.println("nickName : "+u.getNickName()+", email : "+u.getEmail());
        }
    }
}
