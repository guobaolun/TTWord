package com.snowy.common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.snowy.common.BaseApplication;
import com.snowy.common.manager.PermissionManager;
import com.snowy.common.statusbar.StatusBarUtil;

public abstract class BaseActivity extends AppCompatActivity {

    protected boolean isRunning = false;

    protected PermissionManager mPermissionManager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }

        mPermissionManager = new PermissionManager();
        BaseApplication application = (BaseApplication) getApplication();
        application.addActivity(this);

        setContentView(onContentView());

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication application = (BaseApplication) getApplication();
        application.removeActivity(this);

    }

    /**
     * 用户权限 申请 的回调方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionManager.onActivityResult(this, requestCode, resultCode, data);
    }


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }


    public void finishActivity(Class<?> cls) {
        BaseApplication application = (BaseApplication) getApplication();
        application.finishActivity(cls);
    }

//    public void createProgressDialog() {
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(this);
//        }
//    }

//    public void showProgressDialog() {
//        showProgressDialog("加载中...");
//    }
//
//    public void showProgressDialog(String text) {
//        createProgressDialog();
//        if (!progressDialog.isShowing()) {
//            progressDialog.setText(text);
//            progressDialog.show();
//        }
//    }
//
//    public void dismissProgressDialog() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
//    }
//
//
//    public void setDialogDispatchListener(ProgressDialog.OnDispatchListener listener) {
//        createProgressDialog();
//        progressDialog.setDispatchListener(listener);
//    }

    public View getContentView() {
        return ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
    }


    public BaseActivity getBaseActivity() {
        return this;
    }


    public Context getContext() {
        return getBaseContext();
    }

    public Activity getActivity() {
        return this;
    }

    /**
     * 初始化ui
     */
    protected abstract void initView();

    protected abstract int onContentView();


}
