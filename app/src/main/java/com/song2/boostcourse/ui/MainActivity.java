package com.song2.boostcourse.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.song2.boostcourse.R;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.databinding.ActivityMainBinding;
import com.song2.boostcourse.util.ReviewAdapter;

import java.util.ArrayList;

//영화 상세 페이지 activity
public class MainActivity extends AppCompatActivity {

    int REQUEST_CODE_UPLOAD_REAVIEW_ACTIVITY = 7777;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        binding.setThumbUpDown(new ThumbUpDown(15,2)); //xml 에 객체를 만들어 줌

        initialStrSetting();

        settingDataSet();
    }

    public void clickThumpUpBtn(View view) {

        Toast.makeText(this, "ThumpUp", Toast.LENGTH_SHORT).show();

        if (binding.ivMainActThumbDown.isSelected() && !binding.ivMainActThumbUp.isSelected()) {

            //싫어요 버튼을 누른 상태였을 때,
            binding.ivMainActThumbUp.setSelected(true);
            binding.ivMainActThumbDown.setSelected(false);

            //xml에서 생성된 객체 접근
            binding.getThumbUpDown().onClickThumbUp(true);
            binding.getThumbUpDown().onClickThumbDown(false);

        } else {

            //버튼을 누를 경우, state 를 현재상태의 반대 상태로 바꾸어줌
            binding.ivMainActThumbUp.setSelected(!binding.ivMainActThumbUp.isSelected());
            //선택 된 경우에는 -1, 클릭된 경우에는 +1
            binding.getThumbUpDown().onClickThumbUp(binding.ivMainActThumbUp.isSelected());
        }
    }

    public void clickThumpDownBtn(View view) {

        Toast.makeText(this, "ThumpDown", Toast.LENGTH_SHORT).show();

        if (binding.ivMainActThumbUp.isSelected() && !binding.ivMainActThumbDown.isSelected()) {

            //좋아요 버튼을 누른 상태였을 때,
            binding.ivMainActThumbDown.setSelected(true);
            binding.ivMainActThumbUp.setSelected(false);

            binding.getThumbUpDown().onClickThumbDown(true);
            binding.getThumbUpDown().onClickThumbUp(false);

        } else {
            binding.ivMainActThumbDown.setSelected(!binding.ivMainActThumbDown.isSelected());
            binding.getThumbUpDown().onClickThumbDown(binding.ivMainActThumbDown.isSelected());
        }
    }

    public void clickWriteBtn(View view){
        Toast.makeText(this, "WriteBtn", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, UploadReviewActivity.class);
        intent.putExtra("MovieTitle",binding.tvMainActTitle.getText());
        intent.putExtra("MovieRating","15"); //
        startActivityForResult(intent,REQUEST_CODE_UPLOAD_REAVIEW_ACTIVITY);
    }

    public void clickMoreBtn(View view){
        Toast.makeText(this, "MoreBtn", Toast.LENGTH_SHORT).show();
    }

    //댓글 listView Data
    public void settingDataSet(){

        ListView reviewList = binding.listViewMainActReviewList;

        ArrayList<ReviewData> data = new ArrayList<>();

        ReviewData oneData = new ReviewData();
        ReviewData twoData = new ReviewData();

        oneData.profileImg = "tmpImg";
        oneData.userId = "song2";
        oneData.date = "3시간전";
        oneData.comment = "이렇게 흥미로운 영화는 오랜만이에요!";
        oneData.rate = 4;
        oneData.like = "좋아요  "+3;

        twoData.profileImg = "https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwinndibrezjAhXaa94KHR8iAtkQjRx6BAgBEAU&url=http%3A%2F%2Fsocksplus.net%2Fproduct%2Fdetail.html%3Fproduct_no%3D13507&psig=AOvVaw2DZWjPEfCHSHcxbpnpF6CM&ust=1565115898969108";
        twoData.userId = "수면양말";
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

    public void setListView(){

    }

    //string 관리
    public void initialStrSetting(){

        //추후에 api 연결 할 데이터들
        binding.tvMainActTitle.setText("군도");
        binding.tvMainActMovieInfo.setText("2014.07.23 개봉 \n액션 / 137 분");
        binding.tvMainActMovieRateNRank.setText("5위 1.8%");
        binding.tvMainActGrade.setText("8.2");
        binding.tvMainActAudienceCnt.setText("839,399명");
        binding.tvMainActSummary.setText("군도, 백성을 구하라!" +
                "\n양반과 탐관오리들의 착취가 극에 달했던 조선 철종 13년. 힘 없는 백성의 편이 되어 세상을 바로잡고자 하는 의적떼인 군도(群盜), 지리산 추설이 있었다." +
                "\n\n쌍칼 도치 vs 백성의 적 조윤" +
                "\n잦은 자연재해, 기근과 관의 횡포까지 겹쳐 백성들의 삶이 날로 피폐해져 가는 사이, 나주 대부호의 서자로 조선 최고의 무관 출신인 조윤은 극악한 수법으로 양민들을 수탈, 삼남지방 최고의 대부호로 성장한다. 한편 소, 돼지를 잡아 근근이 살아가던 천한 백정 돌무치는 죽어도 잊지 못할 끔찍한 일을 당한 뒤 군도에 합류. 지리산 추설의 신 거성(新 巨星) 도치로 거듭난다." +
                "\n\n뭉치면 백성, 흩어지면 도적!" +
                "\n망할 세상을 뒤집기 위해, 백성이 주인인 새 세상을 향해 도치를 필두로 한 군도는 백성의 적, 조윤과 한 판 승부를 시작하는데...");
        binding.tvMainActDirector.setText("윤종빈");
        binding.tvMainActActor.setText("하정우(도치), 강동원(조윤)");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_UPLOAD_REAVIEW_ACTIVITY){
            if(resultCode == Activity.RESULT_OK){
                //
            }
        }
    }
}
