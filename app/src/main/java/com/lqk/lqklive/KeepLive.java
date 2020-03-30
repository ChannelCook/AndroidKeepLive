package com.lqk.lqklive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.Process;
import android.widget.Toast;

import com.lqk.lqklive.callback.AppBackgroundCallback;
import com.lqk.lqklive.service.JobHandlerService;

import java.lang.ref.WeakReference;

public class KeepLive {
    public static void init(Context context){
        // 大于5.0采用jobService
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            registerJobService(context);
        }else{
            Toast.makeText(BaseApplication.getInstance(), "keeplive init() else", Toast.LENGTH_SHORT).show();
        }

        // 系统消息监听器 监听 前后台切换 熄屏亮屏
        BaseApplication.getInstance().registerActivityLifecycleCallbacks(new AppBackgroundCallback());
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

    private static WeakReference<Activity> weakReference;

    public static void setOnePix(Activity activity){
        if (weakReference == null){
            weakReference = new WeakReference(activity);
        }
    }

    public static void finishOnePix(){
        if (weakReference != null){
            weakReference.get().finish();
            weakReference = null;
        }
    }

    public static boolean isScreenOn(Context context){
        PowerManager powerManager = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        return powerManager.isInteractive();
    }
}
