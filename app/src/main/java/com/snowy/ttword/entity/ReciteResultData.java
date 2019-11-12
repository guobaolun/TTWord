package com.snowy.ttword.entity;


import androidx.annotation.NonNull;

/**
 * @author guobaolun
 */
public class ReciteResultData extends Word implements Comparable<ReciteResultData> {

    private String inputWord;
    private long inputTime;
    private int progress;
    private long reciteTime;
    private boolean newWord;

    public ReciteResultData() {
    }

    public ReciteResultData(String id, String word, String chinese, String usPhonetic, String ukPhonetic) {
        super(id, word, chinese, usPhonetic, ukPhonetic);
    }


    public boolean isNewWord() {
        return newWord;
    }

    public void setNewWord(boolean newWord) {
        this.newWord = newWord;
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

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getReciteTime() {
        return reciteTime;
    }

    public void setReciteTime(long reciteTime) {
        this.reciteTime = reciteTime;
    }


    @Override
    public int compareTo(@NonNull ReciteResultData reciteResultData) {
        int i = reciteResultData.getProgress() - progress;
        if (i == 0) {
            if (reciteResultData.isNewWord()) {
                return -1;
            } else {
                return 1;
            }
        }
        return i;

    }
}
