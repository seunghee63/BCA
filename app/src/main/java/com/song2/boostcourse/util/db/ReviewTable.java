package com.song2.boostcourse.util.db;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.song2.boostcourse.data.MovieDetail;
import com.song2.boostcourse.data.Review;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.ui.moreReview.MoreReviewActivity;

import java.util.ArrayList;

public class ReviewTable {

    public Context context;
    public DatabaseHelper helper;
    public SQLiteDatabase database;

    static final String COMMENTCOLUMN = "id, movie_id, profile_img, writer, time, content, star_rate, recommend";

    public ReviewTable(Context context){
        this.context = context;
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }

    public void insertCommentData(int movieIndex, ReviewData reviewData) {

        Log.e("insertCommentData", "insertCommentData");

        if (database != null) {
            String sql = "insert into review("+COMMENTCOLUMN+") values(?,?,?,?,?,?,?,?)";
            Object[] params = {reviewData.id, movieIndex, reviewData.profileImg, reviewData.userId, reviewData.date, reviewData.comment, reviewData.rate, reviewData.like};

            database.execSQL(sql, params);

            Log.e("insertCommentData", reviewData.toString());
            Log.e("insertCommentData11", params.toString());
        }
    }

    public ArrayList<ReviewData> selectCommentData(int movieIndex, ArrayList<ReviewData> dataList) {

        if (database != null) {

            Log.e("d",context.toString());
            String sql = "select "+COMMENTCOLUMN+" from " + "review WHERE movie_id=" + movieIndex;

            Cursor cursor = database.rawQuery(sql, null);
            Log.e("조회된 데이터 개수 : ", String.valueOf(cursor.getCount()));

            if (cursor.getCount() == 0) {
                Toast.makeText(context, "어플을 처음 실행 한 경우, 인터넷에 연결해야 데이터를 받아 올 수 있습니다.", Toast.LENGTH_SHORT).show();
            } else {

                dataList.clear();

                int count ;

                if(context instanceof Application){
                    count = cursor.getCount();
                }else
                    count = 2;

                for(int i = 0; i<count;i++){

                    cursor.moveToNext();

                    int id = cursor.getInt(0);
                    //int movie_index = cursor.getInt(1);
                    String image = cursor.getString(2);
                    String writer = cursor.getString(3);
                    String time = cursor.getString(4);
                    String content = cursor.getString(5);
                    float star_rate = cursor.getInt(6);
                    int recommend = cursor.getInt(7);

                    ReviewData reviewData = new ReviewData(image, writer, time, content, star_rate, recommend,movieIndex,id);
                    dataList.add(reviewData);
                    Log.e("selectData", image + " " + writer + " " + time + " " + content + " " + star_rate);
                }

                return dataList;
            }

        }
        return null;
    }

    public boolean search(int id) {

        Log.e("searchReview", "중복처리id : " + id);
        Cursor cursor = database.rawQuery("SELECT id FROM review WHERE id =" + id + ";", null);

        if (cursor == null) {
            return true;
        } else if (cursor.getCount() > 0) {
            //cursor.moveToNext();
            //Log.e("searchReview cursor.getInt : ", String.valueOf(cursor.getInt(0)));
            return false;
        }
        return true;
    }
}
