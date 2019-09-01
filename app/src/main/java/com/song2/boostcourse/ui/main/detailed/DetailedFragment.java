package com.song2.boostcourse.ui.main.detailed;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.song2.boostcourse.R;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.databinding.FragmentDetailedBinding;
import com.song2.boostcourse.ui.moreReview.MoreReviewActivity;
import com.song2.boostcourse.ui.upload.UploadReviewActivity;
import com.song2.boostcourse.util.ReviewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailedFragment extends Fragment {

    int REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY = 7777;
    int REQUEST_CODE_MORE_REVIEW_ACTIVITY = 3333;

    int movieIndex = 0;

    //Key값
    String MOVIETITLE = "MovieTitle";
    String MOVIERATING= "MovieRating";
    String MOVIEINDEX = "movieIndex";
    String REVIEWDATALIST = "reviewDataList";
    String WHEREFROM = "whereFrom";

    FragmentDetailedBinding binding;
    ArrayList<ReviewData> dataList = new ArrayList<>();

    public DetailedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detailed, container, false);
        binding.setDetailedFrag(this);

        binding.setThumbUpDown(new ThumbUpDown(16, 2)); //xml 에 객체를 만들어 줌

        initialStrSetting();

        setExampleData();
        setListView();

        if (getArguments() != null) {
            movieIndex = getArguments().getInt(MOVIEINDEX); // 전달한 key 값
            setImage(movieIndex);
        }

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

                dataList.add(addReviewData("tmpImg", "song2**", "방금 전", data.getStringExtra("ReviewContents"), data.getFloatExtra("RatingStarCnt", 5.0f), 0));

                setListView();
            }
        }

        if (requestCode == REQUEST_CODE_MORE_REVIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

                dataList = data.getParcelableArrayListExtra(REVIEWDATALIST);
                Log.e("reviewDataList data = ", String.valueOf(dataList));

                setListView();
            }
        }
    }

    public void clickThumpUpBtn(View view) {

        //Toast.makeText(this, "ThumpUp", Toast.LENGTH_SHORT).show();

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

        //Toast.makeText(this, "ThumpDown", Toast.LENGTH_SHORT).show();

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

    public void clickWriteBtn(View view) {
        //Toast.makeText(this, "WriteBtn", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), UploadReviewActivity.class);
        intent.putExtra(MOVIETITLE, binding.tvMainActTitle.getText());
        intent.putExtra(MOVIERATING, "15");
        intent.putExtra(WHEREFROM, "main");
        startActivityForResult(intent, REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY);
    }

    public void clickMoreBtn(View view) {
        //Toast.makeText(this, "MoreBtn", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), MoreReviewActivity.class);
        intent.putExtra(MOVIETITLE, binding.tvMainActTitle.getText());

        //기존의 댓글 데이터 전송
        intent.putExtra(REVIEWDATALIST, dataList);
        startActivityForResult(intent, REQUEST_CODE_MORE_REVIEW_ACTIVITY);
        //        startActivity(intent);
    }

    //댓글 listView
    public void setListView() {
        ListView reviewList = binding.listViewMainActReviewList;

        //adapter - ListView 뷰 연결
        final ReviewAdapter reviewAdapter = new ReviewAdapter(dataList);
        reviewList.setAdapter(reviewAdapter);
    }

    public void setExampleData() {
        dataList.add(addReviewData("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwinndibrezjAhXaa94KHR8iAtkQjRx6BAgBEAU&url=http%3A%2F%2Fsocksplus.net%2Fproduct%2Fdetail.html%3Fproduct_no%3D13507&psig=AOvVaw2DZWjPEfCHSHcxbpnpF6CM&ust=1565115898969108", "aaa**", "어제", "아 재미없어...", 2.0f, 0));
        dataList.add(addReviewData("tmpImg", "song2**", "3시간 전", "이렇게 흥미로운 영화는 오랜만이에요!", 4.5f, 3));
        dataList.add(addReviewData("testImg", "abab**", "1시간 전", "무난 했어요", 3.5f, 0));
    }

    //댓글 Data
    public ReviewData addReviewData(String img, String userId, String date, String comment, float rate, int like) {

        ReviewData newData = new ReviewData(img, userId, date, comment, rate, like);

        return newData;
    }

    //string 데이터 임시 저장
    //추후에 통신을 통해 받아 올 데이터들
    public void initialStrSetting() {

        //제목, rate, rating
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

    public void setImage(int index) {

        if (index == 1) {
            binding.ivMainActPosterImg.setImageResource(R.drawable.image11);
            binding.tvMainActTitle.setText("군도");

        } else if (index == 2) {
            binding.ivMainActPosterImg.setImageResource(R.drawable.image2);
            binding.tvMainActTitle.setText("공조");

        } else if (index == 3) {
            binding.ivMainActPosterImg.setImageResource(R.drawable.image3);
            binding.tvMainActTitle.setText("더킹");

        } else if (index == 4) {
            binding.ivMainActPosterImg.setImageResource(R.drawable.image4);
            binding.tvMainActTitle.setText("레지던트 이블");

        } else if (index == 5) {
            binding.ivMainActPosterImg.setImageResource(R.drawable.image5);
            binding.tvMainActTitle.setText("럭키");

        } else if (index == 6) {
            binding.ivMainActPosterImg.setImageResource(R.drawable.image6);
            binding.tvMainActTitle.setText("아수라");
        }
    }


}
