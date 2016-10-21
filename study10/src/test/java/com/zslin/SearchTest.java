package com.zslin;

import com.zslin.model.User;
import com.zslin.service.IUserService;
import com.zslin.tools.BaseSearch;
import com.zslin.tools.SearchDto;
import com.zslin.tools.SearchTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/21 17:14.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SearchTest {

    @Autowired
    private IUserService userService;

    private void print(List<User> list) {
        for(User u : list) {
            System.out.println(u.getId()+"==="+u.getUserName());
        }
    }

    @Test
    public void test1() {
        List<User> list = userService.findAll(new BaseSearch<User>(new SearchDto("userName","eq", "user1")));
        print(list);
    }

    @Test
    public void test2() {
        List<User> list = userService.findAll(SearchTools.buildSpecification(
                SearchTools.buildSpeDto("and", new SearchDto("and", "id", "gt", 2)),
                SearchTools.buildSpeDto("and", new SearchDto("userName", "ne", "user5"),
                        new SearchDto("or", "userName", "ne", "user9"))
        ));
        print(list);
    }
}
