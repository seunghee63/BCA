package com.song2.boostcourse.ui.main.movieList;

import android.database.sqlite.SQLiteDatabase;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.song2.boostcourse.R;
import com.song2.boostcourse.data.MovieRank;
import com.song2.boostcourse.databinding.FragmentMovieItemBinding;
import com.song2.boostcourse.ui.main.MovieItemListener;
import com.song2.boostcourse.util.db.DatabaseHelper;

public class MovieItemFragment extends Fragment {

    FragmentMovieItemBinding binding;
    SQLiteDatabase database;

    //key값
    static String MOVIEINDEX = "movieIndex";
    static String MOVIEDATA = "movieData";

    int movieIndex;


    MovieRank rankData = new MovieRank();

    public MovieItemFragment() {
        // Required empty public constructor
    }

    public static MovieItemFragment newInstance(int index, MovieRank data) {

        Bundle bundle = new Bundle();

        bundle.putInt(MOVIEINDEX, index); // key , value
        bundle.putParcelable(MOVIEDATA, data);

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

        DatabaseHelper helper = new DatabaseHelper(getContext());
        database = helper.getWritableDatabase();

        if( getArguments() != null)
        {
            movieIndex = getArguments().getInt(MOVIEINDEX); // 전달한 key 값
            rankData = getArguments().getParcelable(MOVIEDATA);
        }

        Log.e("성공!!! RankData 내용 : ", rankData.title);

        settingMovieData();
        return binding.getRoot();
    }


    public void clickMoreBtn(View view){
        final Bundle bundle = new Bundle();
        bundle.putInt(MOVIEINDEX,movieIndex);

        ((MovieItemListener)getActivity()).replaceDetailedFrag(bundle);
    }

    public void settingMovieData(){

        Glide.with(getActivity()).load(rankData.image).into(binding.ivMovieFragItemPoster);
        binding.tvMovieFragItemRankTitle.setText(rankData.reservation_grade + ". "+  rankData.title);
        binding.tvMovieFragItemRate.setText("예매율 " + rankData.reservation_rate +"%");
        binding.tvMovieFragItemRanting.setText(rankData.grade + "세 관람가");
    }
}
