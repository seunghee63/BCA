package com.song2.boostcourse.data;

import java.util.ArrayList;

public class MovieDetail {
    public String title;
    public int id;
    public String date;
    public float user_rating;
    public float audience_rating;
    public float reviewer_rating;
    public float reservation_rate;
    public int reservation_grade;
    public int grade;
    public String thumb;

    public String image;
    public String photos;
    public String videos;
    public Object outlinks;
    public String genre;
    public int duration;
    public int audience;
    public String synopsis;
    public String director;
    public String actor;
    public int like;
    public int dislike;

    public MovieDetail (int movie_index, String image, String title, String date, String genre, int duration, int reservation_grade, float reservation_rate, float audience_rating, int audience, String synopsus, String director, String actor, int _like, int _dislike, int grade, String photos ,String videos){

        this.title = title;
        this.id = movie_index;
        this.date = date;
        this.audience_rating = audience_rating;
        this.reservation_rate = reservation_rate;
        this.reservation_grade = reservation_grade;
        this.grade = grade;
        this.image = image;
        this.genre = genre;
        this.duration = duration;
        this.audience = audience;
        this.synopsis = synopsus;
        this.director = director;
        this.actor = actor;
        this.like = _like;
        this.dislike = _dislike;

        this.videos = videos;
        this.photos = photos;

        this.user_rating =0;
        this.reviewer_rating = 0;
        this.thumb = "";
        this.outlinks = "";
    }
}
