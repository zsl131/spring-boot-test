package com.zslin.dto;

import com.zslin.utils.ExcelResources;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/28 22:19.
 */
public class WebDto {

    // 网站名称
    private String name;

    // 网址
    private String url;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 日均访问量
    private Integer readCount;

    public WebDto(String name, String url, String username, String password, Integer readCount) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.readCount = readCount;
    }

    public WebDto() {
    }

    @Override
    public String toString() {
        return "WebDto{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", readCount=" + readCount +
                '}';
    }

    @ExcelResources(title = "网站名称", order = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelResources(title = "网址", order = 2)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @ExcelResources(title = "用户名", order = 3)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @ExcelResources(title = "密码", order = 4)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ExcelResources(title = "日均访问量", order = 5)
    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }
}
