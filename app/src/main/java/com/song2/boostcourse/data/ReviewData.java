package com.song2.boostcourse.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewData implements Parcelable {
    public int movie_id;
    public int id;
    public String profileImg;
    public String userId;
    public String date;
    public String comment;
    public float rate;
    public String like;

    public ReviewData (String img, String userId, String date, String comment, float rate, int like, int movie_id , int id){
        this.profileImg = img;
        this.userId = userId;
        this.date = date;
        this.comment = comment;
        this.rate = rate;
        this.like = "좋아요   " + like;
        this.movie_id = movie_id;
        this.id = id;
    }

    protected ReviewData(Parcel in) {
        profileImg = in.readString();
        userId = in.readString();
        date = in.readString();
        comment = in.readString();
        rate = in.readFloat();
        like = in.readString();
    }

    public static final Creator<ReviewData> CREATOR = new Creator<ReviewData>() {
        @Override
        public ReviewData createFromParcel(Parcel in) {
            return new ReviewData(in);
        }

        @Override
        public ReviewData[] newArray(int size) {
            return new ReviewData[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(profileImg);
        parcel.writeString(userId);
        parcel.writeString(date);
        parcel.writeString(comment);
        parcel.writeFloat(rate);
        parcel.writeString(like);
    }
}
