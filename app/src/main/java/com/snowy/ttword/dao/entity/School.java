package com.snowy.ttword.dao.entity;

/**
 * Created by Administrator on 2017/6/29.
 */

public class School {
    private int id ;
    private int schoolId;
    private String name ;
    private String pinyin ;
    private String py ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }
}
