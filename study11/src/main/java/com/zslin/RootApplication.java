package com.zslin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/22 21:50.
 */
@SpringBootApplication
@EnableScheduling
public class RootApplication {

    public static void main(String [] args) {
        SpringApplication.run(RootApplication.class, args);
    }
}
