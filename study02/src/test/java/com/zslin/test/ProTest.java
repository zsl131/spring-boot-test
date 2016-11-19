package com.zslin.test;

import com.zslin.config.MyWebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 钟述林 393156105@qq.com on 2016/11/18 14:49.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProTest {

    @Value("${test.msg}")
    private String msg;

    @Autowired
    private MyWebConfig myWebConfig;

    @Test
    public void test() throws Exception {
        System.out.println("=中文=="+msg);
        System.out.println("=="+myWebConfig.getTitle());
    }
}
