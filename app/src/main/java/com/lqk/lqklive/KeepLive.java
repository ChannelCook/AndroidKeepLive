package com.lqk.lqklive;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Process;
import android.widget.Toast;

import com.lqk.lqklive.service.JobHandlerService;

public class KeepLive {
    public static void init(Context context){
        // 大于5.0采用jobService
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            registerJobService(context);
        }else{
            Toast.makeText(BaseApplication.getInstance(), "keeplive init() else", Toast.LENGTH_SHORT).show();
        }
    }

    private static void registerJobService(Context context){
        Intent intent = new Intent(context, JobHandlerService.class);
        startIntentService(intent);
    }

    private static void startIntentService(Intent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            BaseApplication.getInstance().startForegroundService(intent);
        }else{
            BaseApplication.getInstance().startService(intent);
        }
    }

    private static void registerWorker(Context context){

    }

    /**
     * 获取id
     * @return
     */
    public static int getId(){
        int id = -1;
        if (Process.myUid() <= 0){
            id = Process.myPid();
        }else{
            id = Process.myUid();
        }
        return id;
    }
}
