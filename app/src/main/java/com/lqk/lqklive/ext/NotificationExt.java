package com.lqk.lqklive.ext;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.lqk.lqklive.MainActivity;
import com.lqk.lqklive.entity.Constant;
import com.lqk.lqklive.entity.NotificationConfig;

import java.util.HashMap;
import java.util.Map;

public class NotificationExt{
    private static final Map<String, Boolean> mHasNotification = new HashMap<>();

    public static void setNotification(Service service){
        String tag = Constant.KEEP_LIVE_TAG + System.identityHashCode(service);
        Boolean hasNotification = mHasNotification.get(tag);

        if (hasNotification == null || !hasNotification){
            mHasNotification.put(tag, true);
            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(service);
            Notification notification = getNotification(service.getApplicationContext());
            // 更新Notification
            managerCompat.notify(NotificationConfig.serviceId, notification);
            // 设置前台服务Notification
            service.startForeground(NotificationConfig.serviceId, notification);
        }
    }

    private static Notification getNotification(Context context){
        // 通过广播监听 通知被关闭
//        Intent cancelIntent = new Intent(context, NotificationBroadcastReceiver.class);
//        PendingIntent cancelPI = PendingIntent.getBroadcast(context,2,
//                cancelIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent = new Intent(context, MainActivity.class);
        NotificationManagerCompat compat = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, NotificationConfig.channelId)
                .setContentTitle(NotificationConfig.title)
                .setContentText(NotificationConfig.content)
                .setSmallIcon(NotificationConfig.smallIcon)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setContentIntent(PendingIntent.getActivity(
                        context.getApplicationContext(),
                        1, intent,
                        PendingIntent.FLAG_CANCEL_CURRENT))
                .setAutoCancel(false)
//                .setDeleteIntent(cancelPI)
//                .setContent()
//                .setCustomBigContentView()
                .build();

        if (VERSION.SDK_INT >= VERSION_CODES.O &&
                compat.getNotificationChannel(NotificationConfig.channelId) == null){
            NotificationChannel notificationChannel = new NotificationChannel(
                    NotificationConfig.channelId,
                    NotificationConfig.channelName,
                    NotificationManager.IMPORTANCE_NONE);
            compat.createNotificationChannel(notificationChannel);
        }

        return notification;
    }

}
