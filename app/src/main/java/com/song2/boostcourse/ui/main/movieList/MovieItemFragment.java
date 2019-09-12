package com.song2.boostcourse.ui.main.movieList;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.song2.boostcourse.R;
import com.song2.boostcourse.data.MovieRankList;
import com.song2.boostcourse.databinding.FragmentMovieItemBinding;
import com.song2.boostcourse.ui.main.MovieItemListener;
import com.song2.boostcourse.util.network.AppHelper;

import java.util.HashMap;
import java.util.Map;

public class MovieItemFragment extends Fragment {

    private static final String ARG_NO = "ARG_NO";

    FragmentMovieItemBinding binding;

    //key값
    static String MOVIEINDEX = "movieIndex";
    int movieIndex;

    public MovieItemFragment() {
        // Required empty public constructor
    }

    public static MovieItemFragment newInstance(int index) {

        Bundle bundle = new Bundle();

        bundle.putInt(MOVIEINDEX, index); // key , value

        MovieItemFragment fragment = new MovieItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_item, container, false);
        binding.setMovieItemFrag(this);

        if( getArguments() != null)
        {
            movieIndex = getArguments().getInt(MOVIEINDEX); // 전달한 key 값
            //setImage(movieIndex);
        }

        sendRequest("/movie/readMovieList");

        return binding.getRoot();
    }


    public void clickMoreBtn(View view){
        final Bundle bundle = new Bundle();
        bundle.putInt(MOVIEINDEX,movieIndex);

        ((MovieItemListener)getActivity()).replaceDetailedFrag(bundle);
    }

    public void sendRequest(final String route){

        String base = "http://boostcourse-appapi.connect.or.kr:10000";
        String url = base + route;

        //volley
        if(AppHelper.requestQueue == null ){
            AppHelper.requestQueue = Volley.newRequestQueue(getActivity());
        }

        //get,post :
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("응답 : ", response);

                        if(route == "/movie/readMovieList"){
                            movieListProcessResponse(response);
                        }else if(route == "/movie/readMovie"){

                        }else if(route == ""){

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
        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

        Log.e("sendRequest","요청보냄");
    }

    public void movieListProcessResponse(String response){
        Gson gson = new Gson();
        MovieRankList movieRankList = gson.fromJson(response, MovieRankList.class);

        if (movieRankList != null){
            int count = movieRankList.result.size();

            Log.e("movieIdx 크기 : ", String.valueOf(movieIndex));
            Log.e("데이터 data : ",movieRankList.result.get(movieIndex-1).title);

            Glide.with(getActivity()).load(movieRankList.result.get(movieIndex-1).image).into(binding.ivMovieFragItemPoster);
            binding.tvMovieFragItemRankTitle.setText(movieRankList.result.get(movieIndex-1).reservation_grade + ". "+  movieRankList.result.get(movieIndex-1).title);
            binding.tvMovieFragItemRate.setText("예매율 " + movieRankList.result.get(movieIndex-1).reservation_rate +"%");
            binding.tvMovieFragItemRanting.setText(movieRankList.result.get(movieIndex-1).grade + "세 관람가");

        }else{
            Log.e("데이터 길이 : ", "null");
        }
    }

}
