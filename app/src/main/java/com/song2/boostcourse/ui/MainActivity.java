package com.song2.boostcourse.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.song2.boostcourse.R;
import com.song2.boostcourse.data.MovieInfo;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.databinding.ActivityMainBinding;
import com.song2.boostcourse.util.ReviewAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        binding.setMovieInfo(new MovieInfo(15,2)); //xml에 객체를 만들어 줌

        //리스트 뷰 데이터 삽입
        ListView listViewMainActReviewList = binding.listViewMainActReviewList;

        settingDataSet(listViewMainActReviewList);
    }

    public void clickThumpUpBtn(View view) {

        Toast.makeText(this, "ThumpUp", Toast.LENGTH_SHORT).show();

        if (binding.ivMainActThumbDown.isSelected() && !binding.ivMainActThumbUp.isSelected()) {

            //싫어요 버튼을 누른 상태였을 때,
            binding.ivMainActThumbUp.setSelected(true);
            binding.ivMainActThumbDown.setSelected(false);

            //xml에서 생성된 객체 접근
            binding.getMovieInfo().onClickThumbUp(true);
            binding.getMovieInfo().onClickThumbDown(false);

        } else {

            //버튼을 누를 경우, state 를 현재상태의 반대 상태로 바꾸어줌
            binding.ivMainActThumbUp.setSelected(!binding.ivMainActThumbUp.isSelected());
            //선택 된 경우에는 -1, 클릭된 경우에는 +1
            binding.getMovieInfo().onClickThumbUp(binding.ivMainActThumbUp.isSelected());
        }
    }

    public void clickThumpDownBtn(View view) {

        Toast.makeText(this, "ThumpDown", Toast.LENGTH_SHORT).show();

        if (binding.ivMainActThumbUp.isSelected() && !binding.ivMainActThumbDown.isSelected()) {

            //좋아요 버튼을 누른 상태였을 때,
            binding.ivMainActThumbDown.setSelected(true);
            binding.ivMainActThumbUp.setSelected(false);

            binding.getMovieInfo().onClickThumbDown(true);
            binding.getMovieInfo().onClickThumbUp(false);

        } else {
            binding.ivMainActThumbDown.setSelected(!binding.ivMainActThumbDown.isSelected());
            binding.getMovieInfo().onClickThumbDown(binding.ivMainActThumbDown.isSelected());
        }
    }

    public void clickWriteBtn(View view){
        Toast.makeText(this, "WriteBtn", Toast.LENGTH_SHORT).show();
    }

    public void clickMoreBtn(View view){
        Toast.makeText(this, "MoreBtn", Toast.LENGTH_SHORT).show();
    }

    public void settingDataSet(ListView reviewList){

        ArrayList<ReviewData> data = new ArrayList<>();

        ReviewData oneData = new ReviewData();
        ReviewData twoData = new ReviewData();

        oneData.profile_img = "tmpImg";
        oneData.user_id = "song2";
        oneData.date = "3시간전";
        oneData.comment = "이렇게 흥미로운 영화는 오랜만이에요!";
        oneData.rate = 4;
        oneData.like = "좋아요  "+3;

        twoData.profile_img = "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwinndibrezjAhXaa94KHR8iAtkQjRx6BAgBEAU&url=http%3A%2F%2Fsocksplus.net%2Fproduct%2Fdetail.html%3Fproduct_no%3D13507&psig=AOvVaw2DZWjPEfCHSHcxbpnpF6CM&ust=1565115898969108";
        twoData.user_id = "수면양말";
        twoData.date = "어제";
        twoData.comment = "지루해 죽는줄;;;";
        twoData.rate = 0;
        twoData.like = "좋아요  "+0;

        data.add(oneData);
        data.add(twoData);
        data.add(oneData);

        //어뎁터 - 리스트 뷰 연결
        final ReviewAdapter reviewAdapter = new ReviewAdapter(data);
        reviewList.setAdapter(reviewAdapter);
    }
}
