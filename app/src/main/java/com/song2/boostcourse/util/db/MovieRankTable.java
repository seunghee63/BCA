package com.song2.boostcourse.util.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.song2.boostcourse.data.MovieDetail;
import com.song2.boostcourse.data.MovieRank;

import java.util.ArrayList;

public class MovieRankTable {

    DatabaseHelper helper;
    SQLiteDatabase database;

    static final String MOVIERANKCOLUMN = "image, title, reservation_grade, reservation_rate, grade";

    public MovieRankTable(Context context){

        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
    }


    public void insertData(String image, String title, String reservation_grade, String reservation_rate, String grade){

        Log.e("insertData", "insertData호출");

        if (database != null){
            String sql = "insert into movieRank("+MOVIERANKCOLUMN+") values(?,?,?,?,?)";
            Object[] params = {image, title, reservation_grade, reservation_rate, grade};

            database.execSQL(sql,params);

            Log.e("insertData", "데이터 삽입 완료!");

        }
    }

    public ArrayList<MovieRank> selectData(Context context){

        if (database != null){
            ArrayList<MovieRank> movieRankList = new ArrayList<MovieRank>();

            String sql = "select "+MOVIERANKCOLUMN+" from "+ "movieRank";

            //sql에 ?를 넣고 null 대신 ?를 대체 할 파라미터를 넣는 방법도 가능!
            Cursor cursor = database.rawQuery(sql, null);
            Log.e("조회된 데이터 개수 : " , String.valueOf(cursor.getCount()));

            if (cursor.getCount()==0){
                Toast.makeText(context, "어플을 처음 실행 한 경우, 인터넷에 연결해야 데이터를 받아 올 수 있습니다.", Toast.LENGTH_SHORT).show();
            }else{
                for(int i = 0; i<cursor.getCount();i++){
                    cursor.moveToNext();
                    String image = cursor.getString(0);
                    String title = cursor.getString(1);
                    String reservation_grade = cursor.getString(2);
                    String reservation_rate = cursor.getString(3);
                    String grade = cursor.getString(4);

                    MovieRank movieRank = new MovieRank(image, title, reservation_grade, reservation_rate, grade);
                    movieRankList.add(i,movieRank);

                    Log.e("selectData", image+ " "+ title + " "+  reservation_grade + " "+  reservation_rate + " "+ grade);
                }
                //settingViewPager(cursor.getCount(), movieRankList);
                return movieRankList;
            }
        }
        return null;
    }
}
