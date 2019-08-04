package com.song2.boostcourse;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.song2.boostcourse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
    }

    public void clickThumpUpBtn(View view){

        Toast.makeText(this, "ThumpUp +1", Toast.LENGTH_SHORT).show();

        //1. 아무 버튼도 클릭하지 않은 상태였을 때,
        //2. 좋아요 버튼을 누른 상태였을 때,
        //3. 싫어요 버튼을 누른 상태였을 때,

    }
}
