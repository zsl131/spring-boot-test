package com.zslin;

import com.zslin.config.MyConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/18 11:44.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyConfigTest {

    @Autowired
    private MyConfig myConfig;

    @Test
    public void testConfig() {
        System.out.println("webName: "+myConfig.getName()+
                ", webVersion: "+ myConfig.getVersion()+", webAuthor: "+myConfig.getAuthor());
    }
}
