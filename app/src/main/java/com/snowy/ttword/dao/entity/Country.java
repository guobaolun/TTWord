package com.snowy.ttword.dao.entity;


import androidx.annotation.NonNull;


public class Country implements Comparable{
    private String id;
    private String name;
    private String zhName;
    private String code;
    private String areaCode;
    private String firstLetter;
    private String letter;


    public Country(String id, String name, String zhName, String code, String areaCode, String firstLetter, String letter) {
        this.id = id;
        this.name = name;
        this.zhName = zhName;
        this.code = code;
        this.areaCode = areaCode;
        this.firstLetter = firstLetter;
        this.letter = letter;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZhName() {
        return zhName;
    }

    public void setZhName(String zhName) {
        this.zhName = zhName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    @Override
    public int compareTo(@NonNull Object obj) {

        if (!(obj instanceof Country)) {
            throw new RuntimeException("不是相同对象");
        }

        Country country = (Country) obj;

        if (this.firstLetter.toCharArray()[0] > country.getFirstLetter().toCharArray()[0]) {
            return 1;
        }

        if (this.firstLetter.toCharArray()[0] == country.getFirstLetter().toCharArray()[0]) {
            return this.zhName.compareTo(country.getZhName());
        }

        return -1;
    }


}
