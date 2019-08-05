package com.song2.boostcourse.data;

public class ReviewData {
    public String profile_img;
    public String user_id;
    public String date;
    public String comment;
    public float rate;
    public String like;

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public void setRate(int rate) {
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
                "profile_img='" + profile_img + '\'' +
                ", user_id='" + user_id + '\'' +
                ", date='" + date + '\'' +
                ", rate=" + rate +
                ", comment='" + comment + '\'' +
                ", like=" + like +
                '}';
    }
}
