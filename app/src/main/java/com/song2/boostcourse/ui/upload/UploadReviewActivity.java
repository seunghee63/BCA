package com.song2.boostcourse.ui.upload;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.song2.boostcourse.R;
import com.song2.boostcourse.databinding.ActivityUploadReviewBinding;
import com.song2.boostcourse.ui.main.MainActivity;
import com.song2.boostcourse.ui.moreReview.MoreReviewActivity;
import com.song2.boostcourse.util.network.AppHelper;

import java.util.HashMap;
import java.util.Map;

public class UploadReviewActivity extends AppCompatActivity {

    ActivityUploadReviewBinding binding;

    int rating;
    String MOVIETITLE = "MovieTitle";
    String MOVIERATING= "MovieRating";
    String WHEREFROM = "whereFrom";
    String MOVIEINDEX = "movieIndex";

    String REVIEWCONTENTS = "ReviewContents";
    String REVIEWSTARTCNT = "RatingStarCnt";

    String contents;
    float ratingValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_upload_review);
        binding.setUploadActivity(this);

        getReviewExtra();
    }

    public void clickSaveBtn(View view){

        Toast.makeText(this, "저장", Toast.LENGTH_SHORT).show();

         contents = binding.etUploadActWriteReview.getText().toString();
         ratingValue = binding.rbUploadActRatingBar.getRating();

        if(contents.equals("")){
            Toast.makeText(this, "내용을 입력 해 주세요" + contents, Toast.LENGTH_SHORT).show();

        }else if (getIntent().getStringExtra(WHEREFROM).equals("main")) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            intent.putExtra(REVIEWCONTENTS,contents);
            intent.putExtra(REVIEWSTARTCNT,ratingValue);

            setResult(Activity.RESULT_OK,intent);

            sendRequest("/movie/createComment");
            finish();
        }else {
            Intent intent = new Intent(getApplicationContext(), MoreReviewActivity.class);

            intent.putExtra(REVIEWCONTENTS,contents);
            intent.putExtra(REVIEWSTARTCNT,ratingValue);

            setResult(Activity.RESULT_OK,intent);

            sendRequest("/movie/createComment");
            finish();
        }
    }

    public void clickQuitBtn(View view){
        Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show();

        finish();
    }

    private void getReviewExtra(){

        String title = getIntent().getStringExtra(MOVIETITLE);
        binding.tvUploadActMovieTitle.setText(title);

        rating = getIntent().getIntExtra(MOVIERATING,0);
        setMovieRatingImg(rating);
        Log.e("uploaduploadupload",String.valueOf(rating));
    }

    public void sendRequest(final String route) {

        String base = "http://boostcourse-appapi.connect.or.kr:10000";
        String url = base + route;

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplication());
        }

        //get,post :
        StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("upload Review 응답 status : ", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("에러 : ", error.toString());
                    }
                }
        ){
            //request 객체 안에 메소드 재정의
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                //영화 아이디 받아오기
                params.put("id", String.valueOf(getIntent().getIntExtra(MOVIEINDEX,0)));
                params.put("writer","0603yang");
                //params.put("time",); //없어도 됨!
                params.put("rating",String.valueOf(ratingValue));
                params.put("contents",contents);
                Log.e("contents",contents);

                return params;
            }
        };

        //아래 두 줄은 일반적으로 AppHelper에 넣어서 관리. 메소드 호출해서 여기서 씀..ㅎ
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

        Log.e("sendRequest","요청보냄");

    }

    public void setMovieRatingImg(int rating){

        Log.e("isIn","???");
        binding.ivUploadActMovieRating12.setVisibility(View.GONE);
        binding.ivUploadActMovieRating15.setVisibility(View.GONE);
        binding.ivUploadActMovieRating19.setVisibility(View.GONE);
        binding.ivUploadActMovieRatingAll.setVisibility(View.GONE);

        switch(rating){
            case 12:
                binding.ivUploadActMovieRating15.setVisibility(View.VISIBLE);
                break;

            case 15:
                binding.ivUploadActMovieRating15.setVisibility(View.VISIBLE);
                break;

            case 19:
                binding.ivUploadActMovieRating19.setVisibility(View.VISIBLE);
                break;

            case 0:
                binding.ivUploadActMovieRatingAll.setVisibility(View.VISIBLE);
                break;
        }
    }

}
