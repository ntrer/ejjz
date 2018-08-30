package com.shushang.aishangjia.application;

import android.app.Activity;
import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.shushang.AppContext;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.commonsdk.UMConfigure;

import java.util.Set;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

/**
 * Created by YD on 2018/7/10.
 */

public class MyApplication extends Application {
    private static MyApplication myApplication=null;
    private Set<Activity> allActivities;
    private int	mScreenWidth, mScreenHeight;// 屏幕尺寸
    @Override
    public void onCreate() {
        super.onCreate();
        myApplication=this;
        initOKHttp();
        //工具类初始化
        Utils.init(myApplication);
        AppContext.getInstance().init(myApplication);
//        CrashReport.initCrashReport(getApplicationContext(), "a0626d9293", false);
        Beta.autoCheckUpgrade=true;
        Bugly.init(getApplicationContext(), "a0626d9293", false);
        //字体图标初始化
        Iconify
                .with(new FontAwesomeModule())
                .with(new IoniconsModule());
        UMConfigure.init(this, "5b685121f43e483ac200041d", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    public static MyApplication getInstance(){
        return myApplication;
    }

    private void initOKHttp() {
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());
    }

    /**
     * 退出app
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}
