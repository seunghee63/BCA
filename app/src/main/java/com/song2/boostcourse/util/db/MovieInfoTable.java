package com.song2.boostcourse.util.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.song2.boostcourse.data.MovieDetail;

public class MovieInfoTable {

    public Context context;
    public DatabaseHelper helper;
    public SQLiteDatabase database;

    static final String MOVIEINFOCOLUMN = "movie_index, image, title, date, genre, duration, reservation_grade, reservation_rate, audience_rating, audience, synopsis, director,actor, _like, _dislike, grade, photos, videos";

    public MovieInfoTable(Context context){
        this.context = context;
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }

    public void insertData(int movieIndex , MovieDetail movieDetail) {

        Log.e("insertData", "insertData호출");

        if (database != null) {
            String sql = "insert or replace into movie("+MOVIEINFOCOLUMN+") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {movieIndex, movieDetail.image, movieDetail.title, movieDetail.date, movieDetail.genre, movieDetail.duration, movieDetail.reservation_grade, movieDetail.reservation_rate, movieDetail.audience_rating, movieDetail.audience, movieDetail.synopsis, movieDetail.director, movieDetail.actor, movieDetail.like, movieDetail.dislike, movieDetail.grade, movieDetail.photos, movieDetail.videos};

            database.execSQL(sql, params);

            Log.e("insertData", movieDetail.toString());
            Log.e("insertData11", params.toString());
        }
    }

    public MovieDetail selectData(int movieIndex) {

        if (database != null) {

            MovieDetail movieDetail;

            String sql = "select "+MOVIEINFOCOLUMN+" from " + "movie WHERE movie_index=" + movieIndex;

            Cursor cursor = database.rawQuery(sql, null);
            Log.e("조회된 데이터 개수 : ", String.valueOf(cursor.getCount()));

            if (cursor.getCount() == 0) {
                Toast.makeText(context, "어플을 처음 실행 한 경우, 인터넷에 연결해야 데이터를 받아 올 수 있습니다.", Toast.LENGTH_SHORT).show();
            } else {

                cursor.moveToNext();
                int movie_index = cursor.getInt(0);
                String image = cursor.getString(1);
                String title = cursor.getString(2);
                String date = cursor.getString(3);
                String genre = cursor.getString(4);
                int duration = cursor.getInt(5);
                int reservation_grade = cursor.getInt(6);
                float reservation_rate = cursor.getFloat(7);
                float audience_rating = cursor.getFloat(8);
                int audience = cursor.getInt(9);
                String synopsis = cursor.getString(10);
                String director = cursor.getString(11);
                String actor = cursor.getString(12);
                int _like = cursor.getInt(13);
                int _dislike = cursor.getInt(14);
                int grade = cursor.getInt(15);
                String photos = cursor.getString(16);
                String videos = cursor.getString(17);

                movieDetail = new MovieDetail(movie_index, image, title, date, genre, duration, reservation_grade, reservation_rate, audience_rating, audience, synopsis, director, actor, _like, _dislike, grade, photos, videos);
                Log.e("selectData", image + " " + title + " " + reservation_grade + " " + reservation_rate + " " + grade);

                return movieDetail;
            }
        }
        return null;
    }

    public boolean search(int movie_index) {

        Log.e("search", "중복처리 " + movie_index);
        Cursor cursor = database.rawQuery("SELECT movie_index FROM movie WHERE movie_index =" + movie_index + ";", null);

        if (cursor == null) {
            return true;
        } else if (cursor.getCount() > 0) {
            return false;
        }
        return true;
    }
}
