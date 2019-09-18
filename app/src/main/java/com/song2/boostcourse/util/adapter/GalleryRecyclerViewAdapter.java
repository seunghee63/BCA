package com.song2.boostcourse.util.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.song2.boostcourse.R;
import com.song2.boostcourse.data.GalleryData;

import java.util.ArrayList;

public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.Holder> {

    Context context;
    private ArrayList<GalleryData> mData = null ;

    public GalleryRecyclerViewAdapter(ArrayList<GalleryData> list, Context context) {
        mData = list ;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_gellary, viewGroup, false);

        Holder viewHolder = new Holder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {

        if (mData.get(i).isVideo){
            Log.e("썸네일주소0",mData.get(i).thumb_img);

            String id = mData.get(i).thumb_img.substring(17);
            Log.e("썸네일주소","https://img.youtube.com/vi/"+id+"/default.jpg");

            Glide.with(context).load("https://img.youtube.com/vi/"+id+"/default.jpg").into(holder.thumb);

            holder.play_ic.setVisibility(View.VISIBLE);

            holder.container.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {

                }
            });

        }
        else if(!mData.get(i).isVideo){

            Glide.with(context).load(mData.get(i).thumb_img).into(holder.thumb);

            holder.play_ic.setVisibility(View.GONE);

            holder.container.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
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
}
