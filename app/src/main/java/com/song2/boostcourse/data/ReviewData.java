package com.song2.boostcourse.data;

public class ReviewData{
    public String profileImg;
    public String userId;
    public String date;
    public String comment;
    public float rate;
    public String like;

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "ReviewData{" +
                "profileImg='" + profileImg + '\'' +
                ", userId='" + userId + '\'' +
                ", date='" + date + '\'' +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", like=" + like +
                '}';
    }
}
