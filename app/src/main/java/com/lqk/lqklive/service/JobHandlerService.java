package com.lqk.lqklive.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.lqk.lqklive.BaseApplication;
import com.lqk.lqklive.KeepLive;
import com.lqk.lqklive.ext.ConfigExt;
import com.lqk.lqklive.ext.NotificationExt;

/**
 * jobScheduler + Foreground
 * 定时service绑定前台服务提升oom_adj
 * @author lqk
 */
public class JobHandlerService extends JobService {
//    private static final long TIME = JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS;
    private static final long TIME = 5000L;
    private JobScheduler jobScheduler;

    private int jobId = 4399;
    @Override
    public void onCreate() {
        super.onCreate();
        registerJob();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 给当前的jobService创建一个前台通知 大幅度提升adj
        NotificationExt.setNotification(this);
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        // true，那么系统假设任务是需要一些时间并且是需要在我们自己应用执行的
        // false，该系统假定任何任务运行不需要很长时间并且到方法返回时已经完成
//        Toast.makeText(this, "jobservice living", Toast.LENGTH_SHORT).show();
        Log.e("jobscheduler", "onStartJob");
        return false;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        jobScheduler.cancel(jobId);
        ConfigExt.saveJobId(-1);
        super.onDestroy();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // 有且仅有onStartJob返回值为true时，才会调用onStopJob来销毁job
        // 返回false来销毁这个工作
        return false;
    }

    private void registerJob(){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
            Toast.makeText(this, "当前版本小于21，无法开启jobscheduler", Toast.LENGTH_SHORT).show();
            return;
        }
        jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        //去除当前的jobscheduler的jobid
        jobId = ConfigExt.getJobId();
        if (jobId != -1){
            jobScheduler.cancel(jobId);
        }
        jobId = KeepLive.getId();
        ConfigExt.saveJobId(jobId);
        ComponentName componentName = new ComponentName(BaseApplication.getInstance(), JobHandlerService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobId, componentName);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                //设置满足触发条件后最少延迟一段时间后再触发
                //设置一段时间之后，即使没有任何触发也执行
                //设置job失败后的重试时间间隔
                builder.setMinimumLatency(TIME)
                        .setOverrideDeadline(TIME)
                        .setBackoffCriteria(TIME, JobInfo.BACKOFF_POLICY_LINEAR);
            }else{
                // 周期性执行job执行时间间隔
                //设置作业是否在设备空闲时才会被执行
                builder.setPeriodic(TIME)
                        .setRequiresDeviceIdle(false);
            }
            //需要wifi执行
            //是否需要充电线，只有插入充电线后才开始执行job
            //设置触发条件是否重启手机后仍有效
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setRequiresCharging(false)
                    .setPersisted(true);
        }catch (Exception e){
            Toast.makeText(this, "当前版本小于21，无法开启jobscheduler", Toast.LENGTH_SHORT).show();
        }
        jobScheduler.schedule(builder.build());
    }
}
