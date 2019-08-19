package com.song2.boostcourse.util;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.content.Context;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

import com.song2.boostcourse.R;
import com.song2.boostcourse.data.ReviewData;
import com.song2.boostcourse.databinding.ItemReviewBinding;

import java.util.ArrayList;


public class ReviewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
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

        //데이터바인딩
        ItemReviewBinding binding;

        //item lyout을 객체로 받아옴
        inflater = LayoutInflater.from(viewGroup.getContext());
        binding = DataBindingUtil.getBinding(view);

        if(binding == null){
            //inflate
            binding = DataBindingUtil.inflate(inflater, R.layout.item_review, viewGroup, false);
        }

        notifyDataSetChanged();

        binding.setReviewData((ReviewData)getItem(position));
        binding.executePendingBindings();

        return binding.getRoot();
    }
}
