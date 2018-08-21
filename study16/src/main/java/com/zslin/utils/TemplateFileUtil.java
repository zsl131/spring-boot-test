package com.zslin.utils;

import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/28 23:57.
 */
public class TemplateFileUtil {

    public static FileInputStream getTemplateByClasspath(String templatePath) throws FileNotFoundException {
        return new FileInputStream(ResourceUtils.getFile("classpath:excel-templates/" + templatePath));
    }

    public static FileInputStream getTemplateByPath(String templatePath) throws FileNotFoundException {
        return new FileInputStream(ResourceUtils.getFile(templatePath));
    }
}
