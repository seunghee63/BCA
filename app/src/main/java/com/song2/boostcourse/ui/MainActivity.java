package com.song2.boostcourse.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.song2.boostcourse.R;
import com.song2.boostcourse.data.MovieInfo;
import com.song2.boostcourse.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        binding.setMovieInfo(new MovieInfo(15,2));

        //binding.listViewMainActReviewList;

    }

    public void clickThumpUpBtn(View view) {

        Toast.makeText(this, "ThumpUp", Toast.LENGTH_SHORT).show();

        if (binding.ivMainActThumbDown.isSelected() && !binding.ivMainActThumbUp.isSelected()) {

            //싫어요 버튼을 누른 상태였을 때,
            binding.ivMainActThumbUp.setSelected(true);
            binding.ivMainActThumbDown.setSelected(false);

            binding.getMovieInfo().onClickThumbUp(true);
            binding.getMovieInfo().onClickThumbDown(false);

        } else {
            binding.ivMainActThumbUp.setSelected(!binding.ivMainActThumbUp.isSelected());
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
}
