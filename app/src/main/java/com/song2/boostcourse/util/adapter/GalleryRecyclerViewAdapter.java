package com.song2.boostcourse.util.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.song2.boostcourse.ui.GalleryActivity;

import java.util.ArrayList;

public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.ViewHolder> {

    Context context;
    private ArrayList<GalleryData> mData = null ;


    public GalleryRecyclerViewAdapter(ArrayList<GalleryData> list, Context context) {
        mData = list ;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_gallery, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if (mData.get(position).isVideo){

            //String uri = mData.get(position).thumb_img;
            String id = mData.get(position).thumb_img.substring(17);

            Log.e("썸네일주소","https://img.youtube.com/vi/"+id+"/default.jpg");

            Glide.with(context).load("https://img.youtube.com/vi/"+id+"/default.jpg").into(holder.thumb);

            holder.play_ic.setVisibility(View.VISIBLE);

            holder.container.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse(mData.get(position).thumb_img))
                            .setPackage("com.google.android.youtube");

                    context.startActivity(intent);
                }
            });

        }
        else if(!mData.get(position).isVideo){

            Glide.with(context).load(mData.get(position).thumb_img).into(holder.thumb);

            holder.play_ic.setVisibility(View.GONE);

            holder.container.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {

                    Intent intent = new Intent(context, GalleryActivity.class);
                    intent.putExtra("DetailImg",mData.get(position).thumb_img);
                    context.startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected RelativeLayout container;
        protected ImageView thumb;
        protected ImageView play_ic;

        public ViewHolder(View view) {
            super(view);

            this.container = view.findViewById(R.id.rl_gallery_item_container);
            this.thumb = view.findViewById(R.id.iv_gallery_item_thumb);
            this.play_ic = view.findViewById(R.id.iv_gallery_item_play_ic);

        }
    }

}
