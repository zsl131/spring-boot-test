package com.zslin.tools;

import org.springframework.data.jpa.domain.Specifications;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/22 1:04.
 */
public class SearchSpeDto {

    /** 类型，and或者or */
    private String type;

    public SearchSpeDto(String type, Specifications spes) {
        this.type = type;
        this.spes = spes;
    }

    private Specifications spes;

    public Specifications getSpes() {
        return spes;
    }

    public String getType() {
        return type;
    }

    public void setSpes(Specifications spes) {
        this.spes = spes;
    }

    public void setType(String type) {
        this.type = type;
    }
}
