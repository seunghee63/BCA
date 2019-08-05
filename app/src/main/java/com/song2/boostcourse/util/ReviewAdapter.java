package com.song2.boostcourse.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.song2.boostcourse.R;
import com.song2.boostcourse.data.ReviewData;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private ArrayList<ReviewData> ReviewList;
    private int nListCnt = 0;

    public ReviewAdapter(ArrayList<ReviewData> _ReviewList){
        ReviewList =_ReviewList;
        nListCnt = _ReviewList.size();
    }

    @Override
    public int getCount() {
        return ReviewList.size();
    }

    @Override
    public Object getItem(int position) {
        return ReviewList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        Context context = viewGroup.getContext();

        if (view == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        view = inflater.inflate(R.layout.item_review, viewGroup, false);

        TextView user_id =  view.findViewById(R.id.tv_review_item_user_id);
        TextView review_date = view.findViewById(R.id.tv_review_item_date);
        TextView comment = view.findViewById(R.id.tv_review_item_contents);
        TextView like_cnt = view.findViewById(R.id.tv_review_item_like_cnt);
        CircleImageView profile_img = view.findViewById(R.id.cv_review_item_profile_img);
        RatingBar rating = view.findViewById(R.id.rating_bar_review_item_review_rate);

        //Glide.with(view).load(ReviewList.get(position).profile_img).into(profile_img);
        rating.setRating(ReviewList.get(position).rate);
        user_id.setText(ReviewList.get(position).user_id);
        review_date.setText(ReviewList.get(position).date);
        comment.setText(ReviewList.get(position).comment);
        like_cnt.setText(ReviewList.get(position).like);

        return view;
    }
}
