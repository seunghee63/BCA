package com.song2.boostcourse.ui.gallery;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.song2.boostcourse.R;

public class GalleryActivity extends AppCompatActivity {

    static final String DETAILIMG = "DetailImg";

    //new matrix 정보 저장 변수
    private Matrix matrix = new Matrix();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        final PhotoView pv = findViewById(R.id.photo_view_gallery_act_detailed_img); //터치리스너 이용시, 주석
        ImageView backBtn = findViewById(R.id.iv_gallery_act_back_btn);

        Glide.with(getApplicationContext()).load(getIntent().getStringExtra(DETAILIMG)).into(pv); //터치리스너 이용시, 주석

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //아래 주석은 멀티터치를위한 터치 리스너 구현 부분입니다.
/*
        final ImageView iv = findViewById(R.id.photo_view_gallery_act_detailed_img);

        Glide.with(getApplicationContext()).load(getIntent().getStringExtra(DETAILIMG)).into(pv);


        iv.setScaleType(ImageView.ScaleType.MATRIX);

        iv.setOnTouchListener(new View.OnTouchListener() {

            float old_Pos = -1f; //첫번 째 터치
            float new_Pos = -1f; //두번 째 터

            public boolean onTouch (View v, MotionEvent event){

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_UP:

                        old_Pos = -1f;

                        new_Pos = -1f;

                        break;

                    //첫번째 터치가 일어나는 이벤트
                    case MotionEvent.ACTION_DOWN:

                        break;

                    //두번째 터치가 일어나는 이벤트
                    case MotionEvent.ACTION_POINTER_DOWN:

                        float x = event.getX(0) - event.getX(1);
                        float y = event.getY(0) - event.getY(1);

                        old_Pos = (float) Math.sqrt(x * x + y * y);

                        break;

                    //두번째 터치를 떼면 일어나는 이벤트
                    case MotionEvent.ACTION_POINTER_UP:

                        old_Pos = -1f;
                        new_Pos = -1f;

                        break;

                    //터치된 상태에서 손가락 움직이면 일어나는 이벤트
                    case MotionEvent.ACTION_MOVE:

                        if (event.getPointerCount() > 1) {

                            float x_ = event.getX(0) - event.getX(1);
                            float y_ = event.getY(0) - event.getY(1);

                            new_Pos = (float) Math.sqrt(x_ * x_ + y_ * y_);

                            float scale = new_Pos / old_Pos;
                            matrix.postScale(scale, scale, 0, 0);


                            float[] values = new float[9];
                            matrix.getValues(values);


                            if (values[0] < 0.1f) {

                                scale = 0.1f;

                                values[0] = scale;
                                values[4] = scale;

                                matrix.setValues(values);
                                iv.setImageMatrix(matrix);

                            }else {
                                iv.setImageMatrix(matrix);
                            }
                        }
                        break;
                }
                return GalleryActivity.super.onTouchEvent(event);
            }
        });*/
    }
}
