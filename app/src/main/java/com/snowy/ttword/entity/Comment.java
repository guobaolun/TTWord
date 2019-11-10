package com.snowy.ttword.entity;

import java.util.ArrayList;

/**
 * @author guobaolun
 * Created by Kuo on 2017/8/28.
 */

public class Comment {


    private String id;

    private String articleId;

    private String commentId;

    private String userId;

    private String nickname;

    private String portrait;

    private Long time;

    private String content;

    private String voice;

    private Integer voiceTime;

    private Integer type;

    private Integer commentCount;

    private Integer likeCount;

    private ArrayList<ChildComment> childCommentList;


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getNickname() {
        return nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public Integer getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(Integer voiceTime) {
        this.voiceTime = voiceTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public ArrayList<ChildComment> getChildCommentList() {
        return childCommentList;
    }

    public void setChildCommentList(ArrayList<ChildComment> childCommentList) {
        this.childCommentList = childCommentList;
    }
}
