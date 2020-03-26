package com.lqk.lqklive;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ForegroundService extends Service {
    private static final String TAG="MyService";
    private static final String ID="channel_1";
    private static final String NAME="前台服务";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate ();
        Log.d (TAG,"onCreate");
        if(Build.VERSION.SDK_INT>=26){
            setForeground();
        }else{

        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy ();
        Log.d (TAG,"onDestroy");
        // 尝试重启这个service
        Intent intent = new Intent(getApplicationContext(), ForegroundService.class);
        startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG,"onStartCommand");
        // 当系统杀死这个service时，系统会尝试重新创建这个service
        return START_STICKY;
    }

    @TargetApi(26)
    private void setForeground(){
        NotificationManager manager=(NotificationManager)getSystemService (NOTIFICATION_SERVICE);
        NotificationChannel channel=new NotificationChannel(ID,NAME, NotificationManager.IMPORTANCE_HIGH);
        manager.createNotificationChannel (channel);
        Notification notification=new Notification.Builder (this,ID)
                .setChannelId(ID)
                .setContentTitle ("收到一条重要通知")
                .setContentText ("这是重要通知")
                .setSmallIcon (R.mipmap.ic_launcher)
                .setLargeIcon (BitmapFactory.decodeResource (getResources (),R.mipmap.ic_launcher))
                .build ();
        startForeground (1,notification);
    }
}
