package com.song2.boostcourse.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.song2.boostcourse.data.ReviewData;

import java.util.ArrayList;

public class ReviewAdapter extends BaseAdapter {

    ArrayList<ReviewData> ReviewList = new ArrayList<ReviewData>();

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
        return null;
    }
}
