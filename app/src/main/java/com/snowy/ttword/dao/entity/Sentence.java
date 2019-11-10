package com.snowy.ttword.dao.entity;

/**
 * @author guobaolun
 * @since 2018/5/22
 */
public class Sentence {


    private String id;
    private String en;
    private String zh;
    private String word;


    public Sentence() {
    }


    public Sentence(String id, String en, String zh, String word) {
        this.id = id;
        this.en = en;
        this.zh = zh;
        this.word = word;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
