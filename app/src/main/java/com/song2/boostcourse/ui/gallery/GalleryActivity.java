package com.song2.boostcourse.ui.gallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.song2.boostcourse.R;

public class GalleryActivity extends AppCompatActivity {

    static final String DETAILIMG = "DetailImg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final PhotoView pv = findViewById(R.id.photo_view_gallery_act_detailed_img);
        ImageView backBtn = findViewById(R.id.iv_gallery_act_back_btn);

        Glide.with(getApplicationContext()).load(getIntent().getStringExtra(DETAILIMG)).into(pv);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
