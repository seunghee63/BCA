package com.song2.boostcourse.ui.main.movieList;

import android.database.Cursor;
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

        DatabaseHelper helper = new DatabaseHelper(getContext(), "movieRank",null,1);
        database = helper.getWritableDatabase();

        if( getArguments() != null)
        {
            movieIndex = getArguments().getInt(MOVIEINDEX); // 전달한 key 값
            rankData = getArguments().getParcelable(MOVIEDATA);
            selectData();
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

    public void selectData(){

        if (database != null){

            String sql = "select image, title, reservation_grade, reservation_rate, grade from "+ "movieRank";

            //sql에 ?를 넣고 null 대신 ?를 대체 할 파라미터를 넣는 방법도 가능!
            Cursor cursor = database.rawQuery(sql, null);
            Log.e("조회된 데이터 개수 : " , String.valueOf(cursor.getCount()));

            cursor.move(movieIndex);

            String title = cursor.getString(1);
            String reservation_grade = cursor.getString(2);
            String reservation_rate = cursor.getString(3);
            String grade = cursor.getString(4);

            Log.e("selectData", "movieIndex"+ movieIndex + ", "+ title + ", "+  reservation_grade + ", "+  reservation_rate + ", "+ grade);

        }

    }
}
