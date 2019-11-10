package com.snowy.ttword.entity;

/**
 * @author guobaolun
 */
public class WordProgressData {


    private static final long MINUTE = 1000 * 60;
    private static final long HOUR = MINUTE * 60;
    private static final long DAY = HOUR * 24;

    private String word;
    private Integer progress;
    private String chinese;
    private boolean newWord;
    private long lastReciteTime;
    private String distanceTime;


    public void setDistanceTime() {

        long time = System.currentTimeMillis() - lastReciteTime;

        if (progress <= 10) {
            time = MINUTE * 30 - time;
        } else if (progress <= 20) {
            time = HOUR * 2 - time;
        } else if (progress <= 30) {
            time = HOUR * 6 - time;
        } else if (progress <= 40) {
            time = HOUR * 12 - time;
        } else if (progress <= 50) {
            time = DAY - time;
        } else if (progress <= 60) {
            time = DAY * 2;
        } else if (progress <= 70) {
            time = DAY * 3 - time;
        } else if (progress <= 80) {
            time = DAY * 8 - time;
        } else if (progress <= 90) {
            time = DAY * 8 - time;
        } else if (progress <= 99) {
            time = DAY * 10 - time;
        } else {
            time = 0;
        }

        int day ;
        int hour;
        int minute = 0;

        distanceTime = "";
        if (time < 0) {
            distanceTime = minute + "分种";
        } else {
            day = (int) (time / DAY);
            hour = (int) ((time - day * DAY) / HOUR);
            minute = (int) ((time - day * DAY - hour * HOUR) / MINUTE);

            if (day != 0) {
                distanceTime = day + "天";
            }

            if (hour != 0) {
                distanceTime = distanceTime + hour + "小时";
            }

            distanceTime =  distanceTime + minute + "分种";
        }


    }

    public String getDistanceTime() {
        return distanceTime;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public boolean isNewWord() {
        return newWord;
    }

    public void setNewWord(boolean newWord) {
        this.newWord = newWord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public long getLastReciteTime() {
        return lastReciteTime;
    }

    public void setLastReciteTime(long lastReciteTime) {
        this.lastReciteTime = lastReciteTime;
    }
}
