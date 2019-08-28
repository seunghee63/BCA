package com.song2.boostcourse.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.song2.boostcourse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieItemFragment extends Fragment {

    private static final String ARG_NO = "ARG_NO";

    int movieIndex;
    ImageView posterImg;
    TextView title;

    public MovieItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_item, container, false);


        posterImg = view.findViewById(R.id.iv_movie_frag_item_poster);
        title = view.findViewById(R.id.tv_movie_frag_item_rank_title);

        if( getArguments() != null)
        {
            movieIndex  = getArguments().getInt("movieIndex"); // 전달한 key 값
            setImage(movieIndex);
        }



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RelativeLayout moreBtn = view.findViewById(R.id.rl_movie_item_frag_more_btn);


        final Bundle bundle = new Bundle();
        bundle.putInt("movieIndex",movieIndex);

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MovieMainActivity)getActivity()).replaceDetailedFrag(bundle);
            }
        });

    }


    public void setImage(int index){

        if(index == 1){
            posterImg.setImageResource(R.drawable.image1);
            title.setText("1. 군도");

        }else if(index == 2){
            posterImg.setImageResource(R.drawable.image2);
            title.setText("2. 공조");

        }else if(index == 3){
            posterImg.setImageResource(R.drawable.image3);
            title.setText("3. 더킹");

        }else if(index == 4){
            posterImg.setImageResource(R.drawable.image4);
            title.setText("3. 레지던트 이블");

        }else if(index == 5){
            posterImg.setImageResource(R.drawable.image5);
            title.setText("5. 럭키");

        }else if(index == 6){
            posterImg.setImageResource(R.drawable.image6);
            title.setText("6. 아수");

        }
    }
}
