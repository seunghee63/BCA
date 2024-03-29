package com.song2.boostcourse.ui.main;

import android.database.sqlite.SQLiteDatabase;
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

import com.song2.boostcourse.R;
import com.song2.boostcourse.ui.main.detailed.DetailedFragment;
import com.song2.boostcourse.ui.main.movieList.MovieListFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MovieItemListener {


    DetailedFragment detailedFragment = new DetailedFragment();
    SQLiteDatabase database;
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
        Log.e("TEST=",String.valueOf(getFragmentManager().getBackStackEntryCount()));

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Log.e("MainAct 11", "backpress 들어왔는지 확인용");
            drawer.closeDrawer(GravityCompat.START);
        }else if(getSupportFragmentManager().findFragmentById(R.id.fragment_movie_main_frag_container) instanceof DetailedFragment){
            Log.e("MainAct 22", "backpress 들어왔는지 확인용");
            replaceMovieMainFrag();
        }else{
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void replaceDetailedFrag(Bundle bundle) {

        detailedFragment.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_movie_main_frag_container, detailedFragment)
                .commit();
    }

    @Override
    public void replaceMovieMainFrag() {
        MovieListFragment listFragment = new MovieListFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_movie_main_frag_container, listFragment)
                .commit();
        setTitle("영화 목록");
    }

}
