package com.song2.boostcourse.util.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String MOVIE_RANK =
            "CREATE TABLE IF NOT EXISTS movieRank (_id INTEGER PRIMARY KEY, " +
                    "image TEXT, " +
                    "title TEXT, " +
                    "reservation_grade TEXT, " +
                    "reservation_rate TEXT, " +
                    "grade TEXT )";

    private static final String MOVIE =
            "CREATE TABLE IF NOT EXISTS movie (_id INTEGER PRIMARY KEY, " +
                    "movie_index INTEGER, " +
                    "image TEXT, " +
                    "title TEXT, " +
                    "date TEXT, " +
                    "genre TEXT, " +
                    "duration INTEGER, " +
                    "reservation_grade INTEGER, " +
                    "reservation_rate FLOAT, " +
                    "audience_rating FLOAT, " +
                    "audience INTEGER, " +
                    "synopsis TEXT, " +
                    "director TEXT, " +
                    "actor TEXT, " +
                    "_like INTEGER, " +
                    "_dislike INTEGER, " +
                    "grade INT," +
                    "photos TEXT," +
                    "videos TEXT)";

    private static final String REVIEW =
            "CREATE TABLE IF NOT EXISTS review (_id INTEGER PRIMARY KEY, " +
                    "id INTEGER, " +
                    "movie_id INTEGER, " +
                    "profile_img TEXT, " +
                    "writer TEXT, " +
                    "time TEXT, " +
                    "content TEXT, " +
                    "star_rate FLOAT, " +
                    "recommend INTEGER)";

    //파라미터를 받는 생성자
    public DatabaseHelper(@Nullable Context context) {
        super(context, "movieRank", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MOVIE_RANK);
        db.execSQL(MOVIE);
        db.execSQL(REVIEW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }
}
