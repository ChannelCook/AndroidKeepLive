package com.lqk.lqklive.entity;


import android.widget.RemoteViews;

import com.lqk.lqklive.R;

import java.util.Random;

/**
 * 通知参数
 * @author lqk
 */
public class NotificationConfig {
    public static int serviceId = new Random().nextInt(Integer.MAX_VALUE);
    public static String channelId = "com.jdcloud.mt.aen";
    public static String channelName = "aen";
    public static String title = "手机盒子";
    public static String content = "aen is running";

    public static int smallIcon = R.mipmap.ic_launcher;

    public static RemoteViews bigRemoteViews;
    public static RemoteViews remoteViews;
}
