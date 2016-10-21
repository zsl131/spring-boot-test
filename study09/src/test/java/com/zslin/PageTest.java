package com.zslin;

import com.zslin.model.User;
import com.zslin.service.IUserService;
import com.zslin.tools.PageableTools;
import com.zslin.tools.SortDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/21 14:51.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PageTest {

    @Autowired
    private IUserService userService;

    @Test
    public void test1() {
        Pageable pageable =new PageRequest(0, 5);
        Page<User> datas = userService.findAll(pageable);
        System.out.println("总条数："+datas.getTotalElements());
        System.out.println("总页数："+datas.getTotalPages());
        for(User u : datas) {
            System.out.println(u.getId()+"===="+u.getUserName());
        }
    }

    private void print(Page<User> datas) {
        System.out.println("总条数："+datas.getTotalElements());
        System.out.println("总页数："+datas.getTotalPages());
        for(User u : datas) {
            System.out.println(u.getId()+"===="+u.getUserName());
        }
    }

    @Test
    public void test2() {
        Page<User> datas = userService.findAll(PageableTools.basicPage(0));
        print(datas);
    }

    @Test
    public void test3() {
        Page<User> datas = userService.findAll(PageableTools.basicPage(1, 5));
        print(datas);
    }

    @Test
    public void test4() {
        Page<User> datas = userService.findAll(PageableTools.basicPage(1, 5, new SortDto("id")));
        print(datas);

        Page<User> datas2 = userService.findAll(PageableTools.basicPage(1, 5, new SortDto("ASC", "id")));
        print(datas2);
    }
}
