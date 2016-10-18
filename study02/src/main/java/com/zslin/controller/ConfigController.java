package com.zslin.controller;

import com.zslin.config.MyWebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/18 10:33.
 */
@RestController
@RequestMapping(value = "config")
public class ConfigController {

    @Autowired
    private MyWebConfig myWebConfig;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "webName: "+myWebConfig.getName()+", webVersion: "+
                myWebConfig.getVersion()+", webAuthor: "+myWebConfig.getAuthor();
    }
}
