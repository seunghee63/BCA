package com.song2.boostcourse.ui.main.movieList;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song2.boostcourse.R;
import com.song2.boostcourse.databinding.FragmentMovieItemBinding;
import com.song2.boostcourse.ui.main.MovieItemListener;

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
            setImage(movieIndex);
        }

        return binding.getRoot();
    }

    public void clickMoreBtn(View view){
        final Bundle bundle = new Bundle();
        bundle.putInt(MOVIEINDEX,movieIndex);

        ((MovieItemListener)getActivity()).replaceDetailedFrag(bundle);
    }

    public void setImage(int index){

        if(index == 1){
            binding.ivMovieFragItemPoster.setImageResource(R.drawable.image1);
            binding.tvMovieFragItemRankTitle.setText("1. 군도");

        }else if(index == 2){
            binding.ivMovieFragItemPoster.setImageResource(R.drawable.image2);
            binding.tvMovieFragItemRankTitle.setText("2. 공조");

        }else if(index == 3){
            binding.ivMovieFragItemPoster.setImageResource(R.drawable.image3);
            binding.tvMovieFragItemRankTitle.setText("3. 더킹");

        }else if(index == 4){
            binding.ivMovieFragItemPoster.setImageResource(R.drawable.image4);
            binding.tvMovieFragItemRankTitle.setText("3. 레지던트 이블");

        }else if(index == 5){
            binding.ivMovieFragItemPoster.setImageResource(R.drawable.image5);
            binding.tvMovieFragItemRankTitle.setText("5. 럭키");

        }else if(index == 6){
            binding.ivMovieFragItemPoster.setImageResource(R.drawable.image6);
            binding.tvMovieFragItemRankTitle.setText("6. 아수라");

        }
    }
}
