package com.zslin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/18 11:25.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MyTest {

    @Value("${test.msg}")
    private String msg;

    @Test
    public void testCoreConfig() {
        System.out.println(msg);
    }

    @Autowired
    private Environment env;

    @Test
    public void testCoreConfig2() {
        System.out.println(env.getProperty("test.msg"));
    }
}
