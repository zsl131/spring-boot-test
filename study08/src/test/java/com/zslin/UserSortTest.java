package com.zslin;

import com.zslin.model.User;
import com.zslin.service.IUserService;
import com.zslin.tools.SortDto;
import com.zslin.tools.SortTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/21 10:57.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserSortTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testSort() {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        List<User> list = userService.findAll(sort);
        for(User u : list) {
            System.out.println(u.getUserName());
        }
    }

    @Test
    public void testSort2() {
        List<User> list = userService.findAll(SortTools.basicSort());
        for(User u : list) {
            System.out.println(u.getUserName());
        }
    }

    @Test
    public void testSort3() {
        List<User> list = userService.findAll(SortTools.basicSort("desc", "userName"));
        for(User u : list) {
            System.out.println(u.getId()+"===="+u.getUserName());
        }
    }

    @Test
    public void testSort4() {
        List<User> list = userService.findAll(SortTools.basicSort(new SortDto("desc", "userName"), new SortDto("id")));
        for(User u : list) {
            System.out.println(u.getId()+"===="+u.getUserName());
        }
    }
}
