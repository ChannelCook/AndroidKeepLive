package com.lqk.lqklive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class ServiceReceiver extends BroadcastReceiver {
    public static final String LIVE_BACKGROUND = "background";
    public static final String LIVE_FOREGROUND = "foreground";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (isAction(action, Intent.ACTION_SCREEN_OFF)){

        }else if(isAction(action, Intent.ACTION_SCREEN_ON)){

        }else if (isAction(action, LIVE_BACKGROUND)){

        }else if (isAction(action, LIVE_FOREGROUND)){

        }
    }

    private boolean isAction(String src, String dst){
        return TextUtils.equals(src, dst);
    }

    private void openOnePix(){

    }

    private void closeOnePix(){

    }

    private void playMusic(){

    }

    private void pauseMusic(){

    }

}
