package com.snowy.common;

import android.app.Activity;
import android.app.Application;

import com.snowy.common.manager.CrashHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guobaolun
 */
public abstract class BaseApplication extends Application {

    private List<WeakReference<Activity>> mActivityList;

    @Override
    public void onCreate() {
        super.onCreate();
        mActivityList = new ArrayList<>();
        CrashHandler.getInstance().init(getApplicationContext());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //低内存时执行
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        //程序在进行内存清理时执行
    }

    /**
     * 添加Activity到容器中
     */
    public void addActivity(Activity activity) {
        mActivityList.add(new WeakReference<>(activity));
    }

    /**
     * 遍历所有Activity并finish
     */
    public void exit() {

        for (int i = 0; i < mActivityList.size(); i++) {
            WeakReference<Activity> reference = mActivityList.get(i);
            Activity activity = reference.get();
            activity.finish();
            reference.clear();
        }
        mActivityList.clear();

//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 关闭其他activity
     */
    public void finishOtherActivity(Class<?> cls) {
        for (int i = 0; i < mActivityList.size(); i++) {
            WeakReference<Activity> reference = mActivityList.get(i);
            Activity activity = reference.get();
            if (!activity.getClass().equals(cls)) {
                activity.finish();
                reference.clear();
                mActivityList.remove(i);
            }
        }
    }

    /**
     * 关闭指定activity
     */
    public void finishActivity(Class<?> cls) {

        for (int i = 0; i < mActivityList.size(); i++) {
            WeakReference<Activity> reference = mActivityList.get(i);

            Activity activity = reference.get();
            if (activity.getClass().equals(cls)) {
                activity.finish();
                reference.clear();
                mActivityList.remove(i);
            }
        }

    }


    /**
     * 关闭指定activity
     */
    public void removeActivity(Activity act) {
        for (int i = 0; i < mActivityList.size(); i++) {
            WeakReference<Activity> reference = mActivityList.get(i);
            Activity activity = reference.get();
            if (activity.equals(act)) {
                reference.clear();
                mActivityList.remove(i);
            }
        }
    }

//    /**
//     * 判断容器中是否已经存在该activity
//     *
//     * @param cls class
//     * @return a
//     */
//    public boolean containsActivity(Class<?> cls) {
//
//        for (Activity activity : mList) {
//            if (activity.getClass().equals(cls)) {
//                return true;
//            }
//        }
//        return false;
//    }


}
