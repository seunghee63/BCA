package com.song2.boostcourse.util.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NetworkManager {


    final static String MOVIEINFO_ROUTING = "/movie/readMovie";
    final static String COMMENT_ROUTING = "/movie/readCommentList";
    final static String MOVIELIST_ROUTING = "/movie/readMovieList";

    Context context;
    String result = "";

    public NetworkManager(Context context) {
        this.context = context;
    }

    //GET
    public String sendGetRequest(final String route, final String query) {

        String base = "http://boostcourse-appapi.connect.or.kr:10000";
        String url = base + route + query;

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(context);
        }

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url
                ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("응답 : ", "<" + route + "> ::" + response);

                        result = response;

                        if (route == MOVIEINFO_ROUTING) {

                        } else if (route == COMMENT_ROUTING) {

                        } else if (route == MOVIELIST_ROUTING) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("에러 : ", error.toString());

                        //에러 케이스 나누기
                    }
                }
        );

        request.setShouldCache(true);
        AppHelper.requestQueue.add(request);

        Log.e("sendRequest", "요청보냄");

        return result;
    }


    //POST
    public void sendPostRequest(final String route) {

        String base = "http://boostcourse-appapi.connect.or.kr:10000";
        String url = base + route;

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(context);
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
        ) {
            //request 객체 안에 메소드 재정의
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

/*                //영화 아이디 받아오기
                params.put("id", String.valueOf(getIntent().getIntExtra(MOVIEINDEX,0)));
                params.put("writer","0603yang");
                //params.put("time",); //없어도 됨!
                params.put("rating",String.valueOf(ratingValue));
                params.put("contents",contents);
                Log.e("contents",contents);*/

                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);

        Log.e("sendRequest", "댓글 작성 request!!!");

    }
}
