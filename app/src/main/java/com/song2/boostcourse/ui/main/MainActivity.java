package com.song2.boostcourse.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.song2.boostcourse.R;
import com.song2.boostcourse.data.MovieList;
import com.song2.boostcourse.data.MovieRank;
import com.song2.boostcourse.data.MovieRankList;
import com.song2.boostcourse.ui.main.detailed.DetailedFragment;
import com.song2.boostcourse.ui.main.movieList.MovieListFragment;
import com.song2.boostcourse.util.network.AppHelper;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MovieItemListener {


    DetailedFragment detailedFragment = new DetailedFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("영화 목록");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_movie_main_act);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        replaceMovieMainFrag();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Log.e("TEST=",getSupportFragmentManager().findFragmentById(R.id.fragment_movie_main_frag_container).toString());

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(false){
            //replaceMovieMainFrag();
        } else{
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movie_list_item) {
            replaceMovieMainFrag();
        } else if (id == R.id.nav_api_item) {
        } else if (id == R.id.nav_book_item) {
        } else if (id == R.id.nav_setting_item) {
        }

        replaceMovieMainFrag();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void replaceDetailedFrag(Bundle bundle) {

        detailedFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_movie_main_frag_container, detailedFragment).commit();
    }

    @Override
    public void replaceMovieMainFrag() {
        MovieListFragment listFragment = new MovieListFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_movie_main_frag_container, listFragment).commit();
        setTitle("영화 목록");
    }

    public void sendRequest(final String route){

        String base = "http://boostcourse-appapi.connect.or.kr:10000";
        String url = base + route;

        //url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20120101";
        //volley
        if(AppHelper.requestQueue == null ){
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
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
                        }else if(route == ""){

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
        ){
            //request 객체 안에 메소드 재정의
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

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
            //String data = movieRankList.result.indexOf(0);

            Log.e("데이터 길이 : ", String.valueOf(count));
            Log.e("데이터 data : ",movieRankList.result.get(1).title);
            //Log.e("데이터 non : ",String.valueOf(movieRankList.result.));

        }else{
            Log.e("데이터 길이 : ", "ㅅㅂ?");
        }
    }
}
