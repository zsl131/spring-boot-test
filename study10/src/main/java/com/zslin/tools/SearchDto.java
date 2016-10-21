package com.zslin.tools;

/**
 * Created by 钟述林 393156105@qq.com on 2016/10/21 17:12.
 */
public class SearchDto {

    /** 拼接类型，and或者or */
    private String type;
    private String key;
    private String operation;
    private Object value;

    public SearchDto(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public SearchDto(String type, String key, String operation, Object value) {
        this.type = type;
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public String getOperation() {
        return operation;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
