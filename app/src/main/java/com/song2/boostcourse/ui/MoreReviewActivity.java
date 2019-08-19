package com.song2.boostcourse.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.song2.boostcourse.R;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.databinding.ActivityMoreReviewBinding;
import com.song2.boostcourse.util.ReviewAdapter;

import java.util.ArrayList;

public class MoreReviewActivity extends AppCompatActivity {

    ActivityMoreReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_more_review);
        binding.setMoreReview(this);

        ArrayList<ReviewData> reviewDataArrayList = new ArrayList<>();

        setExampleData(reviewDataArrayList);
        setListView(reviewDataArrayList);

        String title = getIntent().getStringExtra("MovieTitle");
        binding.tvMoreReviewActMovieTitle.setText(title);
    }

    public void setListView(ArrayList<ReviewData> reviewDataArrayList){
        ListView reviewList = binding.listViewMoreReviewActReviewList;

        //어뎁터 - 리스트 뷰 연결
        final ReviewAdapter reviewAdapter = new ReviewAdapter(reviewDataArrayList);
        reviewList.setAdapter(reviewAdapter);
    }

    public ArrayList<ReviewData> setExampleData(ArrayList<ReviewData> dataList){
        dataList.add(addReviewData("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwinndibrezjAhXaa94KHR8iAtkQjRx6BAgBEAU&url=http%3A%2F%2Fsocksplus.net%2Fproduct%2Fdetail.html%3Fproduct_no%3D13507&psig=AOvVaw2DZWjPEfCHSHcxbpnpF6CM&ust=1565115898969108", "aaa**", "어제", "아 재미없어...", 2.0f, 0));
        dataList.add(addReviewData("tmpImg", "song2**", "3시간 전", "이렇게 흥미로운 영화는 오랜만이에요!", 4.5f, 3));
        dataList.add(addReviewData("testImg", "abab**", "1시간 전", "무난 했어요", 3.5f, 0));

        return dataList;
    }

    //댓글 Data
    public ReviewData addReviewData(String img, String userId, String date, String comment, float rate, int like){

        ReviewData newData = new ReviewData();

        newData.profileImg = img;
        newData.userId = userId;
        newData.date = date;
        newData.comment = comment;
        newData.rate = rate;
        newData.like = "좋아요   " + like;

        return newData;
    }

    //관람등급 이미지 setting - api 연결 후 사용 할 함수
    private void setMovieRatingImg(String rating){

        binding.ivMoreReviewActMovieRating12.setVisibility(View.GONE);
        binding.ivMoreReviewActMovieRating15.setVisibility(View.GONE);
        binding.ivMoreReviewActMovieRating19.setVisibility(View.GONE);
        binding.ivMoreReviewActMovieRatingAll.setVisibility(View.GONE);

        switch(rating){
            case "12":
                binding.ivMoreReviewActMovieRating12.setVisibility(View.VISIBLE);
                break;

            case "15":
                binding.ivMoreReviewActMovieRating15.setVisibility(View.VISIBLE);
                break;

            case "19":
                binding.ivMoreReviewActMovieRating19.setVisibility(View.VISIBLE);
                break;

            case "all":
                binding.ivMoreReviewActMovieRatingAll.setVisibility(View.VISIBLE);
                break;
        }
    }

}
