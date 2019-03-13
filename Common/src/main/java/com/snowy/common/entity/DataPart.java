package com.snowy.common.entity;


import com.snowy.common.net.OkHttpManager;

public class DataPart {
    private String name;
    private String value;
    private OkHttpManager.Type type;


    public DataPart(String name, String value, OkHttpManager.Type type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public OkHttpManager.Type getType() {
        return type;
    }

    public void setType(OkHttpManager.Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
