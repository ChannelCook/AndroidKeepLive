package com.lqk.lqklive.ext;

import android.content.Context;

import com.lqk.lqklive.KeepLive;
import com.lqk.lqklive.entity.Constant;
import com.lqk.lqklive.util.SpUtil;

public class ConfigExt {
    public static void saveJobId(int jobId){
        SpUtil.getInstance().putInt(Constant.KEEP_LIVE_TAG, jobId);
    }

    public static int getJobId(){
        return SpUtil.getInstance().getInt(Constant.KEEP_LIVE_TAG, KeepLive.getId());
    }
}
