package com.lqk.lqklive.callback;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lqk.lqklive.onepix.OnePixActivity;
import com.lqk.lqklive.receiver.ServiceReceiver;

import java.lang.ref.WeakReference;

public class AppBackgroundCallback implements Application.ActivityLifecycleCallbacks {


    private WeakReference<Context> mWeakContext;
    /**
     * 前台Activity数量
     */
    private int mFrontActivityCount = 0;
    /**
     * 当Activity数量大于0的时候，标识是否已经发出前后台广播
     */
    private boolean mIsSend = false;
    /**
     * 是否是第一次发送前后台广播
     */
    private boolean mIsFirst = true;

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        if (!(activity instanceof OnePixActivity)){
            mWeakContext = new WeakReference(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!(activity instanceof OnePixActivity)) {
            mFrontActivityCount++;
            post();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (!(activity instanceof OnePixActivity)) {
            mFrontActivityCount--;
            post();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private void post(){
        if (mWeakContext != null){
            if (mFrontActivityCount == 0){
                mIsSend = false;
                Intent intent = new Intent();
                intent.setAction(ServiceReceiver.LIVE_BACKGROUND);
                mWeakContext.get().sendBroadcast(intent);
            }else {
                if (!mIsSend){
                    mIsSend = true;
                    Intent intent = new Intent();
                    intent.setAction(ServiceReceiver.LIVE_FOREGROUND);
                    mWeakContext.get().sendBroadcast(intent);
                }
            }
        }
    }

}
