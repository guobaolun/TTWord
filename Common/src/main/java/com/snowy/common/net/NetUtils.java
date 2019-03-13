package com.snowy.common.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetUtils {

    public static final String NO_NET = "无网络";
    public static final String WIFI = "WIFI";
    public static final String WAP = "WAP";
    public static final String NET = "NET";

    public static final String NO_NET_TEXT = "无网络状态,请检查网络连接";

    public static final String LOAD_FAILURE = "网络连接失败";


    /**
     * 判断是否有网络连接
     *
     * @param context context
     * @return true 有,false 无
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context context
     * @return true 可用,false 不可用
     */
    public boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {

                NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isAvailable();
                }
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context context
     * @return true 可用,false 不可用
     */
    public boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mMobileNetworkInfo != null) {
                    return mMobileNetworkInfo.isAvailable();
                }
            }
        }
        return false;
    }


    /**
     * 检查网络类型
     *
     * @param context context
     * @return 返回值     -1：没有网络      1：WIFI网络     2：wap网络      3：net网络
     */
    public static String checkNetType(Context context) {
        String netType = NO_NET;
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnectivityManager != null) {
            NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                return netType;
            }
            int nType = networkInfo.getType();
            if (nType == ConnectivityManager.TYPE_MOBILE) {
                if ("cmnet".equals(networkInfo.getExtraInfo().toLowerCase())) {
                    netType = NET;
                } else {
                    netType = WAP;
                }
            } else if (nType == ConnectivityManager.TYPE_WIFI) {
                netType = WIFI;
            }
        }
        return netType;
    }


}
