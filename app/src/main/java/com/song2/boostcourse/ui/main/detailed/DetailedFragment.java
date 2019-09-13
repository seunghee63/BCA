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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.song2.boostcourse.R;
import com.song2.boostcourse.data.MovieDetailResult;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.data.ReviewResult;
import com.song2.boostcourse.databinding.FragmentDetailedBinding;
import com.song2.boostcourse.ui.moreReview.MoreReviewActivity;
import com.song2.boostcourse.ui.upload.UploadReviewActivity;
import com.song2.boostcourse.util.adapter.ReviewAdapter;
import com.song2.boostcourse.util.network.AppHelper;

import java.util.ArrayList;

public class DetailedFragment extends Fragment {

    static final int REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY = 7777;
    static final int REQUEST_CODE_MORE_REVIEW_ACTIVITY = 3333;

    int rating = 0;
    int movieIndex = 0;

    //Key값
    static final String MOVIETITLE = "MovieTitle";
    static final String MOVIERATING = "MovieRating";
    static final String MOVIEINDEX = "movieIndex";
    static final String WHEREFROM = "whereFrom";

    FragmentDetailedBinding binding;
    ArrayList<ReviewData> dataList = new ArrayList<>();

    public DetailedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detailed, container, false);
        binding.setDetailedFrag(this);


        getActivity().setTitle("영화 상세");


        if (getArguments() != null) {
            movieIndex = getArguments().getInt(MOVIEINDEX); // 전달한 key 값
        }

        sendRequest("/movie/readMovie","?id="+String.valueOf(movieIndex)); // 영화
        sendRequest("/movie/readCommentList","?id="+String.valueOf(movieIndex)+"&limit=2"); // 댓글

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        dataList.clear();

        if (requestCode == REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {


                sendRequest("/movie/readCommentList","?id="+String.valueOf(movieIndex)+"&limit=2"); // 댓글
                Log.e("reviewDataList data = ", String.valueOf(dataList));

                setListView();
            }
        }

        if (requestCode == REQUEST_CODE_MORE_REVIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

                sendRequest("/movie/readCommentList","?id="+String.valueOf(movieIndex)+"&limit=2"); // 댓글
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
        intent.putExtra(MOVIERATING, rating);
        intent.putExtra(MOVIEINDEX,movieIndex);
        intent.putExtra(WHEREFROM, "main");

        startActivityForResult(intent, REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY);
    }

    public void clickMoreBtn(View view) {
        //Toast.makeText(this, "MoreBtn", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), MoreReviewActivity.class);
        intent.putExtra(MOVIETITLE, binding.tvMainActTitle.getText());
        intent.putExtra(MOVIERATING, rating);
        intent.putExtra(MOVIEINDEX,movieIndex);

        startActivityForResult(intent, REQUEST_CODE_MORE_REVIEW_ACTIVITY);
    }

    //댓글 listView
    public void setListView() {
        ListView reviewList = binding.listViewMainActReviewList;

        //adapter - ListView 뷰 연결
        final ReviewAdapter reviewAdapter = new ReviewAdapter(dataList);
        reviewList.setAdapter(reviewAdapter);
    }

    //댓글 Data
    public ReviewData addReviewData(String img, String userId, String date, String comment, float rate, int like) {

        ReviewData newData = new ReviewData(img, userId, date, comment, rate, like);

        return newData;
    }

    //통신
    public void sendRequest(final String route, final String query) {

        String base = "http://boostcourse-appapi.connect.or.kr:10000";
        String url = base + route + query;

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
        }

        //get,post :
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e("route 경로! : ", "/movie/readMovie?id=" + String.valueOf(movieIndex));
                        Log.e("응답 : ", "<"+route + "> ::" + response);

                        if (route=="/movie/readMovie"){
                            movieDetailedProcessResponse(response);
                        }else if (route =="/movie/readCommentList"){
                            reviewProcessResponse(response);
                        }

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

    //data setting
    public void movieDetailedProcessResponse(String response) {
        Gson gson = new Gson();
        MovieDetailResult movieDetailResult = gson.fromJson(response, MovieDetailResult.class);

        Log.e("movieDetailResult : ", String.valueOf(movieDetailResult));

        if (movieDetailResult != null) {

            Log.e("movieDetailResult : ", String.valueOf(movieDetailResult));

            int count = movieDetailResult.result.size();

            Log.e("movieIdx 크기111 : ", String.valueOf(movieIndex));

            Glide.with(getActivity())
                    .load(movieDetailResult.result.get(0).image)
                    .into(binding.ivMainActPosterImg);

            binding.tvMainActTitle.setText(movieDetailResult.result.get(0).title);
            binding.tvMainActMovieInfo.setText(movieDetailResult.result.get(0).date + " 개봉 \n" + movieDetailResult.result.get(0).genre + " / " + movieDetailResult.result.get(0).duration + " 분");

            binding.tvMainActMovieRateNRank.setText(movieDetailResult.result.get(0).reservation_grade + "위 " + movieDetailResult.result.get(0).reservation_rate + "%");

            binding.rbMainActRatingBar.setRating((movieDetailResult.result.get(0).audience_rating) / 2);
            binding.tvMainActGrade.setText(movieDetailResult.result.get(0).audience_rating + "");

            int audience = movieDetailResult.result.get(0).audience;

            String audience_cnt;
            if (audience > 1000000) {
                audience_cnt = String.valueOf(audience / 1000000) + "," + String.valueOf((audience % 1000000) / 1000) + "," + String.valueOf((audience % 1000000) % 1000);
            } else
                audience_cnt = String.valueOf((audience % 1000000) / 1000) + "," + String.valueOf((audience % 1000000) % 1000);

            binding.tvMainActAudienceCnt.setText(audience_cnt + " 명");

            binding.tvMainActSummary.setText(movieDetailResult.result.get(0).synopsis);
            binding.tvMainActDirector.setText(movieDetailResult.result.get(0).director);
            binding.tvMainActActor.setText(movieDetailResult.result.get(0).actor);

            binding.setThumbUpDown(new ThumbUpDown(movieDetailResult.result.get(0).like, movieDetailResult.result.get(0).dislike)); //xml 에 객체를 만들어 줌

            rating = movieDetailResult.result.get(0).grade;
            setMovieRatingImg(rating);

        } else {
            Log.e("데이터 길이 : ", "null");
        }
    }

    //review data setting
    public void reviewProcessResponse(String response) {
        Gson gson = new Gson();

        ReviewResult reviewResult = gson.fromJson(response, ReviewResult.class);

        Log.e("moviereviewResult : ", response);

        if (reviewResult != null) {
            Log.e("movieDetailResult : ", String.valueOf(reviewResult));

            for (int i=0;i<reviewResult.result.size();i++){
                Log.e("ReviewList : ",i+"번 쨰 댓글");
                dataList.add(addReviewData("tmpImg", reviewResult.result.get(i).writer, reviewResult.result.get(i).time, reviewResult.result.get(i).contents, reviewResult.result.get(i).rating, reviewResult.result.get(i).recommend));
            }

            setListView();

        } else {
            Log.e("데이터 길이 : ", "null");
        }
    }

    //관람등급 이미지 setting
    private void setMovieRatingImg(int rating) {

        binding.ivMainActRating12.setVisibility(View.GONE);
        binding.ivMainActRating15.setVisibility(View.GONE);
        binding.ivMainActRating19.setVisibility(View.GONE);
        binding.ivMainActRatingAll.setVisibility(View.GONE);

        switch (rating) {
            case 12:
                binding.ivMainActRating12.setVisibility(View.VISIBLE);
                break;

            case 15:
                binding.ivMainActRating15.setVisibility(View.VISIBLE);
                break;

            case 19:
                binding.ivMainActRating19.setVisibility(View.VISIBLE);
                break;

            case 0:
                binding.ivMainActRatingAll.setVisibility(View.VISIBLE);
                break;
        }
    }
}
