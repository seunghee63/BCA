package com.song2.boostcourse.ui.main.movieList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song2.boostcourse.R;
import com.song2.boostcourse.ui.main.movieList.MovieItemFragment;
import com.song2.boostcourse.util.MoviePagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment {

    String MOVIEINDEX = "movieIndex";


    public MovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        settingViewPager(view);
        return view ;
    }

    public void settingViewPager(View v) {

        //뷰페이저
        ViewPager pager;

        pager = v.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(6);

        MoviePagerAdapter adapter = new MoviePagerAdapter(getFragmentManager());

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

    //추가하기
    public MovieItemFragment setBundle(int index){

        MovieItemFragment fragment = new MovieItemFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(MOVIEINDEX, index); // key , value

        fragment.setArguments(bundle);

        return fragment;
    }

}
