package com.lqk.lqklive;

import android.app.Application;
import android.content.Intent;
import android.os.Build;

public class BaseApplication extends Application {
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initKeepLive();
    }

    private void initKeepLive(){
        Intent foreground = new Intent(this, ForegroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForegroundService(foreground);
        }else {
            startService(foreground);
        }

        KeepLive.init(this);
    }

}
