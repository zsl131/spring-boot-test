package com.zslin.dto;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/28 22:19.
 */
public class WebDto {

    //网站名称
    private String name;

    //网址
    private String url;

    //用户名
    private String username;

    //密码
    private String password;

    //日均访问量
    private Integer readCount;

    public WebDto(String name, String url, String username, String password, Integer readCount) {
        this.name = name;
        this.url = url;
        this.username = username;
        this.password = password;
        this.readCount = readCount;
    }

    public WebDto() {}

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
}
