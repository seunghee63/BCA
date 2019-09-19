package com.song2.boostcourse.util.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.song2.boostcourse.R;

class Holder extends RecyclerView.ViewHolder {
    protected RelativeLayout container;
    protected ImageView thumb;
    protected ImageView play_ic;


    public Holder(View view) {
        super(view);

        this.container = view.findViewById(R.id.rl_gallery_item_container);
        this.thumb = view.findViewById(R.id.iv_gallery_item_thumb);
        this.play_ic = view.findViewById(R.id.iv_gallery_item_play_ic);
    }
}
