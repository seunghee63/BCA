package com.song2.boostcourse.ui.main.detailed;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.song2.boostcourse.R;
import com.song2.boostcourse.data.MovieDetail;
import com.song2.boostcourse.data.MovieDetailResult;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.data.ReviewResult;
import com.song2.boostcourse.databinding.FragmentDetailedBinding;
import com.song2.boostcourse.ui.moreReview.MoreReviewActivity;
import com.song2.boostcourse.ui.upload.UploadReviewActivity;
import com.song2.boostcourse.util.adapter.ReviewAdapter;
import com.song2.boostcourse.util.db.DatabaseHelper;
import com.song2.boostcourse.util.db.MovieInfoTable;
import com.song2.boostcourse.util.db.ReviewTable;
import com.song2.boostcourse.util.network.AppHelper;
import com.song2.boostcourse.util.network.NetworkStatus;

import java.util.ArrayList;

public class DetailedFragment extends Fragment {

    static final int REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY = 7777;
    static final int REQUEST_CODE_MORE_REVIEW_ACTIVITY = 3333;

    SQLiteDatabase database;
    DatabaseHelper helper;
    Boolean network = false;

    int rating = 0;
    int movieIndex = 0;
    float audienceRating;

    //Key값
    static final String AUDIENCERATING = "AudienceRating";
    static final String MOVIETITLE = "MovieTitle";
    static final String MOVIERATING = "MovieRating";
    static final String MOVIEINDEX = "movieIndex";
    static final String WHEREFROM = "whereFrom";

    //
    MovieInfoTable movieInfoTable;
    ReviewTable reviewTable;

    FragmentDetailedBinding binding;
    ArrayList<ReviewData> dataList ;

    public DetailedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detailed, container, false);
        binding.setDetailedFrag(this);

        helper = new DatabaseHelper(getContext());
        database = helper.getWritableDatabase();

        movieInfoTable = new MovieInfoTable(getContext());
        reviewTable = new ReviewTable(getContext());

        getActivity().setTitle("영화 상세");

        dataList = new ArrayList<>();

        if (getArguments() != null) {
            movieIndex = getArguments().getInt(MOVIEINDEX); // 전달한 key 값
        }

        network = confirmNetwork();
        if (network) {
            sendRequest("/movie/readMovie", "?id=" + movieIndex); // 영화
            sendRequest("/movie/readCommentList", "?id=" + movieIndex + "&limit=2"); // 댓글
        } else {
            //selectData();
            if (movieInfoTable.selectData(getContext(), movieIndex) != null) {
                setMovieData(movieInfoTable.selectData(getContext(), movieIndex));
            }

            dataList = reviewTable.selectCommentData(getContext(), movieIndex, dataList);
            if (dataList != null)
                setListView();
        }

        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

                dataList.clear();

                if (network) {
                    sendRequest("/movie/readCommentList", "?id=" + movieIndex + "&limit=2"); // 댓글
                } else {
                    dataList = reviewTable.selectCommentData(getContext(), movieIndex, dataList);
                }

                Log.e("reviewDataList data = ", String.valueOf(dataList));

                if (dataList != null)
                    setListView();
            }
        }


        if (requestCode == REQUEST_CODE_MORE_REVIEW_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {

                dataList.clear();

                if (network) {
                    sendRequest("/movie/readCommentList", "?id=" + movieIndex + "&limit=2"); // 댓글
                } else {
                    dataList = reviewTable.selectCommentData(getContext(), movieIndex, dataList);
                }

                Log.e("reviewDataList data = ", String.valueOf(dataList));

                if (dataList != null)
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

        if (network) {
            Intent intent = new Intent(getContext(), UploadReviewActivity.class);
            intent.putExtra(MOVIETITLE, binding.tvMainActTitle.getText());
            intent.putExtra(MOVIERATING, rating);
            intent.putExtra(MOVIEINDEX, movieIndex);
            intent.putExtra(WHEREFROM, "main");

            startActivityForResult(intent, REQUEST_CODE_UPLOAD_REVIEW_ACTIVITY);
        } else {
            Toast.makeText(getContext(), "network에 연결된 상태에서만 댓글작성이 가능합니다..", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickMoreBtn(View view) {
        //Toast.makeText(this, "MoreBtn", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), MoreReviewActivity.class);
        intent.putExtra(MOVIETITLE, binding.tvMainActTitle.getText());
        intent.putExtra(MOVIERATING, rating);
        intent.putExtra(MOVIEINDEX, movieIndex);
        intent.putExtra(AUDIENCERATING, audienceRating);

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
    public ReviewData addReviewData(String img, String userId, String date, String comment, float rate, int like, int id) {

        ReviewData newData = new ReviewData(img, userId, date, comment, rate, like, movieIndex, id);

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
                        Log.e("응답 : ", "<" + route + "> ::" + response);

                        if (route == "/movie/readMovie") {
                            movieDetailedProcessResponse(response);
                        } else if (route == "/movie/readCommentList") {
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

            setMovieData(movieDetailResult.result.get(0));
            //insertData("movie", movieDetailResult.result.get(0));
            movieInfoTable.insertData(movieIndex, movieDetailResult.result.get(0));

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

            for (int i = 0; i < reviewResult.result.size(); i++) {
                Log.e("ReviewList : ", i + "번 쨰 댓글");
                dataList.add(addReviewData("tmpImg", reviewResult.result.get(i).writer, reviewResult.result.get(i).time, reviewResult.result.get(i).contents, reviewResult.result.get(i).rating, reviewResult.result.get(i).recommend, reviewResult.result.get(i).id));

                if (helper.searchReview(database, reviewResult.result.get(i).id)) {
                    reviewTable.insertCommentData(movieIndex, addReviewData("tmpImg", reviewResult.result.get(i).writer, reviewResult.result.get(i).time, reviewResult.result.get(i).contents, reviewResult.result.get(i).rating, reviewResult.result.get(i).recommend, reviewResult.result.get(i).id));
                }
            }
            setListView();

        } else {
            Log.e("데이터 길이 : ", "null");
        }
    }

    public void setMovieData(MovieDetail movieDetail) {

        Log.e("movieDetailResult : ", String.valueOf(movieDetail));

        Log.e("movieIdx 크기111 : ", String.valueOf(movieDetail));

        Glide.with(getActivity())
                .load(movieDetail.image)
                .into(binding.ivMainActPosterImg);

        binding.tvMainActTitle.setText(movieDetail.title);
        binding.tvMainActMovieInfo.setText(movieDetail.date + " 개봉 \n"
                + movieDetail.genre + " / " + movieDetail.duration + " 분");

        binding.tvMainActMovieRateNRank.setText(movieDetail.reservation_grade + "위 "
                + movieDetail.reservation_rate + "%");

        audienceRating = (movieDetail.audience_rating);
        binding.rbMainActRatingBar.setRating((movieDetail.audience_rating) / 2);
        binding.tvMainActGrade.setText(movieDetail.audience_rating + "");

        int audience = movieDetail.audience;

        String audience_cnt;
        if (audience > 1000000) {
            audience_cnt = (audience / 1000000) + "," + String.format("%03d", (audience % 1000000) / 1000) + "," + String.format("%03d", (audience % 1000000) % 1000);
        } else if (audience < 1000) {
            audience_cnt = String.valueOf(audience);
        } else
            audience_cnt = ((audience % 1000000) / 1000) + "," + String.format("%03d", (audience % 1000000) % 1000);

        binding.tvMainActAudienceCnt.setText(audience_cnt + " 명");

        binding.tvMainActSummary.setText(movieDetail.synopsis);
        binding.tvMainActDirector.setText(movieDetail.director);
        binding.tvMainActActor.setText(movieDetail.actor);

        binding.setThumbUpDown(new ThumbUpDown(movieDetail.like, movieDetail.dislike)); //xml 에 객체를 만들어 줌

        rating = movieDetail.grade;
        setMovieRatingImg(rating);

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

    public boolean confirmNetwork() {
        int status = NetworkStatus.getConnectivityStatus(getContext());

        if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
            Log.e("연결상태", "연결 안 됨");
            return false;
        }
        return true;
    }


}
