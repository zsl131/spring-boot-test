package com.zslin.utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用来在对象的 get 方法上加入的 annotation，通过该 annotation 说明某个属性所对应的标题
 * Created by 钟述林 393156105@qq.com on 2016/10/29 0:14.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResources {
    /**
     * 属性的标题名称
     *
     * @return 标题
     */
    String title();

    /**
     * 在 excel 的顺序
     *
     * @return 顺序
     */
    int order() default 9999;
}
