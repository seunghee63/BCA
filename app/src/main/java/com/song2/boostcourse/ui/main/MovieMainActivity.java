package com.song2.boostcourse.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.song2.boostcourse.R;
import com.song2.boostcourse.util.MoviePagerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MovieMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DetailedFragment detailedFragment = new DetailedFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_movie_main_act);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        settingViewPager();

    }

    public void settingViewPager() {

        //뷰페이저
        ViewPager pager;

        ArrayList<Fragment> dataList = new ArrayList();

        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(6);

        MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());

        MovieItemFragment fragment1 = new MovieItemFragment();
        MovieItemFragment fragment2 = new MovieItemFragment();
        MovieItemFragment fragment3 = new MovieItemFragment();
        MovieItemFragment fragment4 = new MovieItemFragment();
        MovieItemFragment fragment5 = new MovieItemFragment();
        MovieItemFragment fragment6 = new MovieItemFragment();

        adapter.addItem(fragment1);
        adapter.addItem(fragment2);
        adapter.addItem(fragment3);
        adapter.addItem(fragment4);
        adapter.addItem(fragment5);
        adapter.addItem(fragment6);

        pager.setAdapter(adapter);

        pager.setClipToPadding(false);
        pager.setPadding(20, 0, 20, 0);

        pager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -9);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_movie_list_item) {
            Toast.makeText(this, "메뉴로 가야해!!!", Toast.LENGTH_SHORT).show();
            replaceMovieMainFrag();

        } else if (id == R.id.nav_api_item) {
            replaceDetailedFrag();
        } else if (id == R.id.nav_book_item) {

        } else if (id == R.id.nav_setting_item) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceDetailedFrag() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_movie_main_frag_container, detailedFragment).commit();
        //fragmentTransaction.add(R.id.fragment_movie_main_frag_container, detailedFragment).commit();
    }

    public void replaceMovieMainFrag() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_movie_main_frag_container, detailedFragment).commit();
        fragmentTransaction.remove(detailedFragment).commit();
    }
}
