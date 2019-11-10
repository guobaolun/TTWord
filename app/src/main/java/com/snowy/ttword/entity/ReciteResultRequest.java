package com.snowy.ttword.entity;

/**
 * Created by Kuo on 2017/12/17.
 */

public class ReciteResultRequest {

    private String word;
    private String inputWord;
    private long inputTime;
    private long reciteTime;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getInputWord() {
        return inputWord;
    }

    public void setInputWord(String inputWord) {
        this.inputWord = inputWord;
    }

    public long getInputTime() {
        return inputTime;
    }

    public void setInputTime(long inputTime) {
        this.inputTime = inputTime;
    }

    public long getReciteTime() {
        return reciteTime;
    }

    public void setReciteTime(long reciteTime) {
        this.reciteTime = reciteTime;
    }
}
