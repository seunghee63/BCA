package com.song2.boostcourse.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieRank implements Parcelable {
    public int id;
    public String title;
    public String title_eng;
    public String date;
    public String user_rating;
    public String audience_rating;
    public String reviewer_rating;
    public String reservation_rate;
    public String reservation_grade;
    public String grade;
    public String thumb;
    public String image;

    public MovieRank (String img, String title, String reservation_grade, String reservation_rate, String grade){

        //this.id = in.readInt();
        this.title_eng = "";
        this.date = "";
        this.user_rating = "";
        this.audience_rating = "";
        this.reviewer_rating = "";
        this.image = img;
        this.title = title;
        this.reservation_grade = reservation_grade;
        this.reservation_rate = reservation_rate;
        this.grade = grade;
    }

    public MovieRank(Parcel in) {
        id = in.readInt();
        title = in.readString();
        title_eng = in.readString();
        date = in.readString();
        user_rating = in.readString();
        audience_rating = in.readString();
        reviewer_rating = in.readString();
        reservation_rate = in.readString();
        reservation_grade = in.readString();
        grade = in.readString();
        thumb = in.readString();
        image = in.readString();
    }

    public static final Creator<MovieRank> CREATOR = new Creator<MovieRank>() {
        @Override
        public MovieRank createFromParcel(Parcel in) {
            return new MovieRank(in);
        }

        @Override
        public MovieRank[] newArray(int size) {
            return new MovieRank[size];
        }
    };

    public MovieRank() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(title_eng);
        parcel.writeString(date);
        parcel.writeString(user_rating);
        parcel.writeString(audience_rating);
        parcel.writeString(reviewer_rating);
        parcel.writeString(reservation_rate);
        parcel.writeString(reservation_grade);
        parcel.writeString(grade);
        parcel.writeString(thumb);
        parcel.writeString(image);
    }
}
