package com.lqk.lqklive;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.lqk.lqklive.ext.BatteryOptimizations;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBatteryOptimizations();
    }

    private void initBatteryOptimizations(){
        if (!BatteryOptimizations.isIgnoringBatteryOptimizations()){
            BatteryOptimizations.requestIgnoreBatteryOptimizations(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BatteryOptimizations.PERMISSION_COMPANY_SETTING){
            BatteryOptimizations.checkCompanySetting(this);
        }
    }
}
