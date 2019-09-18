package com.song2.boostcourse.util.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkStatus {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 3;


    public static int getConnectivityStatus(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //권한 추가 access network
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null){
            int type = networkInfo.getType();
            if(type == ConnectivityManager.TYPE_MOBILE){
                return TYPE_MOBILE;
            }else if (type == ConnectivityManager.TYPE_WIFI){
                return TYPE_WIFI;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static boolean confirmNetwork(Context context) {
        int status = NetworkStatus.getConnectivityStatus(context);

        if (status == NetworkStatus.TYPE_NOT_CONNECTED) {
            Log.e("연결상태", "연결 안 됨");
            return false;
        }
        return true;
    }
}
