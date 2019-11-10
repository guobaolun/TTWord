package com.snowy.ttword.entity;

public class ChildComment {

    private String id;

    private String commentId;

    private String userId;

    private String nickname;

    private String portrait;

    private String targetUserId;

    private String targetUserNickName;

    private Long time;

    private String content;

    private String voice;

    private Long voiceTime;

    private Integer type;

    private Integer praiseCount;


    private long childCommentId;

    private Integer likeCount;


    public long getChildCommentId() {
        return childCommentId;
    }

    public void setChildCommentId(long childCommentId) {
        this.childCommentId = childCommentId;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getTargetUserNickName() {
        return targetUserNickName;
    }

    public void setTargetUserNickName(String targetUserNickName) {
        this.targetUserNickName = targetUserNickName;
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

    public Long getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(Long voiceTime) {
        this.voiceTime = voiceTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPraiseCount() {
        return praiseCount;
    }

    public void setPraiseCount(Integer praiseCount) {
        this.praiseCount = praiseCount;
    }
}
