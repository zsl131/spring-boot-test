package com.zslin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/18 14:27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {

    @Value("${test.msg}")
    private String msg;

    @Test
    public void testConfig() {
        System.out.println("cur msg is : "+ msg);
    }
}
