package com.song2.boostcourse.ui.moreReview;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.song2.boostcourse.R;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.data.ReviewResult;
import com.song2.boostcourse.databinding.ActivityMoreReviewBinding;
import com.song2.boostcourse.ui.main.MainActivity;
import com.song2.boostcourse.ui.upload.UploadReviewActivity;
import com.song2.boostcourse.util.adapter.ReviewAdapter;
import com.song2.boostcourse.util.db.DatabaseHelper;
import com.song2.boostcourse.util.network.AppHelper;
import com.song2.boostcourse.util.network.NetworkStatus;

import java.util.ArrayList;

public class MoreReviewActivity extends AppCompatActivity {

    static final int REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY = 4444;

    ActivityMoreReviewBinding binding;
    ArrayList<ReviewData> reviewDataArrayList = new ArrayList<>();

    SQLiteDatabase database;
    DatabaseHelper helper;
    Boolean network = false;


    int movieIndex = 0;
    int rating;
    float audienceRating;

    static final String AUDIENCERATING = "AudienceRating";
    static final String MOVIEINDEX = "movieIndex";
    static final String MOVIETITLE = "MovieTitle";
    static final String MOVIERATING = "MovieRating";
    static final String WHEREFROM = "whereFrom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_more_review);
        binding.setMoreReview(this);

        helper = new DatabaseHelper(getApplicationContext(), "movieRank", null, 1);
        database = helper.getWritableDatabase();


        //main에서 넘어온 데이터 setting
        String title = getIntent().getStringExtra(MOVIETITLE);

        audienceRating = getIntent().getFloatExtra(AUDIENCERATING, 0);
        movieIndex = getIntent().getIntExtra(MOVIEINDEX, 0);
        rating = getIntent().getIntExtra(MOVIERATING, 0);
        setMovieRatingImg(rating);

        //통신데이터 보여주어야 함

        network = confirmNetwork();
        if (network) {
            sendRequest("/movie/readCommentList", "?id=" + String.valueOf(movieIndex) + "&limit=all"); // 댓글
        } else {
            selectCommentData();
        }
        binding.tvMoreReviewActMovieTitle.setText(title);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

                Log.e("통신데이터 : ", "제대로 들어왔는지 확인");
                reviewDataArrayList.clear();
                sendRequest("/movie/readCommentList", "?id=" + String.valueOf(movieIndex) + "&limit=all"); // 댓글

                setListView();
            }
        }
    }

    public void clickWriteBtn(View view) {
        //Toast.makeText(this, "WriteBtn", Toast.LENGTH_SHORT).show();

        if (network) {
            Intent intent = new Intent(getApplicationContext(), UploadReviewActivity.class);
            intent.putExtra(MOVIETITLE, binding.tvMoreReviewActMovieTitle.getText());
            intent.putExtra(MOVIERATING, rating);
            intent.putExtra(MOVIEINDEX, movieIndex);
            intent.putExtra(WHEREFROM, "more");
            startActivityForResult(intent, REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY);
        } else {
            Toast.makeText(getApplicationContext(), "network에 연결된 상태에서만 댓글작성이 가능합니다..", Toast.LENGTH_SHORT).show();
        }

    }

    public void clickBackBtn(View view) {

        //Main 으로 데이터 전달
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    public void setListView() {
        ListView reviewList = binding.listViewMoreReviewActReviewList;

        //어뎁터 - 리스트 뷰 연결
        final ReviewAdapter reviewAdapter = new ReviewAdapter(reviewDataArrayList);
        reviewList.setAdapter(reviewAdapter);
    }

    //댓글 Data
    public ReviewData addReviewData(String img, String userId, String date, String comment, float rate, int like, int id) {

        ReviewData newData = new ReviewData(img, userId, date, comment, rate, like, movieIndex, id);
        return newData;
    }

    //통신
    public void sendRequest(final String route, final String query) {

        String base = "http://boostcourse-appapi.connect.or.kr:10000";
        String url = base + route + query;

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        //get,post :
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("응답 : ", "<" + route + "> ::" + response);

                        reviewProcessResponse(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("에러 : ", error.toString());
                    }
                }
        );

        //아래 두 줄은 일반적으로 AppHelper에 넣어서 관리. 메소드 호출해서 여기서 씀..ㅎ
        request.setShouldCache(true);
        AppHelper.requestQueue.add(request);

        Log.e("sendRequest", "요청보냄");
    }

    //review data setting
    public void reviewProcessResponse(String response) {
        Gson gson = new Gson();

        ReviewResult reviewResult = gson.fromJson(response, ReviewResult.class);

        Log.e("moviereviewResult : ", response);

        if (reviewResult != null) {
            Log.e("movieDetailResult : ", String.valueOf(reviewResult));

            String reviewCnt = "";

            if (reviewResult.totalCount > 1000000) {
                reviewCnt = String.valueOf(reviewResult.totalCount / 1000000) + "," + String.format("%03d", (reviewResult.totalCount % 1000000) / 1000) + "," + String.format("%03d", (reviewResult.totalCount % 1000000) % 1000);
            } else if (reviewResult.totalCount < 1000) {
                reviewCnt = String.valueOf(reviewResult.totalCount);
            } else
                reviewCnt = String.valueOf((reviewResult.totalCount % 1000000) / 1000) + "," + String.format("%03d", (reviewResult.totalCount % 1000000) % 1000);

            binding.tvMoreReviewActGrade.setText(audienceRating + " (" + reviewCnt + "명 참여)");
            binding.rbMoreReviewActRatingBar.setRating(audienceRating / 2);

            for (int i = 0; i < reviewResult.result.size(); i++) {
                Log.e("ReviewList : ", i + "번 쨰 댓글");
                reviewDataArrayList.add(addReviewData("tmpImg", reviewResult.result.get(i).writer, reviewResult.result.get(i).time, reviewResult.result.get(i).contents, reviewResult.result.get(i).rating, reviewResult.result.get(i).recommend, reviewResult.result.get(i).id));

                if(helper.searchReview(database, reviewResult.result.get(i).id)){
                    insertCommentData("review", addReviewData("tmpImg", reviewResult.result.get(i).writer, reviewResult.result.get(i).time, reviewResult.result.get(i).contents, reviewResult.result.get(i).rating, reviewResult.result.get(i).recommend, reviewResult.result.get(i).id));
                }
            }

            setListView();

        } else {
            Log.e("데이터 길이 : ", "null");
        }
    }

    //영화등급
    private void setMovieRatingImg(int rating) {

        binding.ivMoreReviewActMovieRating12.setVisibility(View.GONE);
        binding.ivMoreReviewActMovieRating15.setVisibility(View.GONE);
        binding.ivMoreReviewActMovieRating19.setVisibility(View.GONE);
        binding.ivMoreReviewActMovieRatingAll.setVisibility(View.GONE);

        switch (rating) {
            case 12:
                binding.ivMoreReviewActMovieRating12.setVisibility(View.VISIBLE);
                break;

            case 15:
                binding.ivMoreReviewActMovieRating15.setVisibility(View.VISIBLE);
                break;

            case 19:
                binding.ivMoreReviewActMovieRating19.setVisibility(View.VISIBLE);
                break;

            case 0:
                binding.ivMoreReviewActMovieRatingAll.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void insertCommentData(String tableName, ReviewData reviewData) {

        Log.e("insertCommentData", "insertCommentData");

        if (database != null) {
            String sql = "insert into " + tableName + "(id,movie_id, profile_img, writer, time, content, star_rate, recommend) values(?,?,?,?,?,?,?,?)";
            Object[] params = {reviewData.id,movieIndex, reviewData.profileImg, reviewData.userId, reviewData.date, reviewData.comment, reviewData.rate, reviewData.like};

            database.execSQL(sql, params);

            Log.e("insertCommentData", reviewData.toString());
            Log.e("insertCommentData11", params.toString());
        }
    }

    //댓글 조회
    public void selectCommentData() {

        if (database != null) {

            String sql = "select id,movie_id, profile_img, writer, time, content, star_rate, recommend from " + "review WHERE movie_id=" + movieIndex;

            Cursor cursor = database.rawQuery(sql, null);
            Log.e("조회된 데이터 개수 : ", String.valueOf(cursor.getCount()));

            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "어플을 처음 실행 한 경우, 인터넷에 연결해야 데이터를 받아 올 수 있습니다.", Toast.LENGTH_SHORT).show();
            } else {
                reviewDataArrayList.clear();
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();

                    int id = cursor.getInt(0);
                    //int movie_index = cursor.getInt(1);
                    String image = cursor.getString(2);
                    String writer = cursor.getString(3);
                    String time = cursor.getString(4);
                    String content = cursor.getString(5);
                    float star_rate = cursor.getInt(6);
                    int recommend = cursor.getInt(7);

                    reviewDataArrayList.add(addReviewData(image, writer, time, content, star_rate, recommend,id));
                    Log.e("selectData", image + " " + writer + " " + time + " " + content + " " + star_rate);
                }
                setListView();
            }

        }
    }

    public boolean confirmNetwork() {
        int status = NetworkStatus.getConnectivityStatus(getApplicationContext());

        if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
            Log.e("연결상태", "연결 안 됨");
            return false;
        }
        return true;
    }
}
