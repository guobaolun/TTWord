package com.snowy.common.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {

//    private static String[] permissions = {
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA,
//            Manifest.permission.RECORD_AUDIO,
//
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
//    };

    private static final String PERMISSIONS_DATA = "permissions";
    private static final int REQUEST_CODE = 1201;
    private static final int RESULT_CODE = 1202;


    private AlertDialog dialog;
    //    private String[] mPermissions;
    private PermissionCallback mCallback;


    public void checkPermission(Activity activity, String[] permissions, PermissionCallback mCallback) {
        this.mCallback = mCallback;

        createDialog(activity, permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int position = 0;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    startRequestPermission(activity, permissions);
                    return;
                } else {
                    if (position == permissions.length) {
                        mCallback.grant();
                    }
                }
                position++;
            }
        } else {
            mCallback.grant();
        }

    }


    private void createDialog(final Activity activity, final String[] permissions) {
        dialog = new AlertDialog.Builder(activity)
                .setTitle("权限不可用")
                .setMessage("请在-应用设置-权限允许")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting(activity, permissions);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            int position = 0;
                            for (String permission : permissions) {
                                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                                    mCallback.refuse(permission);
                                    return;
                                } else {
                                    if (position == permissions.length) {
                                        mCallback.grant();
                                    }
                                }
                                position++;
                            }
                        } else {
                            mCallback.grant();
                        }


                    }
                }).setCancelable(false).create();
    }


    // 开始提交请求权限
    private void startRequestPermission(Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE);
    }


    public void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {


        if (requestCode != REQUEST_CODE) {
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int position = 0;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    startRequestPermission(activity, permissions);
                    return;
                } else {
                    if (position == permissions.length) {
                        Toast.makeText(activity, grantResults.toString(), Toast.LENGTH_LONG).show();
                        System.out.println("--------grantResults.toString()--------" + grantResults.toString());
                        showDialog();
                    }
                }
                position++;
            }
        } else {
            mCallback.grant();
        }


    }

    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_CODE && resultCode != Activity.RESULT_OK) {
            return;
        }

        String[] permissions = data.getStringArrayExtra(PERMISSIONS_DATA);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int position = 0;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    showDialog();
                    return;
                } else {
                    if (position == permissions.length) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Toast.makeText(context, "权限获取成功", Toast.LENGTH_SHORT).show();
                        mCallback.grant();
                    }
                }
                position++;
            }
        } else {
            mCallback.grant();
        }


    }


    // 跳转到当前应用的设置界面
    private void goToAppSetting(Activity activity, String[] permissions) {
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(uri);
        intent.putExtra(PERMISSIONS_DATA, permissions);
        activity.startActivityForResult(intent, RESULT_CODE);
    }


    private void showDialog() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }


    public interface PermissionCallback {
        void grant();

        void refuse(String permission);
    }

}
