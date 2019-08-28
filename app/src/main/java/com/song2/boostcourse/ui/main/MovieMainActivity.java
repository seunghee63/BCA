package com.song2.boostcourse.ui.main;

import android.os.Bundle;
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

import com.song2.boostcourse.R;
import com.song2.boostcourse.util.MoviePagerAdapter;

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

        pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(6);

        MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());

        adapter.addItem(setBundle(1));
        adapter.addItem(setBundle(2));
        adapter.addItem(setBundle(3));
        adapter.addItem(setBundle(4));
        adapter.addItem(setBundle(5));
        adapter.addItem(setBundle(6));

        pager.setAdapter(adapter);


        float d = getResources().getDisplayMetrics().density;
        int margin = (int)(30 * d);
        int marginRight = (int)(1 * d);


        pager.setClipToPadding(false);
        pager.setPadding(margin, 0, marginRight, 0);

        pager.setPageMargin(getResources().getDisplayMetrics().widthPixels / -9);

    }

    public MovieItemFragment setBundle(int index){

        MovieItemFragment fragment = new MovieItemFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("movieIndex", index); // key , value

        fragment.setArguments(bundle);

        return fragment;
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
            replaceMovieMainFrag();
        } else if (id == R.id.nav_api_item) {
        } else if (id == R.id.nav_book_item) {

        } else if (id == R.id.nav_setting_item) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceDetailedFrag(Bundle bundle) {

        detailedFragment.setArguments(bundle);

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
