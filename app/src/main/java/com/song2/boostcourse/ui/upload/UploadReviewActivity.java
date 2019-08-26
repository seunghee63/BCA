package com.song2.boostcourse.ui.upload;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.song2.boostcourse.R;
import com.song2.boostcourse.databinding.ActivityUploadReviewBinding;
import com.song2.boostcourse.databinding.ToolbarMainBinding;
import com.song2.boostcourse.ui.moreReview.MoreReviewActivity;
import com.song2.boostcourse.ui.main.MainActivity;

public class UploadReviewActivity extends AppCompatActivity {

    ActivityUploadReviewBinding binding;
    ToolbarMainBinding tbBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_upload_review);
        binding.setUploadActivity(this);

        getReviewExtra();
    }

    public void clickSaveBtn(View view){
        Toast.makeText(this, "저장", Toast.LENGTH_SHORT).show();


        String contents = binding.etUploadActWriteReview.getText().toString();
        float ratingValue =binding.rbUploadActRatingBar.getRating();

        if(contents.equals("")){
            Toast.makeText(this, "내용을 입력 해 주세요" + contents, Toast.LENGTH_SHORT).show();

        }else if (getIntent().getStringExtra("whereFrom").equals("main")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            intent.putExtra("ReviewContents",contents);
            intent.putExtra("RatingStarCnt",ratingValue);

            setResult(Activity.RESULT_OK,intent);

            finish();
        }else {
            Intent intent = new Intent(getApplicationContext(), MoreReviewActivity.class);

            intent.putExtra("ReviewContents",contents);
            intent.putExtra("RatingStarCnt",ratingValue);

            setResult(Activity.RESULT_OK,intent);

            finish();
        }
    }

    public void clickQuitBtn(View view){
        Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void getReviewExtra(){

        String title = getIntent().getStringExtra("MovieTitle");
        String rating = getIntent().getStringExtra("MovieRating");

        binding.tvUploadActMovieTitle.setText(title);
        setMovieRatingImg(rating);
    }

    private void setMovieRatingImg(String rating){

        binding.ivUploadActMovieRating12.setVisibility(View.GONE);
        binding.ivUploadActMovieRating15.setVisibility(View.GONE);
        binding.ivUploadActMovieRating19.setVisibility(View.GONE);
        binding.ivUploadActMovieRatingAll.setVisibility(View.GONE);

        switch(rating){
            case "12":
                binding.ivUploadActMovieRating15.setVisibility(View.VISIBLE);
                break;

            case "15":
                binding.ivUploadActMovieRating15.setVisibility(View.VISIBLE);
                break;

            case "19":
                binding.ivUploadActMovieRating15.setVisibility(View.VISIBLE);
                break;

            case "all":
                binding.ivUploadActMovieRating15.setVisibility(View.VISIBLE);
                break;
        }
    }

}
