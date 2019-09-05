package com.song2.boostcourse.ui.moreReview;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.song2.boostcourse.R;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.databinding.ActivityMoreReviewBinding;
import com.song2.boostcourse.ui.main.MainActivity;
import com.song2.boostcourse.ui.upload.UploadReviewActivity;
import com.song2.boostcourse.util.adapter.ReviewAdapter;

import java.util.ArrayList;

public class MoreReviewActivity extends AppCompatActivity {

    int REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY = 4444;

    ActivityMoreReviewBinding binding;
    ArrayList<ReviewData> reviewDataArrayList = new ArrayList<>();

    int rating;
    String MOVIETITLE = "MovieTitle";
    String MOVIERATING= "MovieRating";
    String REVIEWDATALIST = "reviewDataList";
    String WHEREFROM = "whereFrom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_more_review);
        binding.setMoreReview(this);

        //main에서 넘어온 데이터 setting
        String title = getIntent().getStringExtra(MOVIETITLE);
        rating = getIntent().getIntExtra(MOVIERATING,0);
        setMovieRatingImg(rating);

        reviewDataArrayList = getIntent().getParcelableArrayListExtra(REVIEWDATALIST);
        Log.e("reviewDataList data = ", String.valueOf(reviewDataArrayList));

        //리스트뷰 setting
        //setExampleData();
        setListView();

        binding.tvMoreReviewActMovieTitle.setText(title);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){

                addReviewData("tmpImg", "song2**", "방금 전", data.getStringExtra("ReviewContents"), data.getFloatExtra("RatingStarCnt",5.0f), 0);

                setListView();
            }
        }
    }

    public void clickWriteBtn(View view){
        //Toast.makeText(this, "WriteBtn", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), UploadReviewActivity.class);
        intent.putExtra(MOVIETITLE,binding.tvMoreReviewActMovieTitle.getText());
        intent.putExtra(MOVIERATING,rating);
        intent.putExtra(WHEREFROM,"more");
        startActivityForResult(intent,REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY);
    }

    public void clickBackBtn(View view){

        //Main 으로 데이터 전달
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        setResult(Activity.RESULT_OK,intent);
        intent.putExtra(REVIEWDATALIST,reviewDataArrayList);

        finish();
    }

    public void setListView(){
        ListView reviewList = binding.listViewMoreReviewActReviewList;

        //어뎁터 - 리스트 뷰 연결
        final ReviewAdapter reviewAdapter = new ReviewAdapter(reviewDataArrayList);
        reviewList.setAdapter(reviewAdapter);
    }

    //댓글 Data
    public void addReviewData(String img, String userId, String date, String comment, float rate, int like){

        ReviewData newData = new ReviewData(img, userId, date, comment, rate, like);
        reviewDataArrayList.add(newData);
    }

    private void setMovieRatingImg(int rating){

        binding.ivMoreReviewActMovieRating12.setVisibility(View.GONE);
        binding.ivMoreReviewActMovieRating15.setVisibility(View.GONE);
        binding.ivMoreReviewActMovieRating19.setVisibility(View.GONE);
        binding.ivMoreReviewActMovieRatingAll.setVisibility(View.GONE);

        switch(rating){
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
}
