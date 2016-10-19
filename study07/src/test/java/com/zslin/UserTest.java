package com.zslin;

import com.zslin.model.User;
import com.zslin.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/19 16:28.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testAdd() {
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
    public void testFindById() {
        User u = userService.findById(3);
        System.out.println("nickName : "+u.getNickName()+", email : "+u.getEmail());
    }

    @Test
    public void testFindByUserName() {
        User u = userService.findByUserName("user3");
        System.out.println("nickName : "+u.getNickName()+", email : "+u.getEmail());
    }

    @Test
    public void testFindByNameAndPwd() {
        User u = userService.findByUserNameAndPassword("user4", "pwd4");
        System.out.println("nickName : "+u.getNickName()+", email : "+u.getEmail());
    }

    @Test
    public void test() {
        List<User> list = userService.findByUserNameLike("user%");
        System.out.println(list.size());
    }

    @Test
    public void test2() {
        List<User> list = userService.findByUserNameNotLike("user4");
        System.out.println(list.size());
    }

    @Test
    public void test3() {
        List<User> list = userService.findByUserNameNot("user3");
        System.out.println(list.size());
    }

    @Test
    public void testQuery() {
        List<User> list = userService.findAll("user3");
        System.out.println(list.size());
    }

    @Test
    public void testUpdate() {
        userService.updatePwd("user1", "123456");
    }

    @Test
    public void testDelete() {
        userService.deleteByUserName("user4");
    }

    @Test
    public void testUpdate2() {
        userService.updateEmail("user2", "user2@qq.com");
    }
}
