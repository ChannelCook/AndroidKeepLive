package com.lqk.lqklive.onepix;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.lqk.lqklive.KeepLive;

public class OnePixActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0,0);
        getWindow().setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.x = 0;
        layoutParams.y = 0;
        layoutParams.width = 1;
        layoutParams.height = 1;
        KeepLive.setOnePix(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (KeepLive.isScreenOn(this)){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeepLive.finishOnePix();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
