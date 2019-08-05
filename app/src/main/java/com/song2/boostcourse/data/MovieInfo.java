package com.song2.boostcourse.data;

import android.databinding.ObservableInt;

public class MovieInfo {

    public ObservableInt thumbUp = new ObservableInt();
    public ObservableInt thumbDown = new ObservableInt();

    public MovieInfo(int thumbUpCnt, int thumbDownCnt){

        thumbUp.set(thumbUpCnt);
        thumbDown.set(thumbDownCnt);
    }

    public void onClickThumbUp(Boolean isPlus){

        if(isPlus)
            thumbUp.set(thumbUp.get() + 1);
        else
            thumbUp.set(thumbUp.get() - 1);

    }

    public void onClickThumbDown(Boolean isPlus){

        if(isPlus)
            thumbDown.set(thumbDown.get() + 1);
        else
            thumbDown.set(thumbDown.get() - 1);
    }

}
